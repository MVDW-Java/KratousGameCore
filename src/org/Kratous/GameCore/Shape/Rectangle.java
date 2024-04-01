package org.Kratous.GameCore.Shape;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.Vector;

public class Rectangle extends Shape {
   private Vector iA;
   private Vector iB;

   public Rectangle(Vector min, Vector max) {
      this.iA = Vector.getMinimum(min, max).setY(-200);
      this.iB = Vector.getMaximum(min, max).setY(200);
      String rand = "%%__USER__%%";
   }

   public Vector bO() {
      return null;
   }

   public boolean d(Vector vector) {
      return vector.isInAABB(this.iA, this.iB);
   }

   public List fd() {
      return new ArrayList();
   }

   public Vector fe() {
      return this.iA.getMidpoint(this.iB);
   }
}
