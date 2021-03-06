package com.dynious.refinedrelocation.helper;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class ItemStackHelper
{
    /**
     * compares ItemStack argument to the instance ItemStack; returns true if both ItemStacks are equal
     */
    public static boolean areItemStacksEqual(ItemStack itemStack1, ItemStack itemStack2)
    {
        return itemStack1 == null && itemStack2 == null || (!(itemStack1 == null || itemStack2 == null) && (itemStack1.itemID == itemStack2.itemID && (itemStack1.getItemDamage() == itemStack2.getItemDamage() && (!(itemStack1.stackTagCompound == null && itemStack2.stackTagCompound != null) && (itemStack1.stackTagCompound == null || itemStack1.stackTagCompound.equals(itemStack2.stackTagCompound))))));
    }

    public static ItemStack insert(IInventory inventory, ItemStack itemStack, int side, boolean simulate)
    {
        if (inventory instanceof ISidedInventory && side > -1)
        {
            ISidedInventory isidedinventory = (ISidedInventory)inventory;
            int[] aint = isidedinventory.getAccessibleSlotsFromSide(side);

            for (int j = 0; j < aint.length && itemStack != null && itemStack.stackSize > 0; ++j)
            {
                itemStack = insert(inventory, itemStack, aint[j], side, simulate);
            }
        }
        else
        {
            int k = inventory.getSizeInventory();

            for (int l = 0; l < k && itemStack != null && itemStack.stackSize > 0; ++l)
            {
                itemStack = insert(inventory, itemStack, l, side, simulate);
            }
        }

        if (itemStack != null && itemStack.stackSize == 0)
        {
            itemStack = null;
        }

        return itemStack;
    }

    public static ItemStack insert(IInventory inventory, ItemStack itemStack, int slot, int side, boolean simulate)
    {
        ItemStack itemstack1 = inventory.getStackInSlot(slot);

        if (canInsertItemToInventory(inventory, itemStack, slot, side))
        {
            boolean flag = false;

            if (itemstack1 == null)
            {
                int max = Math.min(itemStack.getMaxStackSize(), inventory.getInventoryStackLimit());
                if (max >= itemStack.stackSize)
                {
                    if (!simulate)
                    {
                        inventory.setInventorySlotContents(slot, itemStack);
                        flag = true;
                    }
                    itemStack = null;
                }
                else
                {
                    if (!simulate)
                    {
                        inventory.setInventorySlotContents(slot, itemStack.splitStack(max));
                        flag = true;
                    }
                    else
                    {
                        itemStack.splitStack(max);
                    }
                }
            }
            else if (areItemStacksEqual(itemstack1, itemStack))
            {
                int max = Math.min(itemStack.getMaxStackSize(), inventory.getInventoryStackLimit());
                if (max > itemstack1.stackSize)
                {
                    int l = Math.min(itemStack.stackSize, max - itemstack1.stackSize);
                    itemStack.stackSize -= l;
                    if (!simulate)
                    {
                        itemstack1.stackSize += l;
                        flag = l > 0;
                    }
                }
            }
            if (flag)
            {
                inventory.onInventoryChanged();
            }
        }

        return itemStack;
    }

    private static boolean canInsertItemToInventory(IInventory inventory, ItemStack itemStack, int slot, int side)
    {
        return inventory.isItemValidForSlot(slot, itemStack) && (!(inventory instanceof ISidedInventory) || ((ISidedInventory) inventory).canInsertItem(slot, itemStack, side));
    }
}
