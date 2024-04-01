package a.a.a.j;

public class KillLog {
   private int fC;
   private int fD;
   private String method;

   public KillLog(int killerId, int killedId, String method) {
      this.fC = killerId;
      this.fD = killedId;
      this.method = method;
   }

   public int dp() {
      return this.fC;
   }

   public int dq() {
      return this.fD;
   }

   public String getMethod() {
      return this.method;
   }
}
