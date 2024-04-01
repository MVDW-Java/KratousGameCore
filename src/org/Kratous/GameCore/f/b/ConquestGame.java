package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.GameMap;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.f.a.a.MapDataException;
import org.Kratous.GameCore.g.a.PlayerKilledEvent;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.Shape.Cylinder;
import org.Kratous.GameCore.q.Util3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;

public class ConquestGame extends GameMap {
   private List dz;
   private Map dA = new HashMap();
   private int bq = 8;
   private int dB;
   private boolean dC;
   private int dD;

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "CONQUEST";
   }

   public void ax() throws MapDataException {
      this.dz = new ArrayList();
      ConfigurationSection YML = this.bl.aS().bh();
      if (!YML.contains("points")) {
         throw new MapDataException("YML must contain a 'points' section");
      } else {
         Iterator var2;
         String name;
         Cylinder shape;
         for(var2 = YML.getConfigurationSection("points").getKeys(false).iterator(); var2.hasNext(); this.dz.add(new CapturePoint(name, shape, this.bl.getWorld()))) {
            String key = (String)var2.next();
            ConfigurationSection pointSection = YML.getConfigurationSection("points." + key);
            name = pointSection.getString("name");
            shape = null;
            if (pointSection.contains("cylinder")) {
               shape = new Cylinder(pointSection.getConfigurationSection("cylinder"));
            } else {
               if (!pointSection.contains("cuboid")) {
                  throw new MapDataException("Could not find a cuboid/cylinder for point " + name);
               }

               shape = new Cylinder(pointSection.getConfigurationSection("cuboid"));
            }
         }

         this.dC = YML.getBoolean("config.killpoints", true);
         this.dB = YML.getInt("config.goal", 500);
         this.dD = YML.getInt("config.rate", 1);
         var2 = this.bl.aS().bv().values().iterator();

         while(var2.hasNext()) {
            MapTeam team = (MapTeam)var2.next();
            this.dA.put(team, 0);
         }

      }
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "Conquest");
      this.cn();
   }

   public void az() {
      Iterator var1;
      CapturePoint point;
      label162:
      for(var1 = this.dz.iterator(); var1.hasNext(); point.cj()) {
         point = (CapturePoint)var1.next();
         MapTeam dominatingTeam = null;
         Map<MapTeam, Integer> players = new HashMap();
         int updateAmount = 0;
         Iterator var6 = this.players.iterator();

         while(true) {
            GamePlayer gPlayer;
            do {
               if (!var6.hasNext()) {
                  if (!players.isEmpty()) {
                     List<Entry<MapTeam, Integer>> sorted = Util3.b((Map)players);
                     if (sorted.size() == 1 || ((Entry)sorted.get(0)).getValue() != ((Entry)sorted.get(1)).getValue()) {
                        dominatingTeam = (MapTeam)((Entry)sorted.get(0)).getKey();
                        if (dominatingTeam.equals(point.cf()) && point.ch()) {
                           dominatingTeam = null;
                        }
                     }
                  }

                  boolean changeColours = false;
                  if (dominatingTeam != null) {
                     point.a((double)updateAmount);
                     if (point.cg() == null) {
                        point.d(dominatingTeam);
                     }

                     if (point.ce() >= 100.0D) {
                        point.b(100.0D);
                        point.c(dominatingTeam);
                        if (point.ci()) {
                           Core.a(point.cf().bP() + point.cf().getName() + ChatColor.DARK_AQUA + " captured " + ChatColor.GOLD + point.getName(), true, true);
                           point.c(false);
                        }

                        changeColours = true;
                     } else if (point.ce() == 0.0D) {
                        point.c((MapTeam)null);
                        point.c(true);
                        point.d((MapTeam)null);
                        point.d(true);
                        changeColours = true;
                     } else if (point.ce() < 0.0D) {
                        point.c(true);
                        point.c((MapTeam)null);
                        point.d((MapTeam)null);
                        point.b(-point.ce());
                        point.d(true);
                        changeColours = true;
                     }
                  } else if (players.isEmpty()) {
                     point.d((MapTeam)null);
                     if (point.ce() < 100.0D && point.cd() && point.ce() > 20.0D) {
                        point.a(2.0D);
                        if (point.ce() >= 100.0D) {
                           point.b(100.0D);
                           changeColours = true;
                        }
                     } else if (point.ce() <= 20.0D || !point.cd()) {
                        point.a(-1.0D);
                        if (point.ce() <= 0.0D) {
                           point.b(0.0D);
                           point.c((MapTeam)null);
                           point.c(true);
                           changeColours = true;
                        }
                     }
                  }

                  Iterator var13 = point.bV().fd().iterator();

                  while(true) {
                     Block block;
                     do {
                        if (!var13.hasNext()) {
                           continue label162;
                        }

                        Vector vector = (Vector)var13.next();
                        block = vector.toLocation(this.bl.getWorld()).getBlock();
                     } while(!block.getType().equals(Material.WOOL) && !block.getType().equals(Material.STAINED_CLAY) && !block.getType().equals(Material.STAINED_GLASS) && !block.getType().equals(Material.STAINED_GLASS_PANE));

                     byte color = !(point.ce() <= 20.0D) && point.cd() ? this.a(point.cf().bP()).getData() : DyeColor.WHITE.getData();
                     if (block.getData() != color) {
                        block.setData(color);
                        point.d(true);
                     }
                  }
               }

               gPlayer = (GamePlayer)var6.next();
            } while(!point.bV().d(gPlayer.getPlayer().getLocation().toVector()));

            if (point.cd() && !gPlayer.ca().equals(point.cf())) {
               if (!gPlayer.ca().equals(point.cf())) {
                  updateAmount -= updateAmount < 0 ? 1 : 2;
               }
            } else {
               updateAmount += updateAmount > 0 ? 1 : 2;
            }

            if (!players.containsKey(gPlayer.ca())) {
               players.put(gPlayer.ca(), 1);
            } else {
               players.put(gPlayer.ca(), (Integer)players.get(gPlayer.ca()) + 1);
            }
         }
      }

      --this.bq;
      if (this.bq < 0) {
         var1 = this.dz.iterator();

         while(var1.hasNext()) {
            point = (CapturePoint)var1.next();
            if (point.cd()) {
               this.dA.put(point.cf(), (Integer)this.dA.get(point.cf()) + this.dD);
            }
         }

         this.bq = 8;
      }

      if (this.bq % 2 == 0) {
         this.cn();
      }

   }

   public MapTeam aE() {
      MapTeam winningTeam = (MapTeam)((Entry)Util3.b(this.dA).get(0)).getKey();
      Iterator var2 = this.dA.keySet().iterator();

      MapTeam team;
      do {
         if (!var2.hasNext()) {
            return winningTeam;
         }

         team = (MapTeam)var2.next();
      } while(winningTeam.equals(team) || !((Integer)this.dA.get(team)).equals(this.dA.get(winningTeam)));

      return null;
   }

   @EventHandler
   public void a(PlayerKilledEvent e) {
      if (this.dC) {
         this.dA.put(e.cQ().ca(), (Integer)this.dA.get(e.cQ().ca()) + 1);
      }

   }

   private DyeColor a(ChatColor color) {
      switch(color) {
      case RED:
      case DARK_RED:
         return DyeColor.RED;
      case BLUE:
      case DARK_BLUE:
         return DyeColor.BLUE;
      case BLACK:
         return DyeColor.BLACK;
      case GOLD:
      case YELLOW:
         return DyeColor.YELLOW;
      case GREEN:
      case DARK_GREEN:
         return DyeColor.GREEN;
      case DARK_PURPLE:
         return DyeColor.PURPLE;
      case LIGHT_PURPLE:
         return DyeColor.PINK;
      default:
         return DyeColor.CYAN;
      }
   }

   private void cn() {
      Iterator var1 = this.bj.keySet().iterator();

      while(var1.hasNext()) {
         MapTeam team = (MapTeam)var1.next();
         Core.e().a(team.bP() + team.getName(), (Integer)this.dA.get(team));
         if ((Integer)this.dA.get(team) >= this.dB) {
            this.bl.a(GameState.bC);
            return;
         }
      }

      Core.e().a("----------------", -1);
      int pointer = -2;

      label43:
      for(Iterator var7 = this.dz.iterator(); var7.hasNext(); --pointer) {
         CapturePoint point = (CapturePoint)var7.next();
         if (point.ck()) {
            Iterator var4 = this.bj.keySet().iterator();

            while(true) {
               MapTeam team;
               do {
                  if (!var4.hasNext()) {
                     Core.e().k(ChatColor.GRAY + point.getName());
                     Core.e().a((point.cd() ? point.cf().bP() : ChatColor.GRAY) + point.getName(), pointer);
                     point.d(false);
                     continue label43;
                  }

                  team = (MapTeam)var4.next();
               } while(point.cd() && point.cf().equals(team));

               Core.e().k(team.bP() + point.getName());
            }
         }
      }

   }
}
