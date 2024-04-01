package org.Kratous.GameCore.a;

import java.util.HashMap;
import java.util.Map;

public class Achievement {
   private int id;
   private String name;
   private AchievementType u;
   private Map v;

   public Achievement(int id, String name, String type) {
      this.id = id;
      this.name = name;
      this.u = AchievementType.valueOf(type.toUpperCase());
      this.v = new HashMap();
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public AchievementType q() {
      return this.u;
   }

   public Map r() {
      return this.v;
   }

   public void a(AchievementLevel level) {
      this.v.put(level.getId(), level);
   }

   public AchievementLevel a(int level) {
      return this.v.containsKey(level) ? (AchievementLevel)this.v.get(level) : null;
   }
}
