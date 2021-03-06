package com.lycanitesmobs.core.renderer;

import com.lycanitesmobs.core.entity.EntityCreatureBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector4f;

@SideOnly(Side.CLIENT)
public class LayerShield extends LayerBase {

    // ==================================================
    //                   Constructor
    // ==================================================
    public LayerShield(RenderCreature renderer) {
        super(renderer);
    }


    // ==================================================
    //                  Render Layer
    // ==================================================
    @Override
    public boolean canRenderLayer(EntityCreatureBase entity, float scale) {
        if(!super.canRenderLayer(entity, scale))
            return false;
        return entity.isBlocking();
    }


    // ==================================================
    //                      Visuals
    // ==================================================
    @Override
    public boolean canRenderPart(String partName, EntityCreatureBase entity, boolean trophy) {
        return "shield".equals(partName);
    }

    @Override
    public Vector4f getPartColor(String partName, EntityCreatureBase entity, boolean trophy) {
        return new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void onRenderStart(String partName, Entity entity, boolean trophy) {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
    }

    @Override
    public void onRenderFinish(String partName, Entity entity, boolean trophy) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
