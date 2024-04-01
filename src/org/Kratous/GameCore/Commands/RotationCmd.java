package org.Kratous.GameCore.Commands;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.a.MapData;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RotationCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.rotationcmd")) {
      }

      if (Core.b().bg) {
         commandSender.sendMessage(Core.a("Rotation not in use, voting enabled"));
         return true;
      } else {
         commandSender.sendMessage(Core.a(ChatColor.GOLD + "Server map rotation:"));
         int num = 0;
         Iterator var6 = Core.b().aa().iterator();

         while(var6.hasNext()) {
            MapData map = (MapData)var6.next();
            String mapname = "YML Error";
            if (map != null && map.bk() != null) {
               mapname = map.bk();
            }

            ChatColor mapcolor;
            if (map.bk() == Core.b().V().aS().bk()) {
               mapcolor = ChatColor.GOLD;
            } else {
               mapcolor = ChatColor.DARK_AQUA;
            }

            ++num;
            commandSender.sendMessage(ChatColor.GREEN + "" + num + " - " + mapcolor + mapname);
         }

         return true;
      }
   }
}
