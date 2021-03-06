package com.dynious.refinedrelocation.sorting;

import com.dynious.refinedrelocation.api.filter.IFilterGUI;
import com.google.common.primitives.Booleans;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;

public class FilterStandard implements IFilterGUI
{
    private static Field displayOnCreativeTab = ReflectionHelper.findField(Block.class, ObfuscationReflectionHelper.remapFieldNames(Block.class.getName(), "displayOnCreativeTab", "field_149772_a", "a"));
    private static Field tabToDisplayOn = ReflectionHelper.findField(Item.class, ObfuscationReflectionHelper.remapFieldNames(Item.class.getName(), "tabToDisplayOn", "field_77701_a", "a"));
    private static Field tabIndex = ReflectionHelper.findField(CreativeTabs.class, ObfuscationReflectionHelper.remapFieldNames(CreativeTabs.class.getName(), "tabIndex", "field_78033_n", "n"));

    private static final int FILTER_SIZE = 10;
    private boolean[] customFilters = new boolean[FILTER_SIZE];
    private boolean[] creativeTabs = new boolean[CreativeTabs.creativeTabArray.length];
    private String userFilter = "";

    private boolean blacklists = true;

    public int getSize()
    {
        return creativeTabs.length - 2 + FILTER_SIZE;
    }

    public boolean passesFilter(ItemStack itemStack)
    {
        return isBlacklisting() ? !isInFilter(itemStack) : isInFilter(itemStack);
    }

    private boolean isInFilter(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            String oreName = null;

            if (getUserFilter() != null && !getUserFilter().isEmpty())
            {
                String filter = getUserFilter().toLowerCase().replaceAll("\\s+", "");
                for (String s : filter.split(","))
                {
                    String filterName;
                    if (s.contains("!"))
                    {
                        filterName = oreName = OreDictionary.getOreName(OreDictionary.getOreID(itemStack)).toLowerCase();
                        s = s.replace("!", "");
                    }
                    else
                    {
                        filterName = itemStack.getDisplayName().toLowerCase();
                    }
                    if (s.startsWith("*") && s.length() > 1)
                    {
                        if (s.endsWith("*") && s.length() > 2)
                        {
                            if (filterName.contains(s.substring(1, s.length() - 1)))
                                return true;
                        }
                        else if (filterName.endsWith(s.substring(1)))
                            return true;
                    }
                    else if (s.endsWith("*") && s.length() > 1)
                    {
                        if (filterName.startsWith(s.substring(0, s.length() - 1)))
                            return true;
                    }
                    else
                    {
                        if (filterName.equalsIgnoreCase(s))
                            return true;
                    }
                }
            }

            if (Booleans.contains(customFilters, true))
            {
                if (oreName == null)
                {
                    oreName = OreDictionary.getOreName(OreDictionary.getOreID(itemStack)).toLowerCase();
                }

                if (customFilters[0] && (oreName.contains("ingot") || itemStack.itemID == Item.ingotIron.itemID || itemStack.itemID == Item.ingotGold.itemID))
                    return true;
                if (customFilters[1] && oreName.contains("ore"))
                    return true;
                if (customFilters[2] && oreName.contains("log"))
                    return true;
                if (customFilters[3] && oreName.contains("plank"))
                    return true;
                if (customFilters[4] && oreName.contains("dust"))
                    return true;
                if (customFilters[5] && oreName.contains("crushed") && !oreName.contains("purified"))
                    return true;
                if (customFilters[6] && oreName.contains("purified"))
                    return true;
                if (customFilters[7] && oreName.contains("plate"))
                    return true;
                if (customFilters[8] && oreName.contains("gem"))
                    return true;
                if (customFilters[9] && itemStack.getItem() instanceof ItemFood)
                    return true;
            }

            if (Booleans.contains(creativeTabs, true))
            {
                try
                {
                    CreativeTabs tab;

                    if (itemStack.getItem() instanceof ItemBlock)
                    {
                        tab = (CreativeTabs) displayOnCreativeTab.get(Block.blocksList[itemStack.itemID]);
                    }
                    else
                    {
                        tab = (CreativeTabs) tabToDisplayOn.get(Item.itemsList[itemStack.itemID]);
                    }
                    if (tab != null)
                    {
                        int index = tabIndex.getInt(tab);

                        for (int i = 0; i < creativeTabs.length; i++)
                        {
                            if (creativeTabs[i] && index == i)
                            {
                                return true;
                            }
                        }
                    }
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void setValue(int place, boolean value)
    {
        if (place < customFilters.length)
        {
            customFilters[place] = value;
        }
        else
        {
            creativeTabs[getCreativeTab(place)] = value;
        }
    }

    public boolean getValue(int place)
    {
        if (place < customFilters.length)
        {
            return customFilters[place];
        }
        else
        {
            return creativeTabs[getCreativeTab(place)];
        }
    }

    public String getName(int place)
    {
        switch (place)
        {
            case 0:
                return "All Ingots";
            case 1:
                return "All Ores";
            case 2:
                return "All Logs";
            case 3:
                return "All Planks";
            case 4:
                return "All Dusts";
            case 5:
                return "All Crushed Ores";
            case 6:
                return "All Purified Ores";
            case 7:
                return "All Plates";
            case 8:
                return "All Gems";
            case 9:
                return "All Food";
            default:
                return I18n.getString(CreativeTabs.creativeTabArray[getCreativeTab(place)].getTranslatedTabLabel());
        }
    }

    public int getCreativeTab(int place)
    {
        int index = place - FILTER_SIZE;
        if (index >= 5)
            index++;
        if (index >= 11)
            index++;
        return index;
    }

    public boolean isBlacklisting()
    {
        return blacklists;
    }

    public void setBlacklists(boolean blacklists)
    {
        this.blacklists = blacklists;
    }

    public String getUserFilter()
    {
        return userFilter;
    }

    public void setUserFilter(String userFilter)
    {
        this.userFilter = userFilter;
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        compound.setString("userFilter", getUserFilter());
        compound.setBoolean("blacklists", isBlacklisting());
        for (int i = 0; i < customFilters.length; i++)
        {
            compound.setBoolean("cumstomFilters" + i, customFilters[i]);
        }
        for (int i = 0; i < creativeTabs.length; i++)
        {
            compound.setBoolean("creativeTabs" + i, creativeTabs[i]);
        }
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        setUserFilter(compound.getString("userFilter"));
        setBlacklists(compound.getBoolean("blacklists"));
        for (int i = 0; i < customFilters.length; i++)
        {
            customFilters[i] = compound.getBoolean("cumstomFilters" + i);
        }
        for (int i = 0; i < creativeTabs.length; i++)
        {
            creativeTabs[i] = compound.getBoolean("creativeTabs" + i);
        }
    }
}
