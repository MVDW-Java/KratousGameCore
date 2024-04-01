package org.Kratous.GameCore.l.d;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.d.b.TicketItem;
import org.Kratous.GameCore.d.b.TicketItemType;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.q.Util3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRankHandler2 {
   private GamePlayer er;
   private Map iq;
   private List ir;
   private int is;
   private int it;

   public PlayerRankHandler2(GamePlayer gamePlayer) {
      this.er = gamePlayer;
      this.is = 0;
      this.it = 0;
      this.iq = new HashMap();
      this.ir = new ArrayList();
      (new BukkitRunnable() {
         public void run() {
         }
      }).runTaskAsynchronously(Core.a());
   }

   public int eV() {
      return this.is;
   }

   public void x(int value) {
      this.a(value, Core.b().V().aS().bj());
   }

   public void a(int value, String type) {
      if (value > 0) {
         ;
      }
   }

   public void y(int amount) {
   }

   public int eW() {
      return this.it;
   }

   public void eX() {
      this.it = 0;
   }

   public void a(double value, String type) {
      this.a(value, type, 60);
   }

   public void a(double value, String type, int time) {
      Multiplier multiplier = new Multiplier(value, type, (double)System.currentTimeMillis() / 1000.0D + (double)(time * 60));
      this.ir.add(multiplier);
      (new BukkitRunnable() {
         public void run() {
         }
      }).runTaskAsynchronously(Core.a());
   }

   private int a(int originalAmount, MultiplierType type) {
      double addAmount = 0.0D;
      Iterator var5 = this.ir.iterator();

      while(var5.hasNext()) {
         Multiplier multiplier = (Multiplier)var5.next();
         if (!multiplier.hasExpired() && multiplier.eT().equals(type)) {
            addAmount += (double)originalAmount * multiplier.getValue();
         }
      }

      return originalAmount + (int)Math.ceil(addAmount);
   }

   public List eY() {
      List<Multiplier> copyList = new ArrayList(this.ir);
      Collections.reverse(copyList);
      List<ItemStack> stacks = new ArrayList();
      Iterator var3 = this.ir.iterator();

      label59:
      while(true) {
         Multiplier multiplier;
         do {
            if (!var3.hasNext()) {
               return stacks;
            }

            multiplier = (Multiplier)var3.next();
         } while(multiplier.hasExpired());

         Iterator var5 = Core.k().C().iterator();

         while(true) {
            TicketItem item;
            do {
               do {
                  if (!var5.hasNext()) {
                     continue label59;
                  }

                  item = (TicketItem)var5.next();
               } while((!item.E().equals(TicketItemType.am) || !multiplier.eT().equals(MultiplierType.in)) && (!item.E().equals(TicketItemType.al) || !multiplier.eT().equals(MultiplierType.io)));
            } while(multiplier.getValue() != item.getValue());

            int amount = 1;
            Object[] var8 = stacks.toArray();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Object objectStack = var8[var10];
               ItemStack stack = (ItemStack)objectStack;
               if (stack.getType().equals(item.getMaterial()) && stack.getItemMeta().getDisplayName().contains(item.getName())) {
                  stacks.remove(stack);
                  amount += stack.getAmount();
               }
            }

            ItemStack stack = new ItemStack(item.getMaterial(), amount);
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + item.getName());
            List<String> lore = new ArrayList();
            lore.add(ChatColor.GOLD + Util3.B((int)(multiplier.eU() - (double)(System.currentTimeMillis() / 1000L))) + ChatColor.DARK_AQUA + " remaining");
            meta.setLore(lore);
            stack.setItemMeta(meta);
            stacks.add(stack);
         }
      }
   }
}
