package a.a.a.g.a;

import a.a.a.l.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InventoryReloadedEvent extends Event {
   private static final HandlerList eq = new HandlerList();
   private GamePlayer er;

   public InventoryReloadedEvent(GamePlayer gPlayer) {
      this.er = gPlayer;
   }

   public GamePlayer cP() {
      return this.er;
   }

   public HandlerList getHandlers() {
      return eq;
   }

   public static HandlerList getHandlerList() {
      return eq;
   }
}
