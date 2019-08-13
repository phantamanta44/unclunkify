package xyz.phanta.unclunkify.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.client.render.RenderThrowableItem;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.entity.EntityMiningExplosive;
import xyz.phanta.unclunkify.item.ItemMisc;

public class UnclunkEntities {

    private static int nextId = 0;

    @InitMe
    public static void init() {
        registerEntity(LangConst.ENTITY_MINING_EXPLOSIVE, EntityMiningExplosive.class, 32, 2, true);
    }

    @SideOnly(Side.CLIENT)
    @InitMe(sides = { Side.CLIENT })
    public static void initRenders() {
        RenderingRegistry.registerEntityRenderingHandler(
                EntityMiningExplosive.class, m -> new RenderThrowableItem<>(m, ItemMisc.Type.MINING_EXPLOSIVE.newStack(1)));
    }

    private static void registerEntity(String name, Class<? extends Entity> clazz,
                                       int trackingRange, int updateFreq, boolean velUpdates) {
        ResourceLocation id = Unclunkify.INSTANCE.newResourceLocation(name);
        EntityRegistry.registerModEntity(id, clazz, id.toString(), nextId++, Unclunkify.INSTANCE,
                trackingRange, updateFreq, velUpdates);
    }

}
