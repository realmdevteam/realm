package mods.realm.dimension.portal;

import java.util.Random;

import mods.realm.DefaultProps;
import mods.realm.Realm;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public class RealmTileEntityPortal extends TileEntity
{
	public boolean canstay = true;
	public RealmPortalPosition coords;
	byte tick;
	public int timebeforetp = -1;
	public boolean wasCollided = false;
	public int ticksWithoutColliding = 0;
	public float rotation = 0;
	public float alpha;
	public double radius;
	public float texPos;
	
	@Override
	public void updateEntity()
	{
		texPos -= 0.05F;
		if(texPos >= 1F) texPos -= 1F;
		
		rotation += 2.5F;
		if(rotation >= 360) rotation -= 360;
		
		radius = Math.sin(Math.toRadians(rotation*2))/4 + 1;
		alpha = (float) Math.sin(Math.toRadians(rotation*3))/4 + 0.5F;
		
		if(!wasCollided) ticksWithoutColliding++;
		if(wasCollided) ticksWithoutColliding = 0;
		if(ticksWithoutColliding > 5)
		{
			timebeforetp = -1;
		}
		if(timebeforetp > 0)
		{
			timebeforetp--;
		}
		wasCollided = false;
		
		if(coords == null)
		{
			coords = new RealmPortalPosition(worldObj.provider.dimensionId, xCoord, yCoord, zCoord);
		}
		
		tick++;
		if(tick >= DefaultProps.ticksbeforeportalcheck)
		{
			tick = 0;
			canstay = canStayPortal();
		}
		
		if(canstay)
		{
			if(!RealmTeleporter.portals.contains(coords))
			{
				RealmTeleporter.portals.add(coords);
			}
		}
		else
		{
			if(RealmTeleporter.portals.contains(coords))
			{
				RealmTeleporter.portals.remove(coords);
			}
			
			if(!worldObj.isRemote)
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord, zCoord, new ItemStack(Realm.itemOpener)));
			worldObj.setBlock(xCoord, yCoord, zCoord, Block.dragonEgg.blockID);
		}
	}
	
	public boolean canStayPortal()
	{
		boolean ret = true;
		
		for(int i=-2; i<=2; i++)
		{
			for(int j=-2; j<=2; j++)
			{
				if(worldObj.getBlockId(xCoord+i, yCoord-2, zCoord+j) != Block.blockNetherQuartz.blockID) ret = false;
				if(worldObj.getBlockMetadata(xCoord+i, yCoord-2, zCoord+j) != 0) ret = false;

			}
		}
		return true;
	
			}
	}

