package com.dynious.refinedrelocation.gui.container;

import com.dynious.refinedrelocation.tileentity.TileSortingIronChest;
import cpw.mods.ironchest.ContainerIronChestBase;
import cpw.mods.ironchest.IronChestType;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

@InventoryContainer(showOptions = false)
public class ContainerSortingIronChest extends ContainerIronChestBase
{
    public IInventory chest;

    public ContainerSortingIronChest(EntityPlayer player, IInventory chestInventory, IronChestType type, int xSize, int ySize)
    {
        super(player.inventory, chestInventory, type, xSize, ySize);
        chest = chestInventory;
        ((TileSortingIronChest)chest).getSortingHandler().addCrafter(player);
    }

    @Override
    public void putStackInSlot(int par1, ItemStack par2ItemStack)
    {
        ((TileSortingIronChest)chest).getSortingHandler().putStackInSlot(par2ItemStack, par1);
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer)
    {
        ((TileSortingIronChest)chest).getSortingHandler().removeCrafter(par1EntityPlayer);
        super.onContainerClosed(par1EntityPlayer);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}
