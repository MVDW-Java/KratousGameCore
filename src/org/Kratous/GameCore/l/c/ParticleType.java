package org.Kratous.GameCore.l.c;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Material;

public enum ParticleType {
   hJ(Material.SLIME_BALL, EnumParticle.SLIME, ParticleStyle.hH, "Slime Halo", 1),
   hK(Material.SLIME_BALL, EnumParticle.SLIME, ParticleStyle.hG, "Slime Aura", 1),
   hL(Material.SLIME_BALL, EnumParticle.SLIME, ParticleStyle.hF, "Slime Feet", 1),
   hM(Material.BED, EnumParticle.HEART, ParticleStyle.hH, "Heart Halo", 2),
   hN(Material.BED, EnumParticle.HEART, ParticleStyle.hG, "Heart Aura", 2),
   hO(Material.BED, EnumParticle.HEART, ParticleStyle.hF, "Heart Feet", 2),
   hP(Material.LAVA_BUCKET, EnumParticle.LAVA, ParticleStyle.hH, "Lava Halo", 3),
   hQ(Material.LAVA_BUCKET, EnumParticle.LAVA, ParticleStyle.hG, "Lava Aura", 3),
   hR(Material.LAVA_BUCKET, EnumParticle.LAVA, ParticleStyle.hF, "Lava Feet", 3),
   hS(Material.FIREWORK, EnumParticle.FIREWORKS_SPARK, ParticleStyle.hH, "Firework Halo", 4),
   hT(Material.FIREWORK, EnumParticle.FIREWORKS_SPARK, ParticleStyle.hG, "Firework Aura", 4),
   hU(Material.FIREWORK, EnumParticle.FIREWORKS_SPARK, ParticleStyle.hF, "Firework Feet", 4),
   hV(Material.REDSTONE, EnumParticle.REDSTONE, ParticleStyle.hH, "Redstone Halo", 5),
   hW(Material.REDSTONE, EnumParticle.REDSTONE, ParticleStyle.hG, "Redstone Aura", 5),
   hX(Material.REDSTONE, EnumParticle.REDSTONE, ParticleStyle.hF, "Redstone Feet", 5),
   hY(Material.NETHER_STAR, EnumParticle.CRIT_MAGIC, ParticleStyle.hH, "Nether Halo", 6),
   hZ(Material.NETHER_STAR, EnumParticle.CRIT_MAGIC, ParticleStyle.hG, "Nether Aura", 6),
   ia(Material.NETHER_STAR, EnumParticle.CRIT_MAGIC, ParticleStyle.hF, "Nether Feet", 6),
   ib(Material.BLAZE_POWDER, EnumParticle.TOWN_AURA, ParticleStyle.hH, "Aura Halo", 7),
   ic(Material.BLAZE_POWDER, EnumParticle.TOWN_AURA, ParticleStyle.hG, "Aura Aura", 7),
   ie(Material.BLAZE_POWDER, EnumParticle.TOWN_AURA, ParticleStyle.hF, "Aura Feet", 7),
   //if(Material.MILK_BUCKET, (EnumParticle)null, ParticleStyle.hH, "Remove Halo Particles", 8),
   ig(Material.MILK_BUCKET, (EnumParticle)null, ParticleStyle.hG, "Remove Aura Particles", 8),
   ih(Material.MILK_BUCKET, (EnumParticle)null, ParticleStyle.hF, "Remove Trail Particles", 8);

   private Material material;
   private String name;
   private EnumParticle ii;
   private ParticleStyle ij;
   private int hD;

   private ParticleType(Material material, EnumParticle effect, ParticleStyle style, String name, int itemSlot) {
      this.name = name;
      this.ii = effect;
      this.material = material;
      this.ij = style;
      this.hD = itemSlot;
   }

   public String getName() {
      return this.name;
   }

   public EnumParticle eR() {
      return this.ii;
   }

   public ParticleStyle eS() {
      return this.ij;
   }

   public Material getMaterial() {
      return this.material;
   }

   public int eQ() {
      return this.hD;
   }
}
