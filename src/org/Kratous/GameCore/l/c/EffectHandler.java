package org.Kratous.GameCore.l.c;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EffectHandler {
   private Map ho;
   private HeadType hp;
   private long hq;
   private GamePlayer er;

   public EffectHandler(GamePlayer gPlayer) {
      this.er = gPlayer;
      this.hq = 0L;
      this.ho = new HashMap();
   }

   public ItemStack eI() {
      ItemStack stack = new ItemStack(this.hp == null ? Material.AIR : this.hp.getMaterial());
      return stack;
   }

   public void eJ() {
      Iterator var1 = this.ho.values().iterator();

      while(true) {
         label26:
         while(var1.hasNext()) {
            ParticleType effect = (ParticleType)var1.next();
            switch(effect.eS()) {
            case hF:
               Iterator var3 = Core.c().getPlayers().iterator();

               while(true) {
                  GamePlayer gPlayer;
                  do {
                     if (!var3.hasNext()) {
                        continue label26;
                     }

                     gPlayer = (GamePlayer)var3.next();
                  } while(gPlayer.dH() && (this.er.dI() || this.er.dS()));

                  if (gPlayer.getPlayer().canSee(this.er.getPlayer())) {
                     gPlayer.a(effect.eR(), this.er.getPlayer().getLocation(), 1.0F, 1);
                  }
               }
            }
         }

         return;
      }
   }

   public void eK() {
      if (System.currentTimeMillis() - this.hq > 700L) {
         Iterator var1 = this.ho.values().iterator();

         while(true) {
            label118:
            while(var1.hasNext()) {
               ParticleType effect = (ParticleType)var1.next();
               Iterator var3;
               Iterator var5;
               if (effect.eS().equals(ParticleStyle.hH)) {
                  var3 = this.b(this.er.getPlayer().getLocation().add(0.0D, 2.0D, 0.0D), 0.5D, 10).iterator();

                  label136:
                  while(var3.hasNext()) {
                     Location location = (Location)var3.next();
                     var5 = Core.c().getPlayers().iterator();

                     while(true) {
                        GamePlayer gPlayer;
                        do {
                           if (!var5.hasNext()) {
                              continue label136;
                           }

                           gPlayer = (GamePlayer)var5.next();
                        } while(gPlayer.dH() && (this.er.dI() || this.er.dS()));

                        if (gPlayer.getPlayer().canSee(this.er.getPlayer())) {
                           gPlayer.a(effect.eR(), location, 0.0F, 1);
                        }
                     }
                  }
               } else if (effect.eS().equals(ParticleStyle.hG)) {
                  var3 = Core.c().getPlayers().iterator();

                  label116:
                  while(true) {
                     GamePlayer gPlayer;
                     do {
                        do {
                           if (!var3.hasNext()) {
                              continue label118;
                           }

                           gPlayer = (GamePlayer)var3.next();
                        } while(gPlayer.dH() && (this.er.dI() || this.er.dS()));
                     } while(!gPlayer.getPlayer().canSee(this.er.getPlayer()));

                     var5 = this.b(this.er.getPlayer().getLocation().add(0.0D, 1.0D, 0.0D), 0.25D, 5).iterator();

                     while(true) {
                        Location location;
                        do {
                           if (!var5.hasNext()) {
                              var5 = this.b(this.er.getPlayer().getLocation().add(0.0D, 1.5D, 0.0D), 0.25D, 5).iterator();

                              while(true) {
                                 do {
                                    if (!var5.hasNext()) {
                                       var5 = this.b(this.er.getPlayer().getLocation(), 0.25D, 5).iterator();

                                       while(true) {
                                          do {
                                             if (!var5.hasNext()) {
                                                continue label116;
                                             }

                                             location = (Location)var5.next();
                                          } while(gPlayer.dH() && (this.er.dI() || this.er.dS()));

                                          gPlayer.a(effect.eR(), location, 0.0F, 1);
                                       }
                                    }

                                    location = (Location)var5.next();
                                 } while(gPlayer.dH() && (this.er.dI() || this.er.dS()));

                                 gPlayer.a(effect.eR(), location, 0.0F, 1);
                              }
                           }

                           location = (Location)var5.next();
                        } while(gPlayer.dH() && (this.er.dI() || this.er.dS()));

                        gPlayer.a(effect.eR(), location, 0.0F, 1);
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public void eL() {
      if (this.er.dH()) {
         this.er.getPlayer().sendMessage(ChatColor.RED + "You cannot use effects whilst participating");
      } else {
         Inventory inventory = Bukkit.createInventory(this.er.getPlayer(), 54, "Choose VIP Effect");
         ItemStack infoStack = new ItemStack(Material.SIGN);
         ItemMeta infoMeta = infoStack.getItemMeta();
         infoMeta.setDisplayName(ChatColor.GOLD + "Head Items");
         infoStack.setItemMeta(infoMeta);
         inventory.setItem(0, infoStack);
         inventory.setItem(9, infoStack);
         HeadType[] var4 = HeadType.values();
         int var5 = var4.length;

         int var6;
         ItemStack headItem;
         ItemMeta headMeta;
         for(var6 = 0; var6 < var5; ++var6) {
            HeadType type = var4[var6];
            headItem = new ItemStack(type.getMaterial());
            headMeta = headItem.getItemMeta();
            headMeta.setDisplayName(type.getName());
            headItem.setItemMeta(headMeta);
            inventory.setItem(type.eQ(), headItem);
         }

         infoStack = new ItemStack(Material.SIGN);
         infoMeta = infoStack.getItemMeta();
         infoMeta.setDisplayName(ChatColor.GOLD + "Particle Halos");
         infoStack.setItemMeta(infoMeta);
         inventory.setItem(27, infoStack);
         infoStack = new ItemStack(Material.SIGN);
         infoMeta = infoStack.getItemMeta();
         infoMeta.setDisplayName(ChatColor.GOLD + "Particle Auras");
         infoStack.setItemMeta(infoMeta);
         inventory.setItem(36, infoStack);
         infoStack = new ItemStack(Material.SIGN);
         infoMeta = infoStack.getItemMeta();
         infoMeta.setDisplayName(ChatColor.GOLD + "Particle Trails");
         infoStack.setItemMeta(infoMeta);
         inventory.setItem(45, infoStack);
         ParticleType[] var11 = ParticleType.values();
         var5 = var11.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ParticleType type = var11[var6];
            headItem = new ItemStack(type.getMaterial());
            headMeta = headItem.getItemMeta();
            headMeta.setDisplayName(type.getName());
            headItem.setItemMeta(headMeta);
            int addAmount = type.eS().equals(ParticleStyle.hG) ? 36 : (type.eS().equals(ParticleStyle.hF) ? 45 : 27);
            inventory.setItem(type.eQ() + addAmount, headItem);
         }

         this.er.getPlayer().openInventory(inventory);
      }
   }

   public void a(InventoryClickEvent e) {
      ParticleType[] var2 = ParticleType.values();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         ParticleType type = var2[var4];
         if (type.getMaterial().equals(e.getCurrentItem().getType()) && type.getName().equals(e.getCurrentItem().getItemMeta().getDisplayName())) {
            if (type.getMaterial().equals(Material.MILK_BUCKET)) {
               if (this.ho.containsKey(type.eS())) {
                  this.ho.remove(type.eS());
               }
            } else {
               this.ho.put(type.eS(), type);
               this.er.dE().eJ();
            }
            break;
         }
      }

      HeadType[] var6 = HeadType.values();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         HeadType type = var6[var4];
         if (type.getMaterial().equals(e.getCurrentItem().getType()) && type.getName().equals(e.getCurrentItem().getItemMeta().getDisplayName())) {
            if (type.getMaterial().equals(Material.SKULL_ITEM)) {
               this.hp = null;
            } else {
               this.hp = type;
            }

            this.er.dO();
            break;
         }
      }

      this.er.getPlayer().closeInventory();
   }

   public void eM() {
      this.hp = null;
   }

   private List b(Location center, double radius, int amount) {
      List<Location> locations = new ArrayList();
      double increment = 6.283185307179586D / (double)amount;

      for(int i = 0; i < amount; ++i) {
         double angle = (double)i * increment;
         double x = center.getX() + radius * Math.cos(angle);
         double z = center.getZ() + radius * Math.sin(angle);
         locations.add(new Location(center.getWorld(), x, center.getY(), z));
      }

      return locations;
   }

   public void eN() {
      this.hq = System.currentTimeMillis();
   }

   public void clearEffects() {
      this.ho.clear();
   }

   public void eO() {
      if (this.er.getPlayer().hasPermission("gamecore.vip")) {
         Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
            public void run() {
            }
         });
      }
   }

   public void eP() {
      Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
         public void run() {
         }
      });
   }
}
