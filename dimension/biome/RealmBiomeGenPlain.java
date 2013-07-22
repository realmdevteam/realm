package mods.realm.dimension.biome;

import java.util.Random;

import mods.realm.Realm;
import mods.realm.dimension.gen.feature.RealmGenRealmWood;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

public class RealmBiomeGenPlain extends BiomeGenBase
{

	
	public RealmBiomeGenPlain(int id)
	{
		super(id);
		this.temperature = 1.8F;
		this.minHeight = 0.1F;
		this.maxHeight = 0.6F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.topBlock = ((byte) Realm.blockGrass.blockID);
		this.fillerBlock = ((byte) Realm.blockDirt.blockID);
		
		this.setBiomeName("Realm Plain");
		
        this.theBiomeDecorator = new BiomeRealmPlainDecorator(this);
		this.theBiomeDecorator.grassPerChunk = 3;
		this.theBiomeDecorator.flowersPerChunk = 1;

	}	
	
}
	
	
//	@Override
//    public void decorate(World world, Random rand, int i, int k){
//		super.decorate(world, rand, i, k);
		
//		if (rand.nextInt(50) == 0)
//        {
//            int x = i + rand.nextInt(16) + 8;
//            int y = k + rand.nextInt(16) + 8;
//            WorldGenDesertWells worldgendesertwells = new WorldGenDesertWells();
//            worldgendesertwells.generate(world, rand, x, world.getHeightValue(x, y) + 1, y);
//        }
//	}
