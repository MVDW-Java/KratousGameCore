package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.o.Shape;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class DestroyableCore {
   private String name;
   private Material material;
   private MapTeam aa;
   private List cw;
   private Block dO;
   private boolean cK;
   private int dP;
   private Shape cD;

   public DestroyableCore(String name, Material material, MapTeam team, List blocks, int minDistance, Shape shape) {
      this.name = name;
      this.material = material;
      this.aa = team;
      this.cw = blocks;
      this.dP = minDistance;
      this.cK = true;
      Iterator var7 = this.cw.iterator();

      while(true) {
         Block b;
         do {
            if (!var7.hasNext()) {
               this.cD = shape;
               return;
            }

            b = (Block)var7.next();
         } while(!b.getType().equals(Material.LAVA) && !b.getType().equals(Material.STATIONARY_LAVA));

         this.dO = b;
      }
   }

   public String getName() {
      return this.name;
   }

   public MapTeam ca() {
      return this.aa;
   }

   public List getBlocks() {
      return this.cw;
   }

   public boolean f(Block block) {
      return this.cw.contains(block);
   }

   public int cp() {
      return this.dP;
   }

   public Block cq() {
      return this.dO;
   }

   public void setMaterial(Material material) {
      this.material = material;
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
}
