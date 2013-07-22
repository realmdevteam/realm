package mods.realm.block;

import java.util.Random;

import mods.realm.DefaultProps;
import mods.realm.Realm;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RealmBlock extends Block 
{
	public RealmBlock(int id, Material mat)
	{
		super(id, mat);
		this.setCreativeTab(Realm.tabRealm);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister itemIcon)
	{
		this.blockIcon = itemIcon.registerIcon(DefaultProps.modId + ":" + getUnlocalizedName().substring(5));
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		if(Realm.isAprilFools)
			return new Random().nextInt(0xFFFFFF);
		else
			return super.colorMultiplier(blockAccess, x, y, z);
	}
	
	public boolean canBeReplacedByLake()
	{
		return true;
	}
}
