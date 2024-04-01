package org.Kratous.GameCore.f.a;

import org.Kratous.GameCore.o.Shape;

public class MapWall {
   private String key;
   private String name;
   private boolean cK;
   private int cL;
   private Shape cD;
   private Shape cu;
   private int cM;

   public MapWall(String key, String name, int timetilldrop, Shape shape, Shape region) {
      this.key = key;
      this.name = name;
      this.cL = timetilldrop;
      this.cK = true;
      this.cD = shape;
      this.cu = region;
      this.cM = 0;
   }

   public String getKey() {
      return this.key;
   }

   public String getName() {
      return this.name;
   }

   public int bT() {
      return this.cM;
   }

   public void i(int d) {
      this.cM = d;
   }

   public int bU() {
      return this.cL;
   }

   public boolean isAlive() {
      return this.cK;
   }

   public void b(boolean alive) {
      this.cK = alive;
   }

   public Shape bV() {
      return this.cD;
   }

   public Shape bW() {
      return this.cu;
   }
}
