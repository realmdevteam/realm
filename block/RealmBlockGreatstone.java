package mods.realm.block;

import java.util.Random;

import mods.realm.Realm;
import net.minecraft.block.material.Material;

public class RealmBlockGreatstone extends RealmBlockHeatable
{
	public RealmBlockGreatstone(int id, Material mat)
	{
		super(id, mat, -273, 300);
		this.setTickRandomly(true);
	}
	
	@Override
	public int idDropped(int par1, Random random, int par3)
	{
		return Realm.blockCobbleGreatstone.blockID;
	}
}
