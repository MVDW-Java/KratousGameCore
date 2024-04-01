package a.a.a;

import a.a.a.a.AchievementHandler;
import a.a.a.Commands.BoostCmd;
import a.a.a.Commands.CancelRestartCmd;
import a.a.a.Commands.CycleGameCmd;
import a.a.a.Commands.ForceNextCmd;
import a.a.a.Commands.ForceStartCmd;
import a.a.a.Commands.GameCoreCmd;
import a.a.a.Commands.HelpCmd;
import a.a.a.Commands.InvisibleCmd;
import a.a.a.Commands.JoinCmd;
import a.a.a.Commands.LobbyCmd;
import a.a.a.Commands.ParticlesCmd;
import a.a.a.Commands.ReloadGamesCmd;
import a.a.a.Commands.ReloadMapsCmd2;
import a.a.a.Commands.RestartNextCmd;
import a.a.a.Commands.RotationCmd;
import a.a.a.Commands.RulesCmd;
import a.a.a.Commands.SpectateCmd;
import a.a.a.Commands.SpyCmd;
import a.a.a.Commands.TeamChatCmd;
import a.a.a.Commands.ToggleCmd;
import a.a.a.Commands.TutorialCmd;
import a.a.a.d.b.TicketHandler1;
import a.a.a.e.a.BarHandler;
import a.a.a.e.b.DisguiseHandler3;
import a.a.a.e.c.HologramHandler;
import a.a.a.f.GameHandler;
import a.a.a.f.c.ScoreboardHandler;
import a.a.a.g.BlockListener;
import a.a.a.g.EntityListener;
import a.a.a.g.InventoryListener;
import a.a.a.g.PlayerListener;
import a.a.a.h.LobbyHandler;
import a.a.a.j.LogHandler;
import a.a.a.l.GameEconomy;
import a.a.a.l.GamePlayer;
import a.a.a.l.GamePlayerData;
import a.a.a.l.GamePlayerHandler;
import a.a.a.l.GamePlayerLoader;
import a.a.a.m.RankHandler;
import a.a.a.n.ServerHandler;
import a.a.a.p.TutorialHandler;
import a.a.a.q.Util3;
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
            Class.forName("a.a.a.k.ShutdownRunnable");
            Class.forName("a.a.a.q.Util1");
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
