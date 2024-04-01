package org.Kratous.GameCore.j;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.k.TaskChain11;
//import org.Kratous.GameCore.k.TaskChain12;
import org.Kratous.GameCore.k.TaskChain4;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;

public class LogHandler {
   private List fF = new ArrayList();
   private List fG = new ArrayList();
   private List fH = new ArrayList();
   private List fI = new ArrayList();
   private int fJ = 0;
   private String fK;

   public int ds() {
      return this.fJ;
   }

   public void m(String map) {
      this.fK = map;
      Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
         public void run() {
         }
      });
   }

   public void a(final GamePlayer killer, final GamePlayer killed, final String method) {
      Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
         public void run() {
            KillLog log = new KillLog(killer == null ? 0 : killer.getId(), killed.getId(), method);
            LogHandler.this.fF.add(log);
         }
      });
   }

   public void a(GamePlayer gPlayer, ObjectiveType type, String name, int value) {
      this.fG.add(new ObjectiveLog(gPlayer.getId(), type, name, value));
   }

   public void a(GamePlayer gPlayer, RewardType type, int value) {
      this.fH.add(new RewardLog(gPlayer.getId(), type, value));
   }

   public void a(int userId, int value) {
      this.fI.add(new KillStreakLog(userId, value));
   }

   public void q(int length) {
      /*TaskChain12.dx().a((TaskChain8)(new TaskChain4() {
         protected void run() {
         }
      })).a((TaskChain8)(new TaskChain11() {
         protected void a(Object arg) {
            LogHandler.this.fF.clear();
            LogHandler.this.fG.clear();
            LogHandler.this.fH.clear();
            LogHandler.this.fI.clear();
         }
      })).execute();*/
   }
}
