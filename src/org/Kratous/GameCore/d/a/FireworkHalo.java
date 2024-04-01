package org.Kratous.GameCore.d.a;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.block.Block;

public class FireworkHalo extends Updatable {
   private Block block;
   private int T;
   private boolean finished;
   private LinkedList U;
   private LinkedList V;
   private List W;
   private int X;

   public FireworkHalo(Block block) {
      this.block = block;
      this.T = 10;
      this.finished = false;
      this.U = new LinkedList();
      this.V = new LinkedList();
      this.W = new ArrayList();
      this.U = this.a(this.block.getLocation(), 4.0D, 125);
      this.X = 0;
   }

   public void update() {
      if (this.T > 0) {
         --this.T;
      } else if (this.U.isEmpty()) {
         this.block.getWorld().strikeLightningEffect(this.block.getLocation());
         this.finished = true;
      } else {
         if (this.V.isEmpty()) {
            this.y();
         }

         ++this.X;
         this.z();
      }
   }

   public boolean x() {
      return this.finished;
   }

   private LinkedList a(Location center, double radius, int amount) {
      LinkedList<Location> locations = new LinkedList();
      double increment = 6.283185307179586D / (double)amount;

      for(int i = 0; i < amount; ++i) {
         double angle = (double)i * increment;
         double x = center.getX() + radius * Math.cos(angle);
         double z = center.getZ() + radius * Math.sin(angle);
         locations.add(new Location(center.getWorld(), x, center.getY(), z));
      }

      return locations;
   }

   private void y() {
      int half = this.U.size() / 2;

      for(int i = half; i < this.U.size(); ++i) {
         this.V.add(this.U.get(i));
      }

      Iterator var4 = this.V.iterator();

      while(var4.hasNext()) {
         Location loc = (Location)var4.next();
         this.U.remove(loc);
      }

      Collections.reverse(this.V);
   }

   private void z() {
      Location location1 = (Location)this.U.get(0);
      Location location2 = (Location)this.V.get(0);
      Iterator var3 = Core.c().getPlayers().iterator();

      while(var3.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var3.next();
         gPlayer.a(this.X % 2 == 0 ? EnumParticle.VILLAGER_HAPPY : EnumParticle.SPELL_WITCH, location1.add(0.0D, 8.0D, 0.0D), 1.0F, 10);
         gPlayer.a(this.X % 2 == 0 ? EnumParticle.VILLAGER_HAPPY : EnumParticle.SPELL_WITCH, location2.add(0.0D, 8.0D, 0.0D), 1.0F, 10);
      }

      this.U.remove(location1);
      this.V.remove(location2);
   }
}
