package mods.realm.render;

import mods.realm.model.ModelBison;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBison extends RenderLiving
{
    private ModelBison model;

    public RenderBison()
    {
        super(new ModelBison(), 0.25F);
        this.model = (ModelBison)super.mainModel;
        this.setRenderPassModel(this.model);
    }

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return null;
	}
}