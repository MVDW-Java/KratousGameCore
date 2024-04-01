package org.Kratous.GameCore.e.a.a;

import org.Kratous.GameCore.l.GamePlayer;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public abstract class FakeEntity {
   protected Location aE;
   protected CraftWorld aF;

   public FakeEntity(Location location) {
      this.aE = location;
      this.aF = (CraftWorld)location.getWorld();
   }

   public abstract Entity getEntity();

   public abstract Packet I();

   public Packet K() {
      Packet packet = null;
      if (this.getEntity() instanceof EntityLiving) {
         packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)this.getEntity());
      }

      return packet;
   }

   public Packet L() {
      Packet packet = new PacketPlayOutEntityDestroy(new int[]{this.getEntity().getId()});
      return packet;
   }

   public Packet e(GamePlayer gPlayer) {
      DataWatcher dataWatcher = new DataWatcher(this.getEntity());
      PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(this.getEntity().getId(), dataWatcher, true);
      return packet;
   }

   public Packet M() {
      PacketPlayOutEntityHeadRotation packet = new PacketPlayOutEntityHeadRotation(this.getEntity(), (byte)((int)this.aE.getYaw()));
      return packet;
   }
}
