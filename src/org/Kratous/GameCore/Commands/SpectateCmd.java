package org.Kratous.GameCore.Commands;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.l.GamePlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         commandSender.sendMessage(ChatColor.RED + "Only players can execute this command");
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         if (gPlayer.dG()) {
            return true;
         } else if (gPlayer.dI()) {
            gPlayer.getPlayer().sendMessage(ChatColor.RED + "You are already spectating");
            return true;
         } else {
            gPlayer.dW();
            if (gPlayer.dH()) {
               if (Core.b().V().aU().equals(GameState.bB)) {
                  if ((double)System.currentTimeMillis() - gPlayer.em() < 10000.0D) {
                     gPlayer.getPlayer().sendMessage(ChatColor.RED + "You must wait 10 seconds before leaving from combat");
                     return true;
                  }

                  gPlayer.dV();
               }

               Core.b().V().aT().m(gPlayer);
               gPlayer.a(GamePlayerState.ha);
               gPlayer.getPlayer().sendMessage(ChatColor.GREEN + "To stop spectating type /lobby");
               gPlayer.dO();
            } else {
               gPlayer.a(GamePlayerState.ha);
               gPlayer.getPlayer().sendMessage(ChatColor.GREEN + "To stop spectating type /lobby");
               if (!gPlayer.dS() || !Core.b().V().aU().equals(GameState.bB) && !Core.b().V().aU().equals(GameState.bA)) {
                  gPlayer.dO();
               } else {
                  gPlayer.dN();
               }
            }

            return true;
         }
      }
   }
}
