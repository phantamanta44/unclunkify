package xyz.phanta.unclunkify.tile.base;

import io.github.phantamanta44.libnine.tile.L9TileEntityTicking;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import io.github.phantamanta44.libnine.util.data.serialization.IDatum;
import net.minecraft.util.EnumFacing;

public abstract class TileMachine extends L9TileEntityTicking {

    @AutoSerialize
    private final IDatum<EnumFacing> frontFace = IDatum.of(EnumFacing.NORTH);

    public TileMachine() {
        markRequiresSync();
        setInitialized();
    }

    public EnumFacing getFrontFace() {
        return frontFace.get();
    }

    public void setFrontFace(EnumFacing face) {
        frontFace.set(face);
    }

    public abstract boolean isActive();

}
