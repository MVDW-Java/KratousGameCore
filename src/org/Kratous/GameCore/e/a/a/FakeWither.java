package org.Kratous.GameCore.e.a.a;

import org.Kratous.GameCore.l.GamePlayer;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class FakeWither extends FakeEntity {
   private float aC;
   private String name;
   private EntityWither aG;

   public FakeWither(String name, Location location) {
      super(location);
      this.name = name;
      this.aC = 300.0F;
      this.aG = new EntityWither(this.aF.getHandle());
      this.aG.setLocation(this.aE.getX(), this.aE.getY(), this.aE.getZ(), 0.0F, 0.0F);
      this.aG.setInvisible(true);
      this.aG.setCustomName(this.name);
      this.aG.setHealth(this.aC);
      this.aG.setSneaking(true);
   }

   public Entity getEntity() {
      return this.aG;
   }

   public Packet e(GamePlayer gPlayer) {
      DataWatcher dataWatcher = new DataWatcher(this.aG);
      dataWatcher.a(6, this.aC);
      dataWatcher.a(0, (byte)32);
      dataWatcher.a(20, 881);
      dataWatcher.a(2, this.name);
      PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(this.aG.getId(), dataWatcher, true);
      return packet;
   }

   public Packet I() {
      this.aG.setLocation(this.aE.getX(), this.aE.getY(), this.aE.getZ(), 0.0F, 0.0F);
      PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(this.aG);
      return packet;
   }

   public Packet J() {
      MobEffect mobEffect = new MobEffect(14, 200000);
      PacketPlayOutEntityEffect packet = new PacketPlayOutEntityEffect(this.aG.getId(), mobEffect);
      return packet;
   }

   public void setHealth(float percent) {
      this.aC = percent / 100.0F * 300.0F;
      this.aG.setHealth(this.aC);
   }

   public void setName(String name) {
      this.name = name;
      this.aG.setCustomName(this.name);
   }

   public void a(Location location, Vector direction) {
      this.aE = location.add(direction.multiply(100));
   }

   public Location getLocation() {
      return this.aE;
   }
}
