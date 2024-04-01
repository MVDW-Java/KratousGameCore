package org.Kratous.GameCore.g;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapRegion;
import org.Kratous.GameCore.f.a.b.RegionAccess;
import org.Kratous.GameCore.f.a.b.RegionAction;
import org.Kratous.GameCore.f.a.b.RegionRule;
import org.Kratous.GameCore.f.a.b.RegionTeam;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class EntityListener implements Listener {
   @EventHandler(
      priority = EventPriority.LOW
   )
   public void a(EntityDamageByEntityEvent e) {
      GamePlayer gPlayer;
      switch(e.getEntityType()) {
      case ITEM_FRAME:
         if (e.getDamager() instanceof Player) {
            gPlayer = Core.c().a((Player)e.getDamager());
            if (gPlayer.dS() && e.getEntity().hasMetadata("VOTE_ITEM")) {
               String itemName = ((ItemFrame)e.getEntity()).getItem().getItemMeta().getDisplayName();
               itemName = itemName.replace(ChatColor.GREEN.toString(), "").split(" - ")[0];
               Core.b().b(gPlayer, itemName);
            }

            if (!gPlayer.dH() || gPlayer.dS()) {
               e.setCancelled(true);
            }
         }
         break;
      case PLAYER:
         gPlayer = Core.c().r(e.getEntity().getName());
         if (!gPlayer.dH() || !Core.b().V().aU().equals(GameState.bB)) {
            if (e.getDamager() instanceof Arrow) {
               gPlayer.getPlayer().teleport(gPlayer.getPlayer().getLocation().add(0.0D, 5.0D, 0.0D), TeleportCause.PLUGIN);
               gPlayer.getPlayer().setAllowFlight(true);
               gPlayer.getPlayer().setFlying(true);
               Arrow arrow = (Arrow)e.getDamager();
               arrow.remove();
               Arrow newArrow = arrow.getWorld().spawnArrow(arrow.getLocation(), arrow.getVelocity(), (float)arrow.getVelocity().length(), 0.0F);
               newArrow.setShooter(arrow.getShooter());
               newArrow.setFireTicks(arrow.getFireTicks());
               e.setCancelled(true);
               return;
            }

            if (e.getDamager() instanceof Snowball) {
               gPlayer.getPlayer().teleport(gPlayer.getPlayer().getLocation().add(0.0D, 5.0D, 0.0D), TeleportCause.PLUGIN);
               gPlayer.getPlayer().setFlying(true);
               Snowball ball = (Snowball)e.getDamager();
               ball.remove();
               Snowball newBall = (Snowball)ball.getWorld().spawnEntity(ball.getLocation(), EntityType.SNOWBALL);
               newBall.setVelocity(ball.getVelocity());
               e.setCancelled(true);
               return;
            }

            if (e.getDamager() instanceof Player || e.getDamager() instanceof ItemFrame) {
               e.setCancelled(true);
               return;
            }
         }

         GamePlayer damagerPlayer = null;
         if (e.getDamager() instanceof Player) {
            damagerPlayer = Core.c().r(e.getDamager().getName());
         } else if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile)e.getDamager();
            if (projectile.getShooter() instanceof Player) {
               damagerPlayer = Core.c().r(((Player)projectile.getShooter()).getName());
            }
         }

         Iterator var9;
         if (damagerPlayer != null) {
            if (!damagerPlayer.dH()) {
               e.setCancelled(true);
               return;
            }

            if (Core.b().V().aT().aC().size() > 1 && gPlayer.dH() && damagerPlayer.dH() && (gPlayer.ca().equals(damagerPlayer.ca()) || damagerPlayer.equals(gPlayer))) {
               e.setCancelled(true);
               return;
            }

            var9 = Core.b().V().aS().bq().iterator();

            label109:
            while(var9.hasNext()) {
               MapRegion region = (MapRegion)var9.next();
               Iterator var6 = region.bD().iterator();

               while(true) {
                  RegionRule rule;
                  do {
                     if (!var6.hasNext()) {
                        continue label109;
                     }

                     rule = (RegionRule)var6.next();
                  } while((!rule.bZ().equals(RegionTeam.dd) || !rule.b(gPlayer.ca())) && !rule.bZ().equals(RegionTeam.dc));

                  if (region.a(gPlayer.getPlayer().getLocation().toVector()) && rule.bX().equals(RegionAction.cU) && rule.bY().equals(RegionAccess.cO)) {
                     e.setCancelled(true);
                  }
               }
            }
         }

         if (!e.isCancelled()) {
            if (damagerPlayer != null) {
               damagerPlayer.en();
               gPlayer.B(damagerPlayer);
            }

            gPlayer.en();
            var9 = Core.c().getPlayers().iterator();

            while(var9.hasNext()) {
               GamePlayer player = (GamePlayer)var9.next();
               if (!player.dS() && player.dF().eB()) {
                  player.getPlayer().playEffect(gPlayer.getPlayer().getLocation(), Effect.STEP_SOUND, 55);
               }
            }
         }
      }

   }

   @EventHandler(
      ignoreCancelled = true
   )
   public void a(EntityDamageEvent e) {
      switch(e.getEntityType()) {
      case PLAYER:
         GamePlayer gPlayer = Core.c().r(((Player)e.getEntity()).getPlayer().getName());
         if (gPlayer.dH() && !gPlayer.dS() && Core.b().V().aU().equals(GameState.bB)) {
            if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
               switch(e.getCause()) {
               case VOID:
                  if (e.isCancelled()) {
                     gPlayer.dN();
                  }
               }
            }
         } else if (e.getCause().equals(DamageCause.VOID)) {
            gPlayer.dN();
         } else {
            e.setCancelled(true);
         }
      default:
      }
   }

   @EventHandler
   public void a(FoodLevelChangeEvent e) {
      if (e.getEntity() instanceof Player) {
         GamePlayer gPlayer = Core.c().r(e.getEntity().getName());
         if (!gPlayer.dH() || !Core.b().V().aU().equals(GameState.bB)) {
            gPlayer.getPlayer().setFoodLevel(20);
            e.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void a(ItemDespawnEvent e) {
      if (e.getEntity().getItemStack().getType().equals(Material.GOLD_NUGGET)) {
         Iterator var2 = Core.c().getPlayers().iterator();

         while(var2.hasNext()) {
            GamePlayer gPlayer = (GamePlayer)var2.next();
            gPlayer.a(EnumParticle.FIREWORKS_SPARK, e.getEntity().getLocation(), 1.0F, 10);
         }
      }

   }

   @EventHandler
   public void a(HangingBreakByEntityEvent e) {
      if (e.getRemover() instanceof Player) {
         GamePlayer gPlayer = Core.c().r(e.getRemover().getName());
         if (!gPlayer.dH() || !Core.b().V().aU().equals(GameState.bB)) {
            e.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void a(EntityExplodeEvent e) {
      List<Block> toRemove = new ArrayList();
      Iterator var3 = Core.b().V().aS().bq().iterator();

      label53:
      while(var3.hasNext()) {
         MapRegion region = (MapRegion)var3.next();
         Iterator var5 = region.bD().iterator();

         label51:
         while(true) {
            RegionRule rule;
            do {
               do {
                  if (!var5.hasNext()) {
                     continue label53;
                  }

                  rule = (RegionRule)var5.next();
               } while(!rule.bX().equals(RegionAction.cW));
            } while(!rule.bY().equals(RegionAccess.cO));

            Iterator var7 = e.blockList().iterator();

            while(true) {
               Block b;
               do {
                  do {
                     if (!var7.hasNext()) {
                        continue label51;
                     }

                     b = (Block)var7.next();
                  } while(!region.a(b.getLocation().toVector()));
               } while(rule.getMaterial() != null && !rule.getMaterial().equals(b.getType()));

               if (!toRemove.contains(b)) {
                  toRemove.add(b);
               }
            }
         }
      }

      e.blockList().removeAll(toRemove);
   }
}
