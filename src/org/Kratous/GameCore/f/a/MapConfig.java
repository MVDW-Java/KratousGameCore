package org.Kratous.GameCore.f.a;

public class MapConfig {
   private int bL;
   private int bM;
   private boolean bN;
   private boolean bO;
   private boolean bP;
   private boolean bQ;
   private boolean bR;
   private boolean bS;

   public MapConfig(int frozenTime, int timeLimit, boolean forceRespawn, boolean showDeathMessages, boolean selectableKits, boolean dropItemsOnDeath, boolean hasWalls, boolean keepMobs) {
      this.bL = frozenTime;
      this.bM = timeLimit;
      this.bN = forceRespawn;
      this.bO = showDeathMessages;
      this.bP = selectableKits;
      this.bQ = dropItemsOnDeath;
      this.bR = hasWalls;
      this.bS = keepMobs;
   }

   public int aZ() {
      return this.bL;
   }

   public int getTimeLimit() {
      return this.bM;
   }

   public boolean ba() {
      return this.bL >= 0;
   }

   public boolean bb() {
      return this.bN;
   }

   public boolean bc() {
      return this.bO;
   }

   public boolean bd() {
      return this.bP;
   }

   public boolean be() {
      return this.bQ;
   }

   public boolean bf() {
      return this.bR;
   }

   public boolean bg() {
      return this.bS;
   }
}
