package a.a.a.h.a;

import a.a.a.e.c.Hologram1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LobbyHologram {
   private List aR = new ArrayList();
   private Location aE;
   private HologramType fj;
   private int bq;

   public LobbyHologram(Location location, String type) {
      this.aE = location;
      this.bq = 0;
      byte var4 = -1;
      switch(type.hashCode()) {
      case -2004814468:
         if (type.equals("woolmonument")) {
            var4 = 20;
         }
         break;
      case -1630763749:
         if (type.equals("mapvotesinfo")) {
            var4 = 4;
         }
         break;
      case -1584153734:
         if (type.equals("corebroke")) {
            var4 = 9;
         }
         break;
      case -1452980912:
         if (type.equals("dtbwelcome")) {
            var4 = 12;
         }
         break;
      case -1268958287:
         if (type.equals("follow")) {
            var4 = 22;
         }
         break;
      case -870782237:
         if (type.equals("corewelcome")) {
            var4 = 8;
         }
         break;
      case -765572091:
         if (type.equals("overallxp")) {
            var4 = 1;
         }
         break;
      case -704927612:
         if (type.equals("kitinfo")) {
            var4 = 5;
         }
         break;
      case -621710535:
         if (type.equals("weeklyxp")) {
            var4 = 0;
         }
         break;
      case -317934649:
         if (type.equals("monument")) {
            var4 = 11;
         }
         break;
      case -114278268:
         if (type.equals("bombdefuse")) {
            var4 = 14;
         }
         break;
      case -49224432:
         if (type.equals("woolroom")) {
            var4 = 19;
         }
         break;
      case -2962802:
         if (type.equals("welcomenova")) {
            var4 = 15;
         }
         break;
      case 114685:
         if (type.equals("tdm")) {
            var4 = 21;
         }
         break;
      case 3059615:
         if (type.equals("core")) {
            var4 = 7;
         }
         break;
      case 3387436:
         if (type.equals("nova")) {
            var4 = 17;
         }
         break;
      case 30941356:
         if (type.equals("bombignite")) {
            var4 = 13;
         }
         break;
      case 200171245:
         if (type.equals("mapvotes")) {
            var4 = 3;
         }
         break;
      case 415536004:
         if (type.equals("welcomectw")) {
            var4 = 16;
         }
         break;
      case 415551835:
         if (type.equals("welcometdm")) {
            var4 = 18;
         }
         break;
      case 538063589:
         if (type.equals("dtmwelcome")) {
            var4 = 10;
         }
         break;
      case 614650867:
         if (type.equals("matchinfo")) {
            var4 = 2;
         }
         break;
      case 1525850600:
         if (type.equals("tutisland")) {
            var4 = 6;
         }
      }

      switch(var4) {
      case 0:
         this.fj = HologramType.eL;
         break;
      case 1:
         this.fj = HologramType.eM;
         break;
      case 2:
         this.fj = HologramType.eN;
         break;
      case 3:
         this.fj = HologramType.eO;
         break;
      case 4:
         this.fj = HologramType.eP;
         break;
      case 5:
         this.fj = HologramType.eQ;
         break;
      case 6:
         this.fj = HologramType.eR;
         break;
      case 7:
         this.fj = HologramType.eS;
         break;
      case 8:
         this.fj = HologramType.eT;
         break;
      case 9:
         this.fj = HologramType.eU;
         break;
      case 10:
         this.fj = HologramType.eV;
         break;
      case 11:
         this.fj = HologramType.eW;
         break;
      case 12:
         this.fj = HologramType.eX;
         break;
      case 13:
         this.fj = HologramType.eY;
         break;
      case 14:
         this.fj = HologramType.eZ;
         break;
      case 15:
         this.fj = HologramType.fa;
         break;
      case 16:
         this.fj = HologramType.fb;
         break;
      case 17:
         this.fj = HologramType.fc;
         break;
      case 18:
         this.fj = HologramType.fd;
         break;
      case 19:
         this.fj = HologramType.fe;
         break;
      case 20:
         this.fj = HologramType.ff;
         break;
      case 21:
         this.fj = HologramType.fg;
         break;
      case 22:
         this.fj = HologramType.fh;
         break;
      default:
         Bukkit.getLogger().severe("Unknown hologram type " + type);
      }

   }

   public boolean isAlive() {
      return !this.aR.isEmpty();
   }

   public void b(Hologram1 hologram) {
      this.aR.add(hologram);
   }

   public void c(Hologram1 hologram) {
      this.aR.remove(hologram);
   }

   public List dh() {
      return new ArrayList(this.aR);
   }

   public Location getLocation() {
      return this.aE;
   }

   public HologramType di() {
      return this.fj;
   }

   public boolean dj() {
      return this.bq-- < 0;
   }

   public void p(int counter) {
      this.bq = counter;
   }

   public void dk() {
      Iterator var1 = this.aR.iterator();

      while(var1.hasNext()) {
         Hologram1 hologram = (Hologram1)var1.next();
         hologram.remove();
      }

      this.aR.clear();
   }
}
