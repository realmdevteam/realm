package mods.realm.item;

import mods.realm.Realm;
import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;

public class RealmItemShovel extends RealmItemTool
{
    /** an array of the blocks this spade is effective against */
    public static final Block[] blocksEffectiveAgainst = new Block[] {Realm.blockGrass, Realm.blockDirt, Realm.blockLeucosand};

    public RealmItemShovel(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, 1, par2EnumToolMaterial, blocksEffectiveAgainst);
    }
    
    @Override
    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block == Block.snow ? true : par1Block == Block.blockSnow;
    }
}
