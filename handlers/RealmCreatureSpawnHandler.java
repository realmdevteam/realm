package mods.realm.handlers;

import mods.realm.Realm;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class RealmCreatureSpawnHandler
{
	@ForgeSubscribe
    public void CheckSpawn(LivingSpawnEvent event)
    {
       	if(event.world.provider.dimensionId == Realm.DimensionID){
       		if(event.entity instanceof EntitySquid || event.entity instanceof EntityBat){
				event.setResult(Result.DENY);
       		}
       	}
    }
}