package xyz.phanta.unclunkify;

import io.github.phantamanta44.libnine.Virtue;
import io.github.phantamanta44.libnine.util.L9CreativeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import xyz.phanta.unclunkify.item.ItemMisc;

@Mod(modid = Unclunkify.MOD_ID, version = Unclunkify.VERSION, useMetadata = true)
public class Unclunkify extends Virtue {

    public static final String MOD_ID = "unclunkify";
    public static final String VERSION = "1.0.2";

    @SuppressWarnings("NullableProblems")
    @Mod.Instance(MOD_ID)
    public static Unclunkify INSTANCE;

    @SuppressWarnings("NullableProblems")
    @SidedProxy(
            clientSide = "xyz.phanta.unclunkify.client.ClientProxy",
            serverSide = "xyz.phanta.unclunkify.CommonProxy")
    public static CommonProxy PROXY;

    @SuppressWarnings("NullableProblems")
    public static Logger LOGGER;

    public Unclunkify() {
        super(MOD_ID, new L9CreativeTab("unclunkify", () -> ItemMisc.Type.MINING_EXPLOSIVE.newStack(1)));
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        PROXY.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        PROXY.onLoadComplete(event);
    }

}
