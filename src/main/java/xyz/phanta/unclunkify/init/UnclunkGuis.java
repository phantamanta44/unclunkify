package xyz.phanta.unclunkify.init;

import io.github.phantamanta44.libnine.InitMe;
import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import io.github.phantamanta44.libnine.tile.L9TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.client.gui.GuiHighTempFurnace;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.inventory.ContainerHighTempFurnace;

import java.util.Objects;

public class UnclunkGuis {

    public static final GuiIdentity<ContainerHighTempFurnace, GuiHighTempFurnace> HIGH_TEMP_FURNACE
            = new GuiIdentity<>(LangConst.CONT_HIGH_TEMP_FURNACE, ContainerHighTempFurnace.class);

    @InitMe(Unclunkify.MOD_ID)
    public static void initCommon() {
        LibNine.PROXY.getRegistrar().queueGuiServerReg(HIGH_TEMP_FURNACE,
                (p, w, x, y, z) -> new ContainerHighTempFurnace(getTile(w, x, y, z), p.inventory));
    }

    @SideOnly(Side.CLIENT)
    @InitMe(value = Unclunkify.MOD_ID, sides = { Side.CLIENT })
    public static void initClient() {
        LibNine.PROXY.getRegistrar().queueGuiClientReg(HIGH_TEMP_FURNACE,
                (c, p, w, x, y, z) -> new GuiHighTempFurnace(c));
    }

    @SuppressWarnings("unchecked")
    private static <T extends L9TileEntity> T getTile(IBlockAccess world, int x, int y, int z) {
        return (T)Objects.requireNonNull(world.getTileEntity(new BlockPos(x, y, z)));
    }

}
