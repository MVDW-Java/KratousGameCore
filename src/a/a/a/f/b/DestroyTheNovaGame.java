package a.a.a.f.b;

import a.a.a.Core;
import a.a.a.a.AchievementType;
import a.a.a.f.GameMap;
import a.a.a.f.GameState;
import a.a.a.f.a.MapRewardType;
import a.a.a.f.a.MapTeam;
import a.a.a.f.a.a.MapDataException;
import a.a.a.f.c.ScoreboardHandler;
import a.a.a.j.ObjectiveType;
import a.a.a.l.GamePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DestroyTheNovaGame extends GameMap {
   private Map dM = new HashMap();
   private int bq = 4;
   private Map dN = new HashMap();

   public void ax() throws Exception {
      YamlConfiguration yml = this.bl.aS().bh();
      ConfigurationSection novaSection = yml.getConfigurationSection("novas");
      Iterator var3 = novaSection.getKeys(false).iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         ConfigurationSection section = novaSection.getConfigurationSection(key);
         String name = section.getString("name");
         int hits = section.getInt("hits", 150);
         String teamName = section.getString("team");
         if (!this.bl.aS().bv().containsKey(teamName)) {
            throw new MapDataException("Cannot find team " + teamName);
         }

         MapTeam team = (MapTeam)this.bl.aS().bv().get(teamName);
         if (!this.dM.containsKey(team)) {
            this.dM.put(team, new ArrayList());
         }

         String[] locationSplit = section.getString("location").replace(" ", "").split(",");
         Location location = new Location(this.bl.getWorld(), Double.valueOf(locationSplit[0]), Double.valueOf(locationSplit[1]), Double.valueOf(locationSplit[2]));
         ((List)this.dM.get(team)).add(new DestroyableNova(name, location, team, hits));
      }

   }

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "DTN";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "Nova");
      Core.e().cn();
      this.cn();
      Iterator var1 = this.dM.keySet().iterator();

      while(var1.hasNext()) {
         MapTeam t = (MapTeam)var1.next();
         Iterator var3 = ((List)this.dM.get(t)).iterator();

         while(var3.hasNext()) {
            DestroyableNova n = (DestroyableNova)var3.next();
            n.a(this.bl.getWorld().spawnEntity(n.getLocation(), EntityType.ENDER_CRYSTAL));
         }
      }

   }

   public MapTeam aE() {
      Map<MapTeam, Integer> percents = new HashMap();
      Iterator var2 = this.dM.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam t = (MapTeam)var2.next();
         if (!percents.containsKey(t)) {
            percents.put(t, 0);
         }

         Iterator var4 = ((List)this.dM.get(t)).iterator();

         while(var4.hasNext()) {
            DestroyableNova n = (DestroyableNova)var4.next();
            percents.put(t, (Integer)percents.get(t) + n.cs());
         }
      }

      MapTeam winner = (MapTeam)percents.keySet().toArray()[0];
      Iterator var7 = percents.keySet().iterator();

      while(var7.hasNext()) {
         MapTeam team = (MapTeam)var7.next();
         if (!team.equals(winner)) {
            if (((Integer)percents.get(team)).equals(percents.get(winner))) {
               return null;
            }

            if ((Integer)percents.get(team) > (Integer)percents.get(winner)) {
               winner = team;
            }
         }
      }

      return winner;
   }

   public void az() {
      if (this.bq > 0) {
         --this.bq;
      } else {
         this.dN.clear();
         Iterator var1 = this.dM.keySet().iterator();

         label41:
         while(var1.hasNext()) {
            MapTeam t = (MapTeam)var1.next();
            Iterator var3 = ((List)this.dM.get(t)).iterator();

            while(true) {
               while(true) {
                  DestroyableNova nova;
                  do {
                     if (!var3.hasNext()) {
                        continue label41;
                     }

                     nova = (DestroyableNova)var3.next();
                  } while(!nova.isAlive());

                  if (nova.getEntity() == null) {
                     nova.a(this.bl.getWorld().spawnEntity(nova.getLocation(), EntityType.ENDER_CRYSTAL));
                  } else if (nova.getEntity().isDead() || !nova.getEntity().isValid()) {
                     nova.getEntity().remove();
                     nova.a(this.bl.getWorld().spawnEntity(nova.getLocation(), EntityType.ENDER_CRYSTAL));
                  }
               }
            }
         }

         this.bq = 4;
      }
   }

   @EventHandler
   public void a(EntityDamageByEntityEvent e) {
      if (e.getDamager() instanceof EnderCrystal) {
         e.setCancelled(true);
      } else {
         EnderCrystal crystal;
         GamePlayer other;
         if (e.getDamager() instanceof Player) {
            GamePlayer gPlayer = Core.c().r(e.getDamager().getName());
            if (!(e.getEntity() instanceof EnderCrystal)) {
               return;
            }

            e.setCancelled(true);
            if (!gPlayer.dH()) {
               return;
            }

            crystal = (EnderCrystal)e.getEntity();
            DestroyableNova nova = this.a(crystal);
            if (nova == null) {
               return;
            }

            if (gPlayer.ca().equals(nova.ca())) {
               gPlayer.getPlayer().sendMessage(Core.a("This is your own team's nova!"));
               return;
            }

            if (gPlayer.getPlayer().getItemInHand() == null || !gPlayer.getPlayer().getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
               gPlayer.getPlayer().sendMessage(Core.a("You can only destroy the nova with a diamond pick!"));
               return;
            }

            if (!this.dN.containsKey(gPlayer)) {
               this.dN.put(gPlayer, 0);
            } else if ((Integer)this.dN.get(gPlayer) >= 10) {
               return;
            }

            nova.cu();
            this.dN.put(gPlayer, (Integer)this.dN.get(gPlayer) + 1);
            if (nova.ct() && nova.isAlive()) {
               Core.a(gPlayer.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + " is destroying " + nova.ca().bP() + nova.getName() + ChatColor.DARK_AQUA + " " + nova.cs() + " hits left", true, true);
            } else if (!nova.isAlive()) {
               Core.g().a(gPlayer, ObjectiveType.fT, nova.getName(), 0);
               Core.a(gPlayer.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + " has destroyed " + nova.ca().bP() + nova.getName() + ChatColor.DARK_AQUA + "!", true, true);
               Iterator var10 = Core.c().getPlayers().iterator();

               while(var10.hasNext()) {
                  other = (GamePlayer)var10.next();
                  other.a(EnumParticle.EXPLOSION_HUGE, nova.getEntity().getLocation(), 1.0F, 1);
               }

               this.a(gPlayer, MapRewardType.cB);
               gPlayer.dC().a(AchievementType.H, 1);
               this.bl.getWorld().strikeLightningEffect(nova.getLocation());
               nova.getEntity().remove();
               this.co();
            }

            this.cn();
         } else if (e.getDamager() instanceof Arrow && e.getEntity() instanceof EnderCrystal) {
            e.setCancelled(true);
            Arrow arrow = (Arrow)e.getDamager();
            arrow.remove();
            crystal = (EnderCrystal)e.getEntity();
            Iterator var4 = crystal.getNearbyEntities(1.0D, 2.0D, 1.0D).iterator();

            while(var4.hasNext()) {
               Entity entity = (Entity)var4.next();
               if (entity instanceof Player) {
                  other = Core.c().r(entity.getName());
                  if (other.dH()) {
                     EntityDamageByEntityEvent entityDamageByEntity = new EntityDamageByEntityEvent(arrow, other.getPlayer(), DamageCause.PROJECTILE, e.getDamage());
                     Bukkit.getPluginManager().callEvent(entityDamageByEntity);
                     if (!entityDamageByEntity.isCancelled()) {
                        other.getPlayer().damage(e.getDamage(), arrow);
                        other.getPlayer().setLastDamageCause(entityDamageByEntity);
                        other.getPlayer().setVelocity(arrow.getVelocity().multiply(0.15D));
                        if (other.getPlayer().getHealth() <= 0.0D) {
                           Bukkit.getPluginManager().callEvent(new PlayerDeathEvent(other.getPlayer(), new ArrayList(), 0, other.getPlayer().getName() + " was shot by " + ((Player)arrow.getShooter()).getName()));
                        }
                     }
                  }
               }
            }
         }

      }
   }

   @EventHandler
   public void a(EntityDeathEvent e) {
      if (e.getEntityType().equals(EntityType.ENDER_CRYSTAL)) {
         EnderCrystal crystal = (EnderCrystal)e.getEntity();
         DestroyableNova nova = this.a(crystal);
         if (nova != null && nova.isAlive()) {
            nova.a(this.bl.getWorld().spawnEntity(nova.getLocation(), EntityType.ENDER_CRYSTAL));
         }
      }

   }

   @EventHandler
   public void a(EntityExplodeEvent e) {
      if (e.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
         e.setCancelled(true);
      }

   }

   private void cn() {
      int scoreCounter = 10;
      Core.e().cM();
      Iterator var2 = this.dM.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam t = (MapTeam)var2.next();
         Core.e().a(ScoreboardHandler.o(scoreCounter) + " ", scoreCounter);
         --scoreCounter;

         for(Iterator var4 = ((List)this.dM.get(t)).iterator(); var4.hasNext(); --scoreCounter) {
            DestroyableNova nova = (DestroyableNova)var4.next();
            if (nova.cs() == 0) {
               Core.e().a(ChatColor.RED + " ✘ " + ChatColor.GRAY + " - " + t.bP() + ChatColor.STRIKETHROUGH + nova.getName(), scoreCounter);
            } else {
               Core.e().a(ChatColor.GOLD + "" + nova.cs() + " Hits" + ChatColor.GRAY + " - " + t.bP() + nova.getName(), scoreCounter);
            }
         }
      }

      Core.e().cn();
   }

   private void co() {
      List<MapTeam> alive = new ArrayList();
      Iterator var2 = this.dM.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam t = (MapTeam)var2.next();
         Iterator var4 = ((List)this.dM.get(t)).iterator();

         while(var4.hasNext()) {
            DestroyableNova nova = (DestroyableNova)var4.next();
            if (nova.isAlive() && !alive.contains(nova.ca())) {
               alive.add(nova.ca());
            }
         }
      }

      if (alive.size() <= 1) {
         this.bl.a(GameState.bC);
      }

   }

   private DestroyableNova a(EnderCrystal crystal) {
      Iterator var2 = this.dM.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam t = (MapTeam)var2.next();
         Iterator var4 = ((List)this.dM.get(t)).iterator();

         while(var4.hasNext()) {
            DestroyableNova nova = (DestroyableNova)var4.next();
            if (nova.getEntity().equals(crystal)) {
               return nova;
            }
         }
      }

      return null;
   }
}
