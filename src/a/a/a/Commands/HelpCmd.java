package a.a.a.Commands;

import a.a.a.Core;
import a.a.a.f.a.MapData;
import a.a.a.q.Util3;
import java.util.Iterator;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCmd implements CommandExecutor {
   public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
      MapData data = Core.b().V().aS();
      String authors = "";

      String author;
      for(Iterator var7 = data.bo().iterator(); var7.hasNext(); authors = authors + ", " + author) {
         author = (String)var7.next();
      }

      authors = authors.substring(2, authors.length());
      if (commandSender instanceof Player) {
         Player cs = (Player)commandSender;
         TextComponent msg = new TextComponent(ChatColor.DARK_AQUA + "GameCore - Game Management Plugin");
         msg.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.DARK_AQUA + "Licenced to: " + ChatColor.GOLD + Core.a().getConfig().getString("game.serverName") + "\n" + ChatColor.DARK_AQUA + "Version: " + ChatColor.GOLD + Core.a().getDescription().getVersion())).create()));
         cs.spigot().sendMessage(msg);
      }

      commandSender.sendMessage(ChatColor.DARK_AQUA + "Map: " + ChatColor.GOLD + data.bk() + ChatColor.DARK_AQUA + " - " + ChatColor.GOLD + Util3.u(data.bj()));
      commandSender.sendMessage(ChatColor.DARK_AQUA + "Created by: " + ChatColor.GOLD + authors);
      commandSender.sendMessage(ChatColor.DARK_AQUA + "Map version: " + ChatColor.GOLD + "v" + data.bl());
      commandSender.sendMessage(ChatColor.DARK_AQUA + "Objective: " + ChatColor.GOLD + data.bm());
      return true;
   }
}
