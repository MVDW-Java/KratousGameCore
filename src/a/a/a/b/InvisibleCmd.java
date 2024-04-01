package a.a.a.b;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvisibleCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (commandSender.hasPermission("gamecore.invisible") && commandSender instanceof Player) {
         GamePlayer gPlayer = Core.c().a((Player)commandSender);
         if (gPlayer.dH()) {
            gPlayer.getPlayer().sendMessage(ChatColor.RED + "You cannot be invisible whilst participating");
            return true;
         } else {
            gPlayer.setInvisible(!gPlayer.isInvisible());
            if (gPlayer.isInvisible()) {
               gPlayer.getPlayer().sendMessage(Core.a("Invisible mode enabled"));
            } else {
               gPlayer.getPlayer().sendMessage(Core.a("Invisible mode disabled"));
            }

            gPlayer.dP();
            Core.b().ap();
            return true;
         }
      } else {
         return false;
      }
   }
}
