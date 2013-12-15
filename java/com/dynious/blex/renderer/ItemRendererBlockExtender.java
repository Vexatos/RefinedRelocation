package com.dynious.blex.renderer;

import com.dynious.blex.lib.Resources;
import com.dynious.blex.model.ModelBlockExtender;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class ItemRendererBlockExtender implements IItemRenderer
{
    private ModelBlockExtender modelBlockExtender = new ModelBlockExtender();

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        switch (type)
        {
            case ENTITY:
            {
                render(-0.5F, 0.0F, -0.5F);
                return;
            }
            case EQUIPPED:
            {
                render(0.0F, 0.0F, 0.0F);
                return;
            }
            case EQUIPPED_FIRST_PERSON:
            {
                render(0.5F, 0.5F, 0.3F);
                return;
            }
            case INVENTORY:
            {
                render(0.5F, 0.3F, 0.5F);
                return;
            }
            default:
                return;
        }
    }

    public void render(float x, float y, float z)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GL11.glPushMatrix();

        GL11.glTranslated(x + 0.5F, y + 1.5F, z + 0.5F);
        GL11.glRotatef(180F, 1F, 0F, 0F);
        GL11.glScalef(1F, 1F, 1F);

        FMLClientHandler.instance().getClient().renderEngine.bindTexture(Resources.MODEL_TEXTURE_BLOCK_EXTENDER);

        modelBlockExtender.renderBase();
        modelBlockExtender.renderPilars();

        GL11.glPushMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glColor4f(1F, 1F, 1F, 0.2F);

        modelBlockExtender.renderSides();

        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopMatrix();

        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }
}
