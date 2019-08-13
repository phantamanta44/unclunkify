package xyz.phanta.unclunkify.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class RenderThrowableItem<T extends Entity> extends RenderSnowball<T> {

    private final ItemStack stack;

    public RenderThrowableItem(RenderManager renderManager, ItemStack stack) {
        super(renderManager, stack.getItem(), Minecraft.getMinecraft().getRenderItem());
        this.stack = stack;
    }

    @Override
    public ItemStack getStackToRender(T entityIn) {
        return stack;
    }

}
