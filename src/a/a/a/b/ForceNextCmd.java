package a.a.a.b;

import a.a.a.Core;
import a.a.a.f.a.MapData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceNextCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.forcenext")) {
         return false;
      } else if (strings.length == 0) {
         commandSender.sendMessage(ChatColor.DARK_AQUA + "Usage: /forcenext <mapname>");
         return true;
      } else {
         String toSearch = "";
         String[] var6 = strings;
         int var7 = strings.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String str = var6[var8];
            toSearch = toSearch + str + " ";
         }

         toSearch = toSearch.trim();
         List<MapData> foundMaps = new ArrayList();
         Iterator var11 = Core.b().af().iterator();

         MapData map;
         while(var11.hasNext()) {
            map = (MapData)var11.next();
            if (map.bk().equalsIgnoreCase(toSearch)) {
               foundMaps.clear();
               foundMaps.add(map);
               break;
            }

            if (map.bk().toLowerCase().contains(toSearch.toLowerCase())) {
               foundMaps.add(map);
            }
         }

         if (foundMaps.size() == 1) {
            Core.b().b((MapData)foundMaps.get(0));
            commandSender.sendMessage(Core.a("Next map has been forced to " + ChatColor.GOLD + ((MapData)foundMaps.get(0)).bk()));
         } else if (foundMaps.isEmpty()) {
            commandSender.sendMessage(ChatColor.RED + "No matches when searching for " + toSearch);
         } else {
            commandSender.sendMessage(ChatColor.DARK_AQUA + " - Found " + foundMaps.size() + " maps");
            var11 = foundMaps.iterator();

            while(var11.hasNext()) {
               map = (MapData)var11.next();
               commandSender.sendMessage(ChatColor.DARK_AQUA + "   - " + map.bk());
            }

            commandSender.sendMessage(ChatColor.DARK_AQUA + " - Please refine your search");
         }

         return true;
      }
   }
}
