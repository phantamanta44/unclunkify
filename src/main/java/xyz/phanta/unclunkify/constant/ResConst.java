package xyz.phanta.unclunkify.constant;

import io.github.phantamanta44.libnine.util.render.TextureRegion;
import io.github.phantamanta44.libnine.util.render.TextureResource;
import xyz.phanta.unclunkify.Unclunkify;

public class ResConst {

    private static final String TEXTURES_KEY = "textures/";

    private static final String GUI_KEY = TEXTURES_KEY + "gui/";

    public static final TextureResource GUI_HIGH_TEMP_FURNACE
            = Unclunkify.INSTANCE.newTextureResource(GUI_KEY + "high_temp_furnace.png", 256, 256);
    public static final TextureRegion GUI_HIGH_TEMP_FURNACE_HEAT = GUI_HIGH_TEMP_FURNACE.getRegion(176, 0, 12, 12);
    public static final TextureRegion GUI_HIGH_TEMP_FURNACE_BURN = GUI_HIGH_TEMP_FURNACE.getRegion(176, 12, 39, 14);
    public static final TextureRegion GUI_HIGH_TEMP_FURNACE_PROGRESS = GUI_HIGH_TEMP_FURNACE.getRegion(176, 26, 22, 16);

}
