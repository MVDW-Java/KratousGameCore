package a.a.a.g.a;

import a.a.a.l.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSpawnEvent extends Event {
   private GamePlayer er;
   private static final HandlerList eq = new HandlerList();

   public PlayerSpawnEvent(GamePlayer gPlayer) {
      this.er = gPlayer;
   }

   public GamePlayer cS() {
      return this.er;
   }

   public HandlerList getHandlers() {
      return eq;
   }

   public static HandlerList getHandlerList() {
      return eq;
   }
}
