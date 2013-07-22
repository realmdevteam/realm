package mods.realm.dimension.portal;

import java.nio.FloatBuffer;
import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.realm.DefaultProps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.*;

@SideOnly(Side.CLIENT)
public class RealmTileEntityPortalRenderer extends TileEntitySpecialRenderer
{

	private static final ResourceLocation portalrender = new ResourceLocation("realm:/textures/misc/beam.png");
	

	public void renderTileEntityPortalAt(RealmTileEntityPortal tile)
	{
		glDisable(GL_LIGHTING);
		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glColor4f(1, 1, 1, tile.alpha);
        FMLClientHandler.instance().getClient().renderEngine.func_110577_a(portalrender);
		
		int faces = 16;
		glBegin(GL_QUADS);
		for(int i = 0; i < faces; i++)
		{
			glTexCoord2f(0, tile.texPos);
			glVertex3d(tile.radius*Math.cos(Math.PI*2*i/faces), -0.5, tile.radius*Math.sin(Math.PI*2*i/faces));
			glTexCoord2f(0, 100+tile.texPos);
			glVertex3d(tile.radius*Math.cos(Math.PI*2*i/faces), 99.5, tile.radius*Math.sin(Math.PI*2*i/faces));
			glTexCoord2f(16/faces, 100+tile.texPos);
			glVertex3d(tile.radius*Math.cos(Math.PI*2*(i+1)/faces), 99.5, tile.radius*Math.sin(Math.PI*2*(i+1)/faces));
			glTexCoord2f(16/faces, tile.texPos);
			glVertex3d(tile.radius*Math.cos(Math.PI*2*(i+1)/faces), -0.5, tile.radius*Math.sin(Math.PI*2*(i+1)/faces));
		}
		glEnd();
		
		glDisable(GL_BLEND);
		glEnable(GL_CULL_FACE);
		glEnable(GL_LIGHTING);
	}
	
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par8)
	{
		RealmTileEntityPortal portalTile = (RealmTileEntityPortal) tile;
		
		if(portalTile.canstay)
		{
			glPushMatrix();
				glTranslated(x+0.5, y, z+0.5);
				glRotatef(portalTile.rotation, 0, 1, 0);
				this.renderTileEntityPortalAt(portalTile);
			glPopMatrix();
		}
	}
}
