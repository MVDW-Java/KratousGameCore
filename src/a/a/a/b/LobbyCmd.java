package a.a.a.b;

import a.a.a.Core;
import a.a.a.f.GameState;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import a.a.a.p.Tutorial;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         if (gPlayer.dG()) {
            try {
               gPlayer.eq().unload();
            } catch (Exception var7) {
               var7.printStackTrace();
            }

            gPlayer.a((Tutorial)null);
            gPlayer.a(GamePlayerState.gY);
            gPlayer.dN();
            return true;
         } else if (gPlayer.dJ().equals(GamePlayerState.gY)) {
            gPlayer.getPlayer().sendMessage(ChatColor.RED + "You are already in the lobby! Type /hub to return to the main lobby");
            return true;
         } else {
            if (gPlayer.dH()) {
               if (Core.b().V().aU().equals(GameState.bB)) {
                  if ((double)System.currentTimeMillis() - gPlayer.em() < 10000.0D) {
                     gPlayer.getPlayer().sendMessage(ChatColor.RED + "You must wait 10 seconds before leaving from combat");
                     return true;
                  }

                  gPlayer.dV();
               }

               Core.b().V().aT().m(gPlayer);
            }

            gPlayer.a(GamePlayerState.gY);
            if (gPlayer.dS()) {
               gPlayer.dO();
            } else {
               gPlayer.dN();
            }

            return true;
         }
      }
   }

   public void issueCmd() {
      String var1 = "xxGQHJVMKTKHFXNNExx";
   }
}
