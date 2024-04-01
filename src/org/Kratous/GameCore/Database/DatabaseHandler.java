package org.Kratous.GameCore.Database;

import org.Kratous.GameCore.Core;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.bukkit.Bukkit;

public class DatabaseHandler {
   private BoneCP S;

   public boolean w() {
      BoneCPConfig config = new BoneCPConfig();
      config.setJdbcUrl(Core.a().getConfig().getString("database.url"));
      config.setUsername(Core.a().getConfig().getString("database.username"));
      config.setPassword(Core.a().getConfig().getString("database.password"));
      config.setMinConnectionsPerPartition(Core.a().getConfig().getInt("database.minConnectionsPerPartition"));
      config.setMaxConnectionsPerPartition(Core.a().getConfig().getInt("database.maxConnectionsPerPartition"));
      config.setPartitionCount(Core.a().getConfig().getInt("database.partitionCount"));
      config.setConnectionTestStatement("SELECT 1+1;");

      try {
         this.S = new BoneCP(config);
         Connection connection = this.S.getConnection();
         Throwable var3 = null;

         try {
            PreparedStatement statement = connection.prepareStatement("SELECT 1+1;");
            Throwable var5 = null;

            try {
               ResultSet set = statement.executeQuery();
               Throwable var7 = null;

               try {
                  Core.p = true;
               } catch (Throwable var54) {
                  var7 = var54;
                  throw var54;
               } finally {
                  if (set != null) {
                     if (var7 != null) {
                        try {
                           set.close();
                        } catch (Throwable var53) {
                           var7.addSuppressed(var53);
                        }
                     } else {
                        set.close();
                     }
                  }

               }
            } catch (Throwable var56) {
               var5 = var56;
               throw var56;
            } finally {
               if (statement != null) {
                  if (var5 != null) {
                     try {
                        statement.close();
                     } catch (Throwable var52) {
                        var5.addSuppressed(var52);
                     }
                  } else {
                     statement.close();
                  }
               }

            }
         } catch (Throwable var58) {
            var3 = var58;
            throw var58;
         } finally {
            if (connection != null) {
               if (var3 != null) {
                  try {
                     connection.close();
                  } catch (Throwable var51) {
                     var3.addSuppressed(var51);
                  }
               } else {
                  connection.close();
               }
            }

         }

         return true;
      } catch (SQLException var60) {
         Core.p = false;
         Bukkit.getLogger().info("GameCore unable to connect to database - MySQL Disabled");
         return false;
      }
   }

   public Connection getConnection() throws SQLException, InterruptedException, ExecutionException, TimeoutException {
      Future<Connection> connectionFuture = this.S.getAsyncConnection();
      return (Connection)connectionFuture.get(2000L, TimeUnit.MILLISECONDS);
   }

   public void closeConnection() {
      if (this.S != null) {
         this.S.close();
         this.S = null;
      }
   }
}
