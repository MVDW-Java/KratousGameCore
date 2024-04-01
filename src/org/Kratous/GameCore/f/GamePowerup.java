package org.Kratous.GameCore.f;

import org.Kratous.GameCore.Core;
import org.Kratous.GameCore.l.GamePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class GamePowerup {
   public abstract String aK();

   public abstract Material getMaterial();

   public abstract void o(GamePlayer var1);

   public void p(GamePlayer gPlayer) {
      Core.a(ChatColor.GOLD + gPlayer.getPlayer().getName() + ChatColor.DARK_AQUA + " just got a " + ChatColor.GOLD + this.aK(), true, true);
   }
}
