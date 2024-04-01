package org.Kratous.GameCore.n;

import org.Kratous.GameCore.Core;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;

public class ServerHandler {
   private int serverId;
   private String serverName = Bukkit.getServer().getMotd();

   public ServerHandler() {
      this.fc();
   }

   public int getServerId() {
      return this.serverId;
   }

   public String getServerName() {
      return this.serverName;
   }

   public void s(final String map) {
      Bukkit.getScheduler().runTaskAsynchronously(Core.a(), new Runnable() {
         public void run() {
         }
      });
   }

   private void b(Connection connection) throws SQLException {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO `servers` (`pretty_name`, `online`, `players`) VALUES (?, '1', 0);", 1);
      Throwable var3 = null;

      try {
         statement.setString(1, this.serverName);
         statement.executeUpdate();
         ResultSet set = statement.getGeneratedKeys();
         Throwable var5 = null;

         try {
            if (set.next()) {
               this.serverId = set.getInt(1);
            }
         } catch (Throwable var28) {
            var5 = var28;
            throw var28;
         } finally {
            if (set != null) {
               if (var5 != null) {
                  try {
                     set.close();
                  } catch (Throwable var27) {
                     var5.addSuppressed(var27);
                  }
               } else {
                  set.close();
               }
            }

         }
      } catch (Throwable var30) {
         var3 = var30;
         throw var30;
      } finally {
         if (statement != null) {
            if (var3 != null) {
               try {
                  statement.close();
               } catch (Throwable var26) {
                  var3.addSuppressed(var26);
               }
            } else {
               statement.close();
            }
         }

      }

   }

   public void destroy() {
   }

   public static String b(String var0, String var1) throws IOException, ClassNotFoundException {
      return "true";
   }

   public void fc() {
   }
}
