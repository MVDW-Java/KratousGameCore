package org.Kratous.GameCore.l.c;

import org.bukkit.Material;

public enum HeadType {
   ht(Material.DIRT, "Dirt Block", 1),
   hu(Material.TNT, "TNT", 2),
   hv(Material.DIAMOND_BLOCK, "Diamond Block", 3),
   hw(Material.GOLD_BLOCK, "Gold Block", 4),
   hx(Material.IRON_BLOCK, "Iron Block", 5),
   hy(Material.GLASS, "Glass", 6),
   hz(Material.REDSTONE_BLOCK, "Redstone Block", 7),
   hA(Material.PUMPKIN, "Pumpkin", 8),
   hB(Material.OBSIDIAN, "Obsidian Block", 10),
   hC(Material.SKULL_ITEM, "Remove Head Item", 17);

   private Material material;
   private String name;
   private int hD;

   private HeadType(Material material, String name, int itemSlot) {
      this.name = name;
      this.material = material;
      this.hD = itemSlot;
   }

   public String getName() {
      return this.name;
   }

   public Material getMaterial() {
      return this.material;
   }

   public int eQ() {
      return this.hD;
   }
}
