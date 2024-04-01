package a.a.a.f.b;

import a.a.a.Core;
import a.a.a.e.c.Hologram1;
import a.a.a.f.GameMap;
import a.a.a.f.GameState;
import a.a.a.f.a.MapRewardType;
import a.a.a.f.a.MapTeam;
import a.a.a.f.a.a.MapDataException;
import a.a.a.l.GamePlayer;
import a.a.a.q.BlockUtil1;
import a.a.a.q.Util3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DetonateTheBombGame extends GameMap {
   private List ea = new ArrayList();
   private Map eb = new HashMap();
   private List ec = new ArrayList();
   private int ed;
   private int ee;
   private int ef;

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "DTB";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "DTB");
      Core.e().cn();
      this.cn();
   }

   public void ax() throws MapDataException {
      ConfigurationSection configSection = this.bl.aS().bh().getConfigurationSection("config.timings");
      this.ed = configSection.getInt("arm") * 4;
      this.ee = configSection.getInt("defuse") * 4;
      this.ef = configSection.getInt("explode") * 4;
      ConfigurationSection bombSection = this.bl.aS().bh().getConfigurationSection("bombs");
      Iterator var3 = bombSection.getKeys(false).iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         ConfigurationSection section = bombSection.getConfigurationSection(key);
         String name = section.getString("name");
         String teamName = section.getString("team");
         if (!this.bl.aS().bv().containsKey(teamName)) {
            throw new MapDataException("Could not find team " + teamName);
         }

         String[] coords = section.getString("block").replace(" ", "").split(",");
         Block block = (new Location(this.bl.getWorld(), Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2]))).getBlock();
         if (!block.getType().equals(Material.TNT)) {
            block.setType(Material.TNT);
         }

         BlockState s = block.getState();
         s.setType(Material.TNT);
         s.update(true, false);
         EntityTNTPrimed primedTNT = new EntityTNTPrimed(((CraftWorld)this.bl.getWorld()).getHandle());
         primedTNT.setLocation((double)block.getX(), (double)block.getY(), (double)block.getZ(), 0.0F, 0.0F);
         primedTNT.fuseTicks = 5000;
         Hologram1 hologram = new Hologram1(block.getLocation().add(0.5D, 2.0D, 0.5D));
         DetonatableBomb bomb = new DetonatableBomb(name, (MapTeam)this.bl.aS().bv().get(teamName), block, primedTNT, ((CraftWorld)this.bl.getWorld()).getHandle(), hologram);
         hologram.d(bomb.ca().bP() + bomb.getName());
         hologram.d(ChatColor.GREEN + "Bomb is defused");
         hologram.d(ChatColor.DARK_AQUA + "Fuse has " + ChatColor.GOLD + this.ef / 4 + ChatColor.DARK_AQUA + " seconds to explosion");
         hologram.O();
         bomb.k(this.ef);
         bomb.j(this.ed);
         this.ea.add(bomb);
      }

   }

   public void az() {
      Iterator var1 = Core.c().getPlayers().iterator();

      while(true) {
         GamePlayer gPlayer;
         Iterator var3;
         do {
            do {
               do {
                  if (!var1.hasNext()) {
                     var1 = this.ea.iterator();

                     while(true) {
                        label175:
                        while(true) {
                           DetonatableBomb bomb;
                           do {
                              if (!var1.hasNext()) {
                                 this.cn();
                                 return;
                              }

                              bomb = (DetonatableBomb)var1.next();
                              bomb.cv();
                           } while(bomb.cz().equals(BombState.dh));

                           GamePlayer gPlayer1;
                           if (bomb.cz().equals(BombState.dg) && bomb.cE() <= 0) {
                              bomb.a(BombState.dh);
                              var3 = Core.c().getPlayers().iterator();

                              while(var3.hasNext()) {
                                 gPlayer = (GamePlayer)var3.next();
                                 gPlayer.a(EnumParticle.EXPLOSION_HUGE, bomb.getBlock().getLocation(), 10.0F, 10);
                              }

                              Core.a(bomb.ca().bP() + bomb.getName() + ChatColor.DARK_AQUA + " exploded!", true, true);
                              ((CraftWorld)this.bl.getWorld()).getHandle().createExplosion(bomb.cG(), bomb.getBlock().getLocation().getX(), bomb.getBlock().getLocation().getY(), bomb.getBlock().getLocation().getZ(), 12.0F, true, true);
                              bomb.cw();
                              bomb.getBlock().setType(Material.AIR);
                              BlockUtil1.a(bomb.getBlock().getLocation(), Material.AIR, 0, true);
                              bomb.cI().setLine(0, (String)bomb.cI().N().get(0));
                              bomb.cI().setLine(1, ChatColor.DARK_RED + "Bomb has exploded");
                              if (bomb.cI().N().size() > 2) {
                                 bomb.cI().d(2);
                              }

                              var3 = bomb.cD().iterator();

                              while(var3.hasNext()) {
                                 gPlayer = (GamePlayer)var3.next();
                                 gPlayer.getPlayer().setExp(0.0F);
                                 this.eb.remove(gPlayer);
                              }
                           } else {
                              if (bomb.cx() > 0) {
                                 if ((double)System.currentTimeMillis() - bomb.cB() > 1000.0D) {
                                    bomb.cC();
                                    bomb.j(bomb.cz().equals(BombState.dg) ? this.ee : this.ed);
                                 } else {
                                    Material itemRequired = bomb.cz().equals(BombState.df) ? Material.BLAZE_POWDER : Material.SHEARS;
                                    Iterator var10 = bomb.cD().iterator();

                                    label168:
                                    while(true) {
                                       GamePlayer gPlayer2;
                                       do {
                                          if (!var10.hasNext()) {
                                             if (bomb.cD().isEmpty()) {
                                                bomb.j(bomb.cz().equals(BombState.dg) ? this.ee : this.ed);
                                                continue label175;
                                             }

                                             for(int i = 0; i < bomb.cD().size(); ++i) {
                                                bomb.cy();
                                             }

                                             double seconds = (double)bomb.cx() / 4.0D;
                                             Iterator var6 = Core.c().getPlayers().iterator();

                                             GamePlayer gPlayer3;
                                             while(var6.hasNext()) {
                                                gPlayer = (GamePlayer)var6.next();
                                                gPlayer.a(EnumParticle.FIREWORKS_SPARK, bomb.getBlock().getLocation(), 1.0F, 1);
                                             }

                                             var6 = bomb.cD().iterator();

                                             while(var6.hasNext()) {
                                                gPlayer = (GamePlayer)var6.next();
                                                gPlayer.getPlayer().setExp((float)bomb.cx() / (float)(bomb.cz().equals(BombState.dg) ? this.ee : this.ed));
                                             }
                                             break label168;
                                          }

                                          gPlayer = (GamePlayer)var10.next();
                                       } while(gPlayer.getPlayer().getItemInHand() != null && gPlayer.getPlayer().getItemInHand().getType().equals(itemRequired));

                                       bomb.s(gPlayer);
                                    }
                                 }
                              } else {
                                 for(var3 = bomb.cD().iterator(); var3.hasNext(); this.eb.remove(gPlayer)) {
                                    gPlayer = (GamePlayer)var3.next();
                                    gPlayer.getPlayer().setExp(0.0F);
                                    if (!this.ec.contains(gPlayer.getPlayer().getName())) {
                                       this.a(gPlayer, MapRewardType.cB);
                                       this.ec.add(gPlayer.getPlayer().getName());
                                    }
                                 }

                                 label133:
                                 switch(bomb.cz()) {
                                 case df:
                                    bomb.a(BombState.dg);
                                    bomb.j(this.ee);
                                    bomb.l(0);
                                    bomb.cC();
                                    bomb.cI().setLine(1, ChatColor.GOLD + "Fuse is lit");
                                    var3 = this.players.iterator();

                                    while(true) {
                                       if (!var3.hasNext()) {
                                          break label133;
                                       }

                                       gPlayer = (GamePlayer)var3.next();
                                       gPlayer.a(bomb.ca().bP() + bomb.getName(), ChatColor.DARK_AQUA + " has been" + ChatColor.GOLD + " armed!", 0.5D, 3.0D, 0.5D);
                                       if (gPlayer.ca().equals(bomb.ca())) {
                                          gPlayer.getPlayer().sendMessage(ChatColor.DARK_RED + ChatColor.MAGIC.toString() + "!!! " + ChatColor.RESET + ChatColor.DARK_RED + ChatColor.BOLD + "Your bomb is lit, Defuse it NOW!" + ChatColor.MAGIC.toString() + " !!!");
                                       }
                                    }
                                 case dg:
                                    bomb.a(BombState.df);
                                    bomb.j(this.ed);
                                    bomb.cw();
                                    bomb.cC();
                                    bomb.cI().setLine(1, ChatColor.GREEN + "Bomb is defused");
                                    var3 = this.players.iterator();

                                    while(var3.hasNext()) {
                                       gPlayer = (GamePlayer)var3.next();
                                       gPlayer.a(bomb.ca().bP() + bomb.getName(), ChatColor.DARK_AQUA + " has been" + ChatColor.GOLD + " defused!", 0.5D, 3.0D, 0.5D);
                                       if (gPlayer.ca().equals(bomb.ca())) {
                                          gPlayer.getPlayer().sendMessage(ChatColor.GREEN + ChatColor.MAGIC.toString() + "!!! " + ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + "Your bomb has been defused!" + ChatColor.MAGIC.toString() + " !!!");
                                       }
                                    }
                                 }
                              }

                              if (bomb.cz().equals(BombState.dg)) {
                                 bomb.cF();
                                 if ((double)bomb.cE() / 4.0D == Math.floor((double)bomb.cE() / 4.0D)) {
                                    bomb.cI().setLine(2, ChatColor.DARK_AQUA + "Fuse has " + ChatColor.GOLD + bomb.cE() / 4 + ChatColor.DARK_AQUA + " seconds to explosion");
                                 }

                                 var3 = this.players.iterator();

                                 while(var3.hasNext()) {
                                    gPlayer = (GamePlayer)var3.next();
                                    if (gPlayer.ca().equals(bomb.ca())) {
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  gPlayer = (GamePlayer)var1.next();
               } while(!gPlayer.dH());
            } while(!this.eb.containsKey(gPlayer));
         } while(System.currentTimeMillis() - (Long)this.eb.get(gPlayer) <= 1000L && gPlayer.getPlayer().getItemInHand() != null);

         var3 = this.ea.iterator();

         while(var3.hasNext()) {
            DetonatableBomb bomb = (DetonatableBomb)var3.next();
            bomb.s(gPlayer);
         }

         gPlayer.getPlayer().setExp(0.0F);
         this.eb.remove(gPlayer);
      }
   }

   public MapTeam aE() {
      Map<MapTeam, Integer> bombCount = new HashMap();
      Iterator var2 = this.bl.aS().bv().values().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         if (!bombCount.containsKey(team)) {
            bombCount.put(team, 0);
         }

         Iterator var4 = this.f(team).iterator();

         while(var4.hasNext()) {
            DetonatableBomb bomb = (DetonatableBomb)var4.next();
            if (!bomb.cz().equals(BombState.dh)) {
               bombCount.put(team, (Integer)bombCount.get(team) + bomb.cE());
            }
         }
      }

      MapTeam winningTeam = null;
      Iterator var7 = Util3.b((Map)bombCount).iterator();

      while(var7.hasNext()) {
         Entry<MapTeam, Integer> team = (Entry)var7.next();
         if (winningTeam == null) {
            winningTeam = (MapTeam)team.getKey();
         } else if (bombCount.get(winningTeam) == team.getValue()) {
            return null;
         }
      }

      return winningTeam;
   }

   @EventHandler
   public void a(PlayerInteractEvent e) {
      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      if (gPlayer.dH()) {
         if (e.getClickedBlock() != null) {
            DetonatableBomb bomb = this.g(e.getClickedBlock());
            if (bomb != null) {
               e.setCancelled(true);
               if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                  gPlayer.getPlayer().sendMessage(Core.a(ChatColor.RED + "Hold right click on the block!"));
               } else if (bomb.cz().equals(BombState.df) && bomb.ca().equals(gPlayer.ca()) && gPlayer.getPlayer().getItemInHand() != null && gPlayer.getPlayer().getItemInHand().getType().equals(Material.SHEARS)) {
                  gPlayer.getPlayer().sendMessage(Core.a("You cannot defuse the bomb when its not lit!"));
               } else if (bomb.cz().equals(BombState.df) && bomb.ca().equals(gPlayer.ca())) {
                  gPlayer.getPlayer().sendMessage(Core.a("You cannot light your own bomb!"));
               } else if (gPlayer.getPlayer().getItemInHand() != null && (!bomb.cz().equals(BombState.dg) || gPlayer.ca().equals(bomb.ca()) && gPlayer.getPlayer().getItemInHand().getType().equals(Material.SHEARS)) && (!bomb.cz().equals(BombState.df) || !gPlayer.ca().equals(bomb.ca()) && gPlayer.getPlayer().getItemInHand().getType().equals(Material.BLAZE_POWDER))) {
                  bomb.r(gPlayer);
                  bomb.cA();
                  this.eb.put(gPlayer, System.currentTimeMillis());
               } else {
                  bomb.s(gPlayer);
               }
            }
         }
      }
   }

   @EventHandler
   public void a(PlayerInteractEntityEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (gPlayer.dH()) {
         if (e.getRightClicked() != null) {
            DetonatableBomb bomb = this.b(e.getRightClicked());
            if (bomb != null) {
               e.setCancelled(true);
               if (gPlayer.getPlayer().getItemInHand() != null && (!bomb.cz().equals(BombState.dg) || gPlayer.ca().equals(bomb.ca()) && gPlayer.getPlayer().getItemInHand().getType().equals(Material.SHEARS)) && (!bomb.cz().equals(BombState.df) || !gPlayer.ca().equals(bomb.ca()) && gPlayer.getPlayer().getItemInHand().getType().equals(Material.BLAZE_POWDER))) {
                  bomb.r(gPlayer);
                  bomb.cA();
                  this.eb.put(gPlayer, System.currentTimeMillis());
               } else {
                  bomb.s(gPlayer);
               }
            }
         }
      }
   }

   @EventHandler
   public void a(ExplosionPrimeEvent e) {
      if (e.getEntityType().equals(EntityType.PRIMED_TNT) && e.getEntity().getWorld() == this.bl.getWorld()) {
         e.setCancelled(true);
         e.setFire(false);
      }

   }

   @EventHandler
   public void a(BlockPlaceEvent e) {
      Block TNT = null;
      BlockFace[] var3 = BlockFace.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BlockFace face = var3[var5];
         Block block = e.getBlock().getRelative(face);
         if (block != null && block.getType().equals(Material.TNT)) {
            TNT = block;
         }
      }

      String name = e.getBlockPlaced().getType().name();
      if (name.contains("REDSTONE") || name.contains("BUTTON") || name.contains("LEVER") || name.contains("PRESSURE")) {
         Iterator var9 = this.ea.iterator();

         while(var9.hasNext()) {
            DetonatableBomb bomb = (DetonatableBomb)var9.next();
            if (bomb.getBlock().getLocation().distance(e.getBlock().getLocation()) < 4.0D) {
               e.setCancelled(true);
               return;
            }
         }
      }

      if (TNT != null) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(BlockIgniteEvent e) {
      Iterator var2 = this.ea.iterator();
      if (var2.hasNext()) {
         DetonatableBomb bomb = (DetonatableBomb)var2.next();
      }
   }

   @EventHandler
   public void a(BlockBurnEvent e) {
      if (e.getBlock().getType().equals(Material.TNT)) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(BlockPhysicsEvent e) {
      if (e.getBlock().getType().equals(Material.TNT)) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(BlockRedstoneEvent e) {
      e.setNewCurrent(0);
   }

   @EventHandler
   public void a(ProjectileLaunchEvent e) {
      if (e.getEntity().getFireTicks() > 0) {
         e.getEntity().setFireTicks(0);
      }

   }

   @EventHandler
   public void a(PlayerDeathEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getEntity().getName());
      if (gPlayer.dH() && this.eb.containsKey(gPlayer)) {
         this.eb.put(gPlayer, 0L);
         Iterator var3 = this.ea.iterator();

         while(var3.hasNext()) {
            DetonatableBomb bomb = (DetonatableBomb)var3.next();
            if (bomb.cD().contains(gPlayer)) {
               bomb.s(gPlayer);
            }
         }
      }

   }

   @EventHandler
   public void a(EntityDamageEvent e) {
      if (e.getCause().equals(DamageCause.BLOCK_EXPLOSION) || e.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
         e.setCancelled(true);
      }

   }

   private DetonatableBomb g(Block block) {
      Iterator var2 = this.ea.iterator();

      DetonatableBomb bomb;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         bomb = (DetonatableBomb)var2.next();
      } while(!bomb.getBlock().equals(block));

      return bomb;
   }

   private DetonatableBomb b(Entity entity) {
      Iterator var2 = this.ea.iterator();

      DetonatableBomb bomb;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         bomb = (DetonatableBomb)var2.next();
      } while(!bomb.cH().contains(entity.getUniqueId()));

      return bomb;
   }

   private List f(MapTeam team) {
      List<DetonatableBomb> teamBombs = new ArrayList();
      Iterator var3 = this.ea.iterator();

      while(var3.hasNext()) {
         DetonatableBomb bomb = (DetonatableBomb)var3.next();
         if (bomb.ca().equals(team)) {
            teamBombs.add(bomb);
         }
      }

      return teamBombs;
   }

   private void cn() {
      Core.e().cM();
      Iterator var1 = this.ea.iterator();

      while(var1.hasNext()) {
         DetonatableBomb bomb = (DetonatableBomb)var1.next();
         if (bomb.cz().equals(BombState.dh)) {
            Core.e().a(ChatColor.RED + " ✘ " + bomb.ca().bP() + bomb.getName(), -1);
         } else {
            Core.e().a(ChatColor.GREEN + " ✔ " + bomb.ca().bP() + bomb.getName(), bomb.cE() / 4);
         }
      }

      this.co();
   }

   private void co() {
      Map<MapTeam, Integer> bombCount = new HashMap();
      Iterator var2 = this.bl.aS().bv().values().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         if (!bombCount.containsKey(team)) {
            bombCount.put(team, 0);
         }

         Iterator var4 = this.f(team).iterator();

         while(var4.hasNext()) {
            DetonatableBomb bomb = (DetonatableBomb)var4.next();
            if (!bomb.cz().equals(BombState.dh)) {
               bombCount.put(team, (Integer)bombCount.get(team) + 1);
            }
         }
      }

      var2 = bombCount.values().iterator();

      while(var2.hasNext()) {
         Integer count = (Integer)var2.next();
         if (count == 0) {
            this.bl.a(GameState.bC);
            break;
         }
      }

   }
}
