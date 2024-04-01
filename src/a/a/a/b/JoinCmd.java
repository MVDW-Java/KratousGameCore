package a.a.a.b;

import a.a.a.Core;
import a.a.a.f.a.MapTeam;
import a.a.a.l.GamePlayer;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         if (gPlayer.dG()) {
            return true;
         } else {
            if (strings.length == 1 && gPlayer.getPlayer().hasPermission("gamecore.chooseteam") && Core.b().V().aS().bv().size() > 1) {
               boolean found = false;
               Iterator var7 = Core.b().V().aS().bv().values().iterator();

               while(var7.hasNext()) {
                  MapTeam team = (MapTeam)var7.next();
                  if (team.getName().toLowerCase().contains(strings[0])) {
                     if (gPlayer.ca() != null && gPlayer.ca().equals(team)) {
                        found = true;
                        break;
                     }

                     if (!Core.b().V().aT().b(gPlayer, team)) {
                        found = true;
                        break;
                     }

                     if (gPlayer.dH()) {
                        Core.b().V().aT().m(gPlayer);
                     }

                     Core.b().V().aT().a(gPlayer, team);
                     found = true;
                  }
               }

               if (!found) {
                  gPlayer.getPlayer().sendMessage(Core.a(ChatColor.RED + "Could not find team " + ChatColor.GOLD + strings[0]));
               }
            } else {
               if (gPlayer.dH()) {
                  gPlayer.getPlayer().sendMessage(ChatColor.RED + "You are already participating!");
                  return true;
               }

               Core.b().V().aT().l(gPlayer);
            }

            return true;
         }
      }
   }
}
