package org.Kratous.GameCore.h.b;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.m.Rank;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class PersonalScoreboard1 {
   private Scoreboard eh;
   private GamePlayer er;
   private List fk;

   public PersonalScoreboard1(Scoreboard scoreboard, GamePlayer gPlayer) {
      this.eh = scoreboard;
      this.er = gPlayer;
      this.fk = new ArrayList();
      Objective sideBar = this.eh.registerNewObjective("PERSONAL", "dummy");
      sideBar.setDisplaySlot(DisplaySlot.SIDEBAR);
   }

   public Scoreboard getScoreboard() {
      return this.eh;
   }

   public void dl() {
      this.fk.add(new ScoreboardItem("Welcome to " + Core.a().getConfig().getString("game.serverName"), ChatColor.BLUE, PersonalScoreboardType.fn, -1));
      this.fk.add(new ScoreboardItem("--------------", ChatColor.GRAY, PersonalScoreboardType.fo, 4));
      this.fk.add(new ScoreboardItem("Current Map:", ChatColor.GREEN, PersonalScoreboardType.fo, 3));
      this.fk.add(new ScoreboardItem("", ChatColor.DARK_AQUA, PersonalScoreboardType.fq, 2));
      this.fk.add(new ScoreboardItem("", ChatColor.DARK_AQUA, PersonalScoreboardType.fr, 1));
   }

   public void cn() {
      Iterator var1 = this.fk.iterator();

      while(true) {
         while(var1.hasNext()) {
            ScoreboardItem item = (ScoreboardItem)var1.next();
            Objective sideBar = this.eh.getObjective(DisplaySlot.SIDEBAR);
            String previousName = item.dn();
            switch(item.dm()) {
            case fn:
               sideBar.setDisplayName(item.toString());
               continue;
            case fp:
               item.l(String.valueOf(this.er.dZ()));
               break;
            case fw:
               item.l(String.valueOf(this.er.ea()));
               break;
            case fq:
               if (Core.b().V() != null) {
                  item.l(Core.b().V().aS().bk());
               }
               break;
            case fr:
               if (Core.b().V() != null) {
                  item.l((Core.b().V().aT() == null ? 0 : Core.b().V().aT().getPlayers().size()) + " / " + Core.b().V().aS().getMaxPlayers());
               }
               break;
            case fs:
               if (Core.b().W() != null) {
                  item.l(Core.b().W().aS().bk());
               }
               break;
            case ft:
               Rank currentRank = Core.h().z(this.er.dD().eV());
               Rank nextRank = Core.h().A(this.er.dD().eV());
               item.l("Level " + currentRank.eZ() + " (" + this.er.dD().eV() + " / " + nextRank.fa() + ")");
               break;
            case fu:
               item.setEnabled(Core.b().V().aS().bn().bd());
               if (this.er.dK() != null) {
                  item.l(this.er.dK().by());
               } else {
                  item.l("No active kit!");
               }
               break;
            case fv:
               item.setEnabled(Core.b().V().aS().bn().bd());
            }

            String nextName = item.toString();
            if (!previousName.equals(nextName)) {
               int score = item.getScore();
               sideBar.getScoreboard().resetScores(previousName);
               if (item.isEnabled()) {
                  sideBar.getScore(nextName).setScore(score);
               }
            }
         }

         Objective belowName = this.eh.getObjective(DisplaySlot.BELOW_NAME);

         GamePlayer var9;
         for(Iterator var8 = Core.c().getPlayers().iterator(); var8.hasNext(); var9 = (GamePlayer)var8.next()) {
         }

         this.er.getPlayer().setScoreboard(this.eh);
         return;
      }
   }
}
