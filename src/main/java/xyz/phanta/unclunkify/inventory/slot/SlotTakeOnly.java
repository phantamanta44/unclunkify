package xyz.phanta.unclunkify.inventory.slot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotTakeOnly extends SlotItemHandler {

    public SlotTakeOnly(IItemHandler itemHandler, int index, int posX, int posY) {
        super(itemHandler, index, posX, posY);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
