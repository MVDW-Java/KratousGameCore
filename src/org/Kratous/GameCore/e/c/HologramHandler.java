package org.Kratous.GameCore.e.c;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HologramHandler {
   private List aR = new ArrayList();
   private Map aS = new HashMap();

   public void k(GamePlayer gPlayer) {
      if (!this.aS.containsKey(gPlayer)) {
         this.aS.put(gPlayer, new ArrayList());
      }

      List<Hologram1> hidden = new ArrayList();
      List<Hologram1> visible = new ArrayList();
      Iterator var4 = ((List)this.aS.get(gPlayer)).iterator();

      Hologram1 hologram;
      while(var4.hasNext()) {
         hologram = (Hologram1)var4.next();
         if (!hologram.getLocation().getWorld().equals(gPlayer.getPlayer().getWorld())) {
            hologram.j(gPlayer);
            hidden.add(hologram);
         }
      }

      var4 = this.aR.iterator();

      while(var4.hasNext()) {
         hologram = (Hologram1)var4.next();
         if (hologram.getLocation().getWorld().equals(gPlayer.getPlayer().getWorld()) && !((List)this.aS.get(gPlayer)).contains(hologram)) {
            hologram.i(gPlayer);
            visible.add(hologram);
         }
      }

      ((List)this.aS.get(gPlayer)).addAll(visible);
      ((List)this.aS.get(gPlayer)).removeAll(hidden);
   }

   public void b(Hologram1 hologram) {
      this.aR.add(hologram);
      Iterator var2 = Core.c().getPlayers().iterator();

      while(var2.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var2.next();
         this.k(gPlayer);
      }

   }
}
