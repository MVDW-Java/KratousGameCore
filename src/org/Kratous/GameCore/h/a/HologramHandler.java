package org.Kratous.GameCore.h.a;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.e.c.Hologram1;
import org.Kratous.GameCore.f.a.MapData;
import org.Kratous.GameCore.h.LobbyHandler;
import org.Kratous.GameCore.k.TaskChain11;
//import org.Kratous.GameCore.k.TaskChain12;
import org.Kratous.GameCore.k.TaskChain5;
import org.Kratous.GameCore.q.BlockUtil1;
import org.Kratous.GameCore.q.Util3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class HologramHandler {
   private LobbyHandler d;
   private Map eH;

   public HologramHandler(LobbyHandler handler) {
      this.d = handler;
      this.eH = new HashMap();
   }

   public void b(YamlConfiguration config) {
      Iterator var2 = config.getConfigurationSection("holograms").getKeys(false).iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         String[] locationString = config.getString("holograms." + key).replace(" ", "").split(",");
         Location location = new Location(this.d.cT().getWorld(), Double.valueOf(locationString[0]), Double.valueOf(locationString[1]), Double.valueOf(locationString[2]));
         this.eH.put(key, new LobbyHologram(location, key));
      }

   }

   public void update() {
      Collection<LobbyHologram> hologramsList = this.eH.values();
      Iterator var2 = hologramsList.iterator();

      while(true) {
         while(true) {
            LobbyHologram lobbyHologram;
            do {
               if (!var2.hasNext()) {
                  return;
               }

               lobbyHologram = (LobbyHologram)var2.next();
            } while(!lobbyHologram.dj());

            final Hologram1 hologram;
            Iterator var6;
            switch(lobbyHologram.di()) {
            case eR:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "Welcome to tutorial island");
                  hologram.d(ChatColor.GREEN + "Follow the bridges to learn about our gamemodes");
                  hologram.d(ChatColor.GOLD + "Proceed across the first bridge to continue");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eS:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "^");
                  hologram.d(ChatColor.GREEN + "This is a core");
                  hologram.d(ChatColor.GOLD + "Mine cores with Diamond Pickaxe to leak lava");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eT:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This island explains the DTC gamemode");
                  hologram.d(ChatColor.GREEN + "(Destroy the core)");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eU:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "When lava leaks out of all cores the map ends");
                  hologram.d(ChatColor.GREEN + "The team that broke the other teams cores wins");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eV:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This island explains the DTM gamemode");
                  hologram.d(ChatColor.GREEN + "(Destroy the monument)");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eY:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This is a bomb");
                  hologram.d(ChatColor.GREEN + "Hold blaze powder on this to ignite");
                  hologram.d(ChatColor.GREEN + "Keep holding until the bomb ignites");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eZ:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "When a bomb is lit you must use shears to defuse");
                  hologram.d(ChatColor.GREEN + "Keep holding until the bomb isn''t lit anymore");
                  hologram.d(ChatColor.GREEN + "The team whos bomb explodes looses");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fa:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This island explains the DTN gamemode");
                  hologram.d(ChatColor.GREEN + "(Destroy the nova)");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fb:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This island explains the CTW gamemode");
                  hologram.d(ChatColor.GREEN + "(Capture the wool)");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fc:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This is a nova");
                  hologram.d(ChatColor.GREEN + "Each nova has a set amount of hits needed to destroy");
                  hologram.d(ChatColor.GREEN + "Use a diamond pickaxe to hit the nova and destroy it");
                  hologram.d(ChatColor.GREEN + "Win by destroying all of the novas");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fd:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "This island explains the TDM gamemode");
                  hologram.d(ChatColor.GREEN + "(Team death match)");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fe:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "In this gamemode you must locate the wool rooms");
                  hologram.d(ChatColor.GREEN + "Take a wool from the chest and take it back to the wool monument");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case ff:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "Wool monuments look like this");
                  hologram.d(ChatColor.GREEN + "They are usually located in or near your spawn");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fg:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "In TDM there are multiple teams");
                  hologram.d(ChatColor.GREEN + "Kill the enemy as much as possible in the time limit");
                  hologram.d(ChatColor.GREEN + "Team with the most kills after at 0 seconds wins");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case fh:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + "Follow the path to learn how to play");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            case eM:
               lobbyHologram.p(60);
               if (!lobbyHologram.isAlive()) {
                  hologram = (new Hologram1(lobbyHologram.getLocation())).O();
                  lobbyHologram.b(hologram);
               } else {
                  hologram = (Hologram1)lobbyHologram.dh().get(0);
               }

               /*TaskChain12.dx().a((TaskChain8)(new TaskChain5() {
                  protected List run() {
                     return HologramHandler.this.df();
                  }
              })).a((TaskChain8)(new TaskChain11() {
                  protected void run(List topXP) {
                     hologram.setLine(0, Core.a("Top XP Overall"));
                     int i = 1;

                     for(Iterator var3 = topXP.iterator(); var3.hasNext(); ++i) {
                        String str = (String)var3.next();
                        hologram.setLine(i, str);
                     }

                  }

                  // $FF: synthetic method
                  protected void a(Object var1) {
                     this.run((List)var1);
                  }
               })).execute();*/
               break;
            case eL:
               lobbyHologram.p(60);
               if (!lobbyHologram.isAlive()) {
                  hologram = (new Hologram1(lobbyHologram.getLocation())).O();
                  lobbyHologram.b(hologram);
               } else {
                  hologram = (Hologram1)lobbyHologram.dh().get(0);
               }

               /*TaskChain12.dx().a((TaskChain8)(new TaskChain5() {
                  protected List run() {
                     return HologramHandler.this.de();
                  }
               })).a((TaskChain8)(new TaskChain11() {
                  protected void run(List topXP) {
                     hologram.setLine(0, Core.a("Top XP Weekly"));
                     int i = 1;

                     for(Iterator var3 = topXP.iterator(); var3.hasNext(); ++i) {
                        String str = (String)var3.next();
                        hologram.setLine(i, str);
                     }

                  }

                  // $FF: synthetic method
                  protected void a(Object var1) {
                     this.run((List)var1);
                  }
               })).execute();*/
               break;
            case eO:
               if (!Core.b().ag()) {
                  if (lobbyHologram.isAlive()) {
                     lobbyHologram.dk();
                  }
                  break;
               }

               List<MapData> votableMaps = Core.b().ah();
               int votes;
               if (!lobbyHologram.isAlive()) {
                  int zPointer = -(votableMaps.size() + 1);

                  for(var6 = votableMaps.iterator(); var6.hasNext(); zPointer += 3) {
                     MapData data = (MapData)var6.next();
                     Location spawnLocation = (new Location(lobbyHologram.getLocation().getWorld(), lobbyHologram.getLocation().getX(), lobbyHologram.getLocation().getY(), lobbyHologram.getLocation().getZ() + (double)zPointer)).add(0.5D, 0.0D, 0.5D);
                     votes = Core.b().f(data.bk());
                     Hologram1 hologram1 = (new Hologram1(spawnLocation)).O();
                     hologram1.d(ChatColor.GOLD + data.bk());
                     hologram1.d(ChatColor.DARK_AQUA + Util3.u(data.bj()));
                     hologram1.d(Core.b().f(data.bk()) + " Vote" + (votes == 1 ? "" : "s"));
                     lobbyHologram.b(hologram1);
                     BlockUtil1.a(hologram1.getLocation(), Material.TRIPWIRE, 0, true);
                  }
               } else {
                  Map<Hologram1, Integer> order = new HashMap();
                  int i = 0;
                  Iterator var16 = lobbyHologram.dh().iterator();

                  while(var16.hasNext()) {
                     Hologram1 hologram2 = (Hologram1)var16.next();
                     votes = Core.b().f(((String)hologram2.N().get(0)).replace(ChatColor.GREEN.toString(), "").replace(ChatColor.GOLD.toString(), ""));
                     hologram2.setLine(2, votes + " Vote" + (votes == 1 ? "" : "s"));
                     order.put(hologram2, votes);
                     BlockUtil1.a(hologram2.getLocation(), Material.TRIPWIRE, 0, true);
                  }

                  int votes1 = (Integer)((Entry)Util3.b((Map)order).get(0)).getValue();
                  Iterator var19 = order.keySet().iterator();

                  label168:
                  while(true) {
                     while(true) {
                        if (!var19.hasNext()) {
                           break label168;
                        }

                        Hologram1 hologram2 = (Hologram1)var19.next();
                        String name = ((String)hologram2.N().get(0)).replace(ChatColor.GREEN.toString(), "").replace(ChatColor.GOLD.toString(), "");
                        if (((Integer)order.get(hologram2)).equals(votes1) && (Integer)order.get(hologram2) > 0) {
                           hologram2.setLine(0, ChatColor.GREEN + name);
                        } else {
                           hologram2.setLine(0, ChatColor.GOLD + name);
                        }
                     }
                  }
               }

               lobbyHologram.p(40);
               break;
            case eP:
               if (!Core.b().ag()) {
                  if (lobbyHologram.isAlive()) {
                     lobbyHologram.dk();
                  }
               } else {
                  if (!lobbyHologram.isAlive()) {
                     hologram = new Hologram1(lobbyHologram.getLocation());
                     lobbyHologram.b(hologram);
                     hologram.d(ChatColor.GRAY + "Click a map to vote for it");
                     hologram.O();
                  }

                  lobbyHologram.p(40);
               }
               break;
            case eN:
               if (lobbyHologram.isAlive()) {
                  lobbyHologram.dk();
               }

               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.setLine(0, Core.a("Match Info"));
                  hologram.d(ChatColor.GOLD + "Current Map:");
                  hologram.d(ChatColor.GREEN + Core.b().V().aS().bk());
                  hologram.d(ChatColor.GOLD + "Built By:");
                  String authors = "";

                  String author;
                  for(var6 = Core.b().V().aS().bo().iterator(); var6.hasNext(); authors = authors + ", " + author) {
                     author = (String)var6.next();
                  }

                  authors = authors.substring(2, authors.length());
                  hologram.d(ChatColor.GREEN + authors);
                  hologram.d(ChatColor.GOLD + "In Game:");
                  hologram.d(ChatColor.GREEN + "" + Core.b().V().aT().getPlayers().size() + " players");
                  hologram.O();
               }

               lobbyHologram.p(40);
               break;
            default:
               if (!lobbyHologram.isAlive()) {
                  hologram = new Hologram1(lobbyHologram.getLocation());
                  lobbyHologram.b(hologram);
                  hologram.d(ChatColor.GREEN + Core.a().getConfig().getString("game.serverName") + " - Default Hologram");
                  hologram.O();
               }

               lobbyHologram.p(40);
            }
         }
      }
   }

   public void a(HologramType type) {
      Iterator var2 = this.eH.values().iterator();

      while(true) {
         LobbyHologram hologram;
         do {
            if (!var2.hasNext()) {
               return;
            }

            hologram = (LobbyHologram)var2.next();
         } while(!hologram.di().equals(type));

         Iterator var4 = hologram.dh().iterator();

         while(var4.hasNext()) {
            Hologram1 holo = (Hologram1)var4.next();
            holo.getLocation().getBlock().setType(Material.AIR);
         }

         hologram.p(-1);
      }
   }

   private List de() {
      List<String> strings = new ArrayList();
      return strings;
   }

   private List df() {
      List<String> strings = new ArrayList();
      return strings;
   }

   public Collection dg() {
      List<LobbyHologram> holograms = new ArrayList(this.eH.values());
      return holograms;
   }
}
