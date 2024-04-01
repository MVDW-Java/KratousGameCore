package a.a.a.p;

import a.a.a.Core;
import a.a.a.i.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.bukkit.Bukkit;

public class TutorialHandler {
   private List aU = new ArrayList();
   private Map iJ = new HashMap();
   private TutorialRunnable iK = new TutorialRunnable();

   public TutorialHandler() {
      Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.a(), this.iK, 5L, 5L);
   }

   public void fr() {
      this.aU.clear();
      File mapFolder = new File(Core.a().getDataFolder(), "tutorials/maps");
      if (!mapFolder.exists()) {
         if (!mapFolder.mkdirs()) {
            Bukkit.getLogger().severe("Could not create the tutorial maps directory");
            return;
         }

         Bukkit.getLogger().warning("Tutorial maps directory created.");
      }

      File[] var2 = mapFolder.listFiles();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File file = var2[var4];
         if (file.isDirectory()) {
            File YML = new File(file, "map.yml");

            try {
               if (YML.exists()) {
                  TutorialData data = new TutorialData(YML);
                  if (!this.iJ.containsKey(data.bj())) {
                     Bukkit.getLogger().warning("Unknown tutorial gamemode '" + data.bj() + "' for tutorials");
                  } else {
                     this.aU.add(data);
                  }
               }
            } catch (Exception var8) {
               Bukkit.getLogger().severe("Could not load tutorial " + YML.getParentFile().getName());
               var8.printStackTrace();
            }
         }
      }

      Bukkit.getLogger().log(Level.INFO, "Successfully loaded " + this.aU.size() + " tutorial maps");
   }

   public void fs() {
      this.iJ.clear();
      File gameFolder = new File(Core.a().getDataFolder(), "tutorials/games");
      if (!gameFolder.exists()) {
         if (!gameFolder.mkdirs()) {
            Logger.log("Could not create the tutorial games directory");
            return;
         }

         Logger.log("Tutorial games directory created.");
      }

      File[] var2 = gameFolder.listFiles();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         File file = var2[var4];
         if (file.getName().endsWith(".jar") && file.isFile()) {
            try {
               List<String> classNames = new ArrayList();
               ZipInputStream zip = new ZipInputStream(new FileInputStream(file));
               Throwable var8 = null;

               try {
                  for(ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                     if (entry.getName().endsWith(".class") && !entry.isDirectory()) {
                        StringBuilder className = new StringBuilder();
                        String[] var11 = entry.getName().split("/");
                        int var12 = var11.length;

                        for(int var13 = 0; var13 < var12; ++var13) {
                           String part = var11[var13];
                           if (className.length() != 0) {
                              className.append(".");
                           }

                           className.append(part);
                           if (part.endsWith(".class")) {
                              className.setLength(className.length() - ".class".length());
                           }
                        }

                        classNames.add(className.toString());
                     }
                  }
               } catch (Throwable var23) {
                  var8 = var23;
                  throw var23;
               } finally {
                  if (zip != null) {
                     if (var8 != null) {
                        try {
                           zip.close();
                        } catch (Throwable var22) {
                           var8.addSuppressed(var22);
                        }
                     } else {
                        zip.close();
                     }
                  }

               }

               String className = file.getName().replace(".jar", "").split("-")[0];
               URLClassLoader loader = new URLClassLoader(new URL[]{new URL("file:///" + file.getAbsolutePath())}, this.getClass().getClassLoader());
               Class<?> clazz = Class.forName("com.minigamemc." + className.toLowerCase() + "." + className, true, loader);
               Class<? extends Tutorial> gameClass = clazz.asSubclass(Tutorial.class);
               Tutorial map = (Tutorial)gameClass.getConstructor().newInstance();
               Iterator var31 = classNames.iterator();

               while(var31.hasNext()) {
                  String str = (String)var31.next();
                  Class.forName(str, true, loader);
               }

               loader.close();
               if (this.iJ.containsKey(map.aI().toUpperCase())) {
                  Bukkit.getLogger().log(Level.SEVERE, "Could not add '" + map.aI() + "' to tutorials... It already exists.");
               } else {
                  this.iJ.put(map.aI().toUpperCase(), map);
               }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IOException var25) {
               Bukkit.getLogger().log(Level.SEVERE, "Could not load gamemode at " + file.getAbsolutePath());
               var25.printStackTrace();
            }
         }
      }

      Bukkit.getLogger().log(Level.INFO, "Successfully loaded " + this.iJ.size() + " tutorial gamemodes");
   }

   public Collection ft() {
      return this.aU;
   }

   public TutorialData t(String name) {
      Iterator var2 = this.aU.iterator();

      TutorialData data;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         data = (TutorialData)var2.next();
      } while(!data.bk().equalsIgnoreCase(name));

      return data;
   }

   public Tutorial b(TutorialData data) {
      try {
         Tutorial tutorial = (Tutorial)((Tutorial)this.iJ.get(data.bj())).getClass().newInstance();
         tutorial.a(data);
         return tutorial;
      } catch (IllegalAccessException | InstantiationException var3) {
         var3.printStackTrace();
         return null;
      }
   }
}
