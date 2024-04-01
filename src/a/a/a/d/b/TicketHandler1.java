package a.a.a.d.b;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class TicketHandler1 {
   private Map ae = new HashMap();

   public void B() {
      this.ae.clear();
      if (Core.a().getConfig().contains("tickets")) {
         Iterator var1 = Core.a().getConfig().getConfigurationSection("tickets").getKeys(false).iterator();

         while(var1.hasNext()) {
            String key = (String)var1.next();
            ConfigurationSection section = Core.a().getConfig().getConfigurationSection("tickets." + key);
            TicketItem item = new TicketItem(section.getString("name"), section.getString("material"), section.getInt("slot"), section.getInt("cost", 0), section.getString("type"), section.getDouble("value", 0.0D));
            this.ae.put(item.getSlot(), item);
         }

      }
   }

   public Collection C() {
      return this.ae.values();
   }

   public boolean a(GamePlayer gPlayer, ItemStack stack) {
      TicketItem item = null;
      Iterator var4 = this.ae.values().iterator();

      while(var4.hasNext()) {
         TicketItem ticketItem = (TicketItem)var4.next();
         if (ticketItem.getMaterial().equals(stack.getType()) && stack.getItemMeta().getDisplayName().contains(ticketItem.getName())) {
            item = ticketItem;
            break;
         }
      }

      if (item != null && !item.getMaterial().equals(Material.SIGN)) {
         if (item.D() > gPlayer.ea()) {
            gPlayer.getPlayer().sendMessage(Core.a("You do not have enough tickets!"));
            return true;
         } else {
            switch(item.E()) {
            case ak:
               gPlayer.getPlayer().sendMessage(Core.a("For info on how to get vote tickets visit " + Core.a().getConfig().getString("game.websiteUrl") + "/vote"));
               return true;
            case al:
               gPlayer.dD().a(item.getValue(), "xp");
               break;
            case am:
               gPlayer.dD().a(item.getValue(), "gold");
               break;
            case ao:
               gPlayer.s((int)item.getValue());
               break;
            case an:
               gPlayer.dD().a((int)item.getValue(), "PACK");
               break;
            default:
               return false;
            }

            gPlayer.u(-item.D());
            gPlayer.getPlayer().sendMessage(Core.a("You have just bought '" + ChatColor.GOLD + item.getName() + ChatColor.DARK_AQUA + "'!"));
            return true;
         }
      } else {
         return false;
      }
   }
}
