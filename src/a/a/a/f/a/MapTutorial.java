package a.a.a.f.a;

import java.util.List;
import org.bukkit.util.Vector;

public class MapTutorial {
   private String title;
   private List cI;
   private Vector cJ;
   private float yaw;
   private float pitch;

   public MapTutorial(String title, List messages, double x, double y, double z, float yaw, float pitch) {
      this.title = title;
      this.cI = messages;
      this.cJ = new Vector(x, y, z);
      this.yaw = yaw;
      this.pitch = pitch;
   }

   public String getTitle() {
      return this.title;
   }

   public List getMessages() {
      return this.cI;
   }

   public Vector bS() {
      return this.cJ;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }
}
