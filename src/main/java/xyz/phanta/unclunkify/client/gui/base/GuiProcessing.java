package xyz.phanta.unclunkify.client.gui.base;

import io.github.phantamanta44.libnine.client.gui.L9GuiContainer;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import io.github.phantamanta44.libnine.util.render.GuiUtils;
import io.github.phantamanta44.libnine.util.render.TextureRegion;
import io.github.phantamanta44.libnine.util.render.TextureResource;
import net.minecraft.client.resources.I18n;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.inventory.base.ContainerProcessing;

public abstract class GuiProcessing extends L9GuiContainer {

    private final ContainerProcessing cont;

    public GuiProcessing(ContainerProcessing cont, TextureResource bg) {
        super(cont, bg.getTexture());
        this.cont = cont;
    }

    @Override
    public void drawForeground(float partialTicks, int mX, int mY) {
        super.drawForeground(partialTicks, mX, mY);
        drawContainerName(I18n.format(getContainerNameKey()));
        float heat = cont.getHeatFraction(), progress = cont.getProgress();
        getHeatTexture().drawPartial(58, 37, 0F, 1F - heat, 1F, 1F);
        getBurnTexture().drawPartial(45, 54, 0F, 1F - cont.getBurnFraction(), 1F, 1F);
        getProgressTexture().drawPartial(80, 35, 0F, 0F, progress, 1F);
        if (GuiUtils.isMouseOver(57, 36, 13, 13, mX, mY)) {
            drawTooltip(formatHeat(FormatUtils.formatPercentage(heat)), mX, mY);
        } else if (GuiUtils.isMouseOver(44, 53, 10, 15, mX, mY) || GuiUtils.isMouseOver(74, 53, 10, 15, mX, mY)) {
            //noinspection IntegerDivisionInFloatingPointContext
            drawTooltip(I18n.format(LangConst.TT_BURN_TIME, (cont.getBurnTicks() / 2) / 10F), mX, mY);
        } else if (GuiUtils.isMouseOver(79, 34, 24, 17, mX, mY)) {
            drawTooltip(I18n.format(LangConst.TT_PROGRESS, FormatUtils.formatPercentage(progress)), mX, mY);
        }
    }

    protected abstract String getContainerNameKey();

    protected abstract TextureRegion getHeatTexture();

    protected abstract TextureRegion getBurnTexture();

    protected abstract TextureRegion getProgressTexture();

    protected abstract String formatHeat(String heat);

}
