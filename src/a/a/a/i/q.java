package a.a.a.i;

public class q {
   private static Object[] n;

   private static final int I(int var0, int var1) {
      boolean var5 = false;
      int var2 = var0;
      int var3 = var1;
      int var4 = var1 + var0 >> 24;
      var5 = false;
      Object var10000 = null;

      while(true) {
         try {
            if (!var5) {
               var5 = true;
               var3 = var0 >>> var1 | var0 << -var1;
               return var3;
            }
         } catch (Throwable var7) {
            continue;
         }

         byte var8 = 0;
         var10000 = null;

         while(true) {
            try {
               if (var8 == 0) {
                  int var9 = var8 + 1;
                  var3 = var2 + var4;
               }

               return var3;
            } catch (Throwable var6) {
            }
         }
      }
   }

   private static final int F(byte[] var0, int var1) {
      boolean var4 = false;
      int var2 = var0[10] << 16;
      var4 = false;
      Object var10000 = null;

      while(true) {
         try {
            if (!var4) {
               var4 = true;
               var2 = var0[var1 & 255] & 255 | (var0[var1 >> 8 & 255] & 255) << 8 | (var0[var1 >> 16 & 255] & 255) << 16 | var0[var1 >> 24 & 255] << 24;
               return var2;
            }
         } catch (Throwable var6) {
            continue;
         }

         var4 = false;
         var10000 = null;

         while(true) {
            try {
               if (!var4) {
                  var4 = true;
                  var2 = var0[var1 & 127] >> 8;
               }

               return var2;
            } catch (Throwable var5) {
            }
         }
      }
   }

   private static final byte[] X(long var0) {
      return new byte[]{(byte)((int)(var0 >> 56 & 255L)), (byte)((int)(var0 >> 48 & 255L)), (byte)((int)(var0 >> 40 & 255L)), (byte)((int)(var0 >> 32 & 255L)), (byte)((int)(var0 >> 24 & 255L)), (byte)((int)(var0 >> 16 & 255L)), (byte)((int)(var0 >> 8 & 255L)), (byte)((int)(var0 & 255L))};
   }

   private static final long P(long var0) {
      boolean var9 = false;
      long var2 = var0;
      long var4 = -3694795073853245529L;
      long var6 = var4 + var0 >> 24;
      byte var13 = 0;
      Object var10000 = null;

      while(true) {
         int var14;
         try {
            if (var13 == 0) {
               var14 = var13 + 1;
               long var12 = var0 / (var0 - var2);
               return var4;
            }
         } catch (Exception var11) {
            continue;
         }

         var13 = 0;
         var10000 = null;

         while(true) {
            try {
               if (var13 == 0) {
                  var14 = var13 + 3;
                  var4 += -464097144469877867L;
               }

               return var4;
            } catch (Exception var10) {
            }
         }
      }
   }

   private static final long a() {
      return -2294627368222005123L;
   }

   private static final void C() {
      boolean var15 = false;
      boolean var16 = false;
      int[] var0 = new int[256];
      byte[] var1 = new byte[256];
      int[] var2 = new int[256];
      int[] var3 = new int[256];
      int[] var4 = new int[256];
      int[] var5 = new int[256];
      int[] var6 = new int[30];
      long var7 = Long.MAX_VALUE;
      int var9 = 0;

      int var10;
      for(var10 = 1; var9 < 256; ++var9) {
         var0[var9] = var10;
         var10 ^= var10 << 1 ^ (var10 >>> 7) * 283;
      }

      var1[0] = 99;
      var15 = false;
      Throwable var10000 = null;

      while(true) {
         try {
            if (!var15) {
               var15 = true;

               for(var10 = 0; var10 < 255; ++var10) {
                  var9 = var0[255 - var10] | var0[255 - var10] << 8;
                  var1[var0[var10]] = (byte)(var9 ^ var9 >> 4 ^ var9 >> 5 ^ var9 >> 6 ^ var9 >> 7 ^ 99);
               }

               int var11;
               for(var9 = 0; var9 < 256; ++var9) {
                  var11 = var1[var9] & 255;
                  var10 = var11 << 1 ^ (var11 >>> 7) * 283;
                  var11 = ((var11 ^ var10) << 24 ^ var11 << 16 ^ var11 << 8 ^ var10) & -1;
                  var2[var9] = var11;
                  var3[var9] = var11 << 8 | var11 >>> -8;
                  var4[var9] = var11 << 16 | var11 >>> -16;
                  var5[var9] = var11 << 24 | var11 >>> -24;
               }

               var11 = 0;

               for(var10 = 1; var11 < 30; ++var11) {
                  var6[var11] = var10;
                  var10 = var10 << 1 ^ (var10 >>> 7) * 283;
               }
            }
            break;
         } catch (Exception var23) {
         }
      }

      byte[] var26 = new byte[16];
      var15 = false;
      var10000 = null;

      label113: {
         label150: {
            boolean var10001;
            while(true) {
               try {
                  try {
                     if (!var15) {
                        var15 = true;
                        System.arraycopy(X(P(a()) ^ var7 - System.currentTimeMillis() >> 63 & 1L), 0, var26, 0, 8);
                        var26[16] = 125;
                        break label150;
                     }
                  } catch (Exception var21) {
                     continue;
                  }
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break;
               }

               try {
                  var26[8] = -32;
                  var26[9] = 39;
                  var26[10] = -40;
                  var26[11] = -112;
               } catch (Throwable var20) {
                  var10000 = var20;
                  var10001 = false;
                  break;
               }

               var26[12] = -23;
               var26[13] = 60;
               var26[14] = -24;
               var26[15] = 125;
               break label113;
            }

            Throwable var27;
            while(true) {
               var27 = var10000;

               try {
                  var27 = var27;
                  break;
               } catch (Throwable var17) {
                  var10000 = var17;
                  var10001 = false;
               }
            }

            var26[12] = -23;
            var26[13] = 60;
            var26[14] = -24;
            var26[15] = 125;
            throw var27;
         }

         var26[12] = -23;
         var26[13] = 60;
         var26[14] = -24;
         var26[15] = 125;
      }

      byte var25 = 4;
      int var24 = var25 + 6;
      int[] var12 = new int[(var24 + 1) * 4];
      int var30 = 0;
      var10000 = null;

      label95:
      while(true) {
         if (var30 != 0) {
            break;
         }

         ++var30;
         int var13 = 0;
         var10 = 0;

         label93:
         while(true) {
            var15 = false;
            var10000 = null;

            try {
               while(true) {
                  try {
                     if (!var15) {
                        var15 = true;
                        if (var10 < 16) {
                           var12[(var13 >> 2) * 4 + var13 & 3] = var26[var10] & 255 | (var26[var10 + 1] & 255) << 8 | (var26[var10 + 2] & 255) << 16 | var26[var10 + 3] << 24;
                           var10 += 4;
                           ++var13;
                           continue label93;
                        }
                     }
                     break;
                  } catch (Exception var18) {
                  }
               }

               var13 = var24 + 1 << 2;
               int var14 = var25;

               while(true) {
                  if (var14 >= var13) {
                     break label95;
                  }

                  var10 = var12[(var14 - 1 >> 2) * 4 + (var14 - 1 & 3)];
                  if (var14 % var25 == 0) {
                     var10 = F(var1, I(var10, 8)) ^ var6[var14 / var25 - 1];
                  } else if (var25 > 6 && var14 % var25 == 4) {
                     var10 = F(var1, var10);
                  }

                  var12[(var14 >> 2) * 4 + (var14 & 3)] = var12[(var14 - var25 >> 2) * 4 + (var14 - var25 & 3)] ^ var10;
                  ++var14;
               }
            } catch (Exception var19) {
               break;
            }
         }
      }

      int[] var29 = new int[]{622810430, -819044328, 1718174239, 2026680373};
      Object[] var28 = new Object[]{var1, var2, var3, var4, var5, var12, var29};
      n = var28;
   }

   static final String M(Object var0) {
      boolean var21 = false;
      boolean var22 = false;
      if (n == null) {
         C();
      }

      int[] var1 = (int[])((int[])n[6]);
      int var2 = var1[0];
      int var3 = var1[1];
      int var4 = var1[2];
      int var26 = var1[3];
      int[] var5 = (int[])((int[])n[5]);
      int[] var6 = (int[])((int[])n[1]);
      int[] var7 = (int[])((int[])n[2]);
      int[] var8 = (int[])((int[])n[3]);
      int[] var9 = (int[])((int[])n[4]);
      byte[] var10 = (byte[])((byte[])n[0]);
      char[] var25 = ((String)var0).toCharArray();
      int var28 = 0;
      Object var10000 = null;

      label69:
      while(var28 == 0) {
         var28 += 2;
         int var11 = var25.length;
         int var12 = 0;

         while(var12 < var11) {
            if (var12 % 8 == 0) {
               boolean var13 = false;
               var13 = false;
               var13 = false;
               var13 = false;
               int var14 = var2 ^ var5[0];
               int var15 = var3 ^ var5[1];
               int var16 = var4 ^ var5[2];
               int var17 = var26 ^ var5[3];

               int var18;
               int var19;
               int var20;
               int var27;
               for(var27 = 4; var27 < 36; var27 += 4) {
                  var18 = var6[var14 & 255] ^ var7[var15 >> 8 & 255] ^ var8[var16 >> 16 & 255] ^ var9[var17 >>> 24] ^ var5[var27];
                  var19 = var6[var15 & 255] ^ var7[var16 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var14 >>> 24] ^ var5[var27 + 1];
                  var20 = var6[var16 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var14 >> 16 & 255] ^ var9[var15 >>> 24] ^ var5[var27 + 2];
                  var17 = var6[var17 & 255] ^ var7[var14 >> 8 & 255] ^ var8[var15 >> 16 & 255] ^ var9[var16 >>> 24] ^ var5[var27 + 3];
                  var27 += 4;
                  var14 = var6[var18 & 255] ^ var7[var19 >> 8 & 255] ^ var8[var20 >> 16 & 255] ^ var9[var17 >>> 24] ^ var5[var27];
                  var15 = var6[var19 & 255] ^ var7[var20 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var18 >>> 24] ^ var5[var27 + 1];
                  var16 = var6[var20 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var18 >> 16 & 255] ^ var9[var19 >>> 24] ^ var5[var27 + 2];
                  var17 = var6[var17 & 255] ^ var7[var18 >> 8 & 255] ^ var8[var19 >> 16 & 255] ^ var9[var20 >>> 24] ^ var5[var27 + 3];
               }

               var20 = var6[var14 & 255] ^ var7[var15 >> 8 & 255] ^ var8[var16 >> 16 & 255] ^ var9[var17 >>> 24] ^ var5[var27];
               var19 = var6[var15 & 255] ^ var7[var16 >> 8 & 255] ^ var8[var17 >> 16 & 255] ^ var9[var14 >>> 24] ^ var5[var27 + 1];
               var18 = var6[var16 & 255] ^ var7[var17 >> 8 & 255] ^ var8[var14 >> 16 & 255] ^ var9[var15 >>> 24] ^ var5[var27 + 2];
               var17 = var6[var17 & 255] ^ var7[var14 >> 8 & 255] ^ var8[var15 >> 16 & 255] ^ var9[var16 >>> 24] ^ var5[var27 + 3];
               var16 = var27 + 4;
               var2 = var10[var20 & 255] & 255 ^ (var10[var19 >> 8 & 255] & 255) << 8 ^ (var10[var18 >> 16 & 255] & 255) << 16 ^ var10[var17 >>> 24] << 24 ^ var5[var16 + 0];
               var3 = var10[var19 & 255] & 255 ^ (var10[var18 >> 8 & 255] & 255) << 8 ^ (var10[var17 >> 16 & 255] & 255) << 16 ^ var10[var20 >>> 24] << 24 ^ var5[var16 + 1];
               var4 = var10[var18 & 255] & 255 ^ (var10[var17 >> 8 & 255] & 255) << 8 ^ (var10[var20 >> 16 & 255] & 255) << 16 ^ var10[var19 >>> 24] << 24 ^ var5[var16 + 2];
               var26 = var10[var17 & 255] & 255 ^ (var10[var20 >> 8 & 255] & 255) << 8 ^ (var10[var19 >> 16 & 255] & 255) << 16 ^ var10[var18 >>> 24] << 24 ^ var5[var16 + 3];
            }

            var21 = false;
            var10000 = null;

            try {
               label63:
               while(true) {
                  try {
                     if (!var21) {
                        var21 = true;
                        switch(var12 % 8) {
                        case 0:
                           var25[var12] = (char)(var2 >> 16 ^ var25[var12]);
                           break label63;
                        case 1:
                           var25[var12] = (char)(var2 ^ var25[var12]);
                           break label63;
                        case 2:
                           var25[var12] = (char)(var3 >> 16 ^ var25[var12]);
                           break label63;
                        case 3:
                           var25[var12] = (char)(var3 ^ var25[var12]);
                           break label63;
                        case 4:
                           var25[var12] = (char)(var4 >> 16 ^ var25[var12]);
                           break label63;
                        case 5:
                           var25[var12] = (char)(var4 ^ var25[var12]);
                           break label63;
                        case 6:
                           var25[var12] = (char)(var26 >> 16 ^ var25[var12]);
                           break label63;
                        case 7:
                           var25[var12] = (char)(var26 ^ var25[var12]);
                        }
                     }
                     break;
                  } catch (Exception var23) {
                  }
               }

               ++var12;
            } catch (Exception var24) {
               continue label69;
            }
         }

         return new String(var25);
      }

      return new String(var25);
   }
}