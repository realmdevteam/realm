package mods.realm.proxy;

import mods.realm.network.RealmPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

public class CommonProxy
{
	
	public void registerRenderers()
	{
		
	}
	
	public void installSounds()
	{
		
	}
	
	public String getMinecraftVersion() {
		return Loader.instance().getMinecraftModContainer().getVersion();
	}

	/* INSTANCES */
	public Object getClient() {
		return null;
	}

	public World getClientWorld() {
		return null;
	}

	/* SIMULATION */
	public boolean isSimulating(World world) {
		return !world.isRemote;
	}

	public boolean isRenderWorld(World world) {
		return world.isRemote;
	}

	public void sendToPlayer(EntityPlayer entityplayer, RealmPacket packet)
	{
		EntityPlayerMP player = (EntityPlayerMP) entityplayer;
		player.playerNetServerHandler.sendPacketToPlayer(packet.getPacket());
	}
	
	public void sendToPlayers(Packet packet, World world, int x, int y, int z, int maxDistance)
	{
		if(packet != null)
		{
			for (int j = 0; j < world.playerEntities.size(); j++)
			{
				EntityPlayerMP player = (EntityPlayerMP) world.playerEntities.get(j);
				if((player.posX - x)*(player.posX - x) + (player.posY - y)*(player.posY - y) + (player.posZ - z)*(player.posZ - z) <= maxDistance*maxDistance)
				{
					player.playerNetServerHandler.sendPacketToPlayer(packet);
				}
			}
		}
	}
	
	public void sendToServer(Packet packet)
	{
		
	}
	
	public String playerName() {
		return "";
	}
}
