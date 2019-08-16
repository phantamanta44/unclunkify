package xyz.phanta.unclunkify.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import xyz.phanta.unclunkify.inventory.base.ContainerProcessing;
import xyz.phanta.unclunkify.tile.TileHighTempFurnace;

public class ContainerHighTempFurnace extends ContainerProcessing {

    public ContainerHighTempFurnace(TileHighTempFurnace tile, InventoryPlayer ipl) {
        super(tile, ipl);
    }

}
