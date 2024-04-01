package org.Kratous.GameCore.i;

import org.Kratous.GameCore.Core;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.bukkit.Bukkit;

public class Logger {
   public static void log(String message) {
      File file = new File(Core.a().getDataFolder(), "log/log.log");
      if (!file.exists()) {
         Bukkit.getLogger().warning("Log file did not exist but has been created");

         try {
            if (!file.getParentFile().exists()) {
               file.getParentFile().mkdirs();
            }

            file.createNewFile();
         } catch (IOException var19) {
            var19.printStackTrace();
         }
      }

      try {
         AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(file.toURI()), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
         Throwable var3 = null;

         try {
            Future<FileLock> featureLock = channel.lock();
            FileLock lock = (FileLock)featureLock.get();
            if (lock.isValid()) {
               DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
               Date date = new Date();
               Future<Integer> future = channel.write(ByteBuffer.wrap(("[" + dateFormat.format(date) + "] " + message + "\n").getBytes()), channel.size());
               future.get();
               lock.release();
            }
         } catch (Throwable var18) {
            var3 = var18;
            throw var18;
         } finally {
            if (channel != null) {
               if (var3 != null) {
                  try {
                     channel.close();
                  } catch (Throwable var17) {
                     var3.addSuppressed(var17);
                  }
               } else {
                  channel.close();
               }
            }

         }
      } catch (InterruptedException | ExecutionException | IOException var21) {
         var21.printStackTrace();
      }

      Bukkit.getLogger().info(message);
   }
}
