package org.Kratous.GameCore.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class Cylinder extends Shape {
   private Vector iC;
   private int iD;
   private int height;

   public Cylinder(ConfigurationSection section) {
      String[] split = section.getString("base").replace(" ", "").split(",");
      this.iC = new Vector(Double.valueOf(split[0]), Double.valueOf(split[1]), Double.valueOf(split[2]));
      this.iD = section.getInt("radius") + 1;
      this.height = section.getInt("height");
   }

   public Cylinder(Vector base, int radius, int height) {
      this.iC = base;
      this.iD = radius + 1;
      this.height = height;
   }

   public Vector bO() {
      Random random = new Random(System.nanoTime());
      double randomRadius = (double)(this.iD == 0 ? 0 : random.nextInt(this.iD * 1000) / 1000);
      double angle = Math.random() * 3.141592653589793D * 2.0D;
      double x = Math.cos(angle) * randomRadius + this.iC.getX();
      double y = (double)(this.height == 0 ? 0 : random.nextInt(this.height * 1000) / 1000) + this.iC.getY();
      double z = Math.sin(angle) * randomRadius + this.iC.getZ();
      return new Vector(x, y, z);
   }

   public boolean d(Vector vector) {
      if (!(vector.getY() < this.iC.getY()) && !(vector.getY() > (double)(this.iC.getBlockY() + this.height))) {
         return vector.clone().setY(0).distance(this.iC.clone().setY(0)) < (double)this.iD;
      } else {
         return false;
      }
   }

   public List fd() {
      List<Vector> vectors = new ArrayList();

      for(int X = this.iC.getBlockX() - this.iD; X < this.iC.getBlockX() + this.iD; ++X) {
         for(int Y = this.iC.getBlockY(); Y <= this.iC.getBlockY() + this.height; ++Y) {
            for(int Z = this.iC.getBlockZ() - this.iD; Z < this.iC.getBlockZ() + this.iD; ++Z) {
               Vector v = new Vector(X, Y, Z);
               if (this.d(v)) {
                  vectors.add(v);
               }
            }
         }
      }

      return vectors;
   }

   public Vector fe() {
      return this.iC;
   }
}
