package mods.realm.dimension.biome;

import mods.realm.Realm;
import net.minecraft.world.biome.BiomeGenBase;

public class RealmBiomeGenOcean extends BiomeGenBase
{
	public RealmBiomeGenOcean(int id)
	{
		super(id);
		this.temperature = 1.5F;
		this.minHeight = -1F;
		this.maxHeight = 0.4F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.topBlock = ((byte) Realm.blockGrass.blockID);
		this.fillerBlock = ((byte) Realm.blockDirt.blockID);
		
		this.setBiomeName("Realm Ocean");
	}
}