package mods.realm.dimension.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS;
import mods.realm.Realm;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeRealmPlainDecorator extends BiomeDecorator
{
	 /** Field that holds one of the plantYellow WorldGenFlowers */
    public WorldGenerator plantAsphodelGen;
    
	public BiomeRealmPlainDecorator(BiomeGenBase biomeGenBase)
	{
		super(biomeGenBase);
        this.plantAsphodelGen = new WorldGenFlowers(Realm.blockBells.blockID);//metadata
	}

	/**
	 * The method that does the work of actually decorating chunks
	 */
	protected void decorate()
	{
		int i;
		int j;
		int k;
		// this.generateOres();
		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, GRASS);
		
		for(int x = 0; doGen && x < this.grassPerChunk; ++x)
		{
			
			i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			j = this.randomGenerator.nextInt(128);
			k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			
			WorldGenerator curlGrass = new WorldGenTallGrass(Realm.blockTallGrass.blockID, 0);
			curlGrass.generate(this.currentWorld, this.randomGenerator, i, j, k);
		}
		
		
		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, FLOWERS);
        for (int x = 0; doGen && x < this.flowersPerChunk; ++x)
        {
        	i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			j = this.randomGenerator.nextInt(128);
			k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            this.plantAsphodelGen.generate(this.currentWorld, this.randomGenerator, i, j, k);

//            if (this.randomGenerator.nextInt(4) == 0)
//            {
//            	i = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
//    			j = this.randomGenerator.nextInt(128);
//    			k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
//                this.plantRedGen.generate(this.currentWorld, this.randomGenerator, i, j, k);
//            }
        }
	}
}
