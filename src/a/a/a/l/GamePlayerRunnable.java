package a.a.a.l;

import a.a.a.Core;
//import a.a.a.k.TaskChain12;
import a.a.a.k.TaskChain4;
//import a.a.a.k.TaskChain6;

import java.util.Iterator;
import org.bukkit.scheduler.BukkitRunnable;

public class GamePlayerRunnable extends BukkitRunnable {
   private final int gW = 300;

   public GamePlayerRunnable() {
      this.runTaskTimer(Core.a(), 0L, 20L);
   }

   public void run() {
      /*TaskChain12.dx().a((TaskChain8)(new TaskChain6() {
         protected void run() {
            Iterator var1 = Core.c().getPlayers().iterator();

            while(true) {
               GamePlayer gPlayer;
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  gPlayer = (GamePlayer)var1.next();
               } while(gPlayer.getPlayer() != null && gPlayer.getPlayer().isOnline());

               Core.b().V().aT().m(gPlayer);
               Core.c().m(gPlayer);
            }
         }
      })).a((TaskChain8)(new TaskChain4() {
         protected void run() {
         }
      })).execute();*/
   }
}
