package a.a.a.Commands;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ParticlesCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         return true;
      } else if (strings.length != 1) {
         commandSender.sendMessage(ChatColor.DARK_AQUA + "Usage: " + ChatColor.GOLD + "/particles <action>");
         commandSender.sendMessage(ChatColor.DARK_AQUA + "Actions: " + ChatColor.GOLD + "clear");
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         String var6 = strings[0].toLowerCase();
         byte var7 = -1;
         switch(var6.hashCode()) {
         case 94746189:
            if (var6.equals("clear")) {
               var7 = 0;
            }
         default:
            switch(var7) {
            case 0:
               gPlayer.dE().clearEffects();
               gPlayer.getPlayer().sendMessage(Core.a("All particles have been cleared"));
            default:
               return true;
            }
         }
      }
   }
}
