package org.Kratous.GameCore.p;

import org.Kratous.GameCore.f.a.MapKit;
import java.util.List;
import org.bukkit.util.Vector;

public class TutorialStep {
   private String title;
   private List cI;
   private List iL;
   private Vector cJ;
   private float yaw;
   private float pitch;
   private String action;
   private String iM;
   private int iN;
   private MapKit cr;

   public TutorialStep(String title, List messages, List successMessages, Vector location, float yaw, float pitch, String action, int delay, MapKit kit) {
      this.title = title;
      this.cI = messages;
      this.iL = successMessages;
      this.cJ = location;
      this.yaw = yaw;
      this.pitch = pitch;
      String[] split = action.split(":");
      this.action = split[0];
      this.iM = split.length > 1 ? split[1] : "";
      this.iN = delay;
      this.cr = kit;
   }

   public String getTitle() {
      return this.title;
   }

   public List getMessages() {
      return this.cI;
   }

   public List fu() {
      return this.iL;
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

   public String getAction() {
      return this.action;
   }

   public String fv() {
      return this.iM;
   }

   public int fw() {
      return this.iN;
   }

   public MapKit bM() {
      return this.cr;
   }
}
