package org.Kratous.GameCore.Commands;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceStartCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!Core.b().V().aU().equals(GameState.bB) && !Core.b().V().aU().equals(GameState.bD)) {
         Core.g().m(Core.b().V().aS().bk());
         Core.b().X().aQ();
         return true;
      } else {
         commandSender.sendMessage(Core.a("Cannot start a game already started!"));
         return true;
      }
   }
}
