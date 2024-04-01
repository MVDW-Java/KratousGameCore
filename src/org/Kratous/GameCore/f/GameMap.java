package org.Kratous.GameCore.f;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.f.a.MapRegion;
import org.Kratous.GameCore.f.a.MapResource;
import org.Kratous.GameCore.f.a.MapRewardType;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.f.a.MapWall;
import org.Kratous.GameCore.f.a.b.RegionRule;
import org.Kratous.GameCore.l.GamePlayer;
import org.Kratous.GameCore.l.GamePlayerState;
import org.Kratous.GameCore.o.Shape;
import org.Kratous.GameCore.q.BlockUtil1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public abstract class GameMap implements Listener {
   protected Map bj;
   protected List players;
   protected Map bk;
   protected GameWorld bl;
   private GamePlayer bm;
   private GamePlayer bn;
   private List bo;

   public void a(GameWorld gameWorld) {
      this.bj = new HashMap();
      this.players = new ArrayList();
      this.bk = new HashMap();
      this.bl = gameWorld;
      this.bo = new ArrayList();
      Iterator var2 = this.bl.aS().bv().values().iterator();

      while(var2.hasNext()) {
         MapTeam team = (MapTeam)var2.next();
         this.bj.put(team, new ArrayList());
      }

   }

   public void l(GamePlayer gPlayer) {
      if (gPlayer.isInvisible()) {
         gPlayer.getPlayer().sendMessage(ChatColor.RED + "You cannot join a game whilst invisible");
      } else if (!this.aH()) {
         gPlayer.getPlayer().sendMessage(ChatColor.RED + "You cannot join at this time");
      } else if (this.players.contains(gPlayer)) {
         gPlayer.getPlayer().sendMessage(ChatColor.RED + "You have already joined the game");
         if (!this.bl.aU().equals(GameState.bz) && !this.bl.aU().equals(GameState.bD)) {
            gPlayer.dN();
         } else {
            gPlayer.dO();
         }

      } else {
         MapTeam smallestTeam = (MapTeam)this.bj.keySet().toArray()[0];
         if (this.bj.size() > 1) {
            Iterator var3 = this.bj.keySet().iterator();

            while(var3.hasNext()) {
               MapTeam mapTeam = (MapTeam)var3.next();
               if (!mapTeam.equals(smallestTeam) && ((List)this.bj.get(smallestTeam)).size() > ((List)this.bj.get(mapTeam)).size()) {
                  smallestTeam = mapTeam;
               }
            }
         }

         this.a(gPlayer, smallestTeam);
      }
   }

   public void c(GamePlayer gPlayer, String teamName) {
      MapTeam team = null;
      Iterator var4 = this.bl.aS().bv().values().iterator();

      while(var4.hasNext()) {
         MapTeam mapTeam = (MapTeam)var4.next();
         if (mapTeam.getName().equalsIgnoreCase(teamName)) {
            team = mapTeam;
         }
      }

      if (team == null) {
         gPlayer.getPlayer().sendMessage(ChatColor.RED + "Oops! We could not find this team.");
      } else {
         this.a(gPlayer, team);
      }
   }

   public void a(GamePlayer gPlayer, MapTeam team) {
      if (this.b(gPlayer, team)) {
         if (team.getMaxPlayers() <= ((List)this.bj.get(team)).size() && !gPlayer.getPlayer().hasPermission("gamecore.joinfullgame")) {
            TextComponent start = new TextComponent("This game is full. ");
            start.setColor(ChatColor.GOLD);
            TextComponent scatter = new TextComponent("Click here ");
            scatter.setColor(ChatColor.GREEN);
            scatter.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/scatter PVP autojoin"));
            TextComponent scatter2 = new TextComponent("to join another game or ");
            scatter2.setColor(ChatColor.GOLD);
            TextComponent vip = new TextComponent("Click here ");
            vip.setColor(ChatColor.GREEN);
            vip.setClickEvent(new ClickEvent(Action.OPEN_URL, "http://store." + Core.a().getConfig().getString("game.websiteUrl")));
            TextComponent vip2 = new TextComponent("to purchase VIP and join full teams");
            vip2.setColor(ChatColor.GOLD);
            TextComponent chatWholeMessage = new TextComponent("");
            chatWholeMessage.addExtra(start);
            chatWholeMessage.addExtra(scatter);
            chatWholeMessage.addExtra(scatter2);
            chatWholeMessage.addExtra(vip);
            chatWholeMessage.addExtra(vip2);
            gPlayer.getPlayer().spigot().sendMessage(chatWholeMessage);
         } else {
            this.players.add(gPlayer);
            ((List)this.bj.get(team)).add(gPlayer);
            Core.d().cV().a(gPlayer, team.bP());
            gPlayer.dE().eM();
            gPlayer.g(team);
            gPlayer.a(GamePlayerState.hb);
            gPlayer.dP();
            gPlayer.dW();
            gPlayer.g(false);
            if (gPlayer.getPlayer().isInsideVehicle()) {
               gPlayer.getPlayer().leaveVehicle();
            }

            if (gPlayer.getPlayer().getPassenger() != null) {
               gPlayer.getPlayer().getPassenger().leaveVehicle();
            }

            if (this.bl.aS().bn().bd() && gPlayer.dK() == null) {
               gPlayer.b(this.bl.aS().bu());
            }

            if (this.bj.size() > 1) {
               gPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You have just joined the " + team.bP() + team.getName());
            } else {
               gPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You have just joined the game!");
            }

            if (this.bl.aU().equals(GameState.bD)) {
               gPlayer.dO();
            } else {
               gPlayer.dN();
               gPlayer.n(ChatColor.GOLD + "[AIM] " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + Core.b().V().aS().bm());
               if (gPlayer.ec() > 0) {
                  gPlayer.s(-gPlayer.ec());
                  gPlayer.ed();
               }
            }

            Core.e().d(gPlayer, team.getName());
            Core.e().v(gPlayer);
         }
      }
   }

   public void m(GamePlayer gPlayer) {
      gPlayer.a(this.bl.aU().equals(GameState.bz) ? GamePlayerState.gY : GamePlayerState.ha);
      if (gPlayer.ef()) {
         gPlayer.f(false);
      }

      if (this.bj.containsKey(gPlayer.ca())) {
         ((List)this.bj.get(gPlayer.ca())).remove(gPlayer);
      }

      this.players.remove(gPlayer);
      gPlayer.g((MapTeam)null);
      Core.e().t(gPlayer);
      Core.d().cV().t(gPlayer);
      Core.l().h(gPlayer);
      gPlayer.dP();
      if (!Core.b().V().aU().equals(GameState.bz) && !Core.b().V().aU().equals(GameState.bD)) {
         gPlayer.dN();
         gPlayer.ep();
      } else {
         gPlayer.dO();
      }

   }

   public boolean b(GamePlayer gPlayer, MapTeam team) {
      int maxDifference = (int)Math.ceil((double)this.players.size() * 0.2D) + (gPlayer.ca() != null ? 0 : 1);
      Iterator var4 = this.bl.aS().bv().values().iterator();

      while(var4.hasNext()) {
         MapTeam outerTeam = (MapTeam)var4.next();
         boolean isOnOuter = gPlayer.ca() != null && gPlayer.ca().equals(outerTeam);
         Iterator var7 = this.bl.aS().bv().values().iterator();

         while(var7.hasNext()) {
            MapTeam innerTeam = (MapTeam)var7.next();
            boolean isOnInner = gPlayer.ca() != null && gPlayer.ca().equals(innerTeam);
            if (((List)this.bj.get(outerTeam)).isEmpty() && !outerTeam.equals(team) && !((List)this.bj.get(team)).isEmpty()) {
               gPlayer.getPlayer().sendMessage(ChatColor.RED + "Fill up the empty teams!");
               return false;
            }

            int outerSize = isOnOuter ? ((List)this.bj.get(outerTeam)).size() - 1 : ((List)this.bj.get(outerTeam)).size();
            int innerSize = isOnInner ? ((List)this.bj.get(innerTeam)).size() - 1 : ((List)this.bj.get(innerTeam)).size();
            if (outerSize - innerSize >= maxDifference && outerTeam.equals(team)) {
               gPlayer.getPlayer().sendMessage(ChatColor.RED + "Teams are too unbalanced to join " + team.getName() + "!");
               return false;
            }
         }
      }

      return true;
   }

   public void aq() {
      HandlerList.unregisterAll(this);
   }

   public void ar() {
      Iterator var1 = this.bj.keySet().iterator();

      while(var1.hasNext()) {
         MapTeam team = (MapTeam)var1.next();
         Core.e().a(team.getName(), team.bP() + team.bQ(), ChatColor.RESET.toString());
      }

      var1 = Core.c().getPlayers().iterator();

      while(var1.hasNext()) {
         GamePlayer gPlayer = (GamePlayer)var1.next();
         if (gPlayer.dH() && gPlayer.ca() != null) {
            Core.e().d(gPlayer, gPlayer.ca().getName());
         }
      }

      Core.e().cn();
   }

   public void a(GamePlayer gPlayer, MapRewardType type) {
      int goldValue = this.bl.aS().b(type);
      int xpValue = this.bl.aS().a(type);
      if (goldValue > 0) {
         gPlayer.dD().y(goldValue);
      }

      if (xpValue > 0) {
         gPlayer.dD().x(xpValue);
      }

   }

   public void n(GamePlayer gPlayer) {
      if (this.bm == null) {
         this.bm = gPlayer;
      }

      this.bn = gPlayer;
   }

   public void as() {
      Iterator var1 = this.bl.aS().bs().iterator();

      while(var1.hasNext()) {
         MapResource resource = (MapResource)var1.next();
         resource.clear();
         Iterator var3 = resource.bK().fd().iterator();

         while(var3.hasNext()) {
            Vector vector = (Vector)var3.next();
            Block block = this.bl.getWorld().getBlockAt(vector.toLocation(this.bl.getWorld()));
            if (block.getType().equals(resource.getMaterial())) {
               resource.b(block);
            }
         }
      }

   }

   public void at() {
      Iterator var1 = this.bl.aS().bs().iterator();

      label39:
      while(var1.hasNext()) {
         MapResource resource = (MapResource)var1.next();
         Iterator var3 = resource.getBlocks().iterator();

         while(true) {
            Block block;
            do {
               if (!var3.hasNext()) {
                  continue label39;
               }

               block = (Block)var3.next();
            } while(block.getType().equals(resource.getMaterial()));

            Iterator var5 = Core.c().getPlayers().iterator();

            while(var5.hasNext()) {
               GamePlayer gPlayer = (GamePlayer)var5.next();
               if (!gPlayer.dS() && gPlayer.getPlayer().getLocation().distance(block.getLocation()) < 2.0D) {
                  gPlayer.getPlayer().teleport(resource.bL().toLocation(this.bl.getWorld()));
               }
            }

            BlockUtil1.a(block.getLocation(), resource.getMaterial(), 0, true);
         }
      }

      Core.a("Resources have been regenerated", true, true);
   }

   public GamePlayer au() {
      return this.bm;
   }

   public GamePlayer av() {
      return this.bn;
   }

   public List getPlayers() {
      return this.players;
   }

   public int a(MapTeam team) {
      return ((List)this.bj.get(team)).size();
   }

   public void aw() {
      List<Class<GamePowerup>> powerupClasses = this.aF();
      if (!powerupClasses.isEmpty()) {
         Random random = new Random(System.nanoTime());
         Class randomPowerupClass = (Class)powerupClasses.get(random.nextInt(powerupClasses.size() - 1));

         try {
            GamePowerup randomPowerup = (GamePowerup)randomPowerupClass.newInstance();
            Location spawnLocation = this.aG();
            if (spawnLocation == null) {
               return;
            }

            Block spawnBlock = spawnLocation.getBlock();
            spawnBlock.setType(randomPowerup.getMaterial());
            this.bk.put(spawnBlock, randomPowerup);
            Core.a("A powerup has spawned!", true, true);
         } catch (Exception var7) {
            var7.printStackTrace();
         }

      }
   }

   public GamePowerup a(Block block) {
      return (GamePowerup)this.bk.get(block);
   }

   public void a(GamePowerup powerup) {
      Block block = null;
      Iterator var3 = this.bk.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<Block, GamePowerup> entry = (Entry)var3.next();
         if (((GamePowerup)entry.getValue()).equals(powerup)) {
            block = (Block)entry.getKey();
            break;
         }
      }

      if (block != null) {
         block.setType(Material.AIR);
         this.bk.remove(block);
      }
   }

   public void ax() throws Exception {
   }

   public void ay() throws Exception {
      this.aA();
   }

   public void az() {
   }

   public void aA() throws Exception {
      List<MapWall> list = this.bl.aS().bx();
      if (list != null) {
         Iterator var2 = list.iterator();

         while(var2.hasNext()) {
            MapWall wall = (MapWall)var2.next();
            wall.b(true);
            this.bo.add(wall);
            MapRegion wallRegion = new MapRegion(wall.getName());
            List<MapTeam> teams = new ArrayList();
            String regionMsg = ChatColor.DARK_RED + "Stay clear from the wall! Wait until it has fallen.";
            Material material = Material.matchMaterial("");
            RegionRule regionRule = new RegionRule("destroy", "deny", teams, regionMsg, material);
            RegionRule regionRuleE = new RegionRule("enter", "deny", teams, regionMsg, material);
            RegionRule regionRuleD = new RegionRule("build", "deny", teams, regionMsg, material);
            wallRegion.a(regionRule);
            wallRegion.a(regionRuleE);
            wallRegion.a(regionRuleD);
            wallRegion.a(wall.bW());
            this.bl.aS().a(wallRegion);
         }

      }
   }

   public void aB() {
      Iterator var1 = this.bo.iterator();

      while(true) {
         while(true) {
            MapWall wall;
            do {
               if (!var1.hasNext()) {
                  return;
               }

               wall = (MapWall)var1.next();
            } while(!wall.isAlive());

            if (Core.b().X().aP() > (double)wall.bU()) {
               Shape shape = wall.bV();
               Iterator var4 = shape.fd().iterator();

               while(var4.hasNext()) {
                  Vector v = (Vector)var4.next();
                  Block block = this.bl.getWorld().getBlockAt(v.toLocation(this.bl.getWorld()));
                  block.setType(Material.AIR);
               }

               var4 = this.bl.aS().bq().iterator();

               while(var4.hasNext()) {
                  MapRegion region = (MapRegion)var4.next();
                  if (region.getName() == wall.getName()) {
                     region.bC();
                  }

                  if (region.getName().equals(wall.getKey() + "-removeable")) {
                     region.bC();
                  }
               }

               wall.b(false);
               var4 = Core.c().getPlayers().iterator();

               while(var4.hasNext()) {
                  GamePlayer gPlayer = (GamePlayer)var4.next();
                  if (gPlayer.dH()) {
                     gPlayer.getPlayer().sendMessage(Core.a(ChatColor.GOLD + wall.getName() + " has fallen!"));
                  }
               }
            } else {
               double timeLeft = (double)wall.bU() - Core.b().X().aP();
               int sec = (int)Math.round(timeLeft);
               if ((timeLeft == 120.0D || timeLeft == 60.0D || timeLeft == 30.0D || timeLeft == 15.0D || timeLeft == 10.0D || timeLeft <= 5.0D) && wall.bT() != sec) {
                  wall.i(sec);
                  Iterator var6 = Core.c().getPlayers().iterator();

                  while(var6.hasNext()) {
                     GamePlayer gPlayer = (GamePlayer)var6.next();
                     if (gPlayer.dH()) {
                        gPlayer.getPlayer().sendMessage(Core.a(ChatColor.DARK_GREEN + wall.getName() + " will fall in " + sec + " seconds!"));
                     }
                  }
               }
            }
         }
      }
   }

   public Map aC() {
      return this.bj;
   }

   public List aD() {
      return new ArrayList();
   }

   public MapTeam aE() {
      return null;
   }

   protected List aF() {
      return new ArrayList();
   }

   protected Location aG() {
      return null;
   }

   public abstract boolean aH();

   public abstract String aI();

   public abstract void aJ();
}
