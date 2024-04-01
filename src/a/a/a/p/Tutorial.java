package a.a.a.p;

import a.a.a.Core;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import a.a.a.r.a.VoidGenerator;
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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public abstract class Tutorial implements Listener {
   private int iE = 0;
   private TutorialData iF;
   private boolean bG = false;
   private String bH;
   private GamePlayer er;
   private World world;
   private int iG = -1;

   public abstract String aI();

   public World getWorld() {
      return this.world;
   }

   public int fh() {
      return this.iG;
   }

   public void fi() {
      this.iG = this.fk().fw();
   }

   public void fj() {
      --this.iG;
   }

   public void a(TutorialData data) {
      this.iF = data;
   }

   public TutorialStep fk() {
      return (TutorialStep)this.iF.fp().get(this.iE);
   }

   public boolean fl() {
      return this.iE == this.iF.fp().size();
   }

   public void fm() {
      ++this.iE;
   }

   public void fn() {
      this.er.dN();
      this.er.getPlayer().sendMessage(this.fk().getTitle());
      Iterator var1 = this.fk().getMessages().iterator();

      while(var1.hasNext()) {
         String str = (String)var1.next();
         this.er.getPlayer().sendMessage(str);
      }

   }

   public void fo() {
      this.fi();
   }

   public void D(GamePlayer gPlayer) throws Exception {
      this.er = gPlayer;
      if (this.bG) {
         throw new Exception("Cannot load a world if it's already loaded");
      } else {
         this.bH = "TUTORIAL-" + UUID.randomUUID().toString();
         this.aV();
         this.aX();
         WorldCreator worldCreator = new WorldCreator(this.bH);
         worldCreator.generator(new VoidGenerator());
         World world = Bukkit.getServer().createWorld(worldCreator);
         world.setAutoSave(false);
         if (this.iF.aZ() >= 0) {
            world.setFullTime((long)this.iF.aZ());
         }

         world.setSpawnFlags(false, false);
         Iterator var4 = world.getLivingEntities().iterator();

         while(var4.hasNext()) {
            LivingEntity entity = (LivingEntity)var4.next();
            entity.remove();
         }

         Vector spawn = this.fk().bS();
         world.loadChunk(world.getChunkAt(new Location(world, (double)spawn.getBlockX(), (double)spawn.getBlockY(), (double)spawn.getBlockZ())));
         this.bG = true;
         this.world = world;
         Bukkit.getLogger().log(Level.INFO, "World " + this.bH + " (" + this.iF.bk() + ") successfully loaded");
         Bukkit.getPluginManager().registerEvents(this, Core.a());
         this.fn();
      }
   }

   public void unload() throws Exception {
      if (!this.bG) {
         throw new Exception("Cannot unload a world if it isn't loaded!");
      } else {
         HandlerList.unregisterAll(this);
         if (!this.world.getPlayers().isEmpty()) {
            Iterator var1 = this.world.getPlayers().iterator();

            while(var1.hasNext()) {
               Player p = (Player)var1.next();
               GamePlayer gPlayer = Core.c().r(p.getName());
               gPlayer.a((Tutorial)null);
               gPlayer.a(GamePlayerState.gY);
               gPlayer.dN();
            }
         }

         if (!Bukkit.getServer().unloadWorld(this.world, false)) {
            throw new Exception("Bukkit failed to unload the world. Uhohhhh");
         } else {
            this.bG = false;
            this.aV();
            Bukkit.getLogger().log(Level.INFO, "World " + this.bH + " (" + this.iF.bk() + ") has been unloaded");
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
      FileUtils.copyDirectory(this.iF.bi(), this.getWorldContainer());
      this.aY();
   }

   private void aY() {
      File uid = new File(this.getWorldContainer(), "uid.dat");
      if (uid.exists()) {
         uid.delete();
      }

   }

   protected boolean a(Player eventPlayer, World eventWorld) {
      return (eventPlayer == null || eventPlayer.equals(this.er.getPlayer())) && (eventWorld == null || eventWorld.equals(this.world));
   }
}
