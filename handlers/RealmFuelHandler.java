package mods.realm.handlers;

import mods.realm.Realm;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class RealmFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.itemID == Realm.itemGarnet.itemID)
			return 2000;
		else
			return 0;
	}

}
