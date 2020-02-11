package xyz.phanta.unclunkify.tile.base;

import io.github.phantamanta44.libnine.util.collection.Accrue;
import net.minecraft.item.ItemStack;

public interface DroppableInventory {

    void accrueDrops(Accrue<ItemStack> drops);

}
