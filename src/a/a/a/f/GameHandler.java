package a.a.a.f;

import a.a.a.Core;
import a.a.a.f.a.MapData;
import a.a.a.f.a.MapKit;
import a.a.a.f.a.MapTeam;
import a.a.a.f.b.CaptureThePointGame;
import a.a.a.f.b.CaptureTheWoolGame;
import a.a.a.f.b.ConquestGame;
import a.a.a.f.b.DestroyTheCoreGame;
import a.a.a.f.b.DestroyTheMonumentGame;
import a.a.a.f.b.DestroyTheNovaGame;
import a.a.a.f.b.DetonateTheBombGame;
import a.a.a.f.b.TeamDeathmatchGame;
import a.a.a.h.a.HologramType;
import a.a.a.k.ShutdownRunnable;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerState;
import a.a.a.q.Util3;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class GameHandler {
   private Map aT = new HashMap();
   private List aU = new ArrayList();
   private List aV = new ArrayList();
   private List aW = new ArrayList();
   private Map aX = new HashMap();
   private Map aY = new HashMap();
   private Map aZ = new HashMap();
   private GameWorld ba = null;
   private GameWorld bb = null;
   private GameWorld bc = null;
   private GameRunnable1 bd = null;
   private int be = 0;
   private boolean bf = false;
   public boolean bg = Core.a().getConfig().getBoolean("game.votingEnabled", false);
   private int bh = 0;
   private int bi = 0;

   public GameWorld U() {
      return this.bb;
   }

   public GameWorld V() {
      return this.ba;
   }

   public GameWorld W() {
      return this.bc;
   }

   public GameRunnable1 X() {
      return this.bd;
   }

   public int Y() {
      return this.bh;
   }

   public int Z() {
      return this.bi;
   }

   public List aa() {
      return this.aW;
   }

   public void a(Class x) {
      Class<? extends GameMap> tdmMap = x;
      GameMap map = null;

      try {
         map = (GameMap)tdmMap.getConstructor().newInstance();
      } catch (InstantiationException var5) {
         var5.printStackTrace();
      } catch (IllegalAccessException var6) {
         var6.printStackTrace();
      } catch (InvocationTargetException var7) {
         var7.printStackTrace();
      } catch (NoSuchMethodException var8) {
         var8.printStackTrace();
      }

      this.aT.put(map.aI().toUpperCase(), map);
   }

   public void ab() {
      this.aT.clear();
      this.a(TeamDeathmatchGame.class);
      this.a(CaptureTheWoolGame.class);
      this.a(DestroyTheCoreGame.class);
      this.a(DestroyTheMonumentGame.class);
      this.a(DestroyTheNovaGame.class);
      this.a(DetonateTheBombGame.class);
      this.a(ConquestGame.class);
      this.a(CaptureThePointGame.class);
      Bukkit.getLogger().log(Level.INFO, "Successfully loaded " + this.aT.size() + " gamemodes");
   }

   public void ac() {
      this.bi = 0;
      this.bh = 0;
      List<String> mapNames = new ArrayList();
      Iterator var2 = this.aU.iterator();

      while(var2.hasNext()) {
         MapData data = (MapData)var2.next();
         mapNames.add(data.bk());
      }

      this.aU.clear();
      this.aV.clear();
      boolean foundLobby = false;
      File mapFolder = new File(Core.a().getDataFolder(), "maps");
      if (!mapFolder.exists()) {
         if (!mapFolder.mkdirs()) {
            Bukkit.getLogger().severe("Could not create the maps directory");
            return;
         }

         Bukkit.getLogger().log(Level.WARNING, "Maps directory created.");

         try {
            Util3.a(new URL("http://license.krato.us/assets/maps.zip"), mapFolder);
         } catch (IOException var11) {
         }
      }

      File[] var4 = mapFolder.listFiles();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         File file = var4[var6];
         if (file.isDirectory()) {
            File YML = new File(file, "map.yml");

            try {
               if (YML.exists()) {
                  MapData data = new MapData(YML);
                  if (data.bj().equalsIgnoreCase("lobby")) {
                     foundLobby = true;
                     Core.d().b(new GameWorld(data, (GameMap)null));
                  } else if (!this.aT.containsKey(data.bj())) {
                     Bukkit.getLogger().warning("Unknown gamemode '" + data.bj() + "' for '" + data.bk() + "'");
                     ++this.bh;
                  } else {
                     if (!mapNames.contains(data.bk())) {
                        ++this.bi;
                     }

                     this.aU.add(data);
                     this.aZ.put(data.bk(), data);
                  }
               }
            } catch (Exception var10) {
               Bukkit.getLogger().severe("Could not load " + YML.getParentFile().getName());
               var10.printStackTrace();
               ++this.bh;
            }
         }
      }

      if (!foundLobby) {
         Bukkit.getLogger().severe("Could not find a lobby map!");
      }

      this.aW.clear();
      this.aW.addAll(this.aU);
      Collections.shuffle(this.aW);
      if (this.aV.size() == 0) {
         this.aV.addAll(this.aU);
      }

      Collections.shuffle(this.aU);
      Bukkit.getLogger().log(Level.INFO, "Successfully loaded " + this.aV.size() + " maps");
      if (this.bg && this.aU.size() < 5) {
         Bukkit.getLogger().log(Level.INFO, "Map voting has been disabled. You need 5 or more maps for this to work.");
         this.bg = false;
      }

   }

   public void ad() {
      File[] var1 = Bukkit.getWorldContainer().listFiles();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         File file = var1[var3];
         if (file.isDirectory()) {
            File mapDataFile = new File(file, "map.yml");
            if (mapDataFile.exists()) {
               try {
                  FileUtils.deleteDirectory(file);
               } catch (IOException var7) {
                  var7.printStackTrace();
               }
            }
         }
      }

   }

   public void ae() {
      this.al();
      this.am();
   }

   public GameMap a(MapData data) {
      GameMap map = (GameMap)this.aT.get(data.bj());

      try {
         map = (GameMap)map.getClass().newInstance();
         return map;
      } catch (InstantiationException | IllegalAccessException var4) {
         Bukkit.getLogger().log(Level.SEVERE, "Instantiating new instance of GameMap threw error");
         var4.printStackTrace();
         return null;
      }
   }

   public MapData e(String name) {
      Iterator var2 = this.aU.iterator();

      MapData mapData;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         mapData = (MapData)var2.next();
      } while(!mapData.bk().equalsIgnoreCase(name));

      return mapData;
   }

   public List af() {
      return this.aU;
   }

   public boolean ag() {
      return this.bc == null && this.bg;
   }

   public List ah() {
      if (this.aV.size() < 5) {
         if (!this.bg) {
            if (this.aV.size() == 0) {
               this.aV.addAll(this.aW);
            }
         } else {
            this.aV.clear();
            this.aV.addAll(this.aU);
            Collections.shuffle(this.aV);
            this.ai();
         }
      }

      if (!this.bg) {
         return this.aW;
      } else {
         if (this.aX.isEmpty()) {
            Iterator var1 = this.aV.iterator();

            while(var1.hasNext()) {
               MapData map = (MapData)var1.next();
               this.aX.put(map, 0);
            }
         }

         return this.aV.subList(0, 4);
      }
   }

   public boolean b(GamePlayer gPlayer, String mapName) {
      MapData votedMap;
      if (this.aY.containsKey(gPlayer.getPlayer().getName())) {
         votedMap = (MapData)this.aY.get(gPlayer.getPlayer().getName());
         if (votedMap.bk().equalsIgnoreCase(mapName)) {
            return false;
         }

         this.aY.remove(gPlayer.getPlayer().getName());
         this.aX.put(votedMap, (Integer)this.aX.get(votedMap) - 1);
      }

      votedMap = this.e(mapName);
      if (votedMap == null) {
         this.ai();
         this.ah();
         return false;
      } else {
         this.aY.put(gPlayer.getPlayer().getName(), votedMap);
         if (!this.aX.containsKey(votedMap)) {
            this.aX.put(votedMap, 1);
         } else {
            this.aX.put(votedMap, (Integer)this.aX.get(votedMap) + 1);
         }

         gPlayer.a("", Core.a("You have voted for " + ChatColor.GREEN + votedMap.bk() + ChatColor.DARK_AQUA + "!"), 0.2D, 2.0D, 0.2D);
         Core.d().da();
         return true;
      }
   }

   public int f(String mapName) {
      MapData mapData = this.e(mapName);
      return !this.aX.containsKey(mapData) ? 0 : (Integer)this.aX.get(mapData);
   }

   public void ai() {
      if (this.ba != null && this.aV.contains(this.ba.aS())) {
         this.aV.remove(this.ba.aS());
      }

      if (this.bc != null && this.aV.contains(this.bc.aS())) {
         this.aV.remove(this.bc.aS());
      }

      if (this.aV.isEmpty()) {
         this.aV.clear();
         if (this.bg) {
            this.aV.addAll(this.aU);
         } else {
            this.aV.addAll(this.aW);
         }
      }

      this.aX.clear();
      this.aY.clear();
      Core.d().db();
      Core.d().da();
      Core.d().cZ().a(HologramType.eO);
   }

   public MapData aj() {
      List<Entry<MapData, Integer>> votingOrder = Util3.b(this.aX);
      return votingOrder.isEmpty() ? (MapData)this.aV.get(0) : (MapData)((Entry)votingOrder.get(0)).getKey();
   }

   public void b(MapData mapData) {
      this.bc = new GameWorld(mapData, this.a(mapData));
   }

   public void ak() {
      if (this.bd != null) {
         this.bd.cancel();
         this.bd = null;
      }

      this.bd = new GameRunnable1(this.ba);
      ++this.be;
   }

   public void al() {
      if (!this.bf) {
         if (this.bc != null && this.bc.isLoaded()) {
            this.bc = new GameWorld(this.bc.aS(), this.a(this.bc.aS()));
         }

         if (this.be >= Core.a().getConfig().getInt("game.mapsBeforeRestart", 15)) {
            Iterator var4 = Core.c().getPlayers().iterator();

            while(var4.hasNext()) {
               GamePlayer gPlayer = (GamePlayer)var4.next();
               if (!gPlayer.dS()) {
                  gPlayer.a(GamePlayerState.gY);
                  gPlayer.g((MapTeam)null);
                  gPlayer.dN();
               }
            }

            if (this.ba != null) {
               this.ba.a(GameState.bE);
            } else if (this.bc != null) {
               this.bc.a(GameState.bE);
            }

            this.bd.cancel();
            this.bd = null;
            new ShutdownRunnable();
            this.bf = true;
         } else {
            if (this.bc == null) {
               MapData nextMapData = null;
               if (this.ag()) {
                  nextMapData = this.aj();
               } else {
                  this.ai();
                  nextMapData = (MapData)this.aV.get(0);
                  this.aV.remove(nextMapData);
               }

               this.bc = new GameWorld(nextMapData, this.a(nextMapData));
               this.ai();
            }

            try {
               this.bc.load();
               this.bc.aT().ax();
               this.bc.aT().ay();
               this.bc.aT().as();
            } catch (Exception var3) {
               var3.printStackTrace();
            }

         }
      }
   }

   public void am() {
      if (!this.bf) {
         this.bb = this.ba;
         this.ba = this.bc;

         for(this.bc = null; this.ba == null || !this.ba.isLoaded(); this.ba = this.bc) {
            this.al();
         }

         Core.d().cY().dc();
         this.ak();

         Iterator var1;
         GamePlayer gPlayer;
         for(var1 = Core.c().getPlayers().iterator(); var1.hasNext(); gPlayer.a(Core.a("Currently Playing: " + ChatColor.GOLD + this.ba.aS().bk()), Core.a(Core.a().getConfig().getString("game.websiteUrl")))) {
            gPlayer = (GamePlayer)var1.next();
            if (gPlayer.dH() || gPlayer.dI()) {
               gPlayer.b((MapKit)null);
               gPlayer.dN();
            }
         }

         if (this.bb != null) {
            if (!this.bb.getWorld().getPlayers().isEmpty()) {
               var1 = this.bb.getWorld().getPlayers().iterator();

               while(var1.hasNext()) {
                  Player p = (Player)var1.next();
                  GamePlayer gPlayer1 = Core.c().r(p.getName());
                  if (gPlayer1 != null) {
                     gPlayer1.g((MapTeam)null);
                     gPlayer1.a(GamePlayerState.gY);
                     gPlayer1.dN();
                  }
               }
            }

            try {
               this.bb.unload();
            } catch (Exception var4) {
               var4.printStackTrace();
            }
         }

      }
   }

   public void an() {
      this.be = Core.a().getConfig().getInt("game.mapsBeforeRestart", 15);
   }

   public void ao() {
      this.be = 0;
   }

   public void ap() {
      Iterator var1 = Core.c().getPlayers().iterator();

      label48:
      while(var1.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var1.next();
         Iterator var3 = Core.c().getPlayers().iterator();

         while(true) {
            while(true) {
               GamePlayer toHide;
               do {
                  if (!var3.hasNext()) {
                     continue label48;
                  }

                  toHide = (GamePlayer)var3.next();
               } while(gPlayer.equals(toHide));

               if (gPlayer.dG()) {
                  gPlayer.getPlayer().hidePlayer(toHide.getPlayer());
               } else if (toHide.isInvisible()) {
                  if (!gPlayer.getPlayer().hasPermission("gamecore.invisiblesee")) {
                     gPlayer.getPlayer().hidePlayer(toHide.getPlayer());
                  }
               } else if (gPlayer.dH()) {
                  if (!toHide.dH() && this.ba.aU().equals(GameState.bB)) {
                     gPlayer.getPlayer().hidePlayer(toHide.getPlayer());
                  } else {
                     gPlayer.getPlayer().showPlayer(toHide.getPlayer());
                  }
               } else {
                  gPlayer.getPlayer().showPlayer(toHide.getPlayer());
               }
            }
         }
      }

   }

   public void destroy() throws Exception {
      this.aT.clear();
      this.aU.clear();
      this.aV.clear();
      this.aX.clear();
      if (this.bd != null) {
         this.bd.cancel();
         this.bd = null;
      }

      if (this.bc != null && this.bc.isLoaded()) {
         this.bc.unload();
      }

   }
}
