package org.Kratous.GameCore.a;

public class AchievementLevel {
   private int id;
   private int x;
   private int y;
   private int z;

   public AchievementLevel(int id, int requirement, int rewardXP, int rewardGold) {
      this.id = id;
      this.x = requirement;
      this.y = rewardXP;
      this.z = rewardGold;
   }

   public int getId() {
      return this.id;
   }

   public int t() {
      return this.x;
   }

   public int u() {
      return this.y;
   }

   public int v() {
      return this.z;
   }
}
