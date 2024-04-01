package a.a.a.a;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AchievementHandler {
   private Map w = new HashMap();

   public void s() {
      this.w.clear();
   }

   private void a(Connection connection, Achievement achievement) throws SQLException {
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM `achievement_levels` WHERE `achievement_id` = " + achievement.getId());
      Throwable var4 = null;

      try {
         ResultSet set = statement.executeQuery();
         Throwable var6 = null;

         try {
            while(set.next()) {
               AchievementLevel level = new AchievementLevel(set.getInt("level_id"), set.getInt("requirement"), set.getInt("reward_xp"), set.getInt("reward_gold"));
               achievement.a(level);
            }
         } catch (Throwable var29) {
            var6 = var29;
            throw var29;
         } finally {
            if (set != null) {
               if (var6 != null) {
                  try {
                     set.close();
                  } catch (Throwable var28) {
                     var6.addSuppressed(var28);
                  }
               } else {
                  set.close();
               }
            }

         }
      } catch (Throwable var31) {
         var4 = var31;
         throw var31;
      } finally {
         if (statement != null) {
            if (var4 != null) {
               try {
                  statement.close();
               } catch (Throwable var27) {
                  var4.addSuppressed(var27);
               }
            } else {
               statement.close();
            }
         }

      }

   }

   public Achievement b(int id) {
      return (Achievement)this.w.get(id);
   }

   public Achievement a(AchievementType type) {
      Iterator var2 = this.w.values().iterator();

      Achievement achievement;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         achievement = (Achievement)var2.next();
      } while(!achievement.q().equals(type));

      return achievement;
   }
}
