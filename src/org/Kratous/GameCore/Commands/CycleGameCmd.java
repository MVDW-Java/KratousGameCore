package org.Kratous.GameCore.Commands;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CycleGameCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.cycle")) {
         return false;
      } else if (Core.b().V().aU().equals(GameState.bD)) {
         return true;
      } else {
         Core.b().V().a(GameState.bC);
         return true;
      }
   }
}
