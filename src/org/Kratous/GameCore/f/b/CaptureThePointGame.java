package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.GameMap;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapRewardType;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.f.a.a.MapDataException;
import org.Kratous.GameCore.j.ObjectiveType;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.Shape.Cuboid;
import org.Kratous.GameCore.Shape.Cylinder;
import org.Kratous.GameCore.Shape.Shape;
import org.Kratous.GameCore.q.Util3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

public class CaptureThePointGame extends GameMap {
   private List dw = new ArrayList();
   private int bq = 12;
   private int dx;

   public void ax() throws Exception {
      YamlConfiguration yml = this.bl.aS().bh();
      ConfigurationSection pointsSection = yml.getConfigurationSection("points");
      this.dx = yml.getInt("config.damagetime", 5) * 4;

      String name;
      MapTeam team;
      Object shape;
      for(Iterator var3 = pointsSection.getKeys(false).iterator(); var3.hasNext(); this.dw.add(new CapturablePoint(name, ((Shape)shape).fd(), team))) {
         String key = (String)var3.next();
         ConfigurationSection point = pointsSection.getConfigurationSection(key);
         name = point.getString("name");
         if (!this.bl.aS().bv().containsKey(point.getString("team"))) {
            throw new MapDataException("Cannot find team " + point.getString("team"));
         }

         team = (MapTeam)this.bl.aS().bv().get(point.getString("team"));
         shape = null;
         ConfigurationSection shapeSection;
         String[] coords;
         if (point.getConfigurationSection("cuboid") != null) {
            shapeSection = point.getConfigurationSection("cuboid");
            coords = shapeSection.getString("min").replace(" ", "").split(",");
            String[] maxCoords = shapeSection.getString("max").replace(" ", "").split(",");
            shape = new Cuboid(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), new Vector(Double.valueOf(maxCoords[0]), Double.valueOf(maxCoords[1]), Double.valueOf(maxCoords[2])));
         } else {
            if (point.getConfigurationSection("cylinder") == null) {
               throw new MapDataException("Cannot find region for point");
            }

            shapeSection = point.getConfigurationSection("cylinder");
            coords = shapeSection.getString("base").replace(" ", "").split(",");
            shape = new Cylinder(new Vector(Double.valueOf(coords[0]), Double.valueOf(coords[1]), Double.valueOf(coords[2])), shapeSection.getInt("radius"), shapeSection.getInt("height"));
         }
      }

   }

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "CTP";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "CTP");
      Core.e().cn();
      this.cn();
   }

   public void az() {
      if (this.bq < this.dx) {
         ++this.bq;
      } else {
         List<CapturablePoint> destroyedPoints = new ArrayList();
         Iterator var2 = Core.c().getPlayers().iterator();

         while(true) {
            GamePlayer gPlayer;
            Iterator var4;
            do {
               if (!var2.hasNext()) {
                  var2 = destroyedPoints.iterator();

                  while(var2.hasNext()) {
                     CapturablePoint point = (CapturablePoint)var2.next();
                     var4 = Core.c().getPlayers().iterator();

                     while(var4.hasNext()) {
                        GamePlayer gPlayer1 = (GamePlayer)var4.next();
                        if (gPlayer1.dH() && point.q(gPlayer1) && point.ca().equals(gPlayer1.ca())) {
                           Core.g().a(gPlayer1, ObjectiveType.fV, point.getName(), 0);
                           this.a(gPlayer1, MapRewardType.cB);
                        }
                     }
                  }

                  this.cn();
                  this.co();
                  this.bq = 0;
                  return;
               }

               gPlayer = (GamePlayer)var2.next();
            } while(!gPlayer.dH());

            var4 = this.dw.iterator();

            while(var4.hasNext()) {
               CapturablePoint point = (CapturablePoint)var4.next();
               if (point.cb() > 0 && point.q(gPlayer) && !point.ca().equals(gPlayer.ca())) {
                  if (point.cb() == 5) {
                     Core.a(gPlayer.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + " has captured " + point.ca().bP() + point.getName() + ChatColor.DARK_AQUA + "!", true, true);
                     destroyedPoints.add(point);
                  } else if (point.cb() != 0) {
                     Core.a(gPlayer.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + " is capturing " + point.ca().bP() + point.getName() + ChatColor.DARK_AQUA + " " + (point.cb() - 5) + "% remaining", true, true);
                  }

                  point.cc();
               }
            }
         }
      }
   }

   public MapTeam aE() {
      Map<MapTeam, Integer> percents = new HashMap();

      CapturablePoint point;
      for(Iterator var2 = this.dw.iterator(); var2.hasNext(); percents.put(point.ca(), (Integer)percents.get(point.ca()) + point.cb())) {
         point = (CapturablePoint)var2.next();
         if (!percents.containsKey(point.ca())) {
            percents.put(point.ca(), 0);
         }
      }

      MapTeam winningTeam = null;
      Iterator var6 = Util3.b((Map)percents).iterator();

      while(var6.hasNext()) {
         Entry<MapTeam, Integer> team = (Entry)var6.next();
         if (winningTeam == null) {
            winningTeam = (MapTeam)team.getKey();
         } else if (((Integer)percents.get(winningTeam)).equals(percents.get(team.getKey()))) {
            return null;
         }
      }

      return winningTeam;
   }

   private void cn() {
      Iterator var1 = this.dw.iterator();

      while(var1.hasNext()) {
         CapturablePoint point = (CapturablePoint)var1.next();
         Core.e().a(point.ca().bP() + point.getName(), point.cb());
      }

      Core.e().cn();
   }

   private void co() {
      List<MapTeam> alive = new ArrayList();
      Iterator var2 = this.dw.iterator();

      while(var2.hasNext()) {
         CapturablePoint point = (CapturablePoint)var2.next();
         if (point.cb() != 0 && !alive.contains(point.ca())) {
            alive.add(point.ca());
         }
      }

      if (alive.size() <= 1) {
         this.bl.a(GameState.bC);
      }

   }
}
