package a.a.a.l.a;

import a.a.a.Core;
import a.a.a.a.Achievement;
import a.a.a.a.AchievementLevel;

public class PlayerAchievementData {
   private int hg;
   private int hh;
   private int hi;

   public PlayerAchievementData(int achievementId, int currentLevel, int progress) {
      this.hg = achievementId;
      this.hh = currentLevel;
      this.hi = progress;
   }

   public int getProgress() {
      return this.hi;
   }

   public void w(int progress) {
      this.hi += progress;
   }

   public Achievement et() {
      return Core.i().b(this.hg);
   }

   public AchievementLevel eu() {
      return this.et().a(this.hh);
   }

   public AchievementLevel ev() {
      AchievementLevel level = this.et().a(this.hh + 1);
      return level == null ? this.eu() : level;
   }

   public void ew() {
      ++this.hh;
   }
}
