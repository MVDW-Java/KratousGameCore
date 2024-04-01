package a.a.a.f.a.b;

import a.a.a.f.a.MapTeam;
import a.a.a.f.a.a.MapDataException;
import java.util.List;
import org.bukkit.Material;

public class RegionRule {
   private RegionAction cZ;
   private RegionAccess da;
   private List db;
   private String message;
   private Material material;

   public RegionRule(String actionType, String accessType, List teams, String message, Material material) throws Exception {
      String var6 = actionType.toLowerCase();
      byte var7 = -1;
      switch(var6.hashCode()) {
      case -1360201941:
         if (var6.equals("teleport")) {
            var7 = 7;
         }
         break;
      case -1309148959:
         if (var6.equals("explode")) {
            var7 = 6;
         }
         break;
      case 3146030:
         if (var6.equals("flow")) {
            var7 = 5;
         }
         break;
      case 94094958:
         if (var6.equals("build")) {
            var7 = 2;
         }
         break;
      case 96667352:
         if (var6.equals("enter")) {
            var7 = 0;
         }
         break;
      case 97429520:
         if (var6.equals("fight")) {
            var7 = 4;
         }
         break;
      case 102846135:
         if (var6.equals("leave")) {
            var7 = 1;
         }
         break;
      case 1557372922:
         if (var6.equals("destroy")) {
            var7 = 3;
         }
      }

      switch(var7) {
      case 0:
         this.cZ = RegionAction.cQ;
         break;
      case 1:
         this.cZ = RegionAction.cR;
         break;
      case 2:
         this.cZ = RegionAction.cS;
         break;
      case 3:
         this.cZ = RegionAction.cT;
         break;
      case 4:
         this.cZ = RegionAction.cU;
         break;
      case 5:
         this.cZ = RegionAction.cV;
         break;
      case 6:
         this.cZ = RegionAction.cW;
         break;
      case 7:
         this.cZ = RegionAction.cX;
         break;
      default:
         throw new MapDataException("Unsupported ActionType for region rule: " + actionType);
      }

      var6 = accessType.toLowerCase();
      var7 = -1;
      switch(var6.hashCode()) {
      case 3079692:
         if (var6.equals("deny")) {
            var7 = 1;
         }
         break;
      case 92906313:
         if (var6.equals("allow")) {
            var7 = 0;
         }
      }

      switch(var7) {
      case 0:
         this.da = RegionAccess.cN;
         break;
      case 1:
         this.da = RegionAccess.cO;
         break;
      default:
         throw new MapDataException("Unsupported AccessType for region rule: " + accessType);
      }

      this.db = teams;
      this.message = message;
      this.material = material;
   }

   public RegionAction bX() {
      return this.cZ;
   }

   public RegionAccess bY() {
      return this.da;
   }

   public RegionTeam bZ() {
      return this.db.isEmpty() ? RegionTeam.dc : RegionTeam.dd;
   }

   public boolean b(MapTeam team) {
      return this.db.contains(team);
   }

   public String getMessage() {
      return this.message;
   }

   public Material getMaterial() {
      return this.material;
   }
}
