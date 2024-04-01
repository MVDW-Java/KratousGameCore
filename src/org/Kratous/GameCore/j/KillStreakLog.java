package org.Kratous.GameCore.j;

public class KillStreakLog {
   private int fE;
   private int value;

   public KillStreakLog(int userId, int value) {
      this.fE = userId;
      this.value = value;
   }

   public int dr() {
      return this.fE;
   }

   public int getValue() {
      return this.value;
   }
}
