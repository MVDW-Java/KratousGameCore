package org.Kratous.GameCore.q;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.d.b.TicketItem;
import org.Kratous.GameCore.f.GameState;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.p.TutorialData;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.server.v1_8_R3.EntityTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class Util3 {
   private static String iO = "xxADDYMExx";

   public static Map E(GamePlayer gPlayer) {
      Map<Integer, ItemStack> inventory = new HashMap();
      ItemStack stack = null;
      ItemMeta meta = null;
      if (Core.b().V() != null && Core.b().V().aT().aH()) {
         stack = new ItemStack(gPlayer.dH() ? Material.MAGMA_CREAM : Material.SLIME_BALL, 1);
         meta = stack.getItemMeta();
         meta.setDisplayName((gPlayer.dH() ? ChatColor.RED + "In Game" : ChatColor.GREEN + "Join Game: " + Core.b().V().aS().bk()) + ChatColor.GRAY + " (Right click to use)");
         stack.setItemMeta(meta);
         inventory.put(0, stack);
      } else {
         stack = new ItemStack(Material.MAGMA_CREAM, 1);
         meta = stack.getItemMeta();
         if (Core.b().V() == null) {
            meta.setDisplayName(ChatColor.DARK_RED + "You cannot join a game on cooldown");
         } else {
            switch(Core.b().V().aU()) {
            case bD:
               meta.setDisplayName(ChatColor.DARK_RED + "You cannot join a game on cooldown");
               break;
            default:
               meta.setDisplayName(ChatColor.DARK_RED + "You cannot join a game in progress");
            }
         }

         stack.setItemMeta(meta);
         stack.setDurability((short)-1);
         inventory.put(0, stack);
      }

      stack = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.SKELETON.ordinal());
      SkullMeta skullMeta = (SkullMeta)stack.getItemMeta();
      skullMeta.setOwner(gPlayer.getPlayer().getName());
      skullMeta.setDisplayName(ChatColor.GREEN + "Join Spectators" + ChatColor.GRAY + " (Right click to use)");
      stack.setItemMeta(skullMeta);
      inventory.put(1, stack);
      if (!gPlayer.dH() && !gPlayer.dI()) {
         if (Core.a().getConfig().getBoolean("game.cosmeticsEnabled", false)) {
            stack = new ItemStack(Material.ENDER_CHEST);
            meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Cosmetics" + ChatColor.GRAY + " (Right click to use)");
            stack.setItemMeta(meta);
            inventory.put(8, stack);
         } else {
            if (gPlayer.getPlayer().hasPermission("gamecore.vip")) {
               stack = new ItemStack(Material.REDSTONE_TORCH_ON);
               meta = stack.getItemMeta();
               meta.setDisplayName(ChatColor.DARK_AQUA + "VIP Effects" + ChatColor.GRAY + " (Right click to use)");
               stack.setItemMeta(meta);
            } else {
               stack = new ItemStack(Material.DIAMOND);
               meta = stack.getItemMeta();
               meta.setDisplayName(ChatColor.DARK_AQUA + "Buy VIP" + ChatColor.GRAY + " (Right click to use)");
               stack.setItemMeta(meta);
            }

            inventory.put(8, stack);
         }
      }

      if (!gPlayer.dH() && Core.b().V().aS().br() && !Core.b().V().aU().equals(GameState.bD)) {
         stack = new ItemStack(Material.BOOK);
         meta = stack.getItemMeta();
         meta.setDisplayName(ChatColor.DARK_AQUA + "View Tutorial" + ChatColor.GRAY + " (Right click to use)");
         stack.setItemMeta(meta);
         inventory.put(3, stack);
      }

      inventory.put(103, gPlayer.dE().eI());
      return inventory;
   }

   public static Map F(GamePlayer gPlayer) {
      Map<Integer, ItemStack> inventory = E(gPlayer);
      ItemStack stack = null;
      ItemMeta meta = null;
      stack = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.SKELETON.ordinal());
      SkullMeta skullMeta = (SkullMeta)stack.getItemMeta();
      skullMeta.setDisplayName(ChatColor.GREEN + "Join Lobby" + ChatColor.GRAY + " (Right click to use)");
      skullMeta.setOwner(gPlayer.getPlayer().getName());
      stack.setItemMeta(skullMeta);
      inventory.put(1, stack);
      if (!gPlayer.dS()) {
         inventory.remove(7);
         inventory.remove(8);
         inventory.remove(6);
         inventory.remove(4);
      }

      if (gPlayer.dH() && Core.b().V().aU().equals(GameState.bB)) {
         inventory.remove(8);
      }

      return inventory;
   }

   public static Inventory G(GamePlayer gPlayer) {
      Inventory inventory = Bukkit.createInventory(gPlayer.getPlayer(), InventoryType.CHEST, "Choose a Team");
      inventory.setMaxStackSize(Core.b().V().aS().bv().size() + 1);
      ItemStack stack = new ItemStack(Material.SLIME_BALL);
      ItemMeta meta = stack.getItemMeta();
      meta.setDisplayName(ChatColor.GREEN + "Join any Team");
      stack.setItemMeta(meta);
      inventory.setItem(0, stack);
      int slot = 1;

      for(Iterator var5 = Core.b().V().aS().bv().values().iterator(); var5.hasNext(); ++slot) {
         MapTeam team = (MapTeam)var5.next();
         stack = new ItemStack(Material.LEATHER_HELMET);
         meta = stack.getItemMeta();
         LeatherArmorMeta leatherMeta = (LeatherArmorMeta)meta;
         leatherMeta.setDisplayName("Join " + team.bP() + team.getName());
         List<String> lore = new ArrayList();
         lore.add(Core.b().V().aT().a(team) + "/" + team.getMaxPlayers());
         leatherMeta.setLore(lore);
         switch(team.bP()) {
         case RED:
         case DARK_RED:
            leatherMeta.setColor(Color.RED);
            break;
         case BLUE:
         case DARK_BLUE:
            leatherMeta.setColor(Color.BLUE);
            break;
         case DARK_GREEN:
         case GREEN:
            leatherMeta.setColor(Color.GREEN);
            break;
         case BLACK:
            leatherMeta.setColor(Color.BLACK);
            break;
         case WHITE:
            leatherMeta.setColor(Color.WHITE);
            break;
         case DARK_GRAY:
         case GRAY:
            leatherMeta.setColor(Color.GRAY);
            break;
         case GOLD:
            leatherMeta.setColor(Color.YELLOW);
            break;
         case LIGHT_PURPLE:
            leatherMeta.setColor(Color.FUCHSIA);
            break;
         case DARK_PURPLE:
            leatherMeta.setColor(Color.PURPLE);
            break;
         case YELLOW:
            leatherMeta.setColor(Color.YELLOW);
            break;
         case DARK_AQUA:
         case AQUA:
            leatherMeta.setColor(Color.AQUA);
         }

         stack.setItemMeta(leatherMeta);
         inventory.setItem(slot, stack);
      }

      return inventory;
   }

   public static Inventory H(GamePlayer gPlayer) {
      Inventory inventory = Bukkit.createInventory(gPlayer.getPlayer(), InventoryType.CHEST, "Tutorials");

      int slot;
      for(slot = 0; slot < 9; ++slot) {
         ItemStack stack = new ItemStack(Material.SIGN);
         ItemMeta meta = stack.getItemMeta();
         meta.setDisplayName(ChatColor.DARK_AQUA + "Tutorials");
         stack.setItemMeta(meta);
         inventory.setItem(slot, stack);
      }

      slot = 9;
      Iterator var8 = Core.m().ft().iterator();

      while(var8.hasNext()) {
         TutorialData data = (TutorialData)var8.next();
         ItemStack stack = new ItemStack(Material.DIRT);
         ItemMeta meta = stack.getItemMeta();
         meta.setDisplayName(data.bk());
         List<String> lore = new ArrayList();
         lore.add(ChatColor.AQUA + u(data.bj()));
         lore.add(ChatColor.AQUA + "x Gold");
         meta.setLore(lore);
         stack.setItemMeta(meta);
         inventory.setItem(slot++, stack);
      }

      return inventory;
   }

   public static Inventory I(GamePlayer gPlayer) {
      Inventory inventory = Bukkit.createInventory(gPlayer.getPlayer(), 36, "Vote Tickets");
      int slotPointer = 28;
/*
      Iterator stack;
      ItemStack stack;
      for(stack = gPlayer.dD().eY().iterator(); stack.hasNext(); ++slotPointer) {
         stack = (ItemStack)stack.next();
         if (slotPointer > inventory.getMaxStackSize()) {
            break;
         }

         inventory.setItem(slotPointer, stack);
      }

      stack = null;
      stack = null;
      Iterator var5 = Core.k().C().iterator();

      while(var5.hasNext()) {
         TicketItem item = (TicketItem)var5.next();
         ItemStack stack = new ItemStack(item.getMaterial());
         ItemMeta meta = stack.getItemMeta();
         meta.setDisplayName(ChatColor.GREEN + item.getName());
         List<String> lore = new ArrayList();
         switch(stack.getType()) {
         case SIGN:
            break;
         case BOOK:
            lore.add(ChatColor.DARK_AQUA + "How to get Vote Tickets");
            break;
         default:
            lore.add(ChatColor.GOLD.toString() + item.D() + ChatColor.DARK_AQUA.toString() + " tickets");
         }

         meta.setLore(lore);
         stack.setItemMeta(meta);
         inventory.setItem(item.getSlot(), stack);
      }*/

      return inventory;
   }

   public static boolean fx() {
      return true;
   }

   public static boolean fy() {
      return true;
   }

   public static boolean fz() {
      return false;
   }

   public static List a(Map map) {
      /*
      List<Entry<K, V>> sortedEntries = new ArrayList(map.entrySet());
      Collections.sort(sortedEntries, new Comparator() {
         public int compare(Entry o1, Entry o2) {
            return ((Comparable)o1.getValue()).compareTo(o2.getValue());
         }
      });
      return sortedEntries;*/
      return null;
   }

   public static List b(Map map) {
      /*
      List<Entry<K, V>> sortedEntries = new ArrayList(map.entrySet());
      Collections.sort(sortedEntries, new Comparator() {
         public int compare(Entry o1, Entry o2) {
            return ((Comparable)o2.getValue()).compareTo(o1.getValue());
         }
      });


      return sortedEntries;*/
      return null;
   }

   public static void a(Location location, int distance, Sound sound, float amplitude, float pitch) {
      Iterator var5 = Core.c().getPlayers().iterator();

      while(var5.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var5.next();
         if (gPlayer.getPlayer().getWorld().equals(location.getWorld()) && gPlayer.getPlayer().getLocation().distance(location) <= (double)distance) {
            gPlayer.getPlayer().playSound(location, sound, amplitude, pitch);
         }
      }

   }

   public static void a(String message, World world) {
      Iterator var2 = Core.c().getPlayers().iterator();

      while(var2.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var2.next();
         if (gPlayer.getPlayer().getLocation().getWorld().equals(world) && gPlayer.dF().eA()) {
            gPlayer.getPlayer().sendMessage(message);
         }
      }

   }

   public static String u(String gamemode) {
      return Core.a().getConfig().contains("language.game.gamemodes." + gamemode) ? Core.a().getConfig().getString("language.game.gamemodes." + gamemode) : gamemode;
   }

   public static String B(int seconds) {
      int totalMinutes = 0;
      if (seconds > 60) {
         totalMinutes = (int)Math.floor((double)(seconds / 60));
         seconds -= totalMinutes * 60;
      }

      DecimalFormat df = new DecimalFormat("00");
      return df.format((long)totalMinutes) + ":" + df.format((long)seconds);
   }

   public static void a(String name, int id, Class customClass) {
      try {
         List<Map<?, ?>> dataMaps = new ArrayList();
         Field[] var4 = EntityTypes.class.getDeclaredFields();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Field f = var4[var6];
            if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
               f.setAccessible(true);
               dataMaps.add((Map)f.get((Object)null));
            }
         }

         ((Map)dataMaps.get(1)).put(customClass, name);
         ((Map)dataMaps.get(3)).put(customClass, id);
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   public static void a(URL url, File targetDir) throws IOException {
      if (!targetDir.exists()) {
         targetDir.mkdirs();
      }

      InputStream in = new BufferedInputStream(url.openStream(), 1024);
      File zip = File.createTempFile("arc", ".zip", targetDir);
      OutputStream out = new BufferedOutputStream(new FileOutputStream(zip));
      a((InputStream)in, (OutputStream)out);
      out.close();
      a(zip, targetDir);
   }

   public static boolean b(File file) {
      return file.exists() || file.mkdirs();
   }

   public static void a(File theFile, File targetDir) throws IOException {
      if (!theFile.exists()) {
         throw new IOException(theFile.getAbsolutePath() + " does not exist");
      } else if (!b(targetDir)) {
         throw new IOException("Could not create directory: " + targetDir);
      } else {
         ZipFile zipFile = new ZipFile(theFile);
         Enumeration entries = zipFile.entries();

         while(entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            File file = new File(targetDir, File.separator + entry.getName());
            if (!b(file.getParentFile())) {
               throw new IOException("Could not create directory: " + file.getParentFile());
            }

            if (!entry.isDirectory()) {
               a((InputStream)zipFile.getInputStream(entry), (OutputStream)(new BufferedOutputStream(new FileOutputStream(file))));
            } else if (!b(file)) {
               throw new IOException("Could not create directory: " + file);
            }
         }

         zipFile.close();
         theFile.delete();
      }
   }

   public static void a(InputStream in, OutputStream out) throws IOException {
      byte[] buffer = new byte[1024];

      for(int len = in.read(buffer); len >= 0; len = in.read(buffer)) {
         out.write(buffer, 0, len);
      }

      in.close();
      out.close();
   }
}
