package org.Kratous.GameCore;

import org.Kratous.GameCore.a.AchievementHandler;
import org.Kratous.GameCore.Commands.BoostCmd;
import org.Kratous.GameCore.Commands.CancelRestartCmd;
import org.Kratous.GameCore.Commands.CycleGameCmd;
import org.Kratous.GameCore.Commands.ForceNextCmd;
import org.Kratous.GameCore.Commands.ForceStartCmd;
import org.Kratous.GameCore.Commands.GameCoreCmd;
import org.Kratous.GameCore.Commands.HelpCmd;
import org.Kratous.GameCore.Commands.InvisibleCmd;
import org.Kratous.GameCore.Commands.JoinCmd;
import org.Kratous.GameCore.Commands.LobbyCmd;
import org.Kratous.GameCore.Commands.ParticlesCmd;
import org.Kratous.GameCore.Commands.ReloadGamesCmd;
import org.Kratous.GameCore.Commands.ReloadMapsCmd2;
import org.Kratous.GameCore.Commands.RestartNextCmd;
import org.Kratous.GameCore.Commands.RotationCmd;
import org.Kratous.GameCore.Commands.RulesCmd;
import org.Kratous.GameCore.Commands.SpectateCmd;
import org.Kratous.GameCore.Commands.SpyCmd;
import org.Kratous.GameCore.Commands.TeamChatCmd;
import org.Kratous.GameCore.Commands.ToggleCmd;
import org.Kratous.GameCore.Commands.TutorialCmd;
import org.Kratous.GameCore.d.b.TicketHandler1;
import org.Kratous.GameCore.e.a.BarHandler;
import org.Kratous.GameCore.e.b.DisguiseHandler3;
import org.Kratous.GameCore.e.c.HologramHandler;
import org.Kratous.GameCore.f.GameHandler;
import org.Kratous.GameCore.f.c.ScoreboardHandler;
import org.Kratous.GameCore.g.BlockListener;
import org.Kratous.GameCore.g.EntityListener;
import org.Kratous.GameCore.g.InventoryListener;
import org.Kratous.GameCore.g.PlayerListener;
import org.Kratous.GameCore.h.LobbyHandler;
import org.Kratous.GameCore.j.LogHandler;
import org.Kratous.GameCore.l.GameEconomy;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.l.GamePlayerData;
import org.Kratous.GameCore.l.GamePlayerHandler;
import org.Kratous.GameCore.l.GamePlayerLoader;
import org.Kratous.GameCore.m.RankHandler;
import org.Kratous.GameCore.n.ServerHandler;
import org.Kratous.GameCore.p.TutorialHandler;
import org.Kratous.GameCore.q.Util3;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
   private static JavaPlugin a;
   private static GameHandler b;
   private static GamePlayerHandler c;
   private static LobbyHandler d;
   private static ScoreboardHandler e;
   private static BarHandler f;
   private static LogHandler g;
   private static RankHandler h;
   private static AchievementHandler i;
   private static TicketHandler1 j;
   private static DisguiseHandler3 k;
   private static TutorialHandler l;
   private static HologramHandler m;
   private static ServerHandler n;
   public static Economy o = null;
   public static boolean p = false;
   public static String q = "";
   public static String r = "";
   public static String s = "";
   public static Integer t = 0;

   public void onEnable() {
      this.getLogger().log(Level.INFO, "Starting GameCore - Game Management Plugin");
      this.p();
      a = this;
      File file = new File(this.getDataFolder(), "config.yml");
      if (!file.exists()) {
         this.getLogger().log(Level.WARNING, "Config file did not exist. Default values have been added");
         this.saveDefaultConfig();
      }

      r = a().getConfig().getString("licence", "");
      if (r.length() < 20) {
         Bukkit.getLogger().log(Level.SEVERE, "Please provide a licence key inside the config.yml");
      } else if (!Util3.fx()) {
         Bukkit.getLogger().log(Level.SEVERE, "ERROR CODE: Invalid Licence - Please enter a correct licence inside the config.yml");
      } else {
         n = new ServerHandler();
         b = new GameHandler();
         c = new GamePlayerHandler();
         d = new LobbyHandler();
         Core.e = new ScoreboardHandler();
         f = new BarHandler();
         g = new LogHandler();
         h = new RankHandler();
         i = new AchievementHandler();
         j = new TicketHandler1();
         k = new DisguiseHandler3();
         l = new TutorialHandler();
         m = new HologramHandler();
         b.ad();
         b.ab();
         b.ac();
         b.ae();
         l.fs();
         l.fr();
         h.fb();
         i.s();
         j.B();
         Iterator var2 = Bukkit.getServer().getOnlinePlayers().iterator();

         while(var2.hasNext()) {
            Player p = (Player)var2.next();
            GamePlayerData gPlayerData = GamePlayerLoader.a(p.getUniqueId());
            if (gPlayerData == null) {
               p.kickPlayer("Please rejoin this server!");
            } else {
               GamePlayer gPlayer = c.a(p);
               gPlayer.dN();
            }
         }

         Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
         Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
         Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
         Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
         this.getCommand("tutorial").setExecutor(new TutorialCmd());
         this.getCommand("reloadgames").setExecutor(new ReloadGamesCmd());
         this.getCommand("reloadmaps").setExecutor(new ReloadMapsCmd2());
         this.getCommand("forcenext").setExecutor(new ForceNextCmd());
         this.getCommand("cycle").setExecutor(new CycleGameCmd());
         this.getCommand("restartnext").setExecutor(new RestartNextCmd());
         this.getCommand("spectate").setExecutor(new SpectateCmd());
         this.getCommand("teamchat").setExecutor(new TeamChatCmd());
         this.getCommand("invisible").setExecutor(new InvisibleCmd());
         this.getCommand("spy").setExecutor(new SpyCmd());
         this.getCommand("help").setExecutor(new HelpCmd());
         this.getCommand("rules").setExecutor(new RulesCmd());
         this.getCommand("toggle").setExecutor(new ToggleCmd());
         this.getCommand("lobby").setExecutor(new LobbyCmd());
         this.getCommand("join").setExecutor(new JoinCmd());
         this.getCommand("forcestart").setExecutor(new ForceStartCmd());
         this.getCommand("particles").setExecutor(new ParticlesCmd());
         this.getCommand("gamecore").setExecutor(new GameCoreCmd());
         this.getCommand("cancelrestart").setExecutor(new CancelRestartCmd());
         this.getCommand("rotation").setExecutor(new RotationCmd());
         this.getCommand("boost").setExecutor(new BoostCmd());

         try {
            Class.forName("org.Kratous.GameCore.k.ShutdownRunnable");
            Class.forName("org.Kratous.GameCore.q.Util1");
         } catch (Exception var6) {
            var6.printStackTrace();
         }

         Bukkit.getLogger().log(Level.INFO, "Successfully initialized " + a().getDescription().getName() + " version " + a().getDescription().getVersion());
         if (!Util3.fy()) {
            Bukkit.getLogger().log(Level.INFO, ChatColor.RED + "GameCore is outdated - Please update: gamecore.krato.us");
         }

      }
   }

   public void onDisable() {
      n.destroy();
      Iterator var1 = c.getPlayers().iterator();

      while(var1.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var1.next();
         if (gPlayer.getPlayer().isDead()) {
            gPlayer.dT();
         }
      }

      try {
         d.cT().unload();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      try {
         d.destroy();
         b.destroy();
         if (b.V() != null) {
            b.V().unload();
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      Core.e.cK();
      f.destroy();
      c.destroy();
   }

   public static JavaPlugin a() {
      return a;
   }

   public static GameHandler b() {
      return b;
   }

   public static GamePlayerHandler c() {
      return c;
   }

   public static LobbyHandler d() {
      return d;
   }

   public static ScoreboardHandler e() {
      return e;
   }

   public static BarHandler f() {
      return f;
   }

   public static LogHandler g() {
      return g;
   }

   public static RankHandler h() {
      return h;
   }

   public static AchievementHandler i() {
      return i;
   }

   public static ServerHandler j() {
      return n;
   }

   public static TicketHandler1 k() {
      return j;
   }

   public static DisguiseHandler3 l() {
      return k;
   }

   public static TutorialHandler m() {
      return l;
   }

   public static HologramHandler n() {
      return m;
   }

   public static void a(String value, boolean playersOnly, boolean format) {
      if (format) {
         value = a(value);
      }

      Iterator var3 = c.getPlayers().iterator();

      while(true) {
         GamePlayer gPlayer;
         do {
            if (!var3.hasNext()) {
               return;
            }

            gPlayer = (GamePlayer)var3.next();
         } while(playersOnly && gPlayer.dS());

         if (gPlayer.dF().eA()) {
            gPlayer.getPlayer().sendMessage(value);
         }
      }
   }

   public static String a(String message) {
      return ChatColor.AQUA + ChatColor.MAGIC.toString() + " !!! " + ChatColor.RESET + ChatColor.DARK_AQUA.toString() + message + ChatColor.AQUA + ChatColor.MAGIC.toString() + " !!! ";
   }

   public static String b(String md5) {
      try {
         MessageDigest md = MessageDigest.getInstance("MD5");
         byte[] array = md.digest(md5.getBytes());
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString(array[i] & 255 | 256).substring(1, 3));
         }

         return sb.toString();
      } catch (NoSuchAlgorithmException var5) {
         return null;
      }
   }

   public static void o() {
      String user = r + "x";
      q = c(user);
   }

   private static String c(String x) {
      return b(b(b(x)));
   }

   private void p() {
      Plugin vault = this.getServer().getPluginManager().getPlugin("Vault");
      if (vault != null) {
         ServicesManager manager = this.getServer().getServicesManager();
         Class<? extends Economy> clazz = GameEconomy.class;
         String name = "GameEconomy";

         try {
            Economy econ = (Economy)clazz.getConstructor(Core.class).newInstance(this);
            manager.register(Economy.class, econ, vault, ServicePriority.Highest);
            vault.getLogger().info(String.format("[Economy] %s found: %s", name, econ.isEnabled() ? "Loaded" : "Waiting"));
         } catch (Exception var6) {
            vault.getLogger().severe(String.format("[Economy] There was an error hooking %s - check to make sure you're using a compatible version!", name));
         }
      }

   }
}
