package a.a.a.b;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import a.a.a.q.Util3;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TutorialCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         if (gPlayer.dH()) {
            gPlayer.getPlayer().sendMessage(ChatColor.RED + "You cannot join the tutorial while playing");
            return true;
         } else {
            gPlayer.getPlayer().openInventory(Util3.H(gPlayer));
            return true;
         }
      }
   }
}
