package a.a.a.g.a;

import a.a.a.l.GamePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKilledEvent extends Event {
   private static final HandlerList eq = new HandlerList();
   private GamePlayer er;
   private GamePlayer es;
   private PlayerDeathEvent et;

   public PlayerKilledEvent(GamePlayer gPlayer, GamePlayer killer, PlayerDeathEvent event) {
      this.er = gPlayer;
      this.es = killer;
      this.et = event;
   }

   public GamePlayer cP() {
      return this.er;
   }

   public GamePlayer cQ() {
      return this.es;
   }

   public PlayerDeathEvent cR() {
      return this.et;
   }

   public HandlerList getHandlers() {
      return eq;
   }

   public static HandlerList getHandlerList() {
      return eq;
   }
}
