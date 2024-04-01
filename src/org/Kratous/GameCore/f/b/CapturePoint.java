package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.e.c.Hologram1;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.o.Shape;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class CapturePoint {
   private String name;
   private Shape cD;
   private double dp;
   private MapTeam dq;
   private MapTeam dr;
   private boolean ds;
   private Hologram1 dt;
   private String du;
   private boolean dv;

   public CapturePoint(String name, Shape shape, World world) {
      this.name = name;
      this.cD = shape;
      this.dp = 0.0D;
      this.dq = null;
      this.dr = null;
      this.ds = true;
      this.dv = true;
      this.du = "";
      this.dt = new Hologram1(this.cD.fe().toLocation(world).add(0.5D, 4.0D, 0.5D));
   }

   public String getName() {
      return this.name;
   }

   public Shape bV() {
      return this.cD;
   }

   public double ce() {
      return this.dp;
   }

   public void a(double value) {
      this.dp += value;
   }

   public void b(double value) {
      this.dp = value;
   }

   public MapTeam cf() {
      return this.dq;
   }

   public void c(MapTeam value) {
      this.dq = value;
   }

   public MapTeam cg() {
      return this.dr;
   }

   public void d(MapTeam value) {
      this.dr = value;
   }

   public boolean cd() {
      return this.dq != null && this.dp > 0.0D;
   }

   public boolean ch() {
      return this.dq != null && this.dp >= 100.0D;
   }

   public boolean ci() {
      return this.ds;
   }

   public void c(boolean value) {
      this.ds = value;
   }

   public void cj() {
      this.dt.O();
      this.dt.setLine(0, this.name);
      this.dt.setLine(1, ChatColor.WHITE + "/" + this.cl() + ChatColor.WHITE + "\\");
   }

   public boolean ck() {
      return this.dv;
   }

   public void d(boolean value) {
      this.dv = value;
   }

   public String cl() {
      String progress = ChatColor.WHITE + "";
      progress = progress + (this.dq != null ? this.dq.bP() + "" : (this.dr != null ? this.dr.bP() + "" : ChatColor.WHITE + ""));

      for(int i = 1; i <= 10; ++i) {
         if ((int)this.dp / 10 >= i) {
            progress = progress + "%";
         } else {
            progress = progress + "_";
         }
      }

      progress = progress + "";
      this.du = progress;
      return progress;
   }

   public String cm() {
      return this.du;
   }
}
