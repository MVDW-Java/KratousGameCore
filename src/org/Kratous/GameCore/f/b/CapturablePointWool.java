package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.f.a.MapTeam;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CapturablePointWool {
   private Block block;
   private String name;
   private DyeColor dn;
   private MapTeam aa;

   public CapturablePointWool(String name, Block block, String color, MapTeam team) {
      this.block = block;
      this.name = name;
      this.dn = DyeColor.valueOf(color.replace(" ", "_").toUpperCase());
      this.aa = team;
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
      return this.aa;
   }

   public boolean cd() {
      return this.block.getType().equals(Material.WOOL);
   }
}
