package org.Kratous.GameCore.f.a;

import org.Kratous.GameCore.f.a.a.MapDataException;
import org.Kratous.GameCore.f.a.b.RegionRule;
import org.Kratous.GameCore.Shape.Cuboid;
import org.Kratous.GameCore.Shape.Cylinder;
import org.Kratous.GameCore.Shape.Shape;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class MapData {
   private File bT;
   private YamlConfiguration bU;
   private String bV;
   private String bW;
   private String bX;
   private String bY;
   private int maxPlayers;
   private MapConfig bZ;
   private List ca;
   private Map cb;
   private Map cc;
   private List cd;
   private LinkedList ce;
   private List cf;
   private Map cg;
   private Map ch;
   private MapSpawn ci;
   private List cj;

   public MapData(File YMLFile) throws Exception {
      this.a(YMLFile);
      this.bT = YMLFile.getParentFile();
   }

   public YamlConfiguration bh() {
      return this.bU;
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

   public String bl() {
      return this.bX == null ? "0.1" : this.bX;
   }

   public String bm() {
      return this.bY;
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public MapConfig bn() {
      return this.bZ;
   }

   public List bo() {
      return this.ca;
   }

   public Map bp() {
      return this.cb;
   }

   public List bq() {
      return this.cd;
   }

   public void a(MapRegion r) {
      this.cd.add(r);
   }

   public MapTutorial g(int pointer) {
      return pointer >= 0 && pointer <= this.ce.size() - 1 ? (MapTutorial)this.ce.get(pointer) : null;
   }

   public boolean br() {
      return !this.ce.isEmpty();
   }

   public List bs() {
      return this.cf;
   }

   public boolean bt() {
      return !this.cf.isEmpty();
   }

   public int a(MapRewardType type) {
      return this.ch.containsKey(type) ? (Integer)this.ch.get(type) : 0;
   }

   public int b(MapRewardType type) {
      return this.cg.containsKey(type) ? (Integer)this.cg.get(type) : 0;
   }

   public MapKit h(String name) {
      Iterator var2 = this.cb.values().iterator();

      MapKit kit;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         kit = (MapKit)var2.next();
      } while(!kit.by().equalsIgnoreCase(name));

      return kit;
   }

   public MapKit bu() {
      if (this.cb.isEmpty()) {
         return null;
      } else {
         Iterator var1 = this.cb.values().iterator();

         MapKit kit;
         do {
            if (!var1.hasNext()) {
               return (MapKit)this.cb.get(0);
            }

            kit = (MapKit)var1.next();
         } while(!kit.bA());

         return kit;
      }
   }

   public Map bv() {
      return this.cc;
   }

   public MapSpawn bw() {
      return this.ci;
   }

   private void a(File YMLFile) throws Exception {
      YamlConfiguration yml = YamlConfiguration.loadConfiguration(YMLFile);
      this.bU = yml;
      this.a(yml.getConfigurationSection("info"));
      this.b(yml.getConfigurationSection("config"));
      this.d(yml.getConfigurationSection("kits"));
      this.c(yml.getConfigurationSection("teams"));
      this.e(yml.getConfigurationSection("regions"));
      this.f(yml.getConfigurationSection("tutorial"));
      this.g(yml.getConfigurationSection("resources"));
      this.i(yml.getConfigurationSection("walls"));
   }

   private void a(ConfigurationSection infoSection) throws Exception {
      this.bW = infoSection.getString("name");
      this.bX = infoSection.getString("version", "0.2");
      this.bV = infoSection.getString("gamemode").toUpperCase();
      this.bY = infoSection.getString("objective", "");
      this.ca = new ArrayList();
      Iterator var2 = infoSection.getStringList("authors").iterator();

      while(var2.hasNext()) {
         String author = (String)var2.next();
         this.ca.add(author);
      }

      this.ci = this.h(infoSection.getConfigurationSection("spawn"));
   }

   private void b(ConfigurationSection configSection) throws Exception {
      this.bZ = new MapConfig(configSection.getInt("frozentime", -1), configSection.getInt("timelimit"), configSection.getBoolean("forcerespawn", true), configSection.getBoolean("deathmessages", true), configSection.getBoolean("selectablekits", false), configSection.getBoolean("deathdrops", true), configSection.getBoolean("walls", false), configSection.getBoolean("keepmobs", false));
      this.cg = new HashMap();
      this.ch = new HashMap();
      if (configSection.contains("rewards")) {
         ConfigurationSection rewardSection = configSection.getConfigurationSection("rewards");
         Iterator var3 = rewardSection.getKeys(false).iterator();

         while(var3.hasNext()) {
            String key = (String)var3.next();
            MapRewardType type = MapRewardType.valueOf(key.toUpperCase());
            ConfigurationSection section = rewardSection.getConfigurationSection(key);
            if (type == null) {
               throw new MapDataException("Unknown reward type " + key);
            }

            int goldReward = section.getInt("gold", 0);
            int XPReward = section.getInt("xp", 0);
            this.ch.put(type, XPReward);
            this.cg.put(type, goldReward);
         }
      }

   }

   private void c(ConfigurationSection teamSection) throws Exception {
      this.cc = new HashMap();
      if (teamSection == null) {
         throw new MapDataException("Could not find teams section in YML");
      } else {
         Iterator var2 = teamSection.getKeys(false).iterator();

         while(var2.hasNext()) {
            String team = (String)var2.next();
            ConfigurationSection section = teamSection.getConfigurationSection(team);
            String teamName = section.getString("name");
            String prefix = section.getString("prefix", "");
            int maxPlayers = section.getInt("max", 50);
            ChatColor color = ChatColor.valueOf(section.getString("color", "gray").replace(" ", "_").toUpperCase());
            List<MapSpawn> spawns = new ArrayList();
            Iterator var10 = section.getConfigurationSection("spawns").getKeys(false).iterator();

            while(var10.hasNext()) {
               String spawn = (String)var10.next();
               spawns.add(this.h(section.getConfigurationSection("spawns").getConfigurationSection(spawn)));
            }

            if (spawns.isEmpty()) {
               throw new MapDataException("Could not load any spawns for team " + team);
            }

            this.maxPlayers += maxPlayers;
            this.cc.put(team, new MapTeam(teamName, color, prefix, maxPlayers, spawns));
         }

         if (this.cc.isEmpty()) {
            throw new MapDataException("Did not load any teams from YML");
         }
      }
   }

   private void d(ConfigurationSection kitSection) throws Exception {
      this.cb = new HashMap();
      if (kitSection != null) {
         Iterator var2 = kitSection.getKeys(false).iterator();

         while(var2.hasNext()) {
            String kitName = (String)var2.next();
            ConfigurationSection kit = kitSection.getConfigurationSection(kitName);
            MapKit mapKit = new MapKit(kit.getString("name"), kit.getInt("gold", 0), kit.getBoolean("default", false));

            Iterator var6;
            String slotString;
            int slot;
            ItemStack stack;
            for(var6 = kit.getConfigurationSection("items").getKeys(false).iterator(); var6.hasNext(); mapKit.a(slot, stack)) {
               slotString = (String)var6.next();
               slot = 0;
               ConfigurationSection slotSelection = kit.getConfigurationSection("items").getConfigurationSection(slotString);
               if (this.isNumeric(slotString)) {
                  slot = Integer.valueOf(slotString);
               } else {
                  byte var11 = -1;
                  switch(slotString.hashCode()) {
                  case -1220934547:
                     if (slotString.equals("helmet")) {
                        var11 = 0;
                     }
                     break;
                  case 93922241:
                     if (slotString.equals("boots")) {
                        var11 = 3;
                     }
                     break;
                  case 1069952181:
                     if (slotString.equals("chestplate")) {
                        var11 = 1;
                     }
                     break;
                  case 1735676010:
                     if (slotString.equals("leggings")) {
                        var11 = 2;
                     }
                  }

                  switch(var11) {
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
               stack = null;
               if (item.equals(Material.POTION)) {
                  Potion kPotion = new Potion(PotionType.valueOf(slotSelection.getString("potiontype")), slotSelection.getInt("potionlevel", 1));
                  kPotion.setSplash(true);
                  stack = kPotion.toItemStack(amount);
               } else {
                  stack = new ItemStack(item, amount, (short)slotSelection.getInt("damage", 0));
               }

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
                  Iterator var22 = slotSelection.getStringList("enchantments").iterator();

                  while(var22.hasNext()) {
                     String enchant = (String)var22.next();
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

            var6 = kit.getStringList("potions").iterator();

            while(var6.hasNext()) {
               slotString = (String)var6.next();
               String[] splits = slotString.split(",");
               PotionEffectType effect = PotionEffectType.getByName(splits[0].replace(" ", "_").toUpperCase());
               if (effect == null) {
                  throw new MapDataException("Unknown potion effect type " + splits[0]);
               }

               mapKit.a(new PotionEffect(effect, Integer.valueOf(splits[1]) * 20, splits.length == 3 ? Integer.valueOf(splits[2]) : 1));
            }

            this.cb.put(kitName, mapKit);
         }

      }
   }

   private void e(ConfigurationSection regionSection) throws Exception {
      this.cd = new ArrayList();
      if (regionSection != null) {
         MapRegion mapRegion;
         for(Iterator var2 = regionSection.getKeys(false).iterator(); var2.hasNext(); this.cd.add(mapRegion)) {
            String region = (String)var2.next();
            mapRegion = new MapRegion(region);
            ConfigurationSection section = regionSection.getConfigurationSection(region);
            Shape shape = null;

            Iterator var7;
            String ruleName;
            ConfigurationSection rule;
            for(var7 = section.getConfigurationSection("areas").getKeys(false).iterator(); var7.hasNext(); mapRegion.a((Shape)shape)) {
               ruleName = (String)var7.next();
               rule = section.getConfigurationSection("areas").getConfigurationSection(ruleName);
               ConfigurationSection cuboid;
               String[] minCoords;
               if (rule.contains("cylinder")) {
                  cuboid = rule.getConfigurationSection("cylinder");
                  minCoords = cuboid.getString("base").replace(" ", "").split(",");
                  shape = new Cylinder(new Vector(Double.valueOf(minCoords[0]), Double.valueOf(minCoords[1]), Double.valueOf(minCoords[2])), cuboid.getInt("radius"), cuboid.getInt("height"));
               } else {
                  String[] maxCoords;
                  if (rule.contains("cuboid")) {
                     cuboid = rule.getConfigurationSection("cuboid");
                     minCoords = cuboid.getString("min").replace(" ", "").split(",");
                     maxCoords = cuboid.getString("max").replace(" ", "").split(",");
                     shape = new Cuboid(new Vector(Double.valueOf(minCoords[0]), Double.valueOf(minCoords[1]), Double.valueOf(minCoords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
                  } else {
                     if (!rule.contains("rectangle")) {
                        throw new MapDataException("Could not find cylinder/cuboid for " + region + " region");
                     }

                     cuboid = rule.getConfigurationSection("rectangle");
                     minCoords = cuboid.getString("min").replace(" ", "").split(",");
                     maxCoords = cuboid.getString("max").replace(" ", "").split(",");
                     shape = new Cuboid(new Vector(Double.valueOf(minCoords[0]), 0.0D, Double.valueOf(minCoords[1])), new Vector(Double.valueOf(maxCoords[0]), 0.0D, Double.valueOf(maxCoords[1])));
                  }
               }
            }

            var7 = section.getConfigurationSection("rules").getKeys(false).iterator();

            while(var7.hasNext()) {
               ruleName = (String)var7.next();
               rule = section.getConfigurationSection("rules").getConfigurationSection(ruleName);
               Material material = Material.matchMaterial(rule.getString("material", ""));
               String message = rule.getString("message", "");
               List<MapTeam> teams = new ArrayList();
               if (rule.contains("team")) {
                  if (rule.getString("team").contains(",")) {
                     String[] var13 = rule.getString("team").replace(" ", "").split(",");
                     int var14 = var13.length;

                     for(int var15 = 0; var15 < var14; ++var15) {
                        String team = var13[var15];
                        if (!this.cc.containsKey(team)) {
                           throw new MapDataException("Could not find team " + team);
                        }

                        //teams.add(this.cc.get(team));
                     }
                  } else {
                     if (!this.cc.containsKey(rule.getString("team"))) {
                        throw new MapDataException("Could not find team " + rule.getString("team"));
                     }

                     //teams.add(this.cc.get(rule.getString("team")));
                  }
               }

               RegionRule regionRule = new RegionRule(rule.getString("action"), rule.getString("access"), teams, message, material);
               mapRegion.a(regionRule);
            }

            ConfigurationSection teleport = section.getConfigurationSection("teleport");
            if (teleport != null) {
               mapRegion.b((float)teleport.getInt("pitch", 0));
               mapRegion.a((float)teleport.getInt("yaw", 0));
               mapRegion.i(teleport.getString("permission"));
               mapRegion.h(teleport.getInt("gold", 0));
               MapKit kit = (MapKit)this.cb.get(teleport.getString("kit"));
               mapRegion.a(kit);
               rule = teleport.getConfigurationSection("cylinder");
               String[] coords = rule.getString("base").replace(" ", "").split(",");
               Shape tShape = new Cylinder(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), rule.getInt("radius"), rule.getInt("height"));
               mapRegion.b(tShape);
            }
         }

      }
   }

   private void f(ConfigurationSection tutorialSection) throws Exception {
      this.ce = new LinkedList();
      if (tutorialSection != null) {
         Iterator var2 = tutorialSection.getKeys(false).iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            ConfigurationSection section = tutorialSection.getConfigurationSection(key);
            String title = section.getString("title");
            String[] point = section.getString("teleport.point").replace(" ", "").split(",");
            this.ce.add(new MapTutorial(title, section.getStringList("message"), Double.valueOf(point[0]), Double.valueOf(point[1]), Double.valueOf(point[2]), (float)section.getDouble("teleport.yaw", 0.0D), (float)section.getDouble("teleport.pitch", 0.0D)));
         }

      }
   }

   private void g(ConfigurationSection resourceSection) throws Exception {
      this.cf = new ArrayList();
      if (resourceSection != null) {
         Iterator var2 = resourceSection.getKeys(false).iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            ConfigurationSection section = resourceSection.getConfigurationSection(key);
            Shape shape = null;
            ConfigurationSection shapeSection = section.getConfigurationSection("shape");
            ConfigurationSection cuboid;
            String[] minCoords;
            if (shapeSection.contains("cylinder")) {
               cuboid = shapeSection.getConfigurationSection("cylinder");
               minCoords = cuboid.getString("base").replace(" ", "").split(",");
               shape = new Cylinder(new Vector(Double.valueOf(minCoords[0]), Double.valueOf(minCoords[1]), Double.valueOf(minCoords[2])), cuboid.getInt("radius"), cuboid.getInt("height"));
            } else {
               if (!shapeSection.contains("cuboid")) {
                  throw new MapDataException("Could not find cylinder/cuboid for regenerating resource");
               }

               cuboid = shapeSection.getConfigurationSection("cuboid");
               minCoords = cuboid.getString("min").replace(" ", "").split(",");
               String[] maxCoords = cuboid.getString("max").replace(" ", "").split(",");
               shape = new Cuboid(new Vector(Double.valueOf(minCoords[0]), Double.valueOf(minCoords[1]), Double.valueOf(minCoords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
            }

            String[] point = section.getString("teleport").replace(" ", "").split(",");
            Vector teleport = new Vector(Double.valueOf(point[0]), Double.valueOf(point[1]), Double.valueOf(point[2]));
            Material material = Material.matchMaterial(section.getString("material"));
            if (material == null) {
               throw new MapDataException("Could not find material " + section.getString("material"));
            }

            this.cf.add(new MapResource((Shape)shape, teleport, material));
         }

      }
   }

   private MapSpawn h(ConfigurationSection section) throws Exception {
      float pitch = (float)section.getDouble("pitch", 0.0D);
      float yaw = (float)section.getDouble("yaw", 0.0D);
      Shape shape = null;
      MapKit kit = null;
      ConfigurationSection cuboid;
      String[] minCoords;
      if (section.contains("cylinder")) {
         cuboid = section.getConfigurationSection("cylinder");
         minCoords = cuboid.getString("base").replace(" ", "").split(",");
         shape = new Cylinder(new Vector(Double.valueOf(minCoords[0]), Double.valueOf(minCoords[1]), Double.valueOf(minCoords[2])), cuboid.getInt("radius"), cuboid.getInt("height"));
      } else {
         if (!section.contains("cuboid")) {
            if (section.contains("rectangle")) {
               throw new MapDataException("Rectangle is not supported for spawns");
            }

            throw new MapDataException("Could not find cylinder/cuboid for spawn region");
         }

         cuboid = section.getConfigurationSection("cuboid");
         minCoords = cuboid.getString("min").replace(" ", "").split(",");
         String[] maxCoords = cuboid.getString("max").replace(" ", "").split(",");
         shape = new Cuboid(new Vector(Double.valueOf(minCoords[0]), Double.valueOf(minCoords[1]), Double.valueOf(minCoords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
      }

      if (section.contains("defaultkit")) {
         if (!this.cb.containsKey(section.getString("defaultkit"))) {
            throw new MapDataException("Cannot find kit " + section.getString("defaultkit"));
         }

         kit = (MapKit)this.cb.get(section.getString("defaultkit"));
      }

      return new MapSpawn((Shape)shape, pitch, yaw, kit);
   }

   public List bx() {
      return this.cj;
   }

   private void i(ConfigurationSection wallsSection) throws Exception {
      if (wallsSection != null) {
         this.cj = new ArrayList();
         Iterator var2 = wallsSection.getKeys(false).iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            ConfigurationSection wall = wallsSection.getConfigurationSection(key);
            String name = wall.getString("name", "The Wall");
            int timetilldrop = wall.getInt("timetilldrop", 300);
            Shape shape = null;
            Shape regionShape = null;
            ConfigurationSection shapeSection;
            String[] coords;
            if (wall.getConfigurationSection("cuboid") != null) {
               shapeSection = wall.getConfigurationSection("cuboid");
               coords = shapeSection.getString("min").replace(" ", "").split(",");
               String[] maxCoords = shapeSection.getString("max").replace(" ", "").split(",");
               shape = new Cuboid(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
               regionShape = new Cuboid(new Vector(Double.valueOf(coords[0]), Double.valueOf("-10"), Double.valueOf(coords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf("300"), Double.valueOf(maxCoords[2])));
            } else {
               if (wall.getConfigurationSection("cylinder") == null) {
                  throw new MapDataException("Cannot find region for walls");
               }

               shapeSection = wall.getConfigurationSection("cylinder");
               coords = shapeSection.getString("base").replace(" ", "").split(",");
               shape = new Cylinder(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), shapeSection.getInt("radius"), shapeSection.getInt("height"));
               regionShape = new Cylinder(new Vector(Double.valueOf(coords[0]), Double.valueOf("140"), Double.valueOf(coords[2])), shapeSection.getInt("radius"), shapeSection.getInt("height"));
            }

            MapWall daWall = new MapWall(key, name, timetilldrop, (Shape)shape, (Shape)regionShape);
            this.cj.add(daWall);
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
