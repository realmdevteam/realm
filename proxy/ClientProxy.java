package mods.realm.proxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import mods.realm.Realm;
import mods.realm.dimension.portal.RealmTileEntityPortal;
import mods.realm.dimension.portal.RealmTileEntityPortalRenderer;
import mods.realm.entity.EntityBison;
import mods.realm.handlers.RealmClientTickHandler;
import mods.realm.render.RenderBison;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	/* INSTANCES */
	@Override
	public Object getClient() {
		return FMLClientHandler.instance().getClient();
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void registerRenderers() {

		ClientRegistry.bindTileEntitySpecialRenderer(
				RealmTileEntityPortal.class,
				new RealmTileEntityPortalRenderer());

		RenderingRegistry.registerEntityRenderingHandler(EntityBison.class,
				new RenderBison());

		TickRegistry.registerTickHandler(new RealmClientTickHandler(),
				Side.CLIENT);
	}

	@Override
	public void installSounds() {
		this.installSound("resources/streaming/FluteTrack.ogg");
		this.installSound("resources/streaming/Uranus Paradise Short.ogg");
	}

	private void installSound(String filename) {
		File soundFile = new File(ModLoader.getMinecraftInstance().mcDataDir,
				filename);

		if (!soundFile.exists()) {
			try {
				String srcPath = filename;
				InputStream inStream = Realm.class.getResourceAsStream(srcPath);
				if (inStream == null) {
					throw new IOException();
				}

				if (!soundFile.getParentFile().exists()) {
					soundFile.getParentFile().mkdirs();
				}

				BufferedInputStream fileIn = new BufferedInputStream(inStream);
				BufferedOutputStream fileOut = new BufferedOutputStream(
						new FileOutputStream(soundFile));
				byte[] buffer = new byte[1024];
				int n = 0;
				while (-1 != (n = fileIn.read(buffer))) {
					fileOut.write(buffer, 0, n);
				}
				fileIn.close();
				fileOut.close();
			} catch (IOException ex) {

			}
		}

		if (soundFile.canRead() && soundFile.isFile()) {
			System.out.println(Realm.consolePrefix + "Loaded soundfile: "
					+ soundFile);
		} else {
			System.err.println(Realm.consolePrefix
					+ "Could not load soundfile: " + soundFile);
		}
	}

	/* NETWORKING */
	@Override
	public void sendToServer(Packet packet) {
		FMLClientHandler.instance().getClient().getNetHandler()
				.addToSendQueue(packet);
	}

	/*
	 * @Override public void sendToPlayer(EntityPlayer entityplayer, RealmPacket
	 * packet){}
	 * 
	 * @Override public void sendToPlayers(Packet packet, World world, int x,
	 * int y, int z, int maxDistance){}
	 */

	@Override
	public String playerName() {
		return FMLClientHandler.instance().getClient().thePlayer.username;
	}
}
