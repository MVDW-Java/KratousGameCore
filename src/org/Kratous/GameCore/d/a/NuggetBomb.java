package org.Kratous.GameCore.d.a;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.q.BlockUtil1;
import org.Kratous.GameCore.q.Util3;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class NuggetBomb extends Updatable {
   private int Y;
   private Block block;
   private int T;
   private int Z;
   private double aa;
   private double ab;
   private double ac;
   Random random;
   LinkedList ad;

   public NuggetBomb(int nuggetCount, Block block) {
      this.Y = nuggetCount;
      this.block = block;
      this.T = 8;
      this.Z = 0;
      this.random = new Random(System.nanoTime());
      this.ad = new LinkedList();
   }

   public void update() {
      if (this.T > 0) {
         --this.T;
      } else {
         if (this.ad.isEmpty()) {
            this.ad = this.a(this.block.getLocation(), 0.6D, 40, this.Z);
            ++this.Z;
            this.aa = ((double)this.random.nextInt(8) + 7.0D) / 10.0D;
            this.ab = (double)this.random.nextInt(15) / 10.0D;
            this.ac = ((double)this.random.nextInt(7) + 9.0D) / 10.0D;
            if (this.Z % 2 == 0) {
               Collections.reverse(this.ad);
            }
         }

         Iterator var1;
         GamePlayer gPlayer;
         if (this.ad.size() >= this.Y) {
            var1 = Core.c().getPlayers().iterator();

            while(var1.hasNext()) {
               gPlayer = (GamePlayer)var1.next();
               gPlayer.a(EnumParticle.EXPLOSION_LARGE, this.block.getLocation().subtract(0.0D, 1.0D, 0.0D), 1.0F, 10);
            }
         } else {
            var1 = Core.c().getPlayers().iterator();

            while(var1.hasNext()) {
               gPlayer = (GamePlayer)var1.next();
               gPlayer.a(EnumParticle.EXPLOSION_NORMAL, this.block.getLocation().subtract(0.0D, 1.0D, 0.0D), 1.0F, 10);
            }
         }

         Util3.a(this.block.getLocation(), 15, Sound.EXPLODE, 2.0F, -3.0F);
         var1 = this.ad.iterator();

         while(var1.hasNext()) {
            Location location = (Location)var1.next();
            Vector direction = location.subtract(this.block.getLocation()).toVector();
            direction.setY(0.6D + Math.abs(Math.sin(direction.getY())));
            direction.multiply(new Vector(this.aa, this.ab, this.ac));
            Item nugget = Core.d().cT().getWorld().dropItemNaturally(this.block.getLocation(), new ItemStack(Material.GOLD_NUGGET, 1));
            nugget.setVelocity(direction);
            BlockUtil1.a(nugget, 30);
            --this.Y;
            if (this.Y <= 0) {
               break;
            }
         }

         this.ad.clear();
         this.T = (this.random.nextInt(40) + 5) * 2;
      }
   }

   public boolean x() {
      return this.Y <= 0;
   }

   public boolean A() {
      return true;
   }

   private LinkedList a(Location center, double radius, int amount, int iteration) {
      LinkedList<Location> locations = new LinkedList();
      double increment = 6.283185307179586D / (double)amount;

      for(int i = 0; i < amount; ++i) {
         double angle = (double)i * increment + (double)(iteration / 10) * 3.141592653589793D;
         double x = center.getX() + radius * Math.cos(angle);
         double z = center.getZ() + radius * Math.sin(angle);
         locations.add(new Location(center.getWorld(), x, center.getY(), z));
      }

      return locations;
   }
}
