package xyz.phanta.unclunkify.inventory.base;

import io.github.phantamanta44.libnine.gui.L9Container;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.items.SlotItemHandler;
import xyz.phanta.unclunkify.inventory.slot.SlotTakeOnly;
import xyz.phanta.unclunkify.tile.base.TileProcessing;

public abstract class ContainerProcessing extends L9Container {

    private final TileProcessing<?> tile;

    public ContainerProcessing(TileProcessing<?> tile, InventoryPlayer ipl) {
        super(ipl);
        this.tile = tile;
        addSlotToContainer(new SlotItemHandler(tile.getInputSlot(), 0, 56, 17));
        addSlotToContainer(new SlotItemHandler(tile.getFuelSlot(), 0, 56, 53));
        addSlotToContainer(new SlotTakeOnly(tile.getOutputSlot(), 0, 116, 35));
    }

    public float getHeatFraction() {
        return tile.getHeatFraction();
    }

    public float getBurnFraction() {
        return tile.getBurnFraction();
    }

    public int getBurnTicks() {
        return tile.getBurnTicks();
    }

    public float getProgress() {
        return tile.getWorkFraction();
    }

}
