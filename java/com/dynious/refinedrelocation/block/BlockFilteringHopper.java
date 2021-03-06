package com.dynious.refinedrelocation.block;

import com.dynious.refinedrelocation.RefinedRelocation;
import com.dynious.refinedrelocation.api.APIUtils;
import com.dynious.refinedrelocation.lib.GuiIds;
import com.dynious.refinedrelocation.lib.Names;
import com.dynious.refinedrelocation.lib.Resources;
import com.dynious.refinedrelocation.tileentity.TileFilteringHopper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockHopper;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class BlockFilteringHopper extends BlockHopper
{
    @SideOnly(Side.CLIENT)
    public Field iconField;
    @SideOnly(Side.CLIENT)
    public Field iconFieldTop;
    @SideOnly(Side.CLIENT)
    public Field iconFieldInside;

    protected BlockFilteringHopper(int id)
    {
        super(id);
        this.setUnlocalizedName(Names.filteringHopper);
        this.setCreativeTab(RefinedRelocation.tabRefinedRelocation);
        this.setHardness(3.0F).setResistance(8.0F).setStepSound(soundWoodFootstep);

        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
        {
            iconField = ReflectionHelper.findField(BlockHopper.class, ObfuscationReflectionHelper.remapFieldNames(BlockHopper.class.getName(), "hopperIcon", "field_94455_b", "b"));
            iconFieldTop = ReflectionHelper.findField(BlockHopper.class, ObfuscationReflectionHelper.remapFieldNames(BlockHopper.class.getName(), "hopperTopIcon", "field_94456_c", "c"));
            iconFieldInside = ReflectionHelper.findField(BlockHopper.class, ObfuscationReflectionHelper.remapFieldNames(BlockHopper.class.getName(), "hopperInsideIcon", "field_94454_cO", "d"));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileFilteringHopper();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (!world.isRemote)
        {
            if (player.isSneaking())
                APIUtils.openFilteringGUI(player, world, x, y, z);
            else
                FMLNetworkHandler.openGui(player, RefinedRelocation.instance, GuiIds.FILTERING_HOPPER, world, x, y, z);
            return true;
        }
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        try
        {
            iconField.set(this, par1IconRegister.registerIcon(Resources.MOD_ID + ":" + "filteringHopper_outside"));
            iconFieldTop.set(this, par1IconRegister.registerIcon(Resources.MOD_ID + ":" + "filteringHopper_top"));
            iconFieldInside.set(this, par1IconRegister.registerIcon(Resources.MOD_ID + ":" + "filteringHopper_inside"));
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getItemIconName()
    {
        return Resources.MOD_ID + ":" + "filteringHopper";
    }

}
