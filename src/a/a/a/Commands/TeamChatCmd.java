package a.a.a.Commands;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamChatCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         commandSender.sendMessage("Command can only be sent from a player!");
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         if (gPlayer.dH() && Core.b().V().aT().aC().size() != 1) {
            if (!Core.a().getConfig().getBoolean("game.teamChat", false)) {
               gPlayer.getPlayer().sendMessage(ChatColor.RED + "Team chat is not enabled on this server");
               return true;
            } else {
               if (strings.length > 0) {
                  String message = "";
                  String[] var7 = strings;
                  int var8 = strings.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     String str = var7[var9];
                     message = message + str + " ";
                  }

                  message = gPlayer.ca().bP() + "[TEAM] " + gPlayer.getPlayer().getName() + ChatColor.RESET + ": " + message.trim();
                  Iterator var11 = Core.c().getPlayers().iterator();

                  while(true) {
                     GamePlayer player;
                     do {
                        if (!var11.hasNext()) {
                           return true;
                        }

                        player = (GamePlayer)var11.next();
                     } while((!player.dH() || !player.ca().equals(gPlayer.ca())) && !player.dF().eC());

                     if (player.dF().ey()) {
                        player.getPlayer().sendMessage(message);
                     }
                  }
               } else {
                  gPlayer.f(!gPlayer.ef());
                  if (gPlayer.ef()) {
                     gPlayer.getPlayer().sendMessage(Core.a("Team chat enabled"));
                  } else {
                     gPlayer.getPlayer().sendMessage(Core.a("Team chat disabled"));
                  }
               }

               return true;
            }
         } else {
            gPlayer.getPlayer().sendMessage(ChatColor.RED + "You don't have a team to talk to");
            return true;
         }
      }
   }
}
