package org.Kratous.GameCore.l.b;

import org.Kratous.GameCore.l.GamePlayer;

public class ConfigHandler {
   private GamePlayer er;
   private boolean hk;
   private boolean bO;
   private boolean hl;
   private boolean hm;
   private boolean hn;

   public ConfigHandler(GamePlayer gPlayer) {
      this.er = gPlayer;
      this.hk = true;
      this.bO = true;
      this.hl = true;
      this.hm = true;
   }

   private void ex() {
   }

   public boolean ey() {
      return this.hk;
   }

   public boolean ez() {
      return this.bO;
   }

   public boolean eA() {
      return this.hl;
   }

   public boolean eB() {
      return this.hm;
   }

   public boolean eC() {
      return this.hn;
   }

   public void eD() {
      this.hk = !this.hk;
      this.ex();
   }

   public void eE() {
      this.bO = !this.bO;
      this.ex();
   }

   public void eF() {
      this.hl = !this.hl;
      this.ex();
   }

   public void eG() {
      this.hm = !this.hm;
      this.ex();
   }

   public void eH() {
      this.hn = !this.hn;
      this.ex();
   }
}
