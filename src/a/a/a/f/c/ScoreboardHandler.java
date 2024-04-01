package a.a.a.f.c;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardHandler {
   private Scoreboard eh = Bukkit.getScoreboardManager().getNewScoreboard();
   private String ei;
   private int ej = 10;

   public ScoreboardHandler() {
      this.cL();
      this.cJ();
      this.cn();
   }

   public Objective j(String title) {
      Iterator var2 = this.eh.getObjectives().iterator();

      while(var2.hasNext()) {
         Objective old = (Objective)var2.next();
         old.unregister();
      }

      Objective obj = this.eh.registerNewObjective(title, "dummy");
      obj.setDisplaySlot(DisplaySlot.SIDEBAR);
      this.ei = title;
      this.cn();
      return obj;
   }

   public void m(int seconds) {
      Objective obj = this.eh.getObjective(DisplaySlot.SIDEBAR);
      if (obj != null) {
         if (seconds > 0) {
            int mins = (int)Math.floor((double)seconds / 60.0D);
            seconds -= mins * 60;
            String minsString = mins < 10 ? "0" + mins : mins + "";
            String secondsString = seconds < 10 ? "0" + seconds : seconds + "";
            obj.setDisplayName(this.ei + ChatColor.GOLD + " [" + minsString + ":" + secondsString + "]");
         } else {
            obj.setDisplayName(this.ei);
         }

      }
   }

   public Objective getObjective() {
      return this.eh.getObjective(DisplaySlot.SIDEBAR);
   }

   public Team a(String teamName, String prefix, String suffix) {
      if (teamName.length() > 16) {
         teamName = teamName.substring(0, 15);
      }

      Team old = this.eh.getTeam(teamName);
      if (old != null) {
         old.unregister();
      }

      Team team = this.eh.registerNewTeam(teamName);
      team.setPrefix(prefix);
      team.setSuffix(suffix);
      this.cn();
      return team;
   }

   public void d(GamePlayer gPlayer, String team) {
      boolean teamExists = false;
      if (team.length() > 16) {
         team = team.substring(0, 15);
      }

      Iterator var4 = this.eh.getTeams().iterator();

      while(var4.hasNext()) {
         Team t = (Team)var4.next();
         if (t.hasPlayer(gPlayer.getPlayer())) {
            t.removePlayer(gPlayer.getPlayer());
            this.cn();
         }

         if (t.getName().equals(team)) {
            teamExists = true;
         }
      }

      if (!teamExists) {
         this.a(team, "", "");
      }

      this.eh.getTeam(team).addPlayer(gPlayer.getPlayer());
      this.cn();
   }

   public void t(GamePlayer gPlayer) {
      Iterator var2 = this.eh.getTeams().iterator();

      while(var2.hasNext()) {
         Team t = (Team)var2.next();
         if (t.hasPlayer(gPlayer.getPlayer())) {
            t.removePlayer(gPlayer.getPlayer());
         }
      }

      this.cn();
   }

   public void cJ() {
      Iterator var1 = this.eh.getTeams().iterator();

      while(var1.hasNext()) {
         Team team = (Team)var1.next();
         if (!team.getName().equals("spectator")) {
            team.unregister();
         }
      }

   }

   public void cK() {
      this.cJ();
      this.cM();
      this.cL();
      this.cn();
   }

   public void cL() {
      Iterator var1 = this.eh.getObjectives().iterator();

      while(var1.hasNext()) {
         Objective objective = (Objective)var1.next();
         objective.unregister();
      }

   }

   public void cM() {
      Iterator var1 = this.eh.getEntries().iterator();

      while(var1.hasNext()) {
         String name = (String)var1.next();
         this.eh.resetScores(name);
      }

   }

   public void k(String name) {
      if (this.eh.getObjective(DisplaySlot.SIDEBAR) != null) {
         if (name.length() > 16) {
            name = name.substring(0, 16);
         }

         if (this.eh.getEntries().contains(name)) {
            this.eh.resetScores(name);
            this.cn();
         }

      }
   }

   public void n(int score) {
      if (this.eh.getObjective(DisplaySlot.SIDEBAR) != null) {
         Iterator var2 = this.eh.getEntries().iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();
            if (this.eh.getObjective(DisplaySlot.SIDEBAR).getScore(name).getScore() == score) {
               this.eh.resetScores(name);
            }
         }

      }
   }

   public void u(GamePlayer gamePlayer) {
      gamePlayer.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
   }

   public void a(String name, int Score) {
      if (this.eh.getObjective(DisplaySlot.SIDEBAR) != null) {
         if (name.length() > 16) {
         }

         this.eh.getObjective(DisplaySlot.SIDEBAR).getScore(name).setScore(Score);
         this.cn();
      }
   }

   public void cn() {
      Iterator var1 = Core.c().getPlayers().iterator();

      while(var1.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var1.next();
         if (!gPlayer.dS()) {
            gPlayer.getPlayer().setScoreboard(this.eh);
         }
      }

   }

   public void v(GamePlayer gPlayer) {
      gPlayer.getPlayer().setScoreboard(this.eh);
   }

   public String w(GamePlayer gPlayer) {
      Iterator var2 = this.eh.getTeams().iterator();

      Team team;
      do {
         if (!var2.hasNext()) {
            return "";
         }

         team = (Team)var2.next();
      } while(!team.getPlayers().contains(gPlayer.getPlayer()));

      return team.getPrefix();
   }

   public static String o(int length) {
      String t = "";

      for(int i = 0; i < length; ++i) {
         t = t + " ";
      }

      return t;
   }
}
