package mods.realm.item;

import mods.realm.DefaultProps;
import mods.realm.Realm;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RealmItem extends Item
{
	public RealmItem(int id)
	{
		super(id);
		this.setCreativeTab(Realm.tabRealm);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister itemIcon)
    {
        this.itemIcon = itemIcon.registerIcon(DefaultProps.modId + ":" + getUnlocalizedName().substring(5));
    }
}
