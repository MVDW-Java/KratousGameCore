package org.Kratous.GameCore.r.a;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class VoidGenerator extends ChunkGenerator {
   public short[][] generateExtBlockSections(World contextWorld, Random worldRandom, int cx, int cz, BiomeGrid biomes) {
      return new short[0][];
   }
}
