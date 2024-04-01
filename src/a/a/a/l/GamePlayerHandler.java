package a.a.a.l;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GamePlayerHandler {
   private ConcurrentHashMap gT = new ConcurrentHashMap();
   private GamePlayerRunnable gU = new GamePlayerRunnable();
   private Object gV = new Object();

   public void q(String name) {
      this.gT.remove(name.toLowerCase());
   }

   public void m(GamePlayer gPlayer) {
      String name = "";
      Iterator var3 = this.gT.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<String, GamePlayer> player = (Entry)var3.next();
         if (((GamePlayer)player.getValue()).equals(gPlayer)) {
            name = (String)player.getKey();
            break;
         }
      }

      this.gT.remove(name);
   }

   public GamePlayer a(Player player) {
      String name = player.getName().toLowerCase();
      if (!this.gT.containsKey(name)) {
         synchronized(this.gV) {
            if (this.gT.containsKey(name)) {
               return (GamePlayer)this.gT.get(name);
            } else {
               GamePlayerData gPlayerData = GamePlayerLoader.a(player.getUniqueId());
               if (gPlayerData == null) {
                  gPlayerData = new GamePlayerData(1, 0, 0);
               }

               GamePlayer gPlayer = new GamePlayer(player, gPlayerData);
               this.gT.put(name, gPlayer);
               return gPlayer;
            }
         }
      } else {
         return (GamePlayer)this.gT.get(name);
      }
   }

   public GamePlayer r(String name) {
      Player player = Bukkit.getPlayer(name);
      if (player == null) {
         Bukkit.getLogger().severe("Could not find the Bukkit Player '" + name + "'");
         return null;
      } else {
         return this.a(player);
      }
   }

   public Collection getPlayers() {
      return this.gT.values();
   }

   public void destroy() {
      this.gU.cancel();
      this.gT.clear();
   }
}
