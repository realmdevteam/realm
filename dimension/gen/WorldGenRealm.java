package mods.realm.dimension.gen;

import java.util.Random;

import mods.realm.Realm;
import mods.realm.api.temperature.TemperatureManager;
import mods.realm.dimension.gen.feature.RealmGenLakes;
import mods.realm.dimension.gen.feature.RealmGenMinable;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenRealm implements IWorldGenerator
{
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world.provider.dimensionId == Realm.DimensionID)
		{
			generateRealm(world, rand, chunkX * 16, chunkZ * 16);
		}
		if(world.provider.dimensionId == 0)
		{
			//generateOverworld(world, rand, chunkX * 16, chunkZ * 16);
		}
	}
	
	private void generateOverworld(World world, Random random, int blockX, int blockZ)
	{
		int x = blockX + random.nextInt(16);
		int z = blockX + random.nextInt(16);
		int y = world.getTopSolidOrLiquidBlock(x, z);
		
		new RealmGenLakes(Realm.waterStill.blockID).generate(world, random, x, y, z);
	}
	
	private void generateRealm(World world, Random random, int blockX, int blockZ)
	{
		
		for(int i = 0; i < 8; i++)
		{
			new RealmGenMinable(Realm.oreSaphire.blockID, 8).generate(world, random, blockX + random.nextInt(16), random.nextInt(64), blockZ + random.nextInt(16));
		}
		
		for(int i = 0; i < 2; i++)
		{
			new RealmGenMinable(Realm.oreTopaz.blockID, 8).generate(world, random, blockX + random.nextInt(16), random.nextInt(32), blockZ + random.nextInt(16));
		}
			
		new RealmGenMinable(Realm.oreGarnet.blockID, 8).generate(world, random, blockX + random.nextInt(16), random.nextInt(32), blockZ + random.nextInt(16));
		
	
		
		for(int j = 0; j < 256; j++)
		{
			int meta = TemperatureManager.getBlockMetadataFromTemperature(Realm.blockGreatstone.blockID, TemperatureManager.getTemperatureForHeight(j));
			for(int i = 0; i < 16; i++)
				for(int k = 0; k < 16; k++)
					if(world.getBlockId(blockX+i, j, blockZ+k) == Realm.blockGreatstone.blockID)
						world.setBlockMetadataWithNotify(blockX+i, j, blockZ+k, meta, 0);
		}
	}
}
