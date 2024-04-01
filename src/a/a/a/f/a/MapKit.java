package a.a.a.f.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class MapKit {
   private String ck;
   private Map ae;
   private List cl;
   private int cm;
   private boolean cn;

   public MapKit(String kitName, int goldPrice, boolean isDefault) {
      this.ck = kitName;
      this.ae = new HashMap();
      this.cl = new ArrayList();
      this.cm = goldPrice;
      this.cn = isDefault;
   }

   public void a(int slot, ItemStack stack) {
      this.ae.put(slot, stack);
   }

   public void a(PotionEffect effect) {
      this.cl.add(effect);
   }

   public String by() {
      return this.ck;
   }

   public int bz() {
      return this.cm;
   }

   public boolean bA() {
      return this.cn;
   }

   public Map bB() {
      return this.ae;
   }

   public List getEffects() {
      return this.cl;
   }
}
