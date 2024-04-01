package a.a.a.g;

import a.a.a.Core;
import a.a.a.a.AchievementType;
import a.a.a.f.GamePowerup;
import a.a.a.f.GameState;
import a.a.a.f.a.MapRegion;
import a.a.a.f.a.MapRewardType;
import a.a.a.f.a.MapTeam;
import a.a.a.f.a.b.RegionAccess;
import a.a.a.f.a.b.RegionAction;
import a.a.a.f.a.b.RegionRule;
import a.a.a.f.a.b.RegionTeam;
import a.a.a.g.a.PlayerKilledEvent;
import a.a.a.i.Logger;
//import a.a.a.k.TaskChain12;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import a.a.a.o.Shape;
import a.a.a.q.BlockUtil1;
import a.a.a.q.Util3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {
   @EventHandler
   public void a(PlayerLoginEvent e) {
      Core.c().q(e.getPlayer().getName());
      if (Core.a().getConfig().getInt("game.maxPlayers", 100) <= Bukkit.getOnlinePlayers().size() && !e.getPlayer().hasPermission("gamecore.maxbypass")) {
         e.setResult(Result.KICK_FULL);
         e.setKickMessage(ChatColor.RED + "This server is full. Buy VIP at store." + Core.a().getConfig().getString("game.websiteUrl") + " to enter full servers");
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(final PlayerJoinEvent e) {
      e.setJoinMessage((String)null);
      if (e.getPlayer().isOp() && !Util3.fy()) {
         e.getPlayer().sendMessage(ChatColor.RED + "GameCore is outdated - Update at http://gamecore.krato.us");
         e.getPlayer().sendMessage(ChatColor.RED + "You are running GameCore v" + ChatColor.GOLD + Core.a().getDescription().getVersion() + ChatColor.RED + ", the latest is GameCore v" + ChatColor.GOLD + Core.s);
      }

      /*TaskChain12.dx().a((TaskChain8)(new TaskChain5() {
         protected GamePlayerData cN() {
            GamePlayerData gPlayerData = GamePlayerLoader.a(e.getPlayer().getUniqueId());
            return gPlayerData == null ? null : gPlayerData;
         }

         // $FF: synthetic method
         protected Object run() {
            return this.cN();
         }
     // })).a((TaskChain8)(new TaskChain11() {
         protected void a(GamePlayerData data) {
            if (data == null) {
            }

            GamePlayer gPlayer = Core.c().a(e.getPlayer());
            gPlayer.dN();
            gPlayer.dE().eO();
            gPlayer.a(Core.a("Currently Playing: " + ChatColor.GOLD + Core.b().V().aS().bk()), Core.a(Core.a().getConfig().getString("game.websiteUrl")));
         }
     // })).execute();*/
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(PlayerQuitEvent e) {
      e.setQuitMessage((String)null);
      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      if ((double)System.currentTimeMillis() - gPlayer.em() < 10000.0D && gPlayer.dL() != null) {
         Core.g().a(gPlayer.dL(), gPlayer, "");
      }

      gPlayer.dE().eP();
      if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
         gPlayer.dV();
      }

      if (gPlayer.getPlayer().getVehicle() != null) {
         gPlayer.getPlayer().leaveVehicle();
      }

      if (gPlayer.getPlayer().getPassenger() != null) {
         gPlayer.getPlayer().getPassenger().leaveVehicle();
      }

      Core.b().V().aT().m(gPlayer);
      Core.c().m(gPlayer);
      Core.f().d(gPlayer);
      Core.l().h(gPlayer);
      Core.e().t(gPlayer);
      Core.e().u(gPlayer);
      Core.b().ap();
      if (gPlayer.dG()) {
         try {
            gPlayer.eq().unload();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(PlayerKickEvent e) {
   }

   @EventHandler
   public void a(final PlayerTeleportEvent e) {
      /*TaskChain12.dx().a((TaskChain8)(new TaskChain5() {
         protected GamePlayer cO() {
            GamePlayer gPlayer = Core.c().a(e.getPlayer());
            return gPlayer;
         }

         // $FF: synthetic method
         protected Object run() {
            return this.cO();
         }
      })).a((TaskChain8)(new TaskChain11() {
         protected void x(GamePlayer gPlayer) {
            if (!e.getFrom().getWorld().equals(e.getTo().getWorld())) {
               gPlayer.dW();
               if (gPlayer.getPlayer().isInsideVehicle()) {
                  gPlayer.getPlayer().leaveVehicle();
               }

               if (gPlayer.getPlayer().getPassenger() != null) {
                  gPlayer.getPlayer().getPassenger().leaveVehicle();
               }

               if (!e.getCause().equals(TeleportCause.COMMAND)) {
                  return;
               }

               if (gPlayer.dH()) {
                  Core.b().V().aT().m(gPlayer);
               }

               if (e.getTo().getWorld().equals(Core.d().cT().getWorld())) {
                  gPlayer.a(GamePlayerState.gY);
                  gPlayer.dN();
                  gPlayer.getPlayer().teleport(e.getTo());
               } else {
                  gPlayer.a(GamePlayerState.ha);
                  gPlayer.dN();
                  gPlayer.getPlayer().teleport(e.getTo());
               }

               Core.n().k(gPlayer);
            }

         }

         // $FF: synthetic method
         protected void a(Object var1) {
            this.x((GamePlayer)var1);
         }
      })).execute();*/
   }

   @EventHandler
   public void a(PlayerInteractAtEntityEvent e) {
      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      Entity entity = e.getRightClicked();
      if (gPlayer.dS() && entity.getType() == EntityType.ARMOR_STAND) {
         e.setCancelled(true);
      }

      if (gPlayer.dS() && entity.getType() == EntityType.ENDER_CRYSTAL) {
         e.setCancelled(true);
      }

      if (gPlayer.dI() && entity.getType() == EntityType.ENDER_CRYSTAL) {
         e.setCancelled(true);
      }

      if (gPlayer.dI() && entity.getType() == EntityType.ARMOR_STAND) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(PlayerInteractEvent e) {
      boolean rightClick = e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK;
      boolean var10000;
      if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) {
         var10000 = false;
      } else {
         var10000 = true;
      }

      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      if (gPlayer.dI() && (!e.getAction().equals(Action.LEFT_CLICK_BLOCK) || !e.getClickedBlock().getType().equals(Material.TRIPWIRE))) {
         e.setCancelled(true);
      }

      if (e.getPlayer().getItemInHand() != null) {
         if (gPlayer.dS() && gPlayer.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD) && rightClick) {
            Core.d().cW().a(gPlayer, e);
         }

         switch(e.getPlayer().getItemInHand().getType()) {
         case SLIME_BALL:
            if (!Core.b().V().aU().equals(GameState.bB) || !gPlayer.dH()) {
               if (rightClick) {
                  if (gPlayer.getPlayer().hasPermission("gamecore.chooseteam") && Core.b().V().aS().bv().size() > 1) {
                     gPlayer.getPlayer().openInventory(Util3.G(gPlayer));
                  } else {
                     Core.b().V().aT().l(gPlayer);
                  }

                  e.setCancelled(true);
               }

               return;
            }
            break;
         case MAGMA_CREAM:
            if (Core.b().V().aU().equals(GameState.bB) && gPlayer.dH()) {
               break;
            }

            if (rightClick) {
               if (e.getPlayer().getItemInHand().getDurability() == -1) {
                  return;
               }

               Core.b().V().aT().m(gPlayer);
               e.setCancelled(true);
            }

            return;
         case SKULL_ITEM:
            if (rightClick) {
               if (Core.b().V().aU().equals(GameState.bB) && gPlayer.dH()) {
                  break;
               }

               gPlayer.dW();
               if (gPlayer.dH()) {
                  Core.b().V().aT().m(gPlayer);
               }

               if (gPlayer.dI()) {
                  gPlayer.a(GamePlayerState.gY);
               } else {
                  gPlayer.a(GamePlayerState.ha);
                  gPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You are now spectating");
                  gPlayer.getPlayer().sendMessage(ChatColor.GREEN + "To stop spectating type /lobby");
               }

               gPlayer.g((MapTeam)null);
               gPlayer.dN();
               e.setCancelled(true);
            }

            return;
         case GOLD_BARDING:
            if (rightClick) {
               if (!gPlayer.dS()) {
                  break;
               }

               if (gPlayer.dZ() < 5) {
                  gPlayer.getPlayer().sendMessage(Core.a("Oops, You don't have enough nuggets!"));
                  return;
               }

               gPlayer.s(-5);
               ItemMeta meta = gPlayer.getPlayer().getItemInHand().getItemMeta();
               int shots = (int)Math.floor((double)(gPlayer.dZ() / 5));
               meta.setDisplayName(ChatColor.GOLD + "Gold Nugget Gun " + (shots == 0 ? ChatColor.RED + "0 Shots" : ChatColor.GREEN.toString() + shots + " Shot" + (shots == 1 ? "" : "s")) + ChatColor.GRAY + " (Right click to use)");
               gPlayer.getPlayer().getItemInHand().setItemMeta(meta);
               Item item = Core.d().cT().getWorld().dropItem(gPlayer.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), new ItemStack(Material.GOLD_NUGGET, 1));
               item.setVelocity(gPlayer.getPlayer().getLocation().getDirection().multiply(2));
               BlockUtil1.a(item, 30);
               Util3.a(gPlayer.getPlayer().getLocation(), 10, Sound.CHICKEN_EGG_POP, 10.0F, -3.0F);
               e.setCancelled(true);
            }

            return;
         case REDSTONE_TORCH_OFF:
         case REDSTONE_TORCH_ON:
            if (rightClick) {
               if (!gPlayer.dH()) {
                  gPlayer.dE().eL();
               }

               e.setCancelled(true);
            }

            return;
         case DIAMOND:
            if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
               break;
            }

            gPlayer.getPlayer().sendMessage(Core.a("You can purchase VIP from http://" + Core.a().getConfig().getString("game.websiteUrl")));
            e.setCancelled(true);
            return;
         case PAPER:
            if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
               break;
            }

            if (rightClick) {
               e.getPlayer().performCommand("tickets");
            }

            return;
         case BONE:
            if (!gPlayer.dH() || !Core.b().V().aU().equals(GameState.bB)) {
               if (rightClick) {
               }

               return;
            }
            break;
         case ENDER_CHEST:
            if (!gPlayer.dH() || !Core.b().V().aU().equals(GameState.bB)) {
               if (rightClick) {
                  Core.a().getServer().dispatchCommand(e.getPlayer(), "uc menu main");
               }

               return;
            }
            break;
         case GOLD_INGOT:
            if (!gPlayer.dH() || !Core.b().V().aU().equals(GameState.bB)) {
               if (rightClick) {
                  Core.a().getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ls o " + e.getPlayer().getName());
               }

               return;
            }
         }
      }

      if (!gPlayer.dI()) {
         if (gPlayer.dH()) {
            if (rightClick && e.getClickedBlock() != null && e.getClickedBlock().getType().equals(Material.FENCE)) {
               e.setCancelled(true);
               return;
            }

            if (rightClick && e.getClickedBlock() != null && Core.b().V().aU().equals(GameState.bA)) {
               e.setCancelled(true);
               return;
            }

            if (Core.b().V().aU().equals(GameState.bB) && e.getClickedBlock() != null && e.getPlayer().getItemInHand() != null) {
               Iterator var8 = Core.c().getPlayers().iterator();

               while(var8.hasNext()) {
                  GamePlayer spectator = (GamePlayer)var8.next();
                  if (spectator.dI() && spectator.getPlayer().getWorld().equals(gPlayer.getPlayer().getWorld()) && spectator.getPlayer().getLocation().distance(e.getClickedBlock().getLocation()) <= 2.0D) {
                     spectator.getPlayer().teleport(spectator.getPlayer().getLocation().add(0.0D, 5.0D, 0.0D));
                     spectator.getPlayer().setFlying(true);
                  }
               }
            }

            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
               GamePowerup powerup = Core.b().V().aT().a(e.getClickedBlock());
               if (powerup != null) {
                  powerup.o(gPlayer);
                  powerup.p(gPlayer);
                  Core.b().V().aT().a(powerup);
               }

               if (gPlayer.getPlayer().getOpenInventory().getType().equals(InventoryType.CRAFTING) && !gPlayer.getPlayer().isSneaking()) {
                  Block block = e.getClickedBlock();
                  if (block != null && block.getType().equals(Material.WORKBENCH) && !e.getPlayer().isSneaking()) {
                     e.setCancelled(true);
                     e.getPlayer().openWorkbench((Location)null, true);
                  }
               }
            }
         }

      }
   }

   @EventHandler
   public void b(PlayerInteractEvent event) {
      GamePlayer gPlayer = Core.c().a(event.getPlayer());
      if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getType() == Material.SOIL && gPlayer.dS()) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void a(PlayerRespawnEvent e) {
      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      if (gPlayer == null) {
         Logger.log("Could not find the player to respawn!");
      } else {
         Location location = gPlayer.getSpawnLocation();
         if (location == null) {
            Logger.log("The respawn location was invalid!");
         } else {
            e.setRespawnLocation(gPlayer.getSpawnLocation());
            gPlayer.dU();
            gPlayer.dO();
            Core.f().d(gPlayer);
            Core.l().g(gPlayer);
            ((CraftPlayer)gPlayer.getPlayer()).getHandle().getDataWatcher().watch(9, (byte)0);
            Core.b().ap();
         }
      }
   }

   @EventHandler
   public void a(PlayerDropItemEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (!gPlayer.dJ().equals(GamePlayerState.hb) || !Core.b().V().aU().equals(GameState.bB)) {
         e.getItemDrop().remove();
         gPlayer.dO();
      }

      if (gPlayer.dJ().equals(GamePlayerState.hb) && Core.b().V().aU().equals(GameState.bB) && gPlayer.er() && BlockUtil1.b(e.getItemDrop().getItemStack())) {
         e.getItemDrop().remove();
      }

   }

   @EventHandler
   public void a(PlayerPickupItemEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (gPlayer.dS()) {
         if (e.getItem().getItemStack().getType().equals(Material.GOLD_NUGGET)) {
            int value = (e.getItem().getItemStack().getAmount() > 0 ? e.getItem().getItemStack().getAmount() : 1) * 5;
            gPlayer.getPlayer().playSound(gPlayer.getPlayer().getLocation(), Sound.ORB_PICKUP, 0.5F, 1.0F);
            gPlayer.s(value);
            e.getItem().remove();
         }

         if (gPlayer.getPlayer().getInventory().getItem(8) != null && gPlayer.getPlayer().getInventory().getItem(8).getType().equals(Material.GOLD_BARDING)) {
            ItemMeta meta = gPlayer.getPlayer().getInventory().getItem(8).getItemMeta();
            int shots = (int)Math.floor((double)(gPlayer.dZ() / 5));
            meta.setDisplayName(ChatColor.GOLD + "Gold Nugget Gun " + (shots == 0 ? ChatColor.RED + "0 Shots" : ChatColor.GREEN.toString() + shots + " Shot" + (shots == 1 ? "" : "s")));
            gPlayer.getPlayer().getInventory().getItem(8).setItemMeta(meta);
         }

         e.setCancelled(true);
      } else {
         if (!gPlayer.dJ().equals(GamePlayerState.hb) || !Core.b().V().aU().equals(GameState.bB)) {
            e.setCancelled(true);
         }

      }
   }

   @EventHandler
   public void a(PlayerAchievementAwardedEvent e) {
      e.setCancelled(true);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(PlayerDeathEvent e) {
      if (e.getDeathMessage() != null) {
         GamePlayer gPlayer = Core.c().a(e.getEntity());
         GamePlayer killer = null;
         boolean needMessageFormatting = true;
         String deathMessage = e.getDeathMessage();
         e.setDeathMessage((String)null);
         if (gPlayer.dJ().equals(GamePlayerState.hb) && Core.b().V().aU().equals(GameState.bB) && gPlayer.getPlayer() != null) {
            if (!Core.b().V().aS().bn().be()) {
               e.getDrops().clear();
            } else {
               List<ItemStack> toRemove = new ArrayList();
               Iterator var7 = e.getDrops().iterator();

               while(var7.hasNext()) {
                  ItemStack stack = (ItemStack)var7.next();
                  if (BlockUtil1.b(stack)) {
                     toRemove.add(stack);
                  }
               }

               e.getDrops().removeAll(toRemove);
            }

            gPlayer.ek();
            if (needMessageFormatting) {
               if (e.getEntity().getKiller() != null) {
                  killer = Core.c().r(e.getEntity().getKiller().getName());
                  if (killer.equals(gPlayer)) {
                     killer = null;
                  } else {
                     deathMessage = deathMessage.replace(killer.getPlayer().getName(), killer.ca().bP() + killer.getPlayer().getName() + ChatColor.GRAY);
                     if (!killer.dH()) {
                        Logger.log("A spectator/lobby player has killed a game participant!!1");
                        return;
                     }
                  }
               }

               EntityDamageByEntityEvent entityDamage;
               if (killer == null && gPlayer.getPlayer().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                  entityDamage = (EntityDamageByEntityEvent)gPlayer.getPlayer().getLastDamageCause();
                  if (entityDamage.getDamager() instanceof Player) {
                     killer = Core.c().a((Player)entityDamage.getDamager());
                  } else if (entityDamage.getDamager() instanceof Projectile) {
                     Projectile projectile = (Projectile)entityDamage.getDamager();
                     if (projectile.getShooter() instanceof Player) {
                        killer = Core.c().a((Player)projectile.getShooter());
                     }
                  }
               }

               deathMessage = deathMessage.replace(gPlayer.getPlayer().getName(), gPlayer.ca().bP() + gPlayer.getPlayer().getName() + ChatColor.GRAY);
               if (killer != null) {
                  deathMessage = deathMessage.replace(killer.getPlayer().getName(), killer.getPlayer().getDisplayName() + ChatColor.GRAY);
               }

               if (gPlayer.getPlayer().getLastDamageCause().getCause() != null) {
                  switch(gPlayer.getPlayer().getLastDamageCause().getCause()) {
                  case LAVA:
                  case BLOCK_EXPLOSION:
                  case FIRE:
                  case FIRE_TICK:
                  case FALL:
                     break;
                  case VOID:
                     if (killer != null && !killer.equals(gPlayer)) {
                        deathMessage = gPlayer.getPlayer().getDisplayName() + ChatColor.GRAY + " was hit out of the world by " + killer.getPlayer().getDisplayName();
                        Core.g().a(killer, gPlayer, "void");
                     }
                     break;
                  default:
                     if (gPlayer.getPlayer().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                        entityDamage = (EntityDamageByEntityEvent)gPlayer.getPlayer().getLastDamageCause();
                        if (entityDamage.getDamager() instanceof Player) {
                           killer = Core.c().r(entityDamage.getDamager().getName());
                           deathMessage = deathMessage + "'s " + killer.getPlayer().getItemInHand().getType().toString().replace("_", " ").toLowerCase().replace("air", "fist");
                           Core.g().a(killer, gPlayer, killer.getPlayer().getItemInHand().getType().toString().replace("_", " ").toLowerCase().replace("air", "fist"));
                        } else if (entityDamage.getDamager() instanceof Projectile) {
                           if (entityDamage.getDamager() instanceof Arrow) {
                              killer = Core.c().a((Player)((Arrow)entityDamage.getDamager()).getShooter());
                              if (killer != null && !killer.equals(gPlayer)) {
                                 killer.dC().a(AchievementType.E, 1);
                              }
                           } else if (entityDamage.getDamager() instanceof FishHook) {
                              killer = Core.c().r(((Player)((FishHook)entityDamage.getDamager()).getShooter()).getName());
                              deathMessage = deathMessage + "'s " + killer.getPlayer().getItemInHand().getType().toString().replace("_", " ").toLowerCase();
                           }

                           if (killer != null) {
                              deathMessage = deathMessage.replace(killer.getPlayer().getName(), killer.getPlayer().getDisplayName() + ChatColor.GRAY);
                              Core.g().a(killer, gPlayer, killer.getPlayer().getItemInHand().getType().toString().replace("_", " ").toLowerCase().replace("air", "fist"));
                           }
                        }
                     }
                  }
               }
            }

            if (killer != null && !killer.equals(gPlayer)) {
               Core.b().V().aT().a(killer, MapRewardType.cA);
               killer.dC().a(AchievementType.A, 1);
               gPlayer.dC().a(AchievementType.B, 1);
               killer.el();
               Bukkit.getPluginManager().callEvent(new PlayerKilledEvent(gPlayer, killer, e));
               if (Core.b().V().aU().equals(GameState.bB)) {
                  Core.b().V().aT().n(killer);
               }
            }

            if (!deathMessage.equals("") && Core.b().V().aS().bn().bc()) {
               int goldEarnt = Core.b().V().aS().b(MapRewardType.cA);
               int xpEarnt = Core.b().V().aS().a(MapRewardType.cA);
               Iterator var14 = Core.c().getPlayers().iterator();

               while(var14.hasNext()) {
                  GamePlayer player = (GamePlayer)var14.next();
                  if (!player.dS() && player.dF().ez()) {
                     player.getPlayer().sendMessage(deathMessage);
                  }
               }
            }

            Core.f().d(gPlayer);
            if (Core.b().V().aS().bn().bb() || !gPlayer.dH()) {
               gPlayer.dT();
            }

         } else {
            e.setDeathMessage((String)null);
            e.getDrops().clear();
         }
      }
   }

   @EventHandler
   public void a(final PlayerMoveEvent e) {
      final GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (gPlayer != null) {
         gPlayer.eh();
         Core.n().k(gPlayer);
         if (gPlayer.dS() && e.getTo() != null && e.getFrom() != null && e.getTo().getBlock() != null && e.getTo().getBlock().getType().equals(Material.STONE_PLATE) && e.getTo().distance(e.getFrom()) > 0.0D) {
            gPlayer.getPlayer().setVelocity(gPlayer.getPlayer().getVelocity().setY(1.5D));
            e.getTo().getBlock().setType(Material.AIR);
         }

         if (e.getFrom().distance(e.getTo()) != 0.0D) {
            gPlayer.dE().eN();
            if (!gPlayer.getPlayer().isSneaking()) {
               gPlayer.dE().eJ();
            }
         }

         if (!gPlayer.dS()) {
            gPlayer.dW();
         }

         if (Core.b().V().aU().equals(GameState.bB) && gPlayer.dH()) {
            if (gPlayer.b(gPlayer.getPlayer().getLocation().toVector())) {
               Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
                  public void run() {
                     boolean invalidPosition = false;
                     Iterator var2 = Core.b().V().aS().bq().iterator();

                     label139:
                     while(var2.hasNext()) {
                        MapRegion region = (MapRegion)var2.next();
                        Iterator var4 = region.bD().iterator();

                        while(true) {
                           while(true) {
                              RegionRule rule;
                              Iterator var6;
                              Vector v;
                              Vector vx;
                              float yaw;
                              do {
                                 do {
                                    while(true) {
                                       do {
                                          if (!var4.hasNext()) {
                                             continue label139;
                                          }

                                          rule = (RegionRule)var4.next();
                                       } while((!rule.bZ().equals(RegionTeam.dd) || !rule.b(gPlayer.ca())) && !rule.bZ().equals(RegionTeam.dc));

                                       if (region.a(gPlayer.getPlayer().getLocation().toVector())) {
                                          if (rule.bX().equals(RegionAction.cX)) {
                                             boolean kickBack = false;
                                             if (region.bI() != null && !region.bI().isEmpty() && !gPlayer.getPlayer().hasPermission(region.bI())) {
                                                gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + "To use this portal you need to be a VIP! store." + Core.a().getConfig().getString("game.websiteUrl") + " to purchase"));
                                                kickBack = true;
                                             }

                                             if (region.bJ() != 0) {
                                                if (gPlayer.dZ() < region.bJ()) {
                                                   gPlayer.getPlayer().sendMessage(Core.a("Oops, You don't have enough gold to use this portal!"));
                                                   kickBack = true;
                                                } else {
                                                   gPlayer.getPlayer().sendMessage(Core.a(ChatColor.AQUA + "You warped through the portal costing you " + ChatColor.GOLD + region.bJ() + " gold!"));
                                                   gPlayer.s(-region.bJ());
                                                }
                                             }

                                             if (!kickBack) {
                                                Shape tpShape = region.bE();
                                                vx = tpShape.bO();
                                                Location tpLoc = new Location(Core.b().V().getWorld(), (double)vx.getBlockX(), (double)vx.getBlockY(), (double)vx.getBlockZ(), region.bF(), region.bG());
                                                gPlayer.getPlayer().teleport(tpLoc);
                                                if (region.bH() != null) {
                                                   gPlayer.b(region.bH());
                                                   gPlayer.dO();
                                                   gPlayer.g(true);
                                                }

                                                return;
                                             }

                                             Iterator var12 = gPlayer.dR().iterator();

                                             while(var12.hasNext()) {
                                                vx = (Vector)var12.next();
                                                if (region.a(vx) && !Core.b().V().getWorld().getBlockAt(vx.toLocation(gPlayer.getPlayer().getWorld())).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                                                   Vector vector = e.getFrom().toVector();
                                                   vector.subtract(e.getTo().toVector());
                                                   float yawx = 0.0F;
                                                   if ((double)Math.abs(vector.angle(gPlayer.getPlayer().getEyeLocation().getDirection())) > 1.5707963267948966D) {
                                                      yawx = 180.0F;
                                                   }

                                                   gPlayer.getPlayer().teleport(vx.toLocation(gPlayer.getPlayer().getWorld(), yawx + gPlayer.getPlayer().getLocation().getYaw(), gPlayer.getPlayer().getLocation().getPitch()));
                                                   gPlayer.getPlayer().setVelocity(gPlayer.getPlayer().getLocation().getDirection());
                                                   break;
                                                }
                                             }

                                             return;
                                          }
                                          break;
                                       }

                                       if (region.a(gPlayer.dQ()) && !region.a(gPlayer.getPlayer().getLocation().toVector()) && rule.bX().equals(RegionAction.cR) && rule.bY().equals(RegionAccess.cO)) {
                                          var6 = gPlayer.dR().iterator();

                                          while(var6.hasNext()) {
                                             v = (Vector)var6.next();
                                             if (region.a(v) && !Core.b().V().getWorld().getBlockAt(v.toLocation(gPlayer.getPlayer().getWorld())).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                                                vx = e.getFrom().toVector();
                                                vx.subtract(e.getTo().toVector());
                                                yaw = 0.0F;
                                                if ((double)Math.abs(vx.angle(gPlayer.getPlayer().getEyeLocation().getDirection())) > 1.5707963267948966D) {
                                                   yaw = 180.0F;
                                                }

                                                gPlayer.getPlayer().teleport(v.toLocation(gPlayer.getPlayer().getWorld(), yaw + gPlayer.getPlayer().getLocation().getYaw(), gPlayer.getPlayer().getLocation().getPitch()));
                                                gPlayer.getPlayer().setVelocity(gPlayer.getPlayer().getLocation().getDirection());
                                                gPlayer.getPlayer().sendMessage(Core.a(rule.getMessage()));
                                                break;
                                             }
                                          }
                                       }
                                    }
                                 } while(!rule.bX().equals(RegionAction.cQ));
                              } while(!rule.bY().equals(RegionAccess.cO));

                              var6 = gPlayer.dR().iterator();

                              while(var6.hasNext()) {
                                 v = (Vector)var6.next();
                                 if (!region.a(v) && !Core.b().V().getWorld().getBlockAt(v.toLocation(gPlayer.getPlayer().getWorld())).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
                                    vx = e.getFrom().toVector();
                                    vx.subtract(e.getTo().toVector());
                                    yaw = 0.0F;
                                    if ((double)Math.abs(vx.angle(gPlayer.getPlayer().getEyeLocation().getDirection())) > 1.5707963267948966D) {
                                       yaw = 180.0F;
                                    }

                                    gPlayer.getPlayer().teleport(v.toLocation(gPlayer.getPlayer().getWorld(), yaw + gPlayer.getPlayer().getLocation().getYaw(), gPlayer.getPlayer().getLocation().getPitch()));
                                    gPlayer.getPlayer().setVelocity(gPlayer.getPlayer().getLocation().getDirection());
                                    gPlayer.getPlayer().sendMessage(Core.a(rule.getMessage()));
                                    break;
                                 }
                              }
                           }
                        }
                     }

                  }
               });
            }

         }
      }
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(AsyncPlayerChatEvent e) {
      GamePlayer chatter = Core.c().r(e.getPlayer().getName());
      chatter.eh();
      if (!e.isCancelled()) {
         if (chatter.dG()) {
            chatter.getPlayer().sendMessage(ChatColor.RED + "You cannot talk whilst on tutorials!");
            e.setCancelled(true);
         } else if (chatter.dH() && chatter.ef()) {
            e.setCancelled(true);
            Iterator var5 = Core.c().getPlayers().iterator();

            while(true) {
               GamePlayer player;
               do {
                  do {
                     if (!var5.hasNext()) {
                        e.setCancelled(true);
                        return;
                     }

                     player = (GamePlayer)var5.next();
                  } while(!player.dF().ey());
               } while((!player.dH() || !player.ca().equals(chatter.ca())) && !player.dF().eC());

               player.getPlayer().sendMessage(chatter.ca().bP() + "[TEAM] " + chatter.getPlayer().getName() + ChatColor.RESET + ": " + e.getMessage().trim());
            }
         } else {
            ChatColor n = ChatColor.GRAY;
            ChatColor m = ChatColor.GRAY;
            if (chatter.getPlayer().hasPermission("gamecore.vip")) {
               n = ChatColor.WHITE;
               m = ChatColor.WHITE;
            }

            if (chatter.ca() != null) {
               n = chatter.ca().bP();
            }

         }
      }
   }

   @EventHandler
   public void a(PlayerItemConsumeEvent e) {
      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      if (gPlayer.getPlayer().getLocation().getY() <= 0.0D) {
         e.setCancelled(true);
      } else {
         if (e.getItem() != null && e.getItem().getType().equals(Material.COOKED_CHICKEN)) {
            gPlayer.dC().a(AchievementType.J, 1);
         }

      }
   }

   @EventHandler
   public void a(PlayerFishEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (e.getState().equals(State.CAUGHT_FISH)) {
         gPlayer.dC().a(AchievementType.L, 1);
      }

   }

   @EventHandler
   public void a(final PlayerChangedWorldEvent e) {
      if (e.getFrom().equals(Core.d().cT().getWorld())) {
         (new BukkitRunnable() {
            public void run() {
               Core.e().cn();
               GamePlayer gPlayer = Core.c().a(e.getPlayer());
               Core.n().k(gPlayer);
               Core.l().f(gPlayer);
            }
         }).runTaskLater(Core.a(), 20L);
      }

      GamePlayer gPlayer = Core.c().a(e.getPlayer());
      Core.f().d(gPlayer);
   }

   @EventHandler
   public void a(PlayerInteractEntityEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (gPlayer.dS()) {
         if (e.getRightClicked() instanceof ItemFrame) {
            e.setCancelled(true);
         }

         if (gPlayer.getPlayer().getItemInHand() != null && gPlayer.getPlayer().getItemInHand().getType().equals(Material.BLAZE_ROD)) {
            Core.d().cW().a(gPlayer, e);
         }
      }

   }

   @EventHandler
   public void a(PlayerBucketEmptyEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      Block block = e.getBlockClicked().getRelative(e.getBlockFace());
      Iterator var4 = Core.b().V().aS().bq().iterator();

      label73:
      while(true) {
         MapRegion region;
         do {
            if (!var4.hasNext()) {
               return;
            }

            region = (MapRegion)var4.next();
         } while(!region.a(block.getLocation().toVector()));

         Iterator var6 = region.bD().iterator();

         while(true) {
            while(true) {
               RegionRule rule;
               do {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           continue label73;
                        }

                        rule = (RegionRule)var6.next();
                     } while(!rule.bX().equals(RegionAction.cS) && !rule.bX().equals(RegionAction.cV));
                  } while((!rule.bZ().equals(RegionTeam.dd) || !rule.b(gPlayer.ca())) && !rule.bZ().equals(RegionTeam.dc));
               } while(!region.a(block.getLocation().toVector()));

               if (rule.getMaterial() == null) {
                  e.setCancelled(true);
               } else if (rule.getMaterial().equals(Material.LAVA) && (block.getType().equals(Material.LAVA) || block.getType().equals(Material.STATIONARY_LAVA)) || rule.getMaterial().equals(Material.WATER) && (block.getType().equals(Material.WATER) || block.getType().equals(Material.STATIONARY_WATER))) {
                  e.setCancelled(true);
               }
            }
         }
      }
   }

   @EventHandler
   public void a(ServerListPingEvent event) {
      if (Core.a().getConfig().getBoolean("game.motd", true)) {
         String mapName = ChatColor.DARK_AQUA + "Currently Playing: " + ChatColor.AQUA + Core.b().V().aS().bk();
         String motd = ChatColor.GOLD + ">> " + mapName + ChatColor.GOLD + " <<";
         if (Core.b().V().aU().equals(GameState.bA)) {
            motd = ChatColor.GREEN + ">> " + mapName + ChatColor.GREEN + " <<";
         }

         if (Core.b().V().aU().equals(GameState.bD) || Core.b().V().aU().equals(GameState.bC)) {
            motd = ChatColor.RED + ">> " + mapName + ChatColor.RED + " <<";
         }

         event.setMotd(ChatColor.DARK_GREEN + "Welcome to " + ChatColor.GOLD + Core.a().getConfig().getString("game.serverName") + "\n" + motd);
      }
   }

   @EventHandler
   public void b(InventoryClickEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getWhoClicked().getName());
      if (Core.b().V().aU().equals(GameState.bB) && gPlayer.dH() && gPlayer.er() && e.getView().getTopInventory().getType() == InventoryType.CHEST && e.getView().getBottomInventory().getType() == InventoryType.PLAYER && BlockUtil1.b(e.getCurrentItem())) {
         e.setCancelled(true);
      }

   }
}
