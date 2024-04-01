package org.Kratous.GameCore.Commands;

import org.Kratous.GameCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RestartNextCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.restart")) {
         return false;
      } else {
         Core.b().an();
         commandSender.sendMessage(Core.a("The server will restart after this game"));
         return true;
      }
   }
}
