package a.a.a.Commands;

import a.a.a.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CancelRestartCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.restart")) {
         return false;
      } else {
         Core.b().ao();
         commandSender.sendMessage(Core.a("Restart has been cancelled."));
         return true;
      }
   }
}
