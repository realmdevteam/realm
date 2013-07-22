package mods.realm.dimension.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import mods.realm.Realm;
import mods.realm.block.RealmBlockFlower;

public class RealmGenRealmWood2 extends WorldGenerator
{
	int leavesId,logId;
	boolean fromSapling;
	
	public RealmGenRealmWood2(int leavesId, int logId, boolean fromSapling)
	{
		super(fromSapling);
		this.leavesId = leavesId;
		this.logId = logId;
		this.fromSapling = fromSapling;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		int cap = random.nextInt(8) + 2;
		int trunk = random.nextInt(3) + 10;
		int minTreeHeight = 6;
		
		int treeHeight = trunk + minTreeHeight;
		
		if(!((RealmBlockFlower)Realm.blockSaplingRealmWood).canThisPlantGrowOnThisBlockID(world.getBlockId(x, y-1, z)))
			return false;
		
		if(fromSapling)
		{
			for(int j = 1; j <= treeHeight; j++)
			{
				if(!world.isAirBlock(x, y+j, z))
				return false;
			}
		}
		else
		{
			for(int i = -8; i <= 8; i++)
			{
				for(int k = -8; k <= 8; k++)
				{
					for(int j = 1; j <= treeHeight; j++)
					{
						if(!world.isAirBlock(x+i, y+j, z+k))
							return false;
					}
				}
	        }
		}
		
		int h1 = treeHeight - random.nextInt(3);
		int h2 = treeHeight - random.nextInt(3);
		int h3 = treeHeight - random.nextInt(3);
		int h4 = treeHeight - random.nextInt(3);
		int h5 = treeHeight - random.nextInt(3);
		int h6 = treeHeight - random.nextInt(3);
		int h7 = treeHeight - random.nextInt(3);
		int h8 = treeHeight - random.nextInt(3);
		
		for(int i = 0; i < treeHeight + cap; i++)
		{
			
			if(i < treeHeight)
			{
				this.setBlock(world, x, y + i, z, logId);
			}
			
			if(i >= trunk && i < treeHeight + 1)
			{
		    	addLeaves(world, x + 2, y+i, z);
		    	addLeaves(world, x - 2, y+i, z);
		    	addLeaves(world, x, y+i, z + 2);
		    	addLeaves(world, x, y+i, z - 2);
		    	addLeaves(world, x + 3, y+i, z);
		    	addLeaves(world, x - 3, y+i, z);
		    	addLeaves(world, x, y+i, z + 3);
		    	addLeaves(world, x, y+i, z - 3);
			}
			
			if(i > trunk && i < h1)
		    	addLeaves(world, x + 2, y+i, z + 2);
			if(i > trunk && i < h2)
				addLeaves(world, x - 2, y+i, z - 2);
			if(i > trunk && i < h3)
				addLeaves(world, x - 2, y+i, z + 2);
			if(i > trunk && i < h4)
				addLeaves(world, x + 2, y+i, z - 2);
			if(i > trunk && i < h5)
		    	addLeaves(world, x + 3, y+i, z + 3);
			if(i > trunk && i < h6)
				addLeaves(world, x - 3, y+i, z - 3);
			if(i > trunk && i < h7)
				addLeaves(world, x - 3, y+i, z + 3);
			if(i > trunk && i < h8)
				addLeaves(world, x + 3, y+i, z - 3);
			
			if(i >= treeHeight)
			{
				addLeaves(world, x, y+i, z);
			}
		}
		
		return true;
	}
	
	private boolean addLeaves(World world, int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
	    Block block = Block.blocksList[id];
		if(block == null || block.canBeReplacedByLeaves(world, x, y, z))
		{
			world.setBlock(x, y, z, leavesId);
			return true;
		}
		return false;
	}
}