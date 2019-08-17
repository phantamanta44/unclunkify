package xyz.phanta.unclunkify.tile.base;

import io.github.phantamanta44.libnine.tile.L9TileEntityTicking;
import io.github.phantamanta44.libnine.util.data.ByteUtils;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import io.github.phantamanta44.libnine.util.data.serialization.IDatum;
import net.minecraft.util.EnumFacing;

public abstract class TileMachine extends L9TileEntityTicking {

    @AutoSerialize
    private final IDatum<EnumFacing> frontFace = IDatum.of(EnumFacing.NORTH);

    private boolean clientActive = false;
    private EnumFacing clientFace = EnumFacing.NORTH;

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

    @Override
    public void deserBytes(ByteUtils.Reader data) {
        super.deserBytes(data);
        boolean active = isActive();
        EnumFacing front = frontFace.get();
        if (clientActive != active || clientFace != front) {
            world.markBlockRangeForRenderUpdate(pos, pos);
            clientActive = active;
            clientFace = front;
        }
    }

}
