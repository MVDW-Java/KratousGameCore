package a.a.a.b;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpyCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.spy")) {
         return false;
      } else if (!(commandSender instanceof Player)) {
         commandSender.sendMessage("This command can only be used from in-game");
         return true;
      } else {
         GamePlayer gPlayer = Core.c().a((Player)commandSender);
         gPlayer.dF().eH();
         if (gPlayer.dF().eC()) {
            gPlayer.getPlayer().sendMessage(Core.a("Chat spying enabled"));
         } else {
            gPlayer.getPlayer().sendMessage(Core.a("Chat spying disabled"));
         }

         return true;
      }
   }
}
