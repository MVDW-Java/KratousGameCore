package a.a.a.f.b;

import a.a.a.Core;
import a.a.a.f.GameMap;
import a.a.a.f.GameState;
import a.a.a.f.a.MapTeam;
import a.a.a.g.a.PlayerKilledEvent;
import a.a.a.q.Util3;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;

public class TeamDeathmatchGame extends GameMap {
   private Map dA = new HashMap();

   public void ax() {
      Iterator var1 = this.bl.aS().bv().values().iterator();

      while(var1.hasNext()) {
         MapTeam team = (MapTeam)var1.next();
         this.dA.put(team, 0);
      }

   }

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "TDM";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "TDM");
      this.cn();
   }

   public MapTeam aE() {
      MapTeam winningTeam = (MapTeam)((Entry)Util3.b(this.dA).get(0)).getKey();
      Iterator var2 = this.dA.keySet().iterator();

      MapTeam team;
      do {
         if (!var2.hasNext()) {
            return winningTeam;
         }

         team = (MapTeam)var2.next();
      } while(winningTeam.equals(team) || !((Integer)this.dA.get(team)).equals(this.dA.get(winningTeam)));

      return null;
   }

   @EventHandler
   public void a(PlayerKilledEvent e) {
      this.dA.put(e.cQ().ca(), (Integer)this.dA.get(e.cQ().ca()) + 1);
      this.cn();
   }

   private void cn() {
      Iterator var1 = this.dA.keySet().iterator();

      while(var1.hasNext()) {
         MapTeam team = (MapTeam)var1.next();
         Core.e().a(team.bP() + team.getName(), (Integer)this.dA.get(team));
      }

      Core.e().cn();
   }
}
