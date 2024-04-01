package a.a.a.Commands;

import a.a.a.Core;
import a.a.a.k.TaskChain11;
//import a.a.a.k.TaskChain12;
import a.a.a.k.TaskChain4;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadMapsCmd2 implements CommandExecutor {
   public boolean onCommand(final CommandSender commandSender, Command command, String s, String[] strings) {
      if (!commandSender.hasPermission("gamecore.reloadmaps")) {
         return false;
      } else {
         /*TaskChain12.dx().a((TaskChain8)(new TaskChain4() {
            protected void run() {
               Core.b().ac();
            }
         })).a((TaskChain8)(new TaskChain11() {
            protected void a(Object arg) {
               commandSender.sendMessage(ChatColor.GOLD + "Maps successfully reloaded");
               commandSender.sendMessage(ChatColor.GOLD.toString() + Core.b().Z() + " new maps and " + Core.b().Y() + " maps errored");
               commandSender.sendMessage(ChatColor.GOLD.toString() + Core.b().af().size() + ChatColor.YELLOW.toString() + " maps in server, " + ChatColor.GOLD.toString() + Core.b().ah().size() + ChatColor.YELLOW.toString() + " maps in rotation");
            }
         })).execute();*/
         return true;
      }
   }
}
