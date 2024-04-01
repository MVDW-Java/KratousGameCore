package a.a.a.p;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import java.util.Iterator;

public class TutorialRunnable implements Runnable {
   public void run() {
      Iterator var1 = Core.c().getPlayers().iterator();

      while(true) {
         GamePlayer gPlayer;
         while(true) {
            do {
               do {
                  if (!var1.hasNext()) {
                     return;
                  }

                  gPlayer = (GamePlayer)var1.next();
               } while(!gPlayer.dG());
            } while(gPlayer.eq().fh() < 0);

            if (gPlayer.eq().fh() != 0) {
               break;
            }

            if (!gPlayer.eq().fk().getMessages().isEmpty()) {
               Iterator var3 = gPlayer.eq().fk().fu().iterator();

               while(var3.hasNext()) {
                  String str = (String)var3.next();
                  gPlayer.getPlayer().sendMessage(str);
               }
            }

            gPlayer.eq().fm();
            if (!gPlayer.eq().fl()) {
               gPlayer.eq().fn();
               break;
            }

            gPlayer.a(GamePlayerState.gY);
            gPlayer.dN();

            try {
               gPlayer.eq().unload();
               gPlayer.a((Tutorial)null);
            } catch (Exception var5) {
               var5.printStackTrace();
               break;
            }
         }

         gPlayer.eq().fj();
      }
   }
}
