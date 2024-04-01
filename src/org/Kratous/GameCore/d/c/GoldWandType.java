package org.Kratous.GameCore.d.c;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum GoldWandType {
   aq(Material.GOLD_BLOCK, ChatColor.GOLD + "Nugget Bomb", 1250, 0, new String[0]),
   ar(Material.FIREBALL, ChatColor.GOLD + "Lightning Strike", 50, 2, new String[0]),
   as(Material.SADDLE, ChatColor.GOLD + "Stacker Saddle", 20, 4, new String[]{ChatColor.DARK_AQUA + "Ride on top of a player"}),
   at(Material.STICK, ChatColor.GOLD + "Hit Stick", 30, 6, new String[0]),
   au(Material.STONE_PLATE, ChatColor.GOLD + "Trampoline", 50, 8, new String[0]);

   private Material material;
   private String name;
   private int av;
   private int aw;
   private String[] ax;

   private GoldWandType(Material material, String name, int price, int inventorySlot, String... lore) {
      this.material = material;
      this.name = name;
      this.av = price;
      this.aw = inventorySlot;
      this.ax = lore;
   }

   public Material getMaterial() {
      return this.material;
   }

   public String getName() {
      return this.name;
   }

   public int F() {
      return this.av;
   }

   public int G() {
      return this.aw;
   }

   public List getLore() {
      List<String> list = new ArrayList();
      String[] var2 = this.ax;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String str = var2[var4];
         list.add(str);
      }

      list.add(ChatColor.DARK_AQUA + "Price: " + ChatColor.GOLD.toString() + this.av + " Gold");
      return list;
   }
}
