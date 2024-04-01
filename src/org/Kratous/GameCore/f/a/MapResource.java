package org.Kratous.GameCore.f.a;

import org.Kratous.GameCore.o.Shape;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class MapResource {
   private Shape cu;
   private Vector cv;
   private Material material;
   private List cw;

   public MapResource(Shape region, Vector teleportVector, Material material) {
      this.cu = region;
      this.cv = teleportVector;
      this.material = material;
      this.cw = new ArrayList();
   }

   public Shape bK() {
      return this.cu;
   }

   public Vector bL() {
      return this.cv;
   }

   public Material getMaterial() {
      return this.material;
   }

   public List getBlocks() {
      return this.cw;
   }

   public void b(Block block) {
      this.cw.add(block);
   }

   public void clear() {
      this.cw = new ArrayList();
   }
}
