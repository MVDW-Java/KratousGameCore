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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class CaptureTheWoolGame extends GameMap {
   public Map dy = new HashMap();

   public void ax() throws Exception {
      YamlConfiguration yml = this.bl.aS().bh();
      ConfigurationSection woolsSection = yml.getConfigurationSection("wools");
      Iterator var3 = woolsSection.getKeys(false).iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         ConfigurationSection section = woolsSection.getConfigurationSection(key);
         String name = section.getString("name");
         String teamName = section.getString("team");
         String colorName = section.getString("color");
         String[] locationSplit = section.getString("location").replace(" ", "").split(",");
         if (!this.bl.aS().bv().containsKey(teamName)) {
            throw new MapDataException("Unknown team " + teamName);
         }

         MapTeam team = (MapTeam)this.bl.aS().bv().get(teamName);
         Location location = new Location(this.bl.getWorld(), Double.valueOf(locationSplit[0]), Double.valueOf(locationSplit[1]), Double.valueOf(locationSplit[2]));
         this.dy.put(location, new CapturablePointWool(name, this.bl.getWorld().getBlockAt(location), colorName, team));
      }

   }

   public boolean aH() {
      return !this.bl.aU().equals(GameState.bD);
   }

   public String aI() {
      return "CTW";
   }

   public void aJ() {
      Core.e().j(ChatColor.DARK_AQUA + "CTW");
      Core.e().cn();
      this.cn();
   }

   public MapTeam aE() {
      Map<MapTeam, Integer> capturedCount = new HashMap();
      Iterator var2 = this.dy.values().iterator();

      while(var2.hasNext()) {
         CapturablePointWool point = (CapturablePointWool)var2.next();
         if (!capturedCount.containsKey(point.ca())) {
            capturedCount.put(point.ca(), 0);
         }

         if (point.cd()) {
            capturedCount.put(point.ca(), (Integer)capturedCount.get(point.ca()) + 1);
         }
      }

      MapTeam winner = (MapTeam)capturedCount.keySet().toArray()[0];
      Iterator var6 = capturedCount.keySet().iterator();

      while(var6.hasNext()) {
         MapTeam team = (MapTeam)var6.next();
         if (!team.equals(winner)) {
            if (((Integer)capturedCount.get(team)).equals(capturedCount.get(winner))) {
               winner = null;
            } else if ((Integer)capturedCount.get(team) > (Integer)capturedCount.get(winner)) {
               winner = team;
            }
         }
      }

      return winner;
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(BlockPlaceEvent e) {
      if (e.getBlockPlaced().getType().equals(Material.WOOL)) {
         GamePlayer gPlayer = Core.c().r(e.getPlayer().getName());
         CapturablePointWool point = (CapturablePointWool)this.dy.get(e.getBlockPlaced().getLocation());
         if (point == null) {
            return;
         }

         if (!point.ca().equals(gPlayer.ca())) {
            e.setCancelled(true);
            return;
         }

         if (!point.c(e.getBlockPlaced())) {
            e.getPlayer().sendMessage(Core.a("Oops! Wrong wool colour"));
            e.setCancelled(true);
            return;
         }

         Core.a(e.getPlayer().getDisplayName() + ChatColor.DARK_AQUA + " captured " + point.getName() + "!", true, true);
         this.a(gPlayer, MapRewardType.cB);
         Core.g().a(gPlayer, ObjectiveType.fW, point.getName(), 0);
         gPlayer.dC().a(AchievementType.I, 1);
      }

      this.co();
      this.cn();
   }

   @EventHandler(
      priority = EventPriority.HIGHEST
   )
   public void a(BlockBreakEvent e) {
      if (e.getBlock().getType().equals(Material.WOOL)) {
         CapturablePointWool point = (CapturablePointWool)this.dy.get(e.getBlock().getLocation());
         if (point != null) {
            e.getPlayer().sendMessage(Core.a("You cannot break wool once it's placed!"));
            e.setCancelled(true);
         }
      }

   }

   @EventHandler
   public void a(CraftItemEvent e) {
      if (e.getRecipe().getResult().getType().equals(Material.WOOL)) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   public void a(InventoryClickEvent e) {
      GamePlayer gPlayer = Core.c().r(e.getWhoClicked().getName());
      if (gPlayer.dH() && e.getCurrentItem() != null && e.getInventory().getType().equals(InventoryType.CHEST)) {
         if (e.getView().getBottomInventory().containsAtLeast(e.getCurrentItem(), 1) && e.getCurrentItem().getType().equals(Material.WOOL)) {
            e.setCancelled(true);
         }

         if (e.getView().getTopInventory().containsAtLeast(new ItemStack(Material.WOOL), 500)) {
            for(int i = 0; i < e.getView().getTopInventory().getSize(); ++i) {
               e.getView().getTopInventory().setItem(i, new ItemStack(e.getCurrentItem().getType(), 10, (short)e.getCurrentItem().getData().getData()));
            }
         }

      }
   }

   @EventHandler
   public void a(InventoryOpenEvent e) {
      if (e.getInventory().getType().equals(InventoryType.CHEST)) {
         if (e.getInventory().containsAtLeast(new ItemStack(Material.WOOL), 300)) {
            ItemStack wool = null;
            int valid = 0;
            ItemStack[] var4 = e.getView().getTopInventory().getContents();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               ItemStack stack = var4[var6];
               if (wool != null && wool.getData().equals(stack.getData())) {
                  ++valid;
               } else {
                  valid = 0;
               }

               if (stack.getType().equals(Material.WOOL)) {
                  wool = stack;
               }

               if (valid > 10) {
                  break;
               }
            }

            if (wool == null) {
               return;
            }

            for(int i = 0; i < e.getView().getTopInventory().getSize(); ++i) {
               e.getView().getTopInventory().setItem(i, new ItemStack(Material.WOOL, 10, (short)wool.getData().getData()));
            }
         }

      }
   }

   private void co() {
      List<MapTeam> nonFinishedTeams = new ArrayList();
      Iterator var2 = this.dy.values().iterator();

      while(var2.hasNext()) {
         CapturablePointWool point = (CapturablePointWool)var2.next();
         if (!point.cd() && !nonFinishedTeams.contains(point.ca())) {
            nonFinishedTeams.add(point.ca());
         }
      }

      if (nonFinishedTeams.size() != this.bj.size()) {
         this.bl.a(GameState.bC);
      }

   }

   private void cn() {
      int pointer = this.dy.size() + this.bj.size();
      Iterator var2 = this.bl.aS().bv().values().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         List<CapturablePointWool> teamPoints = this.e(team);
         Core.e().a(team.bP() + team.getName(), pointer--);
         Iterator var5 = teamPoints.iterator();

         while(var5.hasNext()) {
            CapturablePointWool point = (CapturablePointWool)var5.next();
            if (point.cd()) {
               Core.e().k(ChatColor.RED + " ✘ " + point.getName());
               Core.e().a(ChatColor.GREEN + " ✔ " + point.getName(), pointer--);
            } else {
               Core.e().a(ChatColor.RED + " ✘ " + point.getName(), pointer--);
            }
         }
      }

      Core.e().cn();
   }

   private List e(MapTeam team) {
      List<CapturablePointWool> teamPoints = new ArrayList();
      Iterator var3 = this.dy.values().iterator();

      while(var3.hasNext()) {
         CapturablePointWool point = (CapturablePointWool)var3.next();
         if (point.ca().equals(team)) {
            teamPoints.add(point);
         }
      }

      return teamPoints;
   }
}
