package a.a.a.k;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.io.IOException;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class ShutdownRunnable extends BukkitRunnable {
   private int bq = 10;

   public ShutdownRunnable() {
      this.runTaskTimer(Core.a(), 0L, 20L);
   }

   public void run() {
      Core.f().a((float)this.bq, 10.0F, ChatColor.RED + "Server is restarting in " + ChatColor.GOLD + this.bq + ChatColor.RED + " seconds");
      Iterator var1 = Core.c().getPlayers().iterator();

      while(var1.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var1.next();
         gPlayer.a(ChatColor.DARK_RED + "Server Restarting!", ChatColor.RED + "Rejoin in " + ChatColor.GOLD + this.bq + ChatColor.RED + " seconds", 1.0D, 1.0D, 1.0D);
      }

      if (this.bq <= 0) {
         if (Core.a().getConfig().getBoolean("game.mark2", false) && Core.a().getConfig().getString("game.name") != null) {
            try {
               Runtime.getRuntime().exec("mark2 send -n " + Core.a().getConfig().getString("game.name") + " ~restart");
            } catch (IOException var3) {
               Bukkit.getLogger().severe("Mark2 restarted failed! Revering to Spigot restart");
               Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            }
         } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
         }

         this.cancel();
      }

      --this.bq;
   }
}
