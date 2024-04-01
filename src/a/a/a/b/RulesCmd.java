package a.a.a.b;

import a.a.a.Core;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RulesCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      commandSender.sendMessage(ChatColor.GREEN + "Read the rules at " + Core.a().getConfig().getString("game.websiteUrl") + "/rules");
      return true;
   }
}
