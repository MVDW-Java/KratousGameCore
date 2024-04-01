package org.Kratous.GameCore.l;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.p.Tutorial;
import java.util.ArrayList;

public class GamePlayerData {
   protected int id;
   protected int gF;
   protected int gG;
   protected int gH;
   protected int gI;
   protected boolean gJ;
   protected boolean gK;
   protected boolean gL;
   protected int gM;
   protected int gN;
   protected double gO;
   protected double gP;
   protected Tutorial gQ;
   protected boolean gR;
   protected ArrayList gS;

   public GamePlayerData(int id, int gold, int tickets) {
      this.id = id;
      this.gF = gold;
      this.gG = tickets;
      this.gH = 0;
      this.gI = 0;
      this.gJ = true;
      this.gK = false;
      this.gL = false;
      this.gM = 0;
      this.gN = 0;
      this.gO = 0.0D;
      this.gP = 0.0D;
      this.gQ = null;
      this.gR = false;
      this.gS = new ArrayList();
   }

   public GamePlayerData(GamePlayerData data) {
      this.id = data.id;
      this.gF = data.gF;
      this.gG = data.gG;
      this.gH = data.gH;
      this.gI = data.gI;
      this.gJ = data.gJ;
      this.gK = data.gK;
      this.gL = data.gL;
      this.gM = data.gM;
      this.gO = data.gO;
      this.gP = data.gP;
      this.gQ = data.gQ;
      this.gS = data.gS;
   }

   public int getId() {
      return this.id;
   }

   public int dZ() {
      return this.gF;
   }

   public int ea() {
      return this.gG;
   }

   public int eb() {
      return this.gH;
   }

   public int ec() {
      return this.gI;
   }

   public void v(int amount) {
      this.gI = amount;
   }

   public void ed() {
      this.gI = 0;
   }

   public boolean ee() {
      return this.gJ;
   }

   public boolean isInvisible() {
      return this.gK;
   }

   public boolean ef() {
      return this.gL;
   }

   public void e(boolean value) {
      this.gJ = value;
   }

   public void setInvisible(boolean value) {
      this.gK = value;
   }

   public void f(boolean value) {
      this.gL = value;
   }

   public int eg() {
      return this.gM;
   }

   public void eh() {
      this.gM = 0;
   }

   public void ei() {
      ++this.gM;
   }

   public int ej() {
      return this.gN;
   }

   public void ek() {
      if (this.gN > 1) {
         Core.g().a(this.id, this.gN);
      }

      this.gN = 0;
   }

   public void el() {
      ++this.gN;
   }

   public double em() {
      return this.gO;
   }

   public void en() {
      this.gO = (double)System.currentTimeMillis();
   }

   public double eo() {
      return this.gP;
   }

   public void ep() {
      this.gP = (double)System.currentTimeMillis();
   }

   public Tutorial eq() {
      return this.gQ;
   }

   public void a(Tutorial val) {
      this.gQ = val;
   }

   public void g(boolean k) {
      this.gR = k;
   }

   public boolean er() {
      return this.gR;
   }

   public void p(String n) {
      this.gS.add(n);
   }

   public ArrayList es() {
      return this.gS;
   }
}
