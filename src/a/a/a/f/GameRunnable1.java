package a.a.a.f;

import a.a.a.Core;
import a.a.a.a.AchievementType;
import a.a.a.f.a.MapRewardType;
import a.a.a.f.a.MapTeam;
import a.a.a.j.LogHandler;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import a.a.a.q.Util3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRunnable1 extends BukkitRunnable {
   private GameWorld bl;
   private final int bp = 4;
   private int bq;
   private int br;
   private int bs;
   private int bt;
   private int bu;
   private boolean bv;
   private boolean bw;
   private boolean bx;

   public GameRunnable1(GameWorld gameWorld) {
      this.bl = gameWorld;
      this.bt = 0;
      this.bu = 0;
      this.e(4 * Core.a().getConfig().getInt("game.timer.lobby"));
      JavaPlugin var10001 = Core.a();
      this.getClass();
      this.runTaskTimer(var10001, 0L, (long)(20 / 4));
      Core.j().s(this.bl.aS().bk());
      this.bv = false;
      this.bw = false;
      this.bx = false;
      this.aM();
   }

   public void run() {
      this.bl.getWorld().setStorm(false);
      this.bl.getWorld().setWeatherDuration(0);
      if (this.br > 0) {
         --this.br;
      } else {
         LogHandler var10000;
         int var10001;
         Iterator var1;
         GamePlayer gPlayer;
         switch(this.bl.aU()) {
         case bz:
            Core.e().m(this.aL());
            if (!this.bw) {
               for(var1 = Core.c().getPlayers().iterator(); var1.hasNext(); gPlayer = (GamePlayer)var1.next()) {
               }

               this.bl.aT().ar();
               this.bw = true;
            }

            if (this.bq == 40) {
            }

            if (this.bq <= 0) {
               if (this.getPlayerCount() < Core.a().getConfig().getInt("game.minimumPlayers")) {
                  var10001 = Core.a().getConfig().getInt("game.timer.lobby");
                  this.getClass();
                  this.e(var10001 * 4);
                  this.getClass();
                  this.br = 3 * 4;
                  this.g(ChatColor.RED + "Need more players to start the game!");
               } else {
                  this.aM();
                  Core.g().m(this.bl.aS().bk());
                  var1 = this.bl.aT().getPlayers().iterator();

                  while(var1.hasNext()) {
                     gPlayer = (GamePlayer)var1.next();
                     String authors = "";

                     String author;
                     for(Iterator var4 = this.bl.aS().bo().iterator(); var4.hasNext(); authors = authors + ", " + author) {
                        author = (String)var4.next();
                     }

                     authors = authors.substring(2, authors.length());
                     gPlayer.getPlayer().sendMessage(ChatColor.DARK_GRAY + "#+ ");
                     gPlayer.getPlayer().sendMessage(ChatColor.DARK_GRAY + "#+ " + ChatColor.GOLD + Util3.u(this.bl.aS().bj()) + ChatColor.DARK_GRAY + " >> " + ChatColor.GREEN + this.bl.aS().bk());
                     gPlayer.getPlayer().sendMessage(ChatColor.DARK_GRAY + "#+ " + ChatColor.GOLD + "Created By: " + ChatColor.GREEN + authors);
                     gPlayer.getPlayer().sendMessage(ChatColor.DARK_GRAY + "#+ " + ChatColor.GOLD + "AIM: " + ChatColor.GREEN + this.bl.aS().bm());
                     gPlayer.getPlayer().sendMessage(ChatColor.DARK_GRAY + "#+ ");
                     gPlayer.dX();
                  }

                  this.aQ();
               }
            } else {
               this.g(ChatColor.GOLD + "" + this.aL() + ChatColor.GREEN + " seconds until the match!");
            }
            break;
         case bA:
            if (this.bq <= 0) {
               if (this.getPlayerCount() < Core.a().getConfig().getInt("game.minimumPlayers")) {
                  var10001 = Core.a().getConfig().getInt("game.timer.lobby");
                  this.getClass();
                  this.e(var10001 * 4);
                  this.getClass();
                  this.br = 3 * 4;
                  this.g(ChatColor.RED + "Need more players to start the game!");
               } else {
                  this.aQ();
               }
            } else {
               this.g(ChatColor.GOLD + "" + this.aL() + ChatColor.GREEN + " seconds until the match!");
            }
            break;
         case bB:
            this.g(ChatColor.DARK_AQUA + "Playing: " + ChatColor.GOLD + this.bl.aS().bk() + ChatColor.DARK_AQUA + " - " + Core.a().getConfig().getString("game.websiteUrl"));
            Core.e().m(this.aL());
            this.bl.aT().az();
            ++this.bt;
            if (!this.bx) {
               this.bx = true;
            }

            if (this.bl.aS().bn().bf()) {
               this.bl.aT().aB();
            }

            if (this.bl.aS().bt() && this.bt > 0 && this.bt % 1200 == 0) {
               this.bl.aT().at();
            }

            if (this.bu <= 0) {
               Random random = new Random(System.nanoTime());
               if (random.nextBoolean()) {
                  Core.b().V().aT().aw();
               }

               this.bu = (random.nextInt(30) + 20) * 4;
            }

            if (this.bq <= 0) {
               this.e(4 * Core.a().getConfig().getInt("game.timer.cooldown"));
               this.getClass();
               this.br = 3 * 4;
               this.bl.a(GameState.bD);
               this.bl.aT().aq();
               this.aO();
               var10000 = Core.g();
               var10001 = this.bt;
               this.getClass();
               var10000.q(var10001 / 4);
               this.aM();
               var1 = Core.c().getPlayers().iterator();

               while(var1.hasNext()) {
                  gPlayer = (GamePlayer)var1.next();
                  gPlayer.dO();
               }
            }
            break;
         case bD:
            this.g(ChatColor.GREEN + "Loading next map in " + ChatColor.GOLD + this.aL());
            Core.e().m(0);
            if (!this.bv) {
               var1 = Core.c().getPlayers().iterator();

               while(var1.hasNext()) {
                  gPlayer = (GamePlayer)var1.next();
                  if (!gPlayer.dG()) {
                     if (gPlayer.dH()) {
                        this.bl.aT().m(gPlayer);
                        gPlayer.getPlayer().setAllowFlight(true);
                        gPlayer.getPlayer().setFlying(true);
                        gPlayer.dO();
                     }

                     gPlayer.dU();
                  }
               }

               this.bv = true;
               Core.b().al();
            }

            if (!this.bx) {
               this.aR();
            } else if (this.bq <= 0) {
               this.aR();
            }
            break;
         case bC:
            this.bl.a(GameState.bD);
            this.bl.aT().aq();
            if (this.bl.aT().getPlayers().isEmpty()) {
               this.e(1);
            } else {
               if (this.bx) {
                  this.aO();
                  var10000 = Core.g();
                  var10001 = this.bt;
                  this.getClass();
                  var10000.q(var10001 / 4);
               }

               this.getClass();
               this.br = 3 * 4;
               this.e(4 * Core.a().getConfig().getInt("game.timer.cooldown"));
            }

            Core.e().m(0);
            break;
         case bE:
            return;
         }

         if (this.bl.aS().bn().ba()) {
            this.bl.getWorld().setTime((long)this.bl.aS().bn().aZ());
         }

         --this.bq;
         --this.bu;
      }
   }

   private int aL() {
      int var10000 = this.bq;
      this.getClass();
      return (int)Math.ceil((double)(var10000 / 4));
   }

   private void e(int length) {
      this.bq = length;
      this.bs = length;
   }

   private void g(String message) {
      if (Core.a().getConfig().getBoolean("game.bossbarEnabled", false)) {
         Iterator var2 = Core.c().getPlayers().iterator();

         while(var2.hasNext()) {
            GamePlayer gPlayer = (GamePlayer)var2.next();
            Core.f().a(gPlayer, (float)this.bq, (float)this.bs, message);
         }
      } else if ((this.aL() > 30 && this.aL() % 30 == 0 || this.aL() <= 30 && this.aL() % 5 == 0) && this.bq % 4 == 0) {
         if (this.bl.aU().equals(GameState.bB)) {
            int mins = (int)Math.floor((double)(this.aL() / 60));
            int seconds = this.aL() - mins * 60;
            String minsString = mins < 10 ? "0" + mins : mins + "";
            String secondsString = seconds < 10 ? "0" + seconds : seconds + "";
            message = ChatColor.GOLD + "[" + minsString + ":" + secondsString + "] " + ChatColor.DARK_AQUA + message;
         } else if (this.bl.aU().equals(GameState.bD)) {
            return;
         }

         Core.a(message, this.bl.aU().equals(GameState.bB), true);
      }

   }

   private void aM() {
      Core.f().H();
   }

   private int getPlayerCount() {
      int players = 0;

      GamePlayer gPlayer;
      for(Iterator var2 = Core.c().getPlayers().iterator(); var2.hasNext(); players += gPlayer.dH() ? 1 : 0) {
         gPlayer = (GamePlayer)var2.next();
      }

      return players;
   }

   private void aN() {
      Iterator var1 = Core.c().getPlayers().iterator();

      while(true) {
         GamePlayer gPlayer;
         do {
            if (!var1.hasNext()) {
               return;
            }

            gPlayer = (GamePlayer)var1.next();
         } while(!gPlayer.dH() && !gPlayer.dI());

         gPlayer.a(GamePlayerState.ha);
      }
   }

   private void a(boolean spectators) {
      Iterator var2 = Core.c().getPlayers().iterator();

      while(var2.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var2.next();
         if (gPlayer.dH()) {
            gPlayer.dN();
         } else {
            gPlayer.dO();
         }
      }

   }

   private void aO() {
      Core.a(ChatColor.DARK_GRAY + "#+ ", true, false);
      Core.a(ChatColor.DARK_GRAY + "#+ " + ChatColor.DARK_AQUA + "Game Over!", true, false);
      List<GamePlayer> winningPlayers = new ArrayList();
      MapTeam winningTeam = null;
      GamePlayer gPlayer;
      Iterator var7;
      if (this.bl.aS().bv().size() == 1) {
         int i = 1;
         gPlayer = null;

         Iterator var5;
         GamePlayer gPlayer1;
         for(var5 = this.bl.aT().aD().iterator(); var5.hasNext(); ++i) {
            gPlayer = (GamePlayer)var5.next();
            if (i > 3) {
               break;
            }

            if (i == 1) {
               gPlayer = gPlayer;
               gPlayer.a(ChatColor.GREEN + "You Won!", ChatColor.DARK_AQUA + "You came first", 1.0D, 5.0D, 1.0D);
            } else {
               gPlayer.a(ChatColor.GREEN + "You came " + ChatColor.GOLD.toString() + i + this.f(i) + "!", ChatColor.GOLD + gPlayer.getPlayer().getName() + ChatColor.DARK_AQUA + " won the game", 1.0D, 5.0D, 1.0D);
            }

            winningPlayers.add(gPlayer);
            Core.a(ChatColor.DARK_GRAY + "#+ ++ " + i + ". " + ChatColor.GRAY + gPlayer.getPlayer().getDisplayName(), true, false);
         }

         if (gPlayer != null) {
            var5 = Core.c().getPlayers().iterator();

            while(var5.hasNext()) {
               gPlayer = (GamePlayer)var5.next();
               if (gPlayer.dI()) {
                  gPlayer.a(ChatColor.GOLD + gPlayer.getPlayer().getName(), ChatColor.DARK_AQUA + "Won the Game", 0.5D, 5.0D, 0.5D);
               }
            }
         }
      } else {
         winningTeam = this.bl.aT().aE();
         if (winningTeam != null) {
            Core.a(ChatColor.DARK_GRAY + "#+ " + ChatColor.GREEN + " ++ " + winningTeam.bP() + winningTeam.getName() + ChatColor.GREEN + " Won the Game!" + ChatColor.GREEN + " ++ ", true, false);
            var7 = Core.c().getPlayers().iterator();

            while(var7.hasNext()) {
               gPlayer = (GamePlayer)var7.next();
               if (gPlayer.dH()) {
                  if (gPlayer.ca().equals(winningTeam)) {
                     gPlayer.a(ChatColor.GREEN + "Your Team Won!", winningTeam.bP() + winningTeam.getName() + ChatColor.DARK_AQUA + " won the game", 1.0D, 5.0D, 1.0D);
                  } else {
                     gPlayer.a(ChatColor.RED + "Your Team Lost!", winningTeam.bP() + winningTeam.getName() + ChatColor.DARK_AQUA + " won the game", 1.0D, 5.0D, 1.0D);
                  }
               } else if (gPlayer.dI()) {
                  gPlayer.a(winningTeam.bP() + winningTeam.getName(), ChatColor.DARK_AQUA + "Won the Game", 0.5D, 5.0D, 0.5D);
               }
            }
         }
      }

      if (winningTeam == null && winningPlayers.isEmpty()) {
         Core.a(ChatColor.DARK_GRAY + "#+ " + ChatColor.GRAY + ">> Game Tied " + ChatColor.AQUA + ":(" + ChatColor.GRAY + " <<", true, false);
         var7 = Core.c().getPlayers().iterator();

         label115:
         while(true) {
            do {
               if (!var7.hasNext()) {
                  break label115;
               }

               gPlayer = (GamePlayer)var7.next();
            } while(!gPlayer.dH() && !gPlayer.dI());

            gPlayer.a(ChatColor.DARK_AQUA + "Game Tied", ChatColor.DARK_AQUA + "No one Wins", 1.0D, 5.0D, 1.0D);
         }
      }

      if (winningTeam != null) {
         var7 = ((List)this.bl.aT().aC().get(winningTeam)).iterator();

         while(var7.hasNext()) {
            gPlayer = (GamePlayer)var7.next();
            if ((double)System.currentTimeMillis() - gPlayer.eo() > 60000.0D) {
               this.bl.aT().a(gPlayer, MapRewardType.cx);
               gPlayer.dC().a(AchievementType.D, 1);
            }
         }
      } else if (!winningPlayers.isEmpty()) {
         int i = 0;
         Iterator var9 = winningPlayers.iterator();

         while(var9.hasNext()) {
            GamePlayer gPlayer1 = (GamePlayer)var9.next();
            switch(i) {
            case 0:
               this.bl.aT().a(gPlayer1, MapRewardType.cx);
               gPlayer1.dC().a(AchievementType.D, 1);
               break;
            case 1:
               this.bl.aT().a(gPlayer1, MapRewardType.cy);
               break;
            case 2:
               this.bl.aT().a(gPlayer1, MapRewardType.cz);
            }
         }
      }

      var7 = Core.c().getPlayers().iterator();

      while(var7.hasNext()) {
         gPlayer = (GamePlayer)var7.next();
         if (!gPlayer.dS() && (gPlayer.eb() > 0 || gPlayer.dD().eW() > 0)) {
            gPlayer.getPlayer().sendMessage(ChatColor.DARK_GRAY + "#+ " + ChatColor.DARK_AQUA + "You gained " + gPlayer.eb() + " Gold and " + gPlayer.dD().eW() + " XP");
         }

         if (gPlayer.dI()) {
         }

         gPlayer.dM();
         gPlayer.dD().eX();
      }

      Core.a(ChatColor.DARK_GRAY + "#+ ", true, false);
      if (this.bl.aT().au() != null) {
         this.bl.aT().au().dC().a(AchievementType.N, 1);
      }

      if (this.bl.aT().av() != null) {
         this.bl.aT().av().dC().a(AchievementType.O, 1);
      }

   }

   public double aP() {
      int var10000 = this.bt;
      this.getClass();
      return (double)(var10000 / 4);
   }

   public void aQ() {
      int var10001 = this.bl.aS().bn().getTimeLimit();
      this.getClass();
      this.e(var10001 * 4);
      this.bl.a(GameState.bB);
      Bukkit.getPluginManager().registerEvents(this.bl.aT(), Core.a());
      this.bu = 108;
      this.aM();
      Iterator var1 = Core.c().getPlayers().iterator();

      while(true) {
         GamePlayer gPlayer;
         do {
            if (!var1.hasNext()) {
               this.a(true);
               this.bl.aT().aJ();
               var1 = Core.c().getPlayers().iterator();

               while(var1.hasNext()) {
                  gPlayer = (GamePlayer)var1.next();
                  gPlayer.dM();
                  gPlayer.dD().eX();
                  gPlayer.dO();
                  if (gPlayer.dH() && gPlayer.ec() > 0) {
                     gPlayer.s(-gPlayer.ec());
                     gPlayer.ed();
                  }
               }

               return;
            }

            gPlayer = (GamePlayer)var1.next();
         } while(!gPlayer.dH() && !gPlayer.dI());

         gPlayer.dW();
      }
   }

   private void aR() {
      this.aM();
      Core.b().am();
      Core.e().cK();
      this.aN();
      this.a(true);
      this.cancel();
   }

   private String f(int value) {
      int tenRemainder = value % 10;
      switch(tenRemainder) {
      case 1:
         return "st";
      case 2:
         return "nd";
      case 3:
         return "rd";
      default:
         return "th";
      }
   }
}
