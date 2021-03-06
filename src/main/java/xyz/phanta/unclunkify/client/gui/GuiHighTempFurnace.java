package xyz.phanta.unclunkify.client.gui;

import io.github.phantamanta44.libnine.util.render.TextureRegion;
import net.minecraft.client.resources.I18n;
import xyz.phanta.unclunkify.client.gui.base.GuiProcessing;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.constant.ResConst;
import xyz.phanta.unclunkify.inventory.ContainerHighTempFurnace;

public class GuiHighTempFurnace extends GuiProcessing {

    public GuiHighTempFurnace(ContainerHighTempFurnace cont) {
        super(cont, ResConst.GUI_HIGH_TEMP_FURNACE);
    }

    @Override
    protected String getContainerNameKey() {
        return LangConst.GUI_HIGH_TEMP_FURNACE;
    }

    @Override
    protected TextureRegion getHeatTexture() {
        return ResConst.GUI_HIGH_TEMP_FURNACE_HEAT;
    }

    @Override
    protected TextureRegion getBurnTexture() {
        return ResConst.GUI_HIGH_TEMP_FURNACE_BURN;
    }

    @Override
    protected TextureRegion getProgressTexture() {
        return ResConst.GUI_HIGH_TEMP_FURNACE_PROGRESS;
    }

    @Override
    protected String formatHeat(String heat) {
        return I18n.format(LangConst.TT_HEAT, heat);
    }

}
