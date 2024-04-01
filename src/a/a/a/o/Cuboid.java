package a.a.a.o;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class Cuboid extends Shape {
   private Vector iA;
   private Vector iB;

   public Cuboid(ConfigurationSection section) {
      String[] minSplit = section.getString("min").replace(" ", "").split(",");
      String[] maxSplit = section.getString("max").replace(" ", "").split(",");
      Vector pMin = new Vector(Double.valueOf(minSplit[0]), Double.valueOf(minSplit[1]), Double.valueOf(minSplit[2]));
      Vector pMax = new Vector(Double.valueOf(maxSplit[0]), Double.valueOf(maxSplit[1]), Double.valueOf(maxSplit[2]));
      this.iA = Vector.getMinimum(pMin, pMax);
      this.iB = Vector.getMaximum(pMin, pMax);
   }

   public Cuboid(Vector min, Vector max) {
      this.iA = Vector.getMinimum(min, max);
      this.iB = Vector.getMaximum(min, max);
   }

   public Vector bO() {
      Random random = new Random(System.nanoTime());
      int xRange = this.iB.getBlockX() - this.iA.getBlockX();
      int yRange = this.iB.getBlockY() - this.iA.getBlockY();
      int zRange = this.iB.getBlockZ() - this.iA.getBlockZ();
      int xCoord = (xRange < 0 ? -1 : 1) * (xRange > 0 ? random.nextInt(Math.abs(xRange)) : 0) + this.iA.getBlockX();
      int yCoord = (yRange < 0 ? -1 : 1) * (yRange > 0 ? random.nextInt(Math.abs(yRange)) : 0) + this.iA.getBlockY();
      int zCoord = (zRange < 0 ? -1 : 1) * (zRange > 0 ? random.nextInt(Math.abs(zRange)) : 0) + this.iA.getBlockZ();
      return new Vector(xCoord, yCoord, zCoord);
   }

   public boolean d(Vector vector) {
      return vector.isInAABB(this.iA, this.iB);
   }

   public List fd() {
      List<Vector> vectors = new ArrayList();
      if (this.iA == this.iB) {
         vectors.add(this.iA);
      }

      for(int X = this.iA.getBlockX(); X <= this.iB.getBlockX(); ++X) {
         for(int Y = this.iA.getBlockY(); Y <= this.iB.getBlockY(); ++Y) {
            for(int Z = this.iA.getBlockZ(); Z <= this.iB.getBlockZ(); ++Z) {
               vectors.add(new Vector(X, Y, Z));
            }
         }
      }

      return vectors;
   }

   public Vector fe() {
      return this.iA.getMidpoint(this.iB);
   }

   public Vector ff() {
      return this.iA;
   }

   public Vector fg() {
      return this.iB;
   }
}
