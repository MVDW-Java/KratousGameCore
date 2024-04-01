package org.Kratous.GameCore.f.a;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;

public class MapTeam {
   private String cE;
   private int maxPlayers;
   private String cF;
   private ChatColor cG;
   private List cH;

   public MapTeam(String teamName, ChatColor teamColor, String namePrefix, int maxPlayers, List spawns) {
      this.cE = teamName;
      this.cG = teamColor;
      this.cF = namePrefix;
      this.maxPlayers = maxPlayers;
      this.cH = spawns;
   }

   public String getName() {
      return this.cE;
   }

   public ChatColor bP() {
      return this.cG;
   }

   public String bQ() {
      return this.cF;
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public MapSpawn bR() {
      List<MapSpawn> spawnsCopy = new ArrayList(this.cH);
      Collections.shuffle(spawnsCopy);
      return (MapSpawn)spawnsCopy.get(0);
   }
}
