package xyz.phanta.unclunkify.client.gui;

import io.github.phantamanta44.libnine.util.render.TextureRegion;
import net.minecraft.client.resources.I18n;
import xyz.phanta.unclunkify.client.gui.base.GuiProcessing;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.constant.ResConst;
import xyz.phanta.unclunkify.inventory.ContainerOreCrusher;

public class GuiOreCrusher extends GuiProcessing {

    public GuiOreCrusher(ContainerOreCrusher cont) {
        super(cont, ResConst.GUI_ORE_CRUSHER);
    }

    @Override
    protected String getContainerNameKey() {
        return LangConst.GUI_ORE_CRUSHER;
    }

    @Override
    protected TextureRegion getHeatTexture() {
        return ResConst.GUI_ORE_CRUSHER_HEAT;
    }

    @Override
    protected TextureRegion getBurnTexture() {
        return ResConst.GUI_ORE_CRUSHER_BURN;
    }

    @Override
    protected TextureRegion getProgressTexture() {
        return ResConst.GUI_ORE_CRUSHER_PROGRESS;
    }

    @Override
    protected String formatHeat(String heat) {
        return I18n.format(LangConst.TT_SPIN_UP, heat);
    }

}
