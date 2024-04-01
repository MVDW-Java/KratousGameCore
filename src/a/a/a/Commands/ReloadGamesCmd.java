package a.a.a.Commands;

import a.a.a.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadGamesCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.reloadgames")) {
         return false;
      } else {
         Core.b().ab();
         commandSender.sendMessage(ChatColor.GOLD + "Games successfully reloaded");
         return true;
      }
   }
}
