package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.a.AchievementType;
import org.Kratous.GameCore.f.GameMap;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapRewardType;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.f.a.a.MapDataException;
import org.Kratous.GameCore.f.c.ScoreboardHandler;
import org.Kratous.GameCore.j.ObjectiveType;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.o.Cuboid;
import org.Kratous.GameCore.o.Cylinder;
import org.Kratous.GameCore.o.Shape;
import org.Kratous.GameCore.q.Util3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

public class DestroyTheMonumentGame extends GameMap {
   private Map dJ = new HashMap();
   private Material dK;
   private int dL = 10;

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "DTM";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "Monuments");
      Core.e().cn();
      this.cn();
   }

   public void ax() throws Exception {
      ConfigurationSection monuments = this.bl.aS().bh().getConfigurationSection("monuments");
      Iterator var2 = monuments.getKeys(false).iterator();

      while(true) {
         String key;
         do {
            if (!var2.hasNext()) {
               var2 = this.bl.aS().bv().values().iterator();

               MapTeam team;
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  team = (MapTeam)var2.next();
               } while(this.dJ.containsKey(team));

               throw new MapDataException("Could not find core for team " + team.getName());
            }

            key = (String)var2.next();
         } while(key.equals("material"));

         ConfigurationSection monument = monuments.getConfigurationSection(key);
         String name = monument.getString("name");
         String teamName = monument.getString("team");
         Material type = Material.matchMaterial(monument.getString("material"));
         if (type == null) {
            throw new MapDataException("Unknown material " + monuments.getString("material"));
         }

         Shape shape = null;
         ConfigurationSection shapeSection;
         String[] coords;
         if (monument.getConfigurationSection("cuboid") != null) {
            shapeSection = monument.getConfigurationSection("cuboid");
            coords = shapeSection.getString("min").replace(" ", "").split(",");
            String[] maxCoords = shapeSection.getString("max").replace(" ", "").split(",");
            shape = new Cuboid(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
         } else {
            if (monument.getConfigurationSection("cylinder") == null) {
               throw new MapDataException("Cannot find region for monument");
            }

            shapeSection = monument.getConfigurationSection("cylinder");
            coords = shapeSection.getString("base").replace(" ", "").split(",");
            shape = new Cylinder(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), shapeSection.getInt("radius"), shapeSection.getInt("height"));
         }

         if (!this.bl.aS().bv().containsKey(teamName)) {
            throw new MapDataException("Cannot find team " + teamName);
         }

         MapTeam team = (MapTeam)this.bl.aS().bv().get(teamName);
         if (!this.dJ.containsKey(team)) {
            this.dJ.put(team, new ArrayList());
         }

         List<Block> blocks = new ArrayList();
         Iterator var17 = ((Shape)shape).fd().iterator();

         while(var17.hasNext()) {
            Vector v = (Vector)var17.next();
            Block block = this.bl.getWorld().getBlockAt(v.toLocation(this.bl.getWorld()));
            if (block.getType().equals(type)) {
               blocks.add(block);
            }
         }

         this.dK = type;
         ((List)this.dJ.get(team)).add(new DestroyableMonument(name, type, team, blocks));
      }
   }

   public MapTeam aE() {
      Map<MapTeam, Integer> scores = new HashMap();
      Iterator var2 = this.dJ.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         int livingMonuments = 0;

         DestroyableMonument monument;
         for(Iterator var5 = ((List)this.dJ.get(team)).iterator(); var5.hasNext(); livingMonuments += monument.cr()) {
            monument = (DestroyableMonument)var5.next();
         }

         scores.put(team, livingMonuments);
      }

      MapTeam winners = null;

      Entry entry;
      for(Iterator var8 = Util3.b((Map)scores).iterator(); var8.hasNext(); winners = (MapTeam)entry.getKey()) {
         entry = (Entry)var8.next();
         if (winners != null) {
            if (((Integer)scores.get(winners)).equals(scores.get(entry.getKey()))) {
               winners = null;
            }
            break;
         }
      }

      return winners;
   }

   public void az() {
      Iterator var1;
      MapTeam team;
      Iterator var3;
      DestroyableMonument monument;
      Iterator var5;
      Block block;
      if (Core.b().X().aP() / (double)this.bl.aS().bn().getTimeLimit() >= 0.85D) {
         if (this.dK.equals(Material.GOLD_BLOCK)) {
            var1 = this.dJ.keySet().iterator();

            label76:
            while(var1.hasNext()) {
               team = (MapTeam)var1.next();
               var3 = ((List)this.dJ.get(team)).iterator();

               while(true) {
                  do {
                     if (!var3.hasNext()) {
                        continue label76;
                     }

                     monument = (DestroyableMonument)var3.next();
                  } while(!monument.isAlive());

                  monument.setMaterial(Material.GLASS);
                  var5 = monument.getBlocks().iterator();

                  while(var5.hasNext()) {
                     block = (Block)var5.next();
                     if (block.getType().equals(Material.GOLD_BLOCK)) {
                        block.setType(Material.GLASS);
                     }
                  }
               }
            }

            this.dK = Material.GLASS;
            Core.a("The monuments have now changed to glass", true, true);
         }
      } else if (Core.b().X().aP() / (double)this.bl.aS().bn().getTimeLimit() >= 0.75D && this.dK.equals(Material.OBSIDIAN)) {
         var1 = this.dJ.keySet().iterator();

         label54:
         while(var1.hasNext()) {
            team = (MapTeam)var1.next();
            var3 = ((List)this.dJ.get(team)).iterator();

            while(true) {
               do {
                  if (!var3.hasNext()) {
                     continue label54;
                  }

                  monument = (DestroyableMonument)var3.next();
               } while(!monument.isAlive());

               monument.setMaterial(Material.GOLD_BLOCK);
               var5 = monument.getBlocks().iterator();

               while(var5.hasNext()) {
                  block = (Block)var5.next();
                  if (block.getType().equals(Material.OBSIDIAN)) {
                     block.setType(Material.GOLD_BLOCK);
                  }
               }
            }
         }

         this.dK = Material.GOLD_BLOCK;
         Core.a("The monuments have now changed to gold", true, true);
      }

   }

   @EventHandler
   public void a(BlockPlaceEvent e) {
      DestroyableMonument monument = this.e(e.getBlock());
      if (monument != null) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(BlockBreakEvent e) {
      if (!e.isCancelled()) {
         DestroyableMonument monument = this.e(e.getBlock());
         if (monument != null) {
            GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
            if (monument.ca().equals(gPlayer.ca())) {
               gPlayer.getPlayer().sendMessage(Core.a(ChatColor.RED + "This is your monument!"));
               e.setCancelled(true);
               return;
            }

            e.getBlock().setType(Material.AIR);
            e.setCancelled(true);
            if (monument.cr() == 0) {
               Core.g().a(gPlayer, ObjectiveType.fS, monument.getName(), 0);
               Core.a(e.getPlayer().getDisplayName() + ChatColor.RED + " has destroyed " + monument.ca().bP() + monument.getName() + ChatColor.DARK_AQUA, true, true);
               gPlayer.dC().a(AchievementType.G, 1);
               this.co();
            } else {
               Core.a(e.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + " is destroying " + monument.ca().bP() + monument.getName() + ChatColor.DARK_AQUA + ", " + monument.cr() + "% remaining", true, true);
            }

            this.bl.getWorld().strikeLightningEffect(e.getBlock().getLocation());
            this.a(gPlayer, MapRewardType.cB);
         }

         this.cn();
      }
   }

   private void co() {
      Iterator var1 = this.dJ.keySet().iterator();

      int livingMonuments;
      do {
         if (!var1.hasNext()) {
            return;
         }

         MapTeam team = (MapTeam)var1.next();
         livingMonuments = 0;
         Iterator var4 = ((List)this.dJ.get(team)).iterator();

         while(var4.hasNext()) {
            DestroyableMonument monument = (DestroyableMonument)var4.next();
            if (monument.isAlive()) {
               ++livingMonuments;
            }
         }
      } while(livingMonuments != 0);

      this.bl.a(GameState.bC);
   }

   private DestroyableMonument e(Block b) {
      Iterator var2 = this.dJ.values().iterator();

      while(var2.hasNext()) {
         List<DestroyableMonument> monumentsList = (List)var2.next();
         Iterator var4 = monumentsList.iterator();

         while(var4.hasNext()) {
            DestroyableMonument monument = (DestroyableMonument)var4.next();
            if (monument.f(b)) {
               return monument;
            }
         }
      }

      return null;
   }

   private void cn() {
      this.dL = 10;
      Core.e().cM();
      Iterator var1 = this.dJ.keySet().iterator();

      while(var1.hasNext()) {
         MapTeam team = (MapTeam)var1.next();
         Core.e().a(ScoreboardHandler.o(this.dL) + " ", this.dL);
         --this.dL;

         for(Iterator var3 = ((List)this.dJ.get(team)).iterator(); var3.hasNext(); --this.dL) {
            DestroyableMonument monument = (DestroyableMonument)var3.next();
            if (monument.cr() == 0) {
               Core.e().a(ChatColor.RED + " ✘ " + ChatColor.GRAY + " - " + team.bP() + ChatColor.STRIKETHROUGH + monument.getName(), this.dL);
            } else {
               Core.e().a(ChatColor.GOLD + "" + monument.cr() + "%" + ChatColor.GRAY + " - " + team.bP() + monument.getName(), this.dL);
            }
         }
      }

      this.co();
   }
}
