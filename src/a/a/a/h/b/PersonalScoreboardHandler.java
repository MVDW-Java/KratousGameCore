package a.a.a.h.b;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PersonalScoreboardHandler {
   private ConcurrentHashMap fm = new ConcurrentHashMap();

   public void z(GamePlayer gPlayer) {
      if (!this.fm.containsKey(gPlayer) && gPlayer.dS()) {
         Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
         PersonalScoreboard1 personalScoreboard = new PersonalScoreboard1(scoreboard, gPlayer);
         personalScoreboard.dl();
         ChatColor[] var4 = ChatColor.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ChatColor color = var4[var6];
            Team team = scoreboard.registerNewTeam(color.toString());
            team.setPrefix(color.toString());
            team.setSuffix(ChatColor.RESET.toString());
         }

         this.fm.put(gPlayer, personalScoreboard);
         Iterator var9 = Core.c().getPlayers().iterator();

         while(var9.hasNext()) {
            GamePlayer gP = (GamePlayer)var9.next();
            if (gP.dH() && gP.ca() != null) {
               scoreboard.getTeam(gP.ca().bP().toString()).addPlayer(gP.getPlayer());
            }
         }

         gPlayer.getPlayer().setScoreboard(scoreboard);
      }
   }

   public void A(GamePlayer gPlayer) {
      if (this.fm.containsKey(gPlayer)) {
         PersonalScoreboard1 personalScoreboard = (PersonalScoreboard1)this.fm.get(gPlayer);
         gPlayer.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
         Iterator var3 = personalScoreboard.getScoreboard().getObjectives().iterator();

         while(var3.hasNext()) {
            Objective obj = (Objective)var3.next();
            obj.unregister();
         }

         this.fm.remove(gPlayer);
      }

   }

   public void a(GamePlayer gPlayer, ChatColor team) {
      this.t(gPlayer);
      Iterator var3 = this.fm.values().iterator();

      while(var3.hasNext()) {
         PersonalScoreboard1 scoreboard = (PersonalScoreboard1)var3.next();
         scoreboard.getScoreboard().getTeam(team.toString()).addPlayer(gPlayer.getPlayer());
      }

   }

   public void t(GamePlayer gPlayer) {
      Iterator var2 = this.fm.values().iterator();

      while(var2.hasNext()) {
         PersonalScoreboard1 scoreboard = (PersonalScoreboard1)var2.next();
         Iterator var4 = scoreboard.getScoreboard().getTeams().iterator();

         while(var4.hasNext()) {
            Team team = (Team)var4.next();
            if (team.getPlayers().contains(gPlayer.getPlayer())) {
               team.removePlayer(gPlayer.getPlayer());
            }
         }
      }

   }

   public void update() {
      Iterator var1 = Core.c().getPlayers().iterator();

      GamePlayer gPlayer;
      while(var1.hasNext()) {
         gPlayer = (GamePlayer)var1.next();
         if (gPlayer.dS() && !this.fm.containsKey(gPlayer)) {
            this.z(gPlayer);
         }
      }

      Enumeration players = this.fm.keys();

      while(players.hasMoreElements()) {
         gPlayer = (GamePlayer)players.nextElement();
         if (!gPlayer.dS()) {
            this.A(gPlayer);
         } else {
            PersonalScoreboard1 scoreboard = (PersonalScoreboard1)this.fm.get(gPlayer);
            scoreboard.cn();
         }
      }

   }

   public void destroy() {
      Enumeration players = this.fm.keys();

      while(players.hasMoreElements()) {
         GamePlayer gPlayer = (GamePlayer)players.nextElement();
         this.A(gPlayer);
      }

      this.fm.clear();
   }
}
