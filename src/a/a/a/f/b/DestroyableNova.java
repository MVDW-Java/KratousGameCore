package a.a.a.f.b;

import a.a.a.f.a.MapTeam;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class DestroyableNova {
   private String name;
   private Entity entity;
   private Location aE;
   private int dQ;
   private MapTeam do;

   public DestroyableNova(String name, Location location, MapTeam team, int hitsRequired) {
      this.name = name;
      this.aE = location;
      this.do = team;
      this.dQ = hitsRequired;
   }

   public String getName() {
      return this.name;
   }

   public int cs() {
      return this.dQ;
   }

   public Location getLocation() {
      return this.aE;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public MapTeam ca() {
      return this.do;
   }

   public void a(Entity entity) {
      this.entity = entity;
   }

   public boolean isAlive() {
      return this.dQ > 0;
   }

   public boolean ct() {
      return this.dQ == 1 || this.dQ % 20 == 0;
   }

   public void cu() {
      --this.dQ;
      if (this.dQ < 0) {
         this.dQ = 0;
      }

   }
}
