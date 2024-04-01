package a.a.a.g;

import a.a.a.Core;
import a.a.a.f.GameState;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import a.a.a.p.Tutorial;
import a.a.a.p.TutorialData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
   @EventHandler
   public void a(InventoryClickEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getWhoClicked().getName());
      if (gPlayer.dS()) {
         Core.d().cW().a(gPlayer, e);
      }

      if (gPlayer.getPlayer().getOpenInventory() != null) {
         String var3 = gPlayer.getPlayer().getOpenInventory().getTitle();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -594386763:
            if (var3.equals("Tutorials")) {
               var4 = 3;
            }
            break;
         case -498850587:
            if (var3.equals("Choose a Team")) {
               var4 = 1;
            }
            break;
         case 1450047677:
            if (var3.equals("Choose VIP Effect")) {
               var4 = 0;
            }
            break;
         case 1705068465:
            if (var3.equals("Vote Tickets")) {
               var4 = 2;
            }
         }

         switch(var4) {
         case 0:
            e.setCancelled(true);
            if (e.isRightClick()) {
               return;
            }

            if (gPlayer.dH()) {
               gPlayer.getPlayer().closeInventory();
               return;
            }

            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.SIGN)) {
               return;
            }

            gPlayer.dE().a(e);
            break;
         case 1:
            e.setCancelled(true);
            if (e.isRightClick()) {
               return;
            }

            if (gPlayer.dH()) {
               gPlayer.getPlayer().closeInventory();
               return;
            }

            if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
               if (!Core.b().V().aT().aH()) {
                  return;
               }

               switch(e.getCurrentItem().getType()) {
               case SLIME_BALL:
                  Core.b().V().aT().l(gPlayer);
                  break;
               case LEATHER_HELMET:
                  String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
                  itemName = itemName.replace("Join ", "");
                  ChatColor[] var12 = ChatColor.values();
                  int var7 = var12.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     ChatColor color = var12[var8];
                     itemName = itemName.replace(color.toString(), "");
                  }

                  Core.b().V().aT().c(gPlayer, itemName);
               }

               gPlayer.getPlayer().closeInventory();
               break;
            }

            return;
         case 2:
            e.setCancelled(true);
            if (e.isRightClick()) {
               return;
            }

            if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
               gPlayer.getPlayer().closeInventory();
               return;
            }

            if (e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR)) {
               if (Core.k().a(gPlayer, e.getCurrentItem())) {
                  gPlayer.getPlayer().closeInventory();
               }
               break;
            }

            return;
         case 3:
            e.setCancelled(true);
            if (e.isRightClick()) {
               return;
            }

            if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
               gPlayer.getPlayer().closeInventory();
               return;
            }

            if (e.getCurrentItem() == null || e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().getType().equals(Material.SIGN)) {
               return;
            }

            TutorialData data = Core.m().t(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
            if (data == null) {
               return;
            }

            Tutorial tutorial = Core.m().b(data);
            if (tutorial == null) {
               return;
            }

            gPlayer.a((Tutorial)tutorial);
            gPlayer.a(GamePlayerState.gZ);

            try {
               tutorial.D(gPlayer);
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      }

      if (gPlayer.dH() && !Core.b().V().aU().equals(GameState.bB)) {
      }

   }
}
