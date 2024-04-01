package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.a.AchievementType;
import org.Kratous.GameCore.f.GameMap;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapRewardType;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.f.a.a.MapDataException;
import org.Kratous.GameCore.j.ObjectiveType;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.o.Cuboid;
import org.Kratous.GameCore.o.Cylinder;
import org.Kratous.GameCore.o.Shape;
import org.Kratous.GameCore.q.Util3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class DestroyTheCoreGame extends GameMap {
   private Map dF = new HashMap();
   private Material dG;
   private boolean dH;

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "DTC";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "DTC");
      Core.e().cn();
      this.cn();
   }

   public void ax() throws Exception {
      ConfigurationSection cores = this.bl.aS().bh().getConfigurationSection("cores");
      Material type = Material.matchMaterial(cores.getString("material"));
      int minimumDistance = cores.getInt("mindistance", 5);
      if (minimumDistance > 10) {
         minimumDistance = 10;
      }

      if (type == null) {
         throw new MapDataException("Unknown material " + cores.getString("material"));
      } else {
         Iterator var4 = cores.getKeys(false).iterator();

         label77:
         while(true) {
            String key;
            do {
               do {
                  if (!var4.hasNext()) {
                     var4 = this.bl.aS().bv().values().iterator();

                     MapTeam team;
                     do {
                        if (!var4.hasNext()) {
                           return;
                        }

                        team = (MapTeam)var4.next();
                     } while(this.dF.containsKey(team));

                     throw new MapDataException("Could not find core for team " + team.getName());
                  }

                  key = (String)var4.next();
               } while(key.equals("material"));
            } while(key.equals("mindistance"));

            ConfigurationSection core = cores.getConfigurationSection(key);
            String name = core.getString("name");
            String teamName = core.getString("team");
            Shape shape = null;
            ConfigurationSection shapeSection;
            String[] coords;
            if (core.getConfigurationSection("cuboid") != null) {
               shapeSection = core.getConfigurationSection("cuboid");
               coords = shapeSection.getString("min").replace(" ", "").split(",");
               String[] maxCoords = shapeSection.getString("max").replace(" ", "").split(",");
               shape = new Cuboid(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
            } else {
               if (core.getConfigurationSection("cylinder") == null) {
                  throw new MapDataException("Cannot find region for core");
               }

               shapeSection = core.getConfigurationSection("cylinder");
               coords = shapeSection.getString("base").replace(" ", "").split(",");
               shape = new Cylinder(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), shapeSection.getInt("radius"), shapeSection.getInt("height"));
            }

            if (!this.bl.aS().bv().containsKey(teamName)) {
               throw new MapDataException("Cannot find team " + teamName);
            }

            MapTeam team = (MapTeam)this.bl.aS().bv().get(teamName);
            if (!this.dF.containsKey(team)) {
               this.dF.put(team, new ArrayList());
            }

            List<Block> blocks = new ArrayList();
            Iterator var18 = ((Shape)shape).fd().iterator();

            while(true) {
               Block block;
               do {
                  if (!var18.hasNext()) {
                     this.dG = type;
                     ((List)this.dF.get(team)).add(new DestroyableCore(name, type, team, blocks, minimumDistance, (Shape)shape));
                     continue label77;
                  }

                  Vector v = (Vector)var18.next();
                  block = this.bl.getWorld().getBlockAt(v.toLocation(this.bl.getWorld()));
               } while(!block.getType().equals(type) && !block.getType().equals(Material.LAVA) && !block.getType().equals(Material.STATIONARY_LAVA));

               blocks.add(block);
            }
         }
      }
   }

   public MapTeam aE() {
      Map<MapTeam, Integer> scores = new HashMap();
      Iterator var2 = this.dF.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         int livingCores = 0;

         DestroyableCore monument;
         for(Iterator var5 = ((List)this.dF.get(team)).iterator(); var5.hasNext(); livingCores += monument.isAlive() ? 1 : 0) {
            monument = (DestroyableCore)var5.next();
         }

         scores.put(team, livingCores);
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
      DestroyableCore core;
      Iterator var5;
      Block block;
      if (Core.b().X().aP() / (double)this.bl.aS().bn().getTimeLimit() >= 0.85D) {
         if (this.dG.equals(Material.GOLD_BLOCK)) {
            var1 = this.dF.keySet().iterator();

            label76:
            while(var1.hasNext()) {
               team = (MapTeam)var1.next();
               var3 = ((List)this.dF.get(team)).iterator();

               while(true) {
                  do {
                     if (!var3.hasNext()) {
                        continue label76;
                     }

                     core = (DestroyableCore)var3.next();
                  } while(!core.isAlive());

                  core.setMaterial(Material.GLASS);
                  var5 = core.getBlocks().iterator();

                  while(var5.hasNext()) {
                     block = (Block)var5.next();
                     if (block.getType().equals(Material.GOLD_BLOCK)) {
                        block.setType(Material.GLASS);
                     }
                  }
               }
            }

            this.dG = Material.GLASS;
            Core.a("The cores have now changed to glass", true, true);
         }
      } else if (Core.b().X().aP() / (double)this.bl.aS().bn().getTimeLimit() >= 0.75D && this.dG.equals(Material.OBSIDIAN)) {
         var1 = this.dF.keySet().iterator();

         label54:
         while(var1.hasNext()) {
            team = (MapTeam)var1.next();
            var3 = ((List)this.dF.get(team)).iterator();

            while(true) {
               do {
                  if (!var3.hasNext()) {
                     continue label54;
                  }

                  core = (DestroyableCore)var3.next();
               } while(!core.isAlive());

               core.setMaterial(Material.GOLD_BLOCK);
               var5 = core.getBlocks().iterator();

               while(var5.hasNext()) {
                  block = (Block)var5.next();
                  if (block.getType().equals(Material.OBSIDIAN)) {
                     block.setType(Material.GOLD_BLOCK);
                  }
               }
            }
         }

         this.dG = Material.GOLD_BLOCK;
         Core.a("The cores have now changed to gold", true, true);
      }

   }

   @EventHandler
   public void a(BlockPlaceEvent e) {
      DestroyableCore core = this.d(e.getBlock());
      if (core != null) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(BlockBreakEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
      DestroyableCore core = this.d(e.getBlock());
      if (core != null && e.getBlock().getType().equals(this.dG)) {
         if (core.ca().equals(gPlayer.ca())) {
            gPlayer.getPlayer().sendMessage(Core.a("This is your core!"));
            e.setCancelled(true);
            return;
         }

         this.a(gPlayer, MapRewardType.cB);
         Core.g().a(gPlayer, ObjectiveType.fR, "", 0);
         this.bl.getWorld().strikeLightningEffect(e.getBlock().getLocation());
         gPlayer.dC().a(AchievementType.F, 1);
         e.setCancelled(true);
         e.getBlock().setType(Material.AIR);
      }

      this.cn();
   }

   @EventHandler
   public void a(BlockFromToEvent e) {
      Iterator lavaBlocks;
      Iterator var4;
      if (!e.getBlock().getType().equals(Material.WATER) && !e.getBlock().getType().equals(Material.STATIONARY_WATER)) {
         lavaBlocks = null;
         switch(e.getBlock().getType()) {
         case LAVA:
         case STATIONARY_LAVA:
            List<Block> lavaBlocks1 = this.a(e.getBlock(), new ArrayList(), 0);
            DestroyableCore core = null;
            var4 = lavaBlocks1.iterator();

            while(var4.hasNext()) {
               Block block = (Block)var4.next();
               core = this.d(block);
               if (core != null) {
                  break;
               }
            }

            if (core == null) {
               return;
            } else if (!core.isAlive()) {
               e.setCancelled(true);
               return;
            } else {
               double distance = e.getBlock().getLocation().distance(core.cq().getLocation());
               if (distance >= (double)core.cp()) {
                  core.b(false);
                  Core.a(core.ca().bP() + core.getName() + ChatColor.DARK_AQUA + " has leaked!", true, true);
                  this.cn();
                  this.co();
               }

               return;
            }
         default:
         }
      } else {
         lavaBlocks = this.dF.values().iterator();

         label61:
         while(lavaBlocks.hasNext()) {
            List<DestroyableCore> coreList = (List)lavaBlocks.next();
            var4 = coreList.iterator();

            while(true) {
               DestroyableCore core;
               do {
                  if (!var4.hasNext()) {
                     continue label61;
                  }

                  core = (DestroyableCore)var4.next();
               } while(!core.bV().d(e.getToBlock().getLocation().toVector()) && !core.bV().d(e.getBlock().getLocation().toVector()));

               e.setCancelled(true);
            }
         }

      }
   }

   @EventHandler
   public void a(PlayerBucketFillEvent e) {
      if (e.getBlockClicked() != null && e.getBlockClicked().getType().equals(Material.STATIONARY_LAVA)) {
         DestroyableCore core = this.d(e.getBlockClicked());
         if (core != null) {
            e.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void a(PlayerBucketEmptyEvent e) {
      if (e.getBlockClicked() != null) {
         Iterator var2 = this.dF.values().iterator();

         while(var2.hasNext()) {
            List<DestroyableCore> coreList = (List)var2.next();
            Iterator var4 = coreList.iterator();

            while(var4.hasNext()) {
               DestroyableCore core = (DestroyableCore)var4.next();
               if (core.bV().d(e.getBlockClicked().getLocation().toVector())) {
                  e.setCancelled(true);
                  return;
               }
            }
         }
      }

   }

   @EventHandler
   public void a(PlayerInteractEvent e) {
      if (e.getClickedBlock() != null && e.getPlayer().getItemInHand() != null && this.d(e.getClickedBlock()) != null && e.getPlayer().getItemInHand().getType().equals(Material.BUCKET)) {
         e.setCancelled(true);
      }

   }

   private List a(Block lavaBlock, List collected, int iterations) {
      if (!lavaBlock.getType().equals(Material.LAVA) && !lavaBlock.getType().equals(Material.STATIONARY_LAVA)) {
         return collected;
      } else if (lavaBlock.getData() == 0) {
         return collected;
      } else if (iterations >= 100) {
         return collected;
      } else if (collected.contains(lavaBlock)) {
         return collected;
      } else {
         collected.add(lavaBlock);
         List<BlockFace> faceList = Arrays.asList(BlockFace.UP, BlockFace.WEST, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.DOWN);
         Iterator var5 = faceList.iterator();

         while(var5.hasNext()) {
            BlockFace face = (BlockFace)var5.next();
            this.a(lavaBlock.getRelative(face), collected, iterations++);
         }

         return collected;
      }
   }

   private DestroyableCore d(Block b) {
      Iterator var2 = this.dF.values().iterator();

      while(var2.hasNext()) {
         List<DestroyableCore> coreList = (List)var2.next();
         Iterator var4 = coreList.iterator();

         while(var4.hasNext()) {
            DestroyableCore core = (DestroyableCore)var4.next();
            if (core.f(b)) {
               return core;
            }
         }
      }

      return null;
   }

   private void co() {
      Map<MapTeam, Integer> scores = new HashMap();
      Iterator var2 = this.dF.keySet().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         int livingCores = 0;

         DestroyableCore monument;
         for(Iterator var5 = ((List)this.dF.get(team)).iterator(); var5.hasNext(); livingCores += monument.isAlive() ? 1 : 0) {
            monument = (DestroyableCore)var5.next();
         }

         scores.put(team, livingCores);
      }

      var2 = scores.values().iterator();

      while(var2.hasNext()) {
         int score = (Integer)var2.next();
         if (score == 0) {
            this.bl.a(GameState.bC);
         }
      }

   }

   private void cn() {
      int pointer = this.bj.size();

      Iterator var2;
      MapTeam team;
      for(var2 = this.bl.aS().bv().values().iterator(); var2.hasNext(); pointer += ((List)this.dF.get(team)).size()) {
         team = (MapTeam)var2.next();
      }

      Core.e().cM();
      var2 = this.bl.aS().bv().values().iterator();

      while(var2.hasNext()) {
         team = (MapTeam)var2.next();
         Core.e().a(team.bP() + team.getName(), pointer--);
         Iterator var4 = ((List)this.dF.get(team)).iterator();

         while(var4.hasNext()) {
            DestroyableCore core = (DestroyableCore)var4.next();
            if (!core.isAlive()) {
               Core.e().a(ChatColor.RED + " ✘ " + core.ca().bP() + core.getName(), pointer--);
            } else {
               Core.e().a(ChatColor.GREEN + " ✔ " + core.ca().bP() + core.getName(), pointer--);
            }
         }
      }

      Core.e().cn();
   }
}
