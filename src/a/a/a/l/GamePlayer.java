package a.a.a.l;

import a.a.a.Core;
import a.a.a.a.AchievementType;
import a.a.a.f.GameState;
import a.a.a.f.a.MapKit;
import a.a.a.f.a.MapSpawn;
import a.a.a.f.a.MapTeam;
import a.a.a.g.a.InventoryReloadedEvent;
import a.a.a.g.a.PlayerSpawnEvent;
import a.a.a.j.RewardType;
import a.a.a.l.a.PlayerAchievementHandler;
import a.a.a.l.b.ConfigHandler;
import a.a.a.l.c.EffectHandler;
import a.a.a.l.d.PlayerRankHandler2;
import a.a.a.q.BlockUtil1;
import a.a.a.q.Util3;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCloseWindow;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GamePlayer extends GamePlayerData {
   private Player player;
   private MapTeam do;
   private GamePlayerState gs;
   private MapKit gt;
   private PlayerAchievementHandler gu;
   private PlayerRankHandler2 gv;
   private EffectHandler gw;
   private ConfigHandler gx;
   private LinkedList gy;
   private GamePlayer gz;
   private boolean gA;

   public GamePlayer(Player player, GamePlayerData data) {
      super(data);
      this.player = player;
      this.do = null;
      this.gs = GamePlayerState.gY;
      this.gt = null;
      this.gy = new LinkedList();
      this.gz = null;
      this.gu = new PlayerAchievementHandler(this);
      this.gv = new PlayerRankHandler2(this);
      this.gw = new EffectHandler(this);
      this.gx = new ConfigHandler(this);
      this.gA = false;
   }

   public Player getPlayer() {
      return this.player;
   }

   public String dB() {
      return this.player.getPlayerListName();
   }

   public PlayerAchievementHandler dC() {
      return this.gu;
   }

   public PlayerRankHandler2 dD() {
      return this.gv;
   }

   public EffectHandler dE() {
      return this.gw;
   }

   public ConfigHandler dF() {
      return this.gx;
   }

   public boolean dG() {
      return this.gs.equals(GamePlayerState.gZ);
   }

   public boolean dH() {
      return this.gs.equals(GamePlayerState.hb);
   }

   public boolean dI() {
      return this.gs.equals(GamePlayerState.ha);
   }

   public void g(MapTeam team) {
      this.do = team;
   }

   public MapTeam ca() {
      return this.do;
   }

   public void a(GamePlayerState state) {
      this.gs = state;
   }

   public GamePlayerState dJ() {
      return this.gs;
   }

   public void b(MapKit kit) {
      this.gt = kit;
   }

   public MapKit dK() {
      return this.gt;
   }

   public GamePlayer dL() {
      return this.gz;
   }

   public void B(GamePlayer e) {
      this.gz = e;
   }

   public void s(int amount) {
      this.gF += amount;
      Core.g().a(this, RewardType.ga, amount);
      this.a("coins", (Object)this.gF);
      this.gu.a(AchievementType.M, amount);
   }

   public void t(int amount) {
      this.gH += amount;
   }

   public void u(int amount) {
      this.gG += amount;
      this.a("tickets", (Object)this.gG);
   }

   public void dM() {
      this.gH = 0;
   }

   public void a(Connection connection) throws SQLException {
      PreparedStatement statement = connection.prepareStatement("SELECT `tickets` FROM `users` WHERE `id` = " + this.id + " LIMIT 1");
      Throwable var3 = null;

      try {
         ResultSet set = statement.executeQuery();
         Throwable var5 = null;

         try {
            if (set.next()) {
               this.gG = set.getInt(1);
            }
         } catch (Throwable var28) {
            var5 = var28;
            throw var28;
         } finally {
            if (set != null) {
               if (var5 != null) {
                  try {
                     set.close();
                  } catch (Throwable var27) {
                     var5.addSuppressed(var27);
                  }
               } else {
                  set.close();
               }
            }

         }
      } catch (Throwable var30) {
         var3 = var30;
         throw var30;
      } finally {
         if (statement != null) {
            if (var3 != null) {
               try {
                  statement.close();
               } catch (Throwable var26) {
                  var3.addSuppressed(var26);
               }
            } else {
               statement.close();
            }
         }

      }

   }

   public void dN() {
      GamePlayerState tempState = this.gs;
      switch(tempState) {
      case gZ:
      case gY:
         this.player.setGameMode(GameMode.SURVIVAL);
         if (this.player.hasPermission("gamecore.vip")) {
            this.player.setAllowFlight(true);
            this.player.setFlying(true);
         } else {
            this.player.setAllowFlight(false);
         }

         Core.e().u(this);
         break;
      case ha:
         if (Core.a().getConfig().getBoolean("game.spectatorEnabled", true)) {
            this.player.setGameMode(GameMode.SPECTATOR);
         } else {
            this.player.setGameMode(GameMode.SURVIVAL);
         }

         this.player.setAllowFlight(true);
         this.player.setFlying(true);
         break;
      case hb:
         this.player.setGameMode(GameMode.SURVIVAL);
         this.player.setAllowFlight(false);
         this.gQ = null;
         if (Core.b().V().aU().equals(GameState.bB)) {
            this.player.setAllowFlight(false);
            this.player.setFlying(false);
         } else {
            this.player.setAllowFlight(true);
            this.player.setFlying(true);
         }
      }

      if (this.player.isInsideVehicle()) {
         this.player.getVehicle().eject();
         if (this.player.getVehicle() != null && !(this.player.getVehicle() instanceof Player)) {
            this.player.getVehicle().remove();
         }
      } else if (this.player.getPassenger() != null) {
         this.player.eject();
      }

      if (!this.gA) {
         this.player.teleport(this.getSpawnLocation().add(0.5D, 0.0D, 0.5D), TeleportCause.PLUGIN);
         this.gA = false;
      }

      this.dU();
      this.dP();
      this.gy.clear();
      this.gy.add(this.player.getLocation().toVector());
      this.gO = 0.0D;
      this.dO();
      Core.b().ap();
      Core.l().g(this);
      ((CraftPlayer)this.player).getHandle().getDataWatcher().watch(9, (byte)0);
      if (this.dH() && Core.b().V().aU().equals(GameState.bB)) {
         Bukkit.getPluginManager().callEvent(new PlayerSpawnEvent(this));
      }

   }

   public void dO() {
      Map<Integer, ItemStack> inventorySet = new HashMap();
      GamePlayerState tempState = this.dS() && this.gs.equals(GamePlayerState.hb) ? GamePlayerState.gY : this.gs;
      Iterator var3;
      PotionEffect stack;
      switch(tempState) {
      case gZ:
         if (this.gQ.fk().bM() != null) {
            inventorySet = this.gQ.fk().bM().bB();
            var3 = this.gQ.fk().bM().getEffects().iterator();

            while(var3.hasNext()) {
               stack = (PotionEffect)var3.next();
               this.player.addPotionEffect(stack);
            }
         }
         break;
      case gY:
         inventorySet = Util3.E(this);
         break;
      case ha:
         inventorySet = Util3.F(this);
         break;
      case hb:
         if (this.gt != null && Core.b().V().aU().equals(GameState.bB)) {
            inventorySet = this.gt.bB();
            var3 = this.gt.getEffects().iterator();

            while(var3.hasNext()) {
               stack = (PotionEffect)var3.next();
               this.player.addPotionEffect(stack);
            }
         }
      }

      for(int inventorySlot = 0; inventorySlot <= 103; ++inventorySlot) {
         if (inventorySlot <= 35 || inventorySlot >= 100 && inventorySlot <= 103) {
            stack = null;
            ItemStack stack;
            if (((Map)inventorySet).containsKey(inventorySlot)) {
               stack = BlockUtil1.a((ItemStack)((Map)inventorySet).get(inventorySlot));
            } else {
               stack = new ItemStack(Material.AIR);
            }

            switch(inventorySlot) {
            case 100:
               this.player.getInventory().setBoots(stack);
               break;
            case 101:
               this.player.getInventory().setLeggings(stack);
               break;
            case 102:
               this.player.getInventory().setChestplate(stack);
               break;
            case 103:
               this.player.getInventory().setHelmet(stack);
               break;
            default:
               this.player.getInventory().setItem(inventorySlot, stack);
            }
         }
      }

      Bukkit.getPluginManager().callEvent(new InventoryReloadedEvent(this));
      this.player.updateInventory();
   }

   public Location getSpawnLocation() {
      Vector spawn;
      switch(this.gs) {
      case gZ:
         return this.gQ.fk().bS().toLocation(this.gQ.getWorld());
      case gY:
         spawn = Core.d().cT().aS().bw().bO();
         return new Location(Core.d().cT().getWorld(), (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ(), Core.d().cT().aS().bw().getYaw(), Core.d().cT().aS().bw().getPitch());
      case ha:
         spawn = Core.b().V().aS().bw().bO();
         return new Location(Core.b().V().getWorld(), (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ());
      case hb:
         if (this.do == null) {
            Core.b().V().aT().m(this);
            spawn = Core.d().cT().aS().bw().bO();
            return new Location(Core.d().cT().getWorld(), (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ());
         } else {
            if (Core.b().V().aU().equals(GameState.bB)) {
               MapSpawn spawn = this.do.bR();
               if (!Core.b().V().aS().bn().bd()) {
                  this.gt = spawn.bM();
               }

               Vector vector = spawn.bO();
               return new Location(Core.b().V().getWorld(), (double)vector.getBlockX(), (double)vector.getBlockY(), (double)vector.getBlockZ(), spawn.getYaw(), spawn.getPitch());
            }

            spawn = Core.b().V().aS().bw().bO();
            return new Location(Core.b().V().getWorld(), (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ());
         }
      default:
         return null;
      }
   }

   public void dP() {
      String name = this.player.getName();
      if (this.dH()) {
         name = this.do.bP() + this.do.bQ() + this.player.getName();
      } else if (this.gK) {
         name = ChatColor.ITALIC + this.player.getName();
      }

      if (name.length() > 16) {
      }

      this.player.setDisplayName(name + ChatColor.RESET);
      this.player.setPlayerListName(this.player.getName());
   }

   public void a(EnumParticle particle, Location location, float speed, int count) {
      if (location.getWorld().equals(this.player.getWorld())) {
         this.sendPacket(new PacketPlayOutWorldParticles(particle, true, (float)location.getX(), (float)location.getY(), (float)location.getZ(), 0.0F, 0.0F, 0.0F, speed, count, new int[0]));
      }

   }

   public void sendPacket(Packet packet) {
      ((CraftPlayer)this.player).getHandle().playerConnection.sendPacket(packet);
   }

   public void a(String header, String footer) {
      IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
      IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
      PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

      try {
         Field field = headerPacket.getClass().getDeclaredField("b");
         field.setAccessible(true);
         field.set(headerPacket, tabFoot);
      } catch (Exception var10) {
         var10.printStackTrace();
      } finally {
         this.sendPacket(headerPacket);
      }

   }

   public void n(final String message) {
      (new BukkitRunnable() {
         int gB = 15;

         public void run() {
            if (this.gB > 0 && GamePlayer.this.player.isOnline()) {
               GamePlayer.this.o(message);
               --this.gB;
            } else {
               this.cancel();
            }
         }
      }).runTaskTimer(Core.a(), 0L, 20L);
   }

   public void a(String title, String subtitle, double fadeInSeconds, double showSeconds, double fadeOutSeconds) {
      this.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{ \"text\": \"" + title + "\" }")));
      this.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{ \"text\": \"" + subtitle + "\" }")));
      this.sendPacket(new PacketPlayOutTitle((int)(fadeInSeconds * 20.0D), (int)(showSeconds * 20.0D), (int)(fadeOutSeconds * 20.0D)));
   }

   public void o(String message) {
      this.sendPacket(new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte)2));
   }

   public boolean b(Vector v) {
      Vector last = this.dQ();
      if (last.getBlockX() == v.getBlockX() && last.getBlockY() == v.getBlockY() && last.getBlockZ() == v.getBlockZ()) {
         return false;
      } else {
         if (this.gy.contains(v)) {
            this.gy.remove(v);
         }

         this.gy.add(v);
         return true;
      }
   }

   public Vector dQ() {
      return this.gy.isEmpty() ? this.player.getLocation().toVector() : (Vector)this.gy.get(this.gy.size() - 1);
   }

   public LinkedList dR() {
      LinkedList<Vector> locs = new LinkedList();
      locs.addAll(this.gy);
      Collections.reverse(locs);
      return locs;
   }

   public boolean c(Vector vector) {
      Vector v = this.dQ();
      return vector.getBlockX() == v.getBlockX() && vector.getBlockY() == v.getBlockY() && vector.getBlockZ() == v.getBlockZ();
   }

   public boolean dS() {
      return this.player.getWorld().equals(Core.d().cT().getWorld());
   }

   public void a(String row, Object value) {
      Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
         public void run() {
         }
      });
   }

   public void dT() {
      this.sendPacket(new PacketPlayOutCloseWindow(45));
      Bukkit.getScheduler().scheduleSyncDelayedTask(Core.a(), new Runnable() {
         public void run() {
            PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
            ((CraftPlayer)GamePlayer.this.player).getHandle().playerConnection.a(packet);
            GamePlayer.this.gA = true;
            GamePlayer.this.dN();
         }
      }, 1L);
   }

   public void dU() {
      Iterator var1 = this.player.getActivePotionEffects().iterator();

      while(var1.hasNext()) {
         PotionEffect effect = (PotionEffect)var1.next();
         this.player.removePotionEffect(effect.getType());
      }

      this.player.setFallDistance(0.0F);
      this.player.setFireTicks(0);
      this.player.setFoodLevel(20);
      this.player.setHealth(20.0D);
      this.player.setLevel(0);
      this.player.setExp(0.0F);
      this.gA = false;
      this.gz = null;
   }

   public void dV() {
      if (this.gt != null) {
         ItemStack[] var1 = this.player.getInventory().getContents();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            ItemStack stack = var1[var3];
            if (stack != null && !stack.getType().equals(Material.AIR) && !BlockUtil1.b(stack)) {
               Core.b().V().getWorld().dropItemNaturally(this.player.getLocation(), stack);
            }
         }

      }
   }

   public void dW() {
      if (Core.a().getConfig().getBoolean("game.petsEnabled", false)) {
      }

   }

   public void dX() {
   }

   public void dY() {
      Iterator var1 = Core.c().getPlayers().iterator();

      while(var1.hasNext()) {
         GamePlayer inner = (GamePlayer)var1.next();
         if (this.player.canSee(inner.getPlayer()) && !this.equals(inner) && this.player.getWorld().equals(inner.getPlayer().getWorld())) {
            this.sendPacket(new PacketPlayOutEntityDestroy(new int[]{inner.getPlayer().getEntityId()}));
         }
      }

      Bukkit.getScheduler().scheduleSyncDelayedTask(Core.a(), new Runnable() {
         public void run() {
            Iterator var1 = Core.c().getPlayers().iterator();

            while(var1.hasNext()) {
               GamePlayer inner = (GamePlayer)var1.next();
               if (GamePlayer.this.player.canSee(inner.getPlayer()) && !GamePlayer.this.player.equals(inner.getPlayer()) && GamePlayer.this.player.getWorld().equals(inner.getPlayer().getWorld())) {
                  GamePlayer.this.sendPacket(new PacketPlayOutNamedEntitySpawn((EntityHuman)((EntityHuman)((CraftEntity)inner.getPlayer()).getHandle())));
               }
            }

         }
      }, 2L);
   }
}
