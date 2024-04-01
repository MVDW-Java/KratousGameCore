package org.Kratous.GameCore.e.b;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.i.Logger;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class DisguiseHandler3 {
   private Map aH = new HashMap();

   public void a(GamePlayer gPlayer, EntityType type, boolean showHead) {
      World mcWorld = ((CraftWorld)gPlayer.getPlayer().getWorld()).getHandle();
      switch(type) {
      case ZOMBIE:
         EntityInsentient entity = new EntityZombie(mcWorld);
         this.aH.put(gPlayer, entity);
         ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
         SkullMeta meta = (SkullMeta)stack.getItemMeta();
         meta.setOwner(gPlayer.getPlayer().getName());
         stack.setItemMeta(meta);
         if (showHead) {
            gPlayer.getPlayer().getInventory().setHelmet(stack);
         }

         Iterator var8 = Core.c().getPlayers().iterator();

         while(var8.hasNext()) {
            GamePlayer toSend = (GamePlayer)var8.next();
            if (toSend.getPlayer().getWorld().equals(gPlayer.getPlayer().getWorld()) && !toSend.equals(gPlayer) && toSend.getPlayer().canSee(gPlayer.getPlayer())) {
               this.a(entity, gPlayer, toSend);
            }
         }

         return;
      default:
         Logger.log("Unsupported entity type " + type.toString());
      }
   }

   private void a(final EntityInsentient entity, GamePlayer gPlayer, final GamePlayer toSend) {
      entity.locX = gPlayer.getPlayer().getLocation().getX();
      entity.locY = gPlayer.getPlayer().getLocation().getY();
      entity.locZ = gPlayer.getPlayer().getLocation().getZ();
      entity.pitch = gPlayer.getPlayer().getLocation().getPitch();
      entity.yaw = gPlayer.getPlayer().getLocation().getYaw();
      entity.d(gPlayer.getPlayer().getEntityId());
      entity.setCustomNameVisible(true);
      entity.setCustomName(gPlayer.getPlayer().getDisplayName());
      toSend.sendPacket(new PacketPlayOutEntityDestroy(new int[]{gPlayer.getPlayer().getEntityId()}));
      Bukkit.getScheduler().scheduleSyncDelayedTask(Core.a(), new Runnable() {
         public void run() {
            toSend.sendPacket(new PacketPlayOutSpawnEntityLiving(entity));
         }
      }, 5L);
   }

   public void f(GamePlayer gPlayer) {
      if (this.aH.containsKey(gPlayer)) {
         this.aH.remove(gPlayer);
      }

      this.g(gPlayer);
   }

   public void g(GamePlayer gPlayer) {
      Iterator var2 = this.aH.keySet().iterator();

      while(var2.hasNext()) {
         GamePlayer disguisedPlayer = (GamePlayer)var2.next();
         if (gPlayer.getPlayer().getWorld().equals(disguisedPlayer.getPlayer().getWorld()) && disguisedPlayer.getPlayer().canSee(gPlayer.getPlayer()) && !gPlayer.equals(disguisedPlayer)) {
            this.a((EntityInsentient)this.aH.get(disguisedPlayer), disguisedPlayer, gPlayer);
         }
      }

   }

   public void h(final GamePlayer gPlayer) {
      if (this.aH.containsKey(gPlayer)) {
         this.aH.remove(gPlayer);
         Iterator var2 = Core.c().getPlayers().iterator();

         while(var2.hasNext()) {
            GamePlayer otherPlayer = (GamePlayer)var2.next();
            if (otherPlayer.getPlayer().getWorld().equals(gPlayer.getPlayer().getWorld()) && !otherPlayer.equals(gPlayer)) {
               otherPlayer.sendPacket(new PacketPlayOutEntityDestroy(new int[]{gPlayer.getPlayer().getEntityId()}));
            }
         }

         Bukkit.getScheduler().scheduleSyncDelayedTask(Core.a(), new Runnable() {
            public void run() {
               Iterator var1 = Core.c().getPlayers().iterator();

               while(var1.hasNext()) {
                  GamePlayer otherPlayer = (GamePlayer)var1.next();
                  if (otherPlayer.getPlayer().getWorld().equals(gPlayer.getPlayer().getWorld()) && !otherPlayer.equals(gPlayer) && otherPlayer.getPlayer().canSee(gPlayer.getPlayer())) {
                     otherPlayer.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer)gPlayer.getPlayer()).getHandle()));
                  }
               }

            }
         }, 5L);
      }
   }
}
