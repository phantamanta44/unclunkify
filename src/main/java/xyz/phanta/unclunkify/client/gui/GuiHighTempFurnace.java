package xyz.phanta.unclunkify.client.gui;

import io.github.phantamanta44.libnine.client.gui.L9GuiContainer;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import io.github.phantamanta44.libnine.util.render.GuiUtils;
import net.minecraft.client.resources.I18n;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.constant.ResConst;
import xyz.phanta.unclunkify.inventory.ContainerHighTempFurnace;

public class GuiHighTempFurnace extends L9GuiContainer {

    private final ContainerHighTempFurnace cont;

    public GuiHighTempFurnace(ContainerHighTempFurnace cont) {
        super(cont, ResConst.GUI_HIGH_TEMP_FURNACE.getTexture());
        this.cont = cont;
    }

    @Override
    public void drawForeground(float partialTicks, int mX, int mY) {
        super.drawForeground(partialTicks, mX, mY);
        drawContainerName(I18n.format(LangConst.GUI_HIGH_TEMP_FURNACE));
        float heat = cont.getHeatFraction(), progress = cont.getProgress();
        ResConst.GUI_HIGH_TEMP_FURNACE_HEAT.drawPartial(58, 37, 0F, 1F - heat, 1F, 1F);
        ResConst.GUI_HIGH_TEMP_FURNACE_BURN.drawPartial(45, 54, 0F, 1F - cont.getBurnFraction(), 1F, 1F);
        ResConst.GUI_HIGH_TEMP_FURNACE_PROGRESS.drawPartial(80, 35, 0F, 0F, progress, 1F);
        if (GuiUtils.isMouseOver(57, 36, 13, 13, mX, mY)) {
            drawTooltip(I18n.format(LangConst.TT_HEAT, FormatUtils.formatPercentage(heat)), mX, mY);
        } else if (GuiUtils.isMouseOver(44, 53, 10, 15, mX, mY) || GuiUtils.isMouseOver(74, 53, 10, 15, mX, mY)) {
            //noinspection IntegerDivisionInFloatingPointContext
            drawTooltip(I18n.format(LangConst.TT_BURN_TIME, (cont.getBurnTicks() / 2) / 10F), mX, mY);
        } else if (GuiUtils.isMouseOver(79, 34, 24, 17, mX, mY)) {
            drawTooltip(I18n.format(LangConst.TT_PROGRESS, FormatUtils.formatPercentage(progress)), mX, mY);
        }
    }

}
