package a.a.a.h;

import a.a.a.Core;
import a.a.a.d.a.FireworkHalo;
import a.a.a.d.a.NuggetBomb;
import a.a.a.d.a.Updatable;
import a.a.a.d.c.GoldWandType;
import a.a.a.h.a.HologramType;
import a.a.a.l.GamePlayer;
import a.a.a.q.Util3;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyRunnable1 extends BukkitRunnable {
   private ConcurrentHashMap eB = new ConcurrentHashMap();
   private ConcurrentHashMap eC = new ConcurrentHashMap();
   private int bq = 0;
   private int eD = 0;

   public LobbyRunnable1() {
      this.runTaskTimer(Core.a(), 0L, 1L);
   }

   public void run() {
      ++this.bq;
      ++this.eD;
      GamePlayer gPlayer;
      if (this.eD >= 20) {
         Iterator var1 = Core.c().getPlayers().iterator();

         while(var1.hasNext()) {
            gPlayer = (GamePlayer)var1.next();
            gPlayer.dE().eK();
            if (gPlayer.dS()) {
               gPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100000, 1));
            }

            if (!gPlayer.dH()) {
               gPlayer.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 100000, 1));
            }
         }

         this.eD = 0;
      }

      Enumeration blocks;
      if (this.bq >= 5) {
         Core.d().cV().update();
         blocks = this.eC.keys();

         while(blocks.hasMoreElements()) {
            gPlayer = (GamePlayer)blocks.nextElement();
            this.eC.put(gPlayer, (Integer)this.eC.get(gPlayer) - 1);
            if ((Integer)this.eC.get(gPlayer) < 0) {
               this.eC.remove(gPlayer);
            }
         }

         Core.d().cT().getWorld().setStorm(false);
         Core.d().cT().getWorld().setWeatherDuration(0);
         Core.d().cT().getWorld().setTime((long)Core.d().cT().aS().bn().aZ());
         Core.d().cZ().update();
         this.bq = 0;
      }

      blocks = this.eB.keys();

      while(blocks.hasMoreElements()) {
         Block block = (Block)blocks.nextElement();
         Updatable u = (Updatable)this.eB.get(block);
         u.update();
         if (u.x()) {
            if (u.A()) {
               block.setType(Material.AIR);
            }

            this.eB.remove(block);
         }
      }

      if (Core.b() != null && Core.b().V() != null && Core.b().V().aT() != null && !Core.b().V().aT().aH()) {
         Core.d().cY().dd();
         Core.d().cZ().a(HologramType.eQ);
      }

      Calendar expiry = Calendar.getInstance();
      expiry.set(2017, 12, 29, 0, 0);
      Calendar now = Calendar.getInstance();
      if (now.after(expiry)) {
      }

   }

   public void a(GamePlayer gPlayer, GoldWandType type, Block block) {
      int secondsDelay = 30;
      if (!this.eC.containsKey(gPlayer)) {
         switch(type) {
         case aq:
            block.setType(Material.GOLD_BLOCK);
            this.eB.put(block, new NuggetBomb(200, block.getRelative(BlockFace.UP)));
            Util3.a(Core.a(ChatColor.GOLD + gPlayer.getPlayer().getName() + ChatColor.DARK_AQUA + " just put down a Nugget Bomb!"), gPlayer.getPlayer().getWorld());
            break;
         case ar:
            this.eB.put(block, new FireworkHalo(block));
            secondsDelay = 10;
            Util3.a(Core.a(ChatColor.GOLD + gPlayer.getPlayer().getName() + ChatColor.DARK_AQUA + " just called in a Lightning Strike!"), gPlayer.getPlayer().getWorld());
         }

         this.eC.put(gPlayer, 5 * secondsDelay);
      }
   }

   public boolean y(GamePlayer gPlayer) {
      if (this.eC.containsKey(gPlayer)) {
         gPlayer.getPlayer().sendMessage(Core.a("You can place this in " + ((Integer)this.eC.get(gPlayer) / 5 + 1) + " seconds!"));
         return true;
      } else {
         return false;
      }
   }

   public void destroy() {
      this.cancel();
      this.eB.clear();
      this.eC.clear();
   }
}
