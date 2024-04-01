package org.Kratous.GameCore.j;

public class ObjectiveLog {
   private int fE;
   private ObjectiveType fP;
   private String fQ;
   private int value;

   public ObjectiveLog(int userId, ObjectiveType type, String objectiveName, int value) {
      this.fE = userId;
      this.fP = type;
      this.fQ = objectiveName;
      this.value = value;
   }

   public int dr() {
      return this.fE;
   }

   public ObjectiveType dt() {
      return this.fP;
   }

   public String du() {
      return this.fQ;
   }

   public int getValue() {
      return this.value;
   }
}
