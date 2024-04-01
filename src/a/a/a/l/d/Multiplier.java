package a.a.a.l.d;

public class Multiplier {
   private double value;
   private MultiplierType il;
   private double im;

   public Multiplier(double value, String type, double expiryTimestamp) {
      this.value = value;
      String var6 = type.toUpperCase();
      byte var7 = -1;
      switch(var6.hashCode()) {
      case 2808:
         if (var6.equals("XP")) {
            var7 = 0;
         }
         break;
      case 2193504:
         if (var6.equals("GOLD")) {
            var7 = 1;
         }
      }

      switch(var7) {
      case 0:
         this.il = MultiplierType.io;
         break;
      case 1:
         this.il = MultiplierType.in;
      }

      this.im = expiryTimestamp;
   }

   public double getValue() {
      return this.value;
   }

   public MultiplierType eT() {
      return this.il;
   }

   public double eU() {
      return this.im;
   }

   public boolean hasExpired() {
      return this.im < (double)(System.currentTimeMillis() / 1000L);
   }
}
