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
   private String bH = UUID.randomUUID().toString();
   private MapData bI;
   private GameMap bJ;
   private World world;
   private GameState bK;

   public GameWorld(MapData data, GameMap gameMap) {
      this.bI = data;
      this.bJ = gameMap;
      this.world = null;
      this.bK = null;
      if (this.bJ != null) {
         this.bJ.a(this);
      }

   }

   public MapData aS() {
      return this.bI;
   }

   public GameMap aT() {
      return this.bJ;
   }

   public World getWorld() {
      return this.world;
   }

   public boolean isLoaded() {
      return this.bG;
   }

   public GameState aU() {
      return this.bK;
   }

   public void a(GameState state) {
      if (!this.bK.equals(GameState.bD)) {
         this.bK = state;
      }
   }

   public void load() throws Exception {
      if (this.bG) {
         throw new Exception("Cannot load a world if it's already loaded");
      } else {
         this.aV();
         this.aX();
         WorldCreator worldCreator = new WorldCreator(this.bH);
         worldCreator.generator(new VoidGenerator());
         World world = Bukkit.getServer().createWorld(worldCreator);
         world.setAutoSave(false);
         if (this.bI.bn().ba()) {
            world.setFullTime((long)this.bI.bn().aZ());
         }

         world.setSpawnFlags(false, false);
         if (!this.bI.bn().bg()) {
            Iterator var3 = world.getLivingEntities().iterator();

            while(var3.hasNext()) {
               LivingEntity entity = (LivingEntity)var3.next();
               if (!entity.getType().equals(EntityType.ARMOR_STAND) && !entity.getType().equals(EntityType.MINECART)) {
                  entity.remove();
               }
            }
         }

         Vector spawn = this.bI.bw().bO();
         world.loadChunk(world.getChunkAt(new Location(world, (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ())));
         this.bG = true;
         this.world = world;
         this.bK = GameState.bz;
         Bukkit.getLogger().log(Level.INFO, "World " + this.bH + " (" + this.bI.bk() + ") successfully loaded");
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
            this.bJ = null;
            this.aV();
            Bukkit.getLogger().log(Level.INFO, "World " + this.bH + " (" + this.bI.bk() + ") has been unloaded");
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
               if (((File)entry.getKey()).toString().startsWith("." + File.separator + this.bH)) {
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
      return new File(Bukkit.getWorldContainer(), this.bH);
   }

   private void aX() throws IOException {
      FileUtils.copyDirectory(this.bI.bi(), this.getWorldContainer());
      this.aY();
   }

   private void aY() {
      File uid = new File(this.getWorldContainer(), "uid.dat");
      if (uid.exists()) {
         uid.delete();
      }

   }
}
