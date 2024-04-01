package org.Kratous.GameCore.f.a;

import org.Kratous.GameCore.f.a.b.RegionRule;
import org.Kratous.GameCore.Shape.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.util.Vector;

public class MapRegion {
   private String name;
   private List co;
   private List cp;
   private Shape cq;
   private float yaw;
   private float pitch;
   private MapKit cr;
   private String cs;
   private int ct;

   public MapRegion(String name) {
      this.name = name;
      this.co = new ArrayList();
      this.cp = new ArrayList();
   }

   public void a(RegionRule rule) {
      this.cp.add(rule);
   }

   public void a(Shape shape) {
      this.co.add(shape);
   }

   public String getName() {
      return this.name;
   }

   public boolean a(Vector vector) {
      Iterator var2 = this.co.iterator();

      Shape shape;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         shape = (Shape)var2.next();
      } while(!shape.d(vector));

      return true;
   }

   public void bC() {
      this.cp = new ArrayList();
   }

   public List bD() {
      return this.cp;
   }

   public void b(Shape t) {
      this.cq = t;
   }

   public Shape bE() {
      return this.cq;
   }

   public void a(float t) {
      this.yaw = t;
   }

   public float bF() {
      return this.yaw;
   }

   public void b(float t) {
      this.pitch = t;
   }

   public float bG() {
      return this.pitch;
   }

   public void a(MapKit t) {
      this.cr = t;
   }

   public MapKit bH() {
      return this.cr;
   }

   public void i(String t) {
      this.cs = t;
   }

   public String bI() {
      return this.cs;
   }

   public void h(int t) {
      this.ct = t;
   }

   public int bJ() {
      return this.ct;
   }
}
