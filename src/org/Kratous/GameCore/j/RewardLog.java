package org.Kratous.GameCore.j;

public class RewardLog {
   private int fE;
   private RewardType fY;
   private int value;
   private int fZ;

   public RewardLog(int userId, RewardType type, int value) {
      this.fE = userId;
      this.fY = type;
      this.value = value;
      this.fZ = (int)(System.currentTimeMillis() / 1000L);
   }

   public int dr() {
      return this.fE;
   }

   public RewardType dv() {
      return this.fY;
   }

   public int getValue() {
      return this.value;
   }

   public int dw() {
      return this.fZ;
   }
}
