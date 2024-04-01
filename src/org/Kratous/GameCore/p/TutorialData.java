package org.Kratous.GameCore.p;

import org.Kratous.GameCore.f.a.MapKit;
import org.Kratous.GameCore.f.a.a.MapDataException;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class TutorialData {
   private YamlConfiguration bU;
   private File bT;
   private String bV;
   private String bW;
   private LinkedList iH;
   private int iI;

   public TutorialData(File YMLFile) throws MapDataException {
      this.bT = YMLFile.getParentFile();
      this.bU = YamlConfiguration.loadConfiguration(YMLFile);
      this.fq();
   }

   public File bi() {
      return this.bT;
   }

   public String bj() {
      return this.bV;
   }

   public String bk() {
      return this.bW;
   }

   public int aZ() {
      return this.iI;
   }

   public LinkedList fp() {
      return this.iH;
   }

   private void fq() throws MapDataException {
      this.bV = this.bU.getString("info.gamemode");
      this.bW = this.bU.getString("info.name");
      this.iI = this.bU.getInt("config.frozentime", -1);
      this.j(this.bU.getConfigurationSection("steps"));
   }

   private void j(ConfigurationSection section) throws MapDataException {
      this.iH = new LinkedList();

      ConfigurationSection stepSection;
      Vector vector;
      MapKit mapKit;
      for(Iterator var2 = section.getKeys(false).iterator(); var2.hasNext(); this.iH.add(new TutorialStep(stepSection.getString("title"), stepSection.getStringList("messages"), stepSection.getStringList("success"), vector, (float)stepSection.getDouble("yaw", 0.0D), (float)stepSection.getDouble("pitch", 0.0D), stepSection.getString("action", "none"), stepSection.getInt("delay", 0) * 4, mapKit))) {
         String key = (String)var2.next();
         stepSection = section.getConfigurationSection(key);
         String[] split = stepSection.getString("location").replace(" ", "").split(",");
         vector = new Vector(Double.valueOf(split[0]), Double.valueOf(split[1]), Double.valueOf(split[2]));
         mapKit = null;
         if (stepSection.contains("kit")) {
            ConfigurationSection kit = stepSection.getConfigurationSection("kit");
            mapKit = new MapKit("", 0, true);

            Iterator var9;
            String slotString;
            int slot;
            ItemStack stack;
            for(var9 = kit.getConfigurationSection("items").getKeys(false).iterator(); var9.hasNext(); mapKit.a(slot, stack)) {
               slotString = (String)var9.next();
               slot = 0;
               ConfigurationSection slotSelection = kit.getConfigurationSection("items").getConfigurationSection(slotString);
               if (this.isNumeric(slotString)) {
                  slot = Integer.valueOf(slotString);
               } else {
                  byte var14 = -1;
                  switch(slotString.hashCode()) {
                  case -1220934547:
                     if (slotString.equals("helmet")) {
                        var14 = 0;
                     }
                     break;
                  case 93922241:
                     if (slotString.equals("boots")) {
                        var14 = 3;
                     }
                     break;
                  case 1069952181:
                     if (slotString.equals("chestplate")) {
                        var14 = 1;
                     }
                     break;
                  case 1735676010:
                     if (slotString.equals("leggings")) {
                        var14 = 2;
                     }
                  }

                  switch(var14) {
                  case 0:
                     slot = 103;
                     break;
                  case 1:
                     slot = 102;
                     break;
                  case 2:
                     slot = 101;
                     break;
                  case 3:
                     slot = 100;
                  }
               }

               int amount = slotSelection.getInt("amount", 1);
               Material item = Material.matchMaterial(slotSelection.getString("item"));
               stack = new ItemStack(item, amount, (short)slotSelection.getInt("damage", 0));
               ItemMeta meta = stack.getItemMeta();
               if (slotSelection.contains("title")) {
                  meta.setDisplayName(slotSelection.getString("title"));
               }

               if (slotSelection.contains("lore")) {
                  meta.setLore(slotSelection.getStringList("lore"));
               }

               if (slotSelection.contains("color") && meta instanceof LeatherArmorMeta) {
                  String colorHex = slotSelection.getString("color");
                  ((LeatherArmorMeta)meta).setColor(Color.fromRGB(Integer.valueOf(colorHex.substring(0, 2), 16), Integer.valueOf(colorHex.substring(2, 4), 16), Integer.valueOf(colorHex.substring(4, 6), 16)));
               }

               stack.setItemMeta(meta);
               if (slotSelection.contains("enchantments")) {
                  Iterator var24 = slotSelection.getStringList("enchantments").iterator();

                  while(var24.hasNext()) {
                     String enchant = (String)var24.next();
                     int level = 1;
                     if (enchant.contains(":")) {
                        level = Integer.valueOf(enchant.split(":")[1]);
                        enchant = enchant.split(":")[0].replace(" ", "_");
                     }

                     enchant = enchant.replace(" ", "_").toUpperCase();
                     Enchantment enchantment = Enchantment.getByName(enchant);
                     if (enchantment == null) {
                        throw new MapDataException("Unknown Enchantment " + enchant + " for " + this.bW);
                     }

                     stack.addUnsafeEnchantment(enchantment, level);
                  }
               }
            }

            var9 = kit.getStringList("potions").iterator();

            while(var9.hasNext()) {
               slotString = (String)var9.next();
               String[] splits = slotString.split(",");
               PotionEffectType effect = PotionEffectType.getByName(splits[0].replace(" ", "_").toUpperCase());
               if (effect == null) {
                  throw new MapDataException("Unknown potion effect type " + splits[0]);
               }

               mapKit.a(new PotionEffect(effect, Integer.valueOf(splits[1]) * 20, splits.length == 3 ? Integer.valueOf(splits[2]) : 1));
            }
         }
      }

   }

   private boolean isNumeric(String message) {
      char[] var2 = message.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         if (!Character.isDigit(c)) {
            return false;
         }
      }

      return true;
   }
}
