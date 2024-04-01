package org.Kratous.GameCore.f.a;

import org.Kratous.GameCore.Shape.Shape;
import org.bukkit.util.Vector;

public class MapSpawn {
   private float pitch;
   private float yaw;
   private Shape cD;
   private MapKit cr;

   public MapSpawn(Shape shape, float pitch, float yaw, MapKit kit) {
      this.cD = shape;
      this.pitch = pitch;
      this.yaw = yaw;
      this.cr = kit;
   }

   public float getPitch() {
      return this.pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public MapKit bM() {
      return this.cr;
   }

   public boolean bN() {
      return this.cr != null;
   }

   public Vector bO() {
      return this.cD.bO();
   }
}
