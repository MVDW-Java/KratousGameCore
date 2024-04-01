package a.a.a.e.c;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class Hologram1 {
   private List aN = new ArrayList();
   private Location aE;
   private List aO;
   private boolean aP;

   public Hologram1(Location location) {
      this.aE = location;
      this.aO = new LinkedList();
      this.aP = false;
   }

   private int getId() {
      try {
         Field field = Entity.class.getDeclaredField("entityCount");
         field.setAccessible(true);
         int id = field.getInt((Object)null);
         field.set((Object)null, id + 1);
         return id;
      } catch (Exception var3) {
         var3.printStackTrace();
         return -1;
      }
   }

   public Location getLocation() {
      return this.aE;
   }

   public List N() {
      return this.aO;
   }

   public void d(String line) {
      this.aO.add(line);
      this.R();
   }

   public void setLine(int key, String line) {
      boolean contains = key <= this.aO.size() - 1;
      if (contains) {
         if (((String)this.aO.get(key)).equals(line)) {
            return;
         }

         this.aO.set(key, line);
         this.S();
      } else {
         this.aO.add(key, line);
         this.R();
      }

   }

   public void d(int key) {
      this.aO.remove(key);
      this.T();
   }

   public Hologram1 O() {
      if (this.aP) {
         return this;
      } else {
         for(int i = 0; i < this.aO.size(); ++i) {
            EntityLiving living = new EntityArmorStand(((CraftWorld)this.aE.getWorld()).getHandle());
            living.setLocation(this.aE.getX(), this.aE.getY() - 2.0D - (double)i * 0.285D, this.aE.getZ(), 0.0F, 0.0F);
            living.setCustomNameVisible(true);

            try {
               Field id = Entity.class.getDeclaredField("id");
               id.setAccessible(true);
               id.setInt(living, this.getId());
            } catch (Exception var6) {
               var6.printStackTrace();
            }

            this.aN.add(living);
         }

         if (!this.aN.isEmpty()) {
            Packet[] spawnPackets = this.P();
            Packet[] var8 = spawnPackets;
            int var9 = spawnPackets.length;

            for(int var4 = 0; var4 < var9; ++var4) {
               Packet var10000 = var8[var4];
            }
         }

         this.aP = true;
         Core.n().b(this);
         return this;
      }
   }

   public void remove() {
      if (this.aP) {
         Packet[] var1 = this.Q();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Packet packet = var1[var3];
            this.a(packet);
         }

         this.aN.clear();
      }
   }

   public void i(final GamePlayer gPlayer) {
      if (this.aP && !this.aN.isEmpty()) {
         Bukkit.getScheduler().runTaskLater(Core.a(), new Runnable() {
            public void run() {
               Packet[] var1 = Hologram1.this.P();
               int var2 = var1.length;

               for(int var3 = 0; var3 < var2; ++var3) {
                  Packet p = var1[var3];
                  if (p != null) {
                     gPlayer.sendPacket(p);
                  }
               }

            }
         }, 5L);
      }
   }

   public void j(GamePlayer gPlayer) {
      if (this.aP && !this.aN.isEmpty()) {
         Packet[] var2 = this.Q();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Packet p = var2[var4];
            if (p != null) {
               gPlayer.sendPacket(p);
            }
         }

      }
   }

   private Packet[] P() {
      Packet[] spawnPackets = new Packet[this.aN.size()];
      int height = 0;

      for(Iterator var3 = this.aN.iterator(); var3.hasNext(); ++height) {
         EntityLiving living = (EntityLiving)var3.next();
         spawnPackets[height] = this.a(living, height);
      }

      return spawnPackets;
   }

   public Packet[] Q() {
      Packet[] packets = new Packet[this.aN.size()];
      int line = 0;

      for(Iterator var3 = this.aN.iterator(); var3.hasNext(); ++line) {
         EntityLiving entity = (EntityLiving)var3.next();
         PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[]{entity.getId()});
         packets[line] = packet;
      }

      return packets;
   }

   private void R() {
      if (this.aP) {
         EntityLiving living = new EntityArmorStand(((CraftWorld)this.aE.getWorld()).getHandle());
         living.setLocation(this.aE.getX(), this.aE.getY() - 2.0D - (double)(this.aN.size() - 1) * 0.285D, this.aE.getZ(), 0.0F, 0.0F);
         living.setCustomNameVisible(true);

         try {
            Field id = Entity.class.getDeclaredField("id");
            id.setAccessible(true);
            id.setInt(living, this.getId());
         } catch (Exception var3) {
            var3.printStackTrace();
         }

         this.aN.add(living);
         this.a(this.a(living, this.aN.size() - 1));
      }
   }

   private void S() {
      if (this.aP) {
         Packet[] packets = new Packet[this.aN.size()];
         int line = 0;

         for(Iterator var3 = this.aN.iterator(); var3.hasNext(); ++line) {
            EntityLiving entity = (EntityLiving)var3.next();
            DataWatcher watcher = new DataWatcher(entity);
            watcher.a(0, (byte)32);
            watcher.a(2, this.aO.get(line));
            watcher.a(3, (byte)1);
            PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(entity.getId(), watcher, true);
            packets[line] = packet;
         }

         Packet[] var7 = packets;
         int var8 = packets.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Packet packet = var7[var9];
            this.a(packet);
         }

      }
   }

   private void T() {
      if (this.aP) {
         this.a((Packet)(new PacketPlayOutEntityDestroy(new int[]{((EntityLiving)this.aN.remove(this.aN.size() - 1)).getId()})));
      }
   }

   private Packet a(EntityLiving entity, int height) {
      PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entity);

      try {
         Class clazz = PacketPlayOutSpawnEntityLiving.class;
         Field l = clazz.getDeclaredField("l");
         l.setAccessible(true);
         DataWatcher watcher = new DataWatcher((Entity)null);
         watcher.a(0, (byte)32);
         watcher.a(2, this.aO.get(height));
         watcher.a(3, (byte)1);
         l.set(packet, watcher);
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return packet;
   }

   private void a(Packet packet) {
      if (packet != null) {
         Iterator var2 = Core.c().getPlayers().iterator();

         while(var2.hasNext()) {
            GamePlayer gPlayer = (GamePlayer)var2.next();
            if (gPlayer.getPlayer().getWorld().equals(this.aE.getWorld())) {
               gPlayer.sendPacket(packet);
            }
         }

      }
   }
}
