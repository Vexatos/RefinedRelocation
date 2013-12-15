package com.dynious.blex.lib;

import net.minecraft.util.ResourceLocation;

public class Resources
{
    public static final String MOD_ID = Reference.modid.toLowerCase();
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    public static final String MODEL_SHEET_LOCATION = "textures/model/";
    public static final String MODEL_LOCATION = "/assets/" + MOD_ID + "/models/";

    public static final ResourceLocation GUI_ADVANCED_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, GUI_SHEET_LOCATION + "advancedBlockExtender.png");
    public static final ResourceLocation GUI_FILTERED_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, GUI_SHEET_LOCATION + "filteredBlockExtender.png");
    public static final ResourceLocation GUI_ADVANCED_FILTERED_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, GUI_SHEET_LOCATION + "advancedFilteredBlockExtender.png");

    public static final ResourceLocation MODEL_TEXTURE_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, MODEL_SHEET_LOCATION + "blockExtender.png");
    public static final ResourceLocation MODEL_TEXTURE_BASE_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, MODEL_SHEET_LOCATION + "blockExtenderBase.png");
    public static final ResourceLocation MODEL_TEXTURE_PILAR_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, MODEL_SHEET_LOCATION + "blockExtenderPilar.png");
    public static final ResourceLocation MODEL_TEXTURE_SIDE_BLOCK_EXTENDER = new ResourceLocation(MOD_ID, MODEL_SHEET_LOCATION + "blockExtenderSide.png");

    public static final String MODEL_BLOCK_EXTENDER = MODEL_LOCATION + "blockExtender4.obj";
}


