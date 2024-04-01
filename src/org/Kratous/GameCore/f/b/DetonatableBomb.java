package org.Kratous.GameCore.f.b;

import org.Kratous.GameCore.e.c.Hologram1;
import org.Kratous.GameCore.f.a.MapTeam;
import org.Kratous.GameCore.l.GamePlayer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class DetonatableBomb {
   private String name;
   private MapTeam ScoreboardString;
   private Block block;
   private EntityTNTPrimed dR;
   private List dS;
   private WorldServer dT;
   private int dU;
   private int dV;
   private int dW;
   private double dX;
   List dY;
   private BombState dZ;
   private Hologram1 dt;

   public DetonatableBomb(String name, MapTeam team, Block block, EntityTNTPrimed entity, WorldServer worldServer, Hologram1 hologram) {
      this.name = name;
      this.ScoreboardString = team;
      this.block = block;
      this.dR = entity;
      this.dT = worldServer;
      this.dS = new ArrayList();
      this.dU = 0;
      this.dX = 0.0D;
      this.dV = 0;
      this.dW = 0;
      this.dY = new ArrayList();
      this.dZ = BombState.df;
      this.dt = hologram;
   }

   public String getName() {
      return this.name;
   }

   public MapTeam ca() {
      return this.ScoreboardString;
   }

   public Block getBlock() {
      return this.block;
   }

   public void cv() {
      if (!this.dZ.equals(BombState.dg)) {
         this.dW = 0;
      } else {
         --this.dW;
         if (this.dW < 0) {
            this.dT.removeEntity(this.dR);
            this.dR = new EntityTNTPrimed(this.dT);
            this.dR.getBukkitEntity().setVelocity(new Vector(0.0D, 0.1D, 0.0D));
            this.dR.setLocation((double)this.block.getX(), (double)this.block.getY() + 0.5D, (double)this.block.getZ(), 0.0F, 0.0F);
            this.dR.fuseTicks = 5000;
            this.dT.addEntity(this.dR);
            this.dS.add(this.dR.getBukkitEntity().getUniqueId());
            this.dW = 12;
         }

         this.block.setType(Material.AIR);
      }
   }

   public void cw() {
      this.dT.removeEntity(this.dR);
      this.dS.clear();
      this.block.setType(Material.TNT);
   }

   public int cx() {
      return this.dU;
   }

   public void j(int value) {
      this.dU = value;
   }

   public void cy() {
      --this.dU;
   }

   public BombState cz() {
      return this.dZ;
   }

   public void a(BombState state) {
      this.dZ = state;
   }

   public void cA() {
      this.dX = (double)System.currentTimeMillis();
   }

   public double cB() {
      return this.dX;
   }

   public void r(GamePlayer gPlayer) {
      if (!this.dY.contains(gPlayer)) {
         this.dY.add(gPlayer);
      }
   }

   public void s(GamePlayer gPlayer) {
      if (this.dY.contains(gPlayer)) {
         this.dY.remove(gPlayer);
      }
   }

   public void cC() {
      this.dY.clear();
   }

   public List cD() {
      List<GamePlayer> a = new ArrayList(this.dY);
      return a;
   }

   public void k(int value) {
      this.dV = value;
   }

   public int cE() {
      return this.dV;
   }

   public void cF() {
      --this.dV;
   }

   public void l(int timer) {
      this.dW = timer;
   }

   public EntityTNTPrimed cG() {
      return this.dR;
   }

   public List cH() {
      return this.dS;
   }

   public Hologram1 cI() {
      return this.dt;
   }
}
