package a.a.a.f.b;

import a.a.a.f.a.MapTeam;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class DestroyableMonument {
   private String name;
   private Material material;
   private MapTeam aa;
   private List cw;

   public DestroyableMonument(String name, Material material, MapTeam team, List blocks) {
      this.name = name;
      this.material = material;
      this.aa = team;
      this.cw = blocks;
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

   public int cr() {
      int totalBlocks = this.cw.size();
      int remainingBlocks = 0;
      Iterator var3 = this.cw.iterator();

      while(var3.hasNext()) {
         Block b = (Block)var3.next();
         if (b.getType().equals(this.material)) {
            ++remainingBlocks;
         }
      }

      return (int)((float)remainingBlocks / (float)totalBlocks * 100.0F);
   }

   public boolean isAlive() {
      return this.cr() > 0;
   }

   public void setMaterial(Material material) {
      this.material = material;
   }
}
