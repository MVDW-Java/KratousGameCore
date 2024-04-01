package a.a.a.f.b;

import a.a.a.f.a.MapTeam;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CapturablePointWool {
   private Block block;
   private String name;
   private DyeColor dn;
   private MapTeam do;

   public CapturablePointWool(String name, Block block, String color, MapTeam team) {
      this.block = block;
      this.name = name;
      this.dn = DyeColor.valueOf(color.replace(" ", "_").toUpperCase());
      this.do = team;
   }

   public Block getBlock() {
      return this.block;
   }

   public String getName() {
      return this.name;
   }

   public boolean c(Block block) {
      return block.getData() == this.dn.getData();
   }

   public MapTeam ca() {
      return this.do;
   }

   public boolean cd() {
      return this.block.getType().equals(Material.WOOL);
   }
}
