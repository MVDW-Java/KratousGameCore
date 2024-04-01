package a.a.a.d.b;

import org.bukkit.Material;

public class TicketItem {
   private String name;
   private Material material;
   private int ag;
   private int ah;
   private TicketItemType ai;
   private double value;

   public TicketItem(String name, String material, int slot, int cost, String type, double value) {
      this.name = name;
      this.material = Material.matchMaterial(material);
      this.ag = slot;
      this.ah = cost;
      this.ai = TicketItemType.valueOf(type.toUpperCase().replace(" ", "_"));
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   public Material getMaterial() {
      return this.material;
   }

   public int getSlot() {
      return this.ag;
   }

   public int D() {
      return this.ah;
   }

   public TicketItemType E() {
      return this.ai;
   }

   public double getValue() {
      return this.value;
   }
}
