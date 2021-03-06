package mods.realm.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

import mods.realm.Realm;
import mods.realm.api.Plants;
import mods.realm.block.RealmBlockSaplingRealmWood;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.sound.PlayBackgroundMusicEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.src.*;

public class RealmBonemealHandler {
	
    protected static Random itemRand = new Random();
    
	@ForgeSubscribe
	public void onUseBonemeal(BonemealEvent event) {
		if (event.ID == Realm.blockSaplingRealmWood.blockID) {
			if (!event.world.isRemote) {
				((RealmBlockSaplingRealmWood) Realm.blockSaplingRealmWood).markOrGrowMarked(event.world, event.X, event.Y, event.Z, new Random());
				event.setResult(Result.ALLOW);

               
			}
		}
		if(event.ID == Realm.blockGrass.blockID || event.ID == Realm.blockDirt.blockID){
			if(!event.world.isRemote){
				label102:
				for (int i1 = 0; i1 < 128; ++i1)
                {
                    int j1 = event.X;
                    int k1 = event.Y + 1;
                    int l1 = event.Z;

                    for (int i2 = 0; i2 < i1 / 16; ++i2)
                    {
                        j1 += itemRand.nextInt(3) - 1;
                        k1 += (itemRand.nextInt(3) - 1) * itemRand.nextInt(3) / 2;
                        l1 += itemRand.nextInt(3) - 1;

                        if (event.world.getBlockId(j1, k1 - 1, l1) != Realm.blockGrass.blockID || event.world.isBlockNormalCube(j1, k1, l1))
                        {
                            continue label102;
                        }
                    }

                    if (event.world.getBlockId(j1, k1, l1) == 0)
                    {
                    	Plants.plantGrass(event.world, j1, k1, l1);
                    }
                }
			}
		}  
    }
}
