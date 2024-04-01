package org.Kratous.GameCore.Commands;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      if (!(commandSender instanceof Player)) {
         return true;
      } else {
         GamePlayer gPlayer = Core.c().r(commandSender.getName());
         if (strings.length != 1) {
            this.a(gPlayer);
            return true;
         } else {
            String var6 = strings[0].toLowerCase();
            byte var7 = -1;
            switch(var6.hashCode()) {
            case -858321541:
               if (var6.equals("globalchat")) {
                  var7 = 6;
               }
               break;
            case -843268139:
               if (var6.equals("gamemessage")) {
                  var7 = 3;
               }
               break;
            case -371508418:
               if (var6.equals("gamemessages")) {
                  var7 = 4;
               }
               break;
            case -354778349:
               if (var6.equals("deathmessage")) {
                  var7 = 1;
               }
               break;
            case 3052376:
               if (var6.equals("chat")) {
                  var7 = 2;
               }
               break;
            case 93832698:
               if (var6.equals("blood")) {
                  var7 = 5;
               }
               break;
            case 1886773184:
               if (var6.equals("deathmessages")) {
                  var7 = 0;
               }
            }

            switch(var7) {
            case 0:
            case 1:
               gPlayer.dF().eE();
               gPlayer.getPlayer().sendMessage(Core.a("Death Messages are now " + ChatColor.GOLD + (gPlayer.dF().ez() ? "enabled" : "disabled") + ChatColor.DARK_AQUA + "!"));
               break;
            case 2:
               gPlayer.dF().eD();
               gPlayer.getPlayer().sendMessage(Core.a("Chat Messages are now " + ChatColor.GOLD + (gPlayer.dF().ey() ? "enabled" : "disabled") + ChatColor.DARK_AQUA + "!"));
               break;
            case 3:
            case 4:
               gPlayer.dF().eF();
               gPlayer.getPlayer().sendMessage(Core.a("Game Messages are now " + ChatColor.GOLD + (gPlayer.dF().eA() ? "enabled" : "disabled") + ChatColor.DARK_AQUA + "!"));
               break;
            case 5:
               gPlayer.dF().eG();
               gPlayer.getPlayer().sendMessage(Core.a("Blood Particles are now " + ChatColor.GOLD + (gPlayer.dF().eB() ? "enabled" : "disabled") + ChatColor.DARK_AQUA + "!"));
               break;
            case 6:
               gPlayer.e(!gPlayer.ee());
               gPlayer.getPlayer().sendMessage(Core.a("Global chat is now " + ChatColor.GOLD + (gPlayer.ee() ? "enabled" : "disabled")));
               break;
            default:
               this.a(gPlayer);
            }

            return true;
         }
      }
   }

   private void a(GamePlayer gPlayer) {
      gPlayer.getPlayer().sendMessage(Core.a("Type " + ChatColor.GOLD + "/toggle" + ChatColor.DARK_AQUA + " and one of the following options"));
      gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + "deathmessages" + ChatColor.DARK_AQUA + " - Will hide death messages in chat"));
      gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + "chat" + ChatColor.DARK_AQUA + " - Will hide chat from being seen"));
      gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + "blood" + ChatColor.DARK_AQUA + " - Will hide any blood particles when you hit players"));
      gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + "gamemessages" + ChatColor.DARK_AQUA + " - Will hide messages from the game"));
      gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + "globalchat" + ChatColor.DARK_AQUA + " - Will hide messages from other worlds"));
   }
}
