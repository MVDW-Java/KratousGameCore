package a.a.a.e.a;

import a.a.a.Core;
import a.a.a.e.a.a.FakeWither;
import a.a.a.l.GamePlayer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BarHandler {
   private Map aB = new HashMap();

   public void a(GamePlayer gPlayer, float numerator, float denominator, String message) {
      float percentage = numerator / denominator * 100.0F;
      if (percentage < 0.0F) {
         percentage = 1.0E-4F;
      } else if (percentage > 100.0F) {
         percentage = 100.0F;
      }

      FakeWither dragon = this.a(gPlayer, message);
      dragon.setName(message);
      dragon.setHealth(percentage);
      dragon.a(gPlayer.getPlayer().getEyeLocation(), gPlayer.getPlayer().getEyeLocation().getDirection());
      gPlayer.sendPacket(dragon.I());
      gPlayer.sendPacket(dragon.e(gPlayer));
   }

   public void a(float numerator, float denominator, String message) {
      float percentage = numerator / denominator * 100.0F;
      if (percentage < 0.0F) {
         percentage = 0.0F;
      } else if (percentage > 100.0F) {
         percentage = 100.0F;
      }

      Iterator var5 = Core.c().getPlayers().iterator();

      while(var5.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var5.next();
         FakeWither dragon = this.a(gPlayer, message);
         dragon.setName(message);
         dragon.setHealth(percentage);
         dragon.a(gPlayer.getPlayer().getEyeLocation(), gPlayer.getPlayer().getEyeLocation().getDirection());
         gPlayer.sendPacket(dragon.I());
         gPlayer.sendPacket(dragon.e(gPlayer));
      }

   }

   public void c(GamePlayer gPlayer) {
      if (this.aB.containsKey(gPlayer)) {
         FakeWither dragon = (FakeWither)this.aB.get(gPlayer);
         gPlayer.sendPacket(dragon.I());
         gPlayer.sendPacket(dragon.J());
      }
   }

   public void d(GamePlayer gPlayer) {
      if (this.aB.containsKey(gPlayer)) {
         FakeWither dragon = (FakeWither)this.aB.get(gPlayer);
         gPlayer.sendPacket(dragon.L());
         this.aB.remove(gPlayer);
      }
   }

   public void H() {
      Iterator var1 = Core.c().getPlayers().iterator();

      while(var1.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var1.next();
         this.d(gPlayer);
      }

      this.aB.clear();
   }

   private FakeWither a(GamePlayer gPlayer, String message) {
      FakeWither dragon = null;
      if (this.aB.containsKey(gPlayer) && ((FakeWither)this.aB.get(gPlayer)).getLocation().getWorld().equals(gPlayer.getPlayer().getLocation().getWorld())) {
         dragon = (FakeWither)this.aB.get(gPlayer);
         if (!dragon.getLocation().getWorld().equals(gPlayer.getPlayer().getWorld())) {
            gPlayer.sendPacket(dragon.L());
            dragon = new FakeWither(message, gPlayer.getPlayer().getLocation().subtract(0.0D, -300.0D, 0.0D));
            gPlayer.sendPacket(dragon.K());
            gPlayer.sendPacket(dragon.J());
            this.aB.put(gPlayer, dragon);
         }
      } else {
         dragon = new FakeWither(message, gPlayer.getPlayer().getLocation().subtract(0.0D, -300.0D, 0.0D));
         gPlayer.sendPacket(dragon.K());
         gPlayer.sendPacket(dragon.J());
         this.aB.put(gPlayer, dragon);
      }

      return dragon;
   }

   public void destroy() {
      this.H();
      this.aB.clear();
      this.aB = null;
   }
}
