package a.a.a.b;

import a.a.a.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GameCoreCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (strings.length != 1) {
         commandSender.sendMessage(ChatColor.GREEN + "GameCore - Game Management Plugin");
         commandSender.sendMessage(ChatColor.GREEN + "Licenced to: " + ChatColor.GOLD + Core.a().getConfig().getString("game.serverName"));
         commandSender.sendMessage(ChatColor.GREEN + "Version: " + ChatColor.GOLD + Core.a().getDescription().getVersion());
         commandSender.sendMessage(ChatColor.GREEN + "Purchase at: " + ChatColor.GOLD + "http://GameCore.krato.us");
         return true;
      } else {
         String var5 = strings[0];
         byte var6 = -1;
         switch(var5.hashCode()) {
         case -934641255:
            if (var5.equals("reload")) {
               var6 = 0;
            }
            break;
         case 118:
            if (var5.equals("v")) {
               var6 = 3;
            }
            break;
         case 116643:
            if (var5.equals("ver")) {
               var6 = 2;
            }
            break;
         case 351608024:
            if (var5.equals("version")) {
               var6 = 1;
            }
         }

         switch(var6) {
         case 0:
            if (commandSender.hasPermission("gamecore.manage")) {
               Core.a().reloadConfig();
               commandSender.sendMessage(Core.a("Config has been reloaded!"));
            }
            break;
         case 1:
         case 2:
         case 3:
            commandSender.sendMessage(Core.a(Core.a().getDescription().getName() + " version " + Core.a().getDescription().getVersion()));
         }

         return true;
      }
   }
}
