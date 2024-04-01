package org.Kratous.GameCore.f;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.a.MapData;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.l.GamePlayerState;
import org.Kratous.GameCore.r.a.VoidGenerator;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.logging.Level;
import net.minecraft.server.v1_8_R3.RegionFile;
import net.minecraft.server.v1_8_R3.RegionFileCache;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class GameWorld {
   private boolean bG = false;
   private final String MapID = UUID.randomUUID().toString();
   private final MapData MapData;
   private GameMap GameMap;
   private World world;
   private GameState GameState;

   public GameWorld(MapData data, GameMap gameMap) {
      this.MapData = data;
      this.GameMap = gameMap;
      if (this.GameMap != null) {
         this.GameMap.a(this);
      }

   }

   public MapData aS() {
      return this.MapData;
   }

   public GameMap aT() {
      return this.GameMap;
   }

   public World getWorld() {
      return this.world;
   }

   public boolean isLoaded() {
      return this.bG;
   }

   public GameState aU() {
      return this.GameState;
   }

   public void a(GameState state) {
      if (!this.GameState.equals(GameState.bD)) {
         this.GameState = state;
      }
   }

   public void load() throws Exception {
      if (this.bG) {
         throw new Exception("Cannot load a world if it's already loaded");
      } else {
         this.aV();
         this.aX();
         WorldCreator worldCreator = new WorldCreator(this.MapID);
         worldCreator.generator(new VoidGenerator());
         World world = Bukkit.getServer().createWorld(worldCreator);
         world.setAutoSave(false);
         if (this.MapData.bn().ba()) {
            world.setFullTime((long)this.MapData.bn().aZ());
         }

         world.setSpawnFlags(false, false);
         if (!this.MapData.bn().bg()) {
            Iterator var3 = world.getLivingEntities().iterator();

            while(var3.hasNext()) {
               LivingEntity entity = (LivingEntity)var3.next();
               if (!entity.getType().equals(EntityType.ARMOR_STAND) && !entity.getType().equals(EntityType.MINECART)) {
                  entity.remove();
               }
            }
         }

         Vector spawn = this.MapData.bw().bO();
         world.loadChunk(world.getChunkAt(new Location(world, (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ())));
         this.bG = true;
         this.world = world;
         this.GameState = GameState.bz;
         Bukkit.getLogger().log(Level.INFO, "World " + this.MapID + " (" + this.MapData.bk() + ") successfully loaded");
      }
   }

   public void unload() throws Exception {
      if (!this.bG) {
         throw new Exception("Cannot unload a world if it isn't loaded!");
      } else {
         if (!this.world.getPlayers().isEmpty()) {
            Iterator var1 = this.world.getPlayers().iterator();

            while(var1.hasNext()) {
               Player p = (Player)var1.next();
               GamePlayer gPlayer = Core.c().r(p.getName());
               gPlayer.a(GamePlayerState.gY);
               gPlayer.g((MapTeam)null);
               gPlayer.dN();
            }
         }

         if (!Bukkit.getServer().unloadWorld(this.world, false)) {
            throw new Exception("Bukkit failed to unload the world. Uhohhhh");
         } else {
            this.bG = false;
            this.GameMap = null;
            this.aV();
            Bukkit.getLogger().log(Level.INFO, "World " + this.MapID + " (" + this.MapData.bk() + ") has been unloaded");
         }
      }
   }

   private void aV() throws Exception {
      File container = this.getWorldContainer();
      if (container.exists()) {
         if (this.bG) {
            throw new Exception("Cannot Delete While the world is still loaded");
         } else {
            this.aW();
            FileUtils.deleteDirectory(container);
         }
      }
   }

   private void aW() throws Exception {
      if (this.bG) {
         throw new Exception("Cannot Clear References While the world is still loaded");
      } else {
         ArrayList<File> removedKeys = new ArrayList();
         HashMap regionFiles = null;

         try {
            Field a = RegionFileCache.class.getDeclaredField("a");
            a.setAccessible(true);
            regionFiles = (HashMap)a.get((Object)null);
            Field c = RegionFile.class.getDeclaredField("c");
            c.setAccessible(true);
            Iterator var5 = regionFiles.entrySet().iterator();

            while(var5.hasNext()) {
               Entry<File, RegionFile> entry = (Entry)var5.next();
               if (((File)entry.getKey()).toString().startsWith("." + File.separator + this.MapID)) {
                  RegionFile rfile = (RegionFile)entry.getValue();
                  if (rfile != null) {
                     RandomAccessFile raf = (RandomAccessFile)c.get(rfile);
                     raf.close();
                     removedKeys.add(entry.getKey());
                  }
               }
            }
         } catch (IllegalAccessException | IOException | IllegalArgumentException | NoSuchFieldException | SecurityException var9) {
            var9.printStackTrace();
         }

         Iterator var10 = removedKeys.iterator();

         while(var10.hasNext()) {
            File file = (File)var10.next();
            regionFiles.remove(file);
         }

      }
   }

   private File getWorldContainer() {
      return new File(Bukkit.getWorldContainer(), this.MapID);
   }

   private void aX() throws IOException {
      FileUtils.copyDirectory(this.MapData.bi(), this.getWorldContainer());
      this.aY();
   }

   private void aY() {
      File uid = new File(this.getWorldContainer(), "uid.dat");
      if (uid.exists()) {
         uid.delete();
      }

   }
}
