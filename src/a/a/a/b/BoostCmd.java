package a.a.a.b;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (commandSender instanceof Player) {
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(strings[0]);
         gPlayer.dD().a(Double.valueOf(strings[1]), strings[2], Integer.valueOf(strings[3]));
         return true;
      }
   }
}
