package org.Kratous.GameCore.q;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class BlockUtil1 {
   public static void h(Block b) {
      b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
      a(b.getLocation(), Material.AIR, 0, true);
   }

   public static void a(Location location, Material material, int data, boolean notifyClient) {
      Chunk chunk = location.getWorld().getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);
      net.minecraft.server.v1_8_R3.Chunk c = ((CraftChunk)chunk).getHandle();
      c.a(new BlockPosition(location.getBlockX() & 15, location.getBlockY(), location.getBlockZ() & 15), net.minecraft.server.v1_8_R3.Block.getByCombinedId(material.getId()));
      if (notifyClient) {
         ((CraftWorld)location.getWorld()).getHandle().notify(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
      }

   }

   public static void a(List chunks) {
      World world = ((Chunk)chunks.get(0)).getWorld();
      Iterator var2 = chunks.iterator();

      while(var2.hasNext()) {
         Chunk chunk = (Chunk)var2.next();
         chunk.getWorld().refreshChunk(chunk.getX(), chunk.getZ());
      }

      var2 = Core.c().getPlayers().iterator();

      while(var2.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var2.next();
         if (gPlayer.getPlayer().getWorld().equals(world)) {
            gPlayer.dY();
         }
      }

   }

   public static Item a(Item item, int itemAge) {
      ItemStack itemstack = item.getItemStack();
      Location location = item.getLocation();
      EntityItem ei = new EntityItem(((CraftWorld)location.getWorld()).getHandle(), location.getX(), location.getY(), location.getZ(), CraftItemStack.asNMSCopy(itemstack)) {
         public void c(int i) {
            super.c(i);
         }

         public void t_() {
            try {
               Field x = EntityItem.class.getDeclaredField("age");
               x.setAccessible(true);
               Field y = EntityItem.class.getDeclaredField("lastTick");
               y.setAccessible(true);
               int age = (Integer)x.get(this);
               int lastTick = (Integer)y.get(this);
               if (this.getItemStack() == null) {
                  this.die();
               } else {
                  super.s_();
                  int elapsedTicks = MinecraftServer.currentTick - lastTick;
                  this.pickupDelay -= elapsedTicks;
                  Field z = EntityItem.class.getDeclaredField("age");
                  z.setAccessible(true);
                  z.set(this, age + elapsedTicks);
                  Field d = EntityItem.class.getDeclaredField("lastTick");
                  d.setAccessible(true);
                  d.set(this, MinecraftServer.currentTick);
                  this.lastX = this.locX;
                  this.lastY = this.locY;
                  this.lastZ = this.locZ;
                  this.motY -= 0.03999999910593033D;
                  this.noclip = this.j(this.locX, (this.getBoundingBox().b + this.getBoundingBox().e) / 2.0D, this.locZ);
                  this.move(this.motX, this.motY, this.motZ);
                  boolean flag = (int)this.lastX != (int)this.locX || (int)this.lastY != (int)this.locY || (int)this.lastZ != (int)this.locZ;
                  if ((flag || this.ticksLived % 25 == 0) && this.world.getType(new BlockPosition(this)).getBlock().getMaterial() == net.minecraft.server.v1_8_R3.Material.LAVA) {
                     this.motY = 0.20000000298023224D;
                     this.motX = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                     this.motZ = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                     this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                  }

                  float f = 0.98F;
                  if (this.onGround) {
                     f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.98F;
                  }

                  this.motX *= (double)f;
                  this.motY *= 0.9800000190734863D;
                  this.motZ *= (double)f;
                  if (this.onGround) {
                     this.motY *= -0.5D;
                  }

                  this.W();
                  if (!this.world.isClientSide && age >= this.world.spigotConfig.itemDespawnRate) {
                     if (CraftEventFactory.callItemDespawnEvent(this).isCancelled()) {
                        z.set(this, 0);
                        return;
                     }

                     this.die();
                  }
               }
            } catch (Exception var10) {
               var10.printStackTrace();
            }

         }
      };
      ei.pickupDelay = 10;

      try {
         Field f = EntityItem.class.getDeclaredField("age");
         f.setAccessible(true);
         f.set(ei, 6000 - itemAge * 20);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      ei.getBukkitEntity().setVelocity(item.getVelocity());
      ((CraftWorld)location.getWorld()).getHandle().addEntity(ei);
      item.remove();
      return (Item)ei.getBukkitEntity();
   }

   public static ItemStack a(ItemStack item) {
      net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
      if (item != null && !item.getType().equals(Material.AIR) && nmsStack != null) {
         if (nmsStack.getTag() == null) {
            nmsStack.setTag(new NBTTagCompound());
         }

         nmsStack.getTag().setInt("kitItem", 1);
         return CraftItemStack.asCraftMirror(nmsStack);
      } else {
         return item;
      }
   }

   public static boolean b(ItemStack item) {
      if (item != null && !item.getType().equals(Material.AIR)) {
         net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
         return nmsStack.getTag() == null ? false : nmsStack.getTag().hasKey("kitItem");
      } else {
         return false;
      }
   }
}
