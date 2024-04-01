package a.a.a.h;

import a.a.a.d.c.WandHandler1;
import a.a.a.f.GameWorld;
import a.a.a.h.a.HologramHandler;
import a.a.a.h.b.PersonalScoreboardHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Difficulty;
import org.bukkit.entity.ItemFrame;

public class LobbyHandler {
   private GameWorld eu;
   private PersonalScoreboardHandler ev = new PersonalScoreboardHandler();
   private LobbyRunnable1 ew;
   private WandHandler1 ex = new WandHandler1();
   private SelectableHandler ey = new SelectableHandler(this);
   private HologramHandler ez = new HologramHandler(this);
   private List eA = new ArrayList();

   public void b(GameWorld lobby) {
      if (this.eu == null) {
         this.eu = lobby;

         try {
            this.eu.load();
            this.cU();
            this.eu.getWorld().setDifficulty(Difficulty.PEACEFUL);
            this.ew = new LobbyRunnable1();
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

   }

   public GameWorld cT() {
      return this.eu;
   }

   private void cU() throws Exception {
      this.ey.a(this.eu.aS().bh());
      this.ez.b(this.eu.aS().bh());
   }

   public PersonalScoreboardHandler cV() {
      return this.ev;
   }

   public WandHandler1 cW() {
      return this.ex;
   }

   public LobbyRunnable1 cX() {
      return this.ew;
   }

   public SelectableHandler cY() {
      return this.ey;
   }

   public HologramHandler cZ() {
      return this.ez;
   }

   public void da() {
   }

   public void db() {
      Iterator var1 = this.eA.iterator();

      while(var1.hasNext()) {
         ItemFrame frame = (ItemFrame)var1.next();
         frame.remove();
      }

      this.eA.clear();
   }

   public void destroy() {
      try {
         this.ev.destroy();
         this.ew.destroy();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }
}
