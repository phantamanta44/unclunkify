package xyz.phanta.unclunkify.recipe;

import io.github.phantamanta44.libnine.recipe.type.SmeltingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class OreDoublingRecipe extends SmeltingRecipe {

    public OreDoublingRecipe(ItemStack input, ItemStack output) {
        super(input, ItemHandlerHelper.copyStackWithSize(output, output.getCount() * 2));
    }

}
