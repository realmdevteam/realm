package mods.realm.dimension.portal;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import mods.realm.DefaultProps;
import mods.realm.Realm;
import mods.realm.block.RealmBlockContainer;
import mods.realm.client.particle.RealmEntityFX;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class RealmBlockPortalCore extends RealmBlockContainer
{
	public RealmBlockPortalCore(int id, Material mat)
	{
		super(id, mat);
		this.setBlockBounds(0.5F, 1F, 0.5F, 0.5F, 1F, 0.5F);
		this.setTickRandomly(true);
		this.setCreativeTab(null);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World wolrd, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
	{
		world.setBlock(x, y, z, this.blockID);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if((entity.riddenByEntity == null) && (entity.ridingEntity == null))
		{
			if(entity instanceof EntityPlayerMP)
			{
				EntityPlayerMP player = (EntityPlayerMP) entity;
				RealmTileEntityPortal tile = (RealmTileEntityPortal)world.getBlockTileEntity(x, y, z);
				
				
				if(tile.timebeforetp == 0)
				{
					tile.timebeforetp = -1;
					
					if(player.dimension == Realm.DimensionID)
					{
						player.mcServer.getConfigurationManager().transferPlayerToDimension(player, 0, new RealmTeleporter(player.mcServer.worldServerForDimension(0)));
					}
					else
					{
						player.mcServer.getConfigurationManager().transferPlayerToDimension(player, Realm.DimensionID, new RealmTeleporter(player.mcServer.worldServerForDimension(Realm.DimensionID)));
					}
				}
				else if(player.prevPosY == player.posY)
				{
					tile.wasCollided = true;
					if(tile.timebeforetp == -1) tile.timebeforetp = DefaultProps.ticksbeforeportalteleport;
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		double rad = ((RealmTileEntityPortal)world.getBlockTileEntity(x, y, z)).radius;
		
		int part = random.nextInt(20);
		for(int i = 0; i < part; i++)
		{
			int deg = random.nextInt(360);
			RealmEntityFX entityfx = new RealmEntityFX(world, x+rad*Math.cos(Math.toRadians(deg))+0.5D, y+random.nextInt(100), z+rad*Math.sin(Math.toRadians(deg))+0.5D, 0, 0.1D, 0);
			entityfx.setRBGColorF(1F, 1F, 0F);
			entityfx.setBrightness(125);
			entityfx.setTextureFile("/mods/realm/textures/misc/particles/beam.png");
			ModLoader.getMinecraftInstance().effectRenderer.addEffect(entityfx);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new RealmTileEntityPortal();
	}
	
	/**
	 * Returns the correct warping message for Realm depending on where the player is.
	 * @param player
	 * @return Message to where the player is warping
	 */
}
