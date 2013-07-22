package mods.realm;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;

public class RealmTab extends CreativeTabs
{
	public RealmTab(int par1, String par2Str)
	{
		super(par1, par2Str);
	}
	@SideOnly(Side.CLIENT)
	public int getTabIconItemIndex()
	{
		return Realm.blockGreatstone.blockID;
	}

	public String getTranslatedTabLabel()
	{
		return "The Realm";
	}
}
