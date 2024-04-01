package org.Kratous.GameCore.g;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.a.AchievementType;
import org.Kratous.GameCore.e.c.Hologram1;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapRegion;
import org.Kratous.GameCore.f.a.b.RegionAccess;
import org.Kratous.GameCore.f.a.b.RegionAction;
import org.Kratous.GameCore.f.a.b.RegionRule;
import org.Kratous.GameCore.f.a.b.RegionTeam;
import org.Kratous.GameCore.h.a.HologramType;
import org.Kratous.GameCore.h.a.LobbyHologram;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
   @EventHandler
   public void a(BlockPlaceEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (!gPlayer.dG()) {
         if (Core.b().V().aU().equals(GameState.bB) && gPlayer.dH()) {
            String message = "";
            Iterator var4 = Core.b().V().aS().bq().iterator();

            label66:
            while(true) {
               MapRegion region;
               do {
                  if (!var4.hasNext()) {
                     if (e.isCancelled() && !message.equals("")) {
                        gPlayer.getPlayer().sendMessage(Core.a(message));
                        e.getBlockPlaced().setType(Material.AIR);
                     }

                     return;
                  }

                  region = (MapRegion)var4.next();
               } while(!region.a(e.getBlock().getLocation().toVector()));

               Iterator var6 = region.bD().iterator();

               while(true) {
                  RegionRule rule;
                  do {
                     do {
                        if (!var6.hasNext()) {
                           continue label66;
                        }

                        rule = (RegionRule)var6.next();
                     } while(!rule.bX().equals(RegionAction.cS));
                  } while((!rule.bZ().equals(RegionTeam.dd) || !rule.b(gPlayer.ca())) && !rule.bZ().equals(RegionTeam.dc));

                  if (region.a(e.getBlockPlaced().getLocation().toVector())) {
                     if (rule.getMaterial() != null) {
                        if (rule.getMaterial().equals(e.getBlockPlaced().getType())) {
                           if (rule.bY().equals(RegionAccess.cN)) {
                              e.setCancelled(false);
                              return;
                           }

                           e.setCancelled(true);
                           message = rule.getMessage();
                        }
                     } else if (rule.bY().equals(RegionAccess.cO)) {
                        e.setCancelled(true);
                        message = rule.getMessage();
                     }
                  }
               }
            }
         } else {
            e.setCancelled(true);
         }
      }
   }

   @EventHandler(
      priority = EventPriority.LOW
   )
   public void a(BlockBreakEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      if (gPlayer.dS()) {
         e.setCancelled(true);
         if (e.getBlock().getType().equals(Material.TRIPWIRE)) {
            Iterator var9 = Core.d().cZ().dg().iterator();

            while(var9.hasNext()) {
               LobbyHologram lobbyHologram = (LobbyHologram)var9.next();
               Iterator var12 = lobbyHologram.dh().iterator();

               while(var12.hasNext()) {
                  Hologram1 hologram = (Hologram1)var12.next();
                  if (hologram.getLocation().getBlock().equals(e.getBlock())) {
                     String name = ((String)hologram.N().get(0)).replace(ChatColor.GREEN.toString(), "").replace(ChatColor.GOLD.toString(), "");
                     if (Core.b().b(gPlayer, name)) {
                        Core.d().cZ().a(HologramType.eO);
                        Core.d().cZ().update();
                     }
                  }
               }
            }
         }

      } else if (!gPlayer.dG()) {
         if (Core.b().V().aU().equals(GameState.bB) && gPlayer.dH()) {
            Iterator var4;
            if (Core.b().V().aS().bv().size() > 1) {
               Block up = e.getBlock().getRelative(BlockFace.UP);
               var4 = Core.c().getPlayers().iterator();

               while(var4.hasNext()) {
                  GamePlayer other = (GamePlayer)var4.next();
                  if (other.dH() && !other.equals(gPlayer) && other.ca().equals(gPlayer.ca()) && up.equals(other.getPlayer().getLocation().getBlock())) {
                     e.setCancelled(true);
                     return;
                  }
               }
            }

            String message = "";
            var4 = Core.b().V().aS().bq().iterator();

            label114:
            while(true) {
               MapRegion region;
               do {
                  if (!var4.hasNext()) {
                     if (e.isCancelled() && !message.equals("")) {
                        gPlayer.getPlayer().sendMessage(Core.a(message));
                     } else if (!e.isCancelled()) {
                        gPlayer.dC().a(AchievementType.K, 1);
                     }

                     return;
                  }

                  region = (MapRegion)var4.next();
               } while(!region.a(e.getBlock().getLocation().toVector()));

               Iterator var6 = region.bD().iterator();

               while(true) {
                  while(true) {
                     RegionRule rule;
                     do {
                        do {
                           if (!var6.hasNext()) {
                              continue label114;
                           }

                           rule = (RegionRule)var6.next();
                        } while(!rule.bX().equals(RegionAction.cT));
                     } while((!rule.bZ().equals(RegionTeam.dd) || !rule.b(gPlayer.ca())) && !rule.bZ().equals(RegionTeam.dc));

                     if (rule.getMaterial() != null && rule.getMaterial().equals(e.getBlock().getType())) {
                        if (rule.bY().equals(RegionAccess.cN)) {
                           e.setCancelled(false);
                           return;
                        }

                        e.setCancelled(true);
                        message = rule.getMessage();
                     } else if (rule.bY().equals(RegionAccess.cO) && rule.getMaterial() == null) {
                        e.setCancelled(true);
                        message = rule.getMessage();
                     }
                  }
               }
            }
         } else {
            e.setCancelled(true);
         }
      }
   }

   @EventHandler
   public void a(BlockFromToEvent e) {
      Block block = e.getToBlock();
      Iterator var3 = Core.b().V().aS().bq().iterator();

      label58:
      while(var3.hasNext()) {
         MapRegion region = (MapRegion)var3.next();
         Iterator var5 = region.bD().iterator();

         while(true) {
            while(true) {
               RegionRule rule;
               do {
                  do {
                     do {
                        if (!var5.hasNext()) {
                           continue label58;
                        }

                        rule = (RegionRule)var5.next();
                     } while(!region.a(block.getLocation().toVector()));
                  } while(!rule.bX().equals(RegionAction.cV));
               } while(!rule.bY().equals(RegionAccess.cO));

               if (rule.getMaterial() == null) {
                  e.setCancelled(true);
               } else if (rule.getMaterial().equals(Material.LAVA) && (block.getType().equals(Material.LAVA) || block.getType().equals(Material.STATIONARY_LAVA)) || rule.getMaterial().equals(Material.WATER) && (block.getType().equals(Material.WATER) || block.getType().equals(Material.STATIONARY_WATER))) {
                  e.setCancelled(true);
               }
            }
         }
      }

   }
}
