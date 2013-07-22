package mods.realm.item;

import mods.realm.Realm;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RealmItemOpener extends RealmItem
{
	public RealmItemOpener(int id)
	{
		super(id);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		if(world.getBlockId(x, y, z) == Block.dragonEgg.blockID)
		{
			world.setBlock(x, y, z, Realm.blockPortalCore.blockID);
			if(!player.capabilities.isCreativeMode)
				stack.stackSize--;
			//return true;
		}
        return false;
    }
}
