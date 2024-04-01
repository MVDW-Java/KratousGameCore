package a.a.a.f.b;

import a.a.a.Core;
import a.a.a.f.a.MapTeam;
import a.a.a.l.GamePlayer;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class CapturablePoint {
   private String dj;
   private List dk;
   private MapTeam dl;
   private int dm;

   public CapturablePoint(String name, List locations, MapTeam team) {
      this.dj = name;
      this.dk = locations;
      this.dl = team;
      this.dm = 100;
   }

   public String getName() {
      return this.dj;
   }

   public MapTeam ca() {
      return this.dl;
   }

   public int cb() {
      return this.dm;
   }

   public boolean q(GamePlayer gPlayer) {
      if (gPlayer.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.OBSIDIAN)) {
         Iterator var2 = this.dk.iterator();

         while(var2.hasNext()) {
            Vector vector = (Vector)var2.next();
            if (gPlayer.getPlayer().getLocation().getBlockX() == vector.getBlockX() && Math.abs(gPlayer.getPlayer().getLocation().getBlockY() - vector.getBlockY()) < 3 && gPlayer.getPlayer().getLocation().getBlockZ() == vector.getBlockZ()) {
               return true;
            }
         }
      }

      return false;
   }

   public void cc() {
      this.dm -= 5;
      if (this.dm < 0) {
         Iterator var1 = this.dk.iterator();

         while(var1.hasNext()) {
            Vector v = (Vector)var1.next();
            Block block = Core.b().V().getWorld().getBlockAt(v.toLocation(Core.b().V().getWorld()));
            if (block.getType().equals(Material.OBSIDIAN)) {
               block.setType(Material.BEDROCK);
            }
         }

         this.dm = 0;
      }

   }
}
