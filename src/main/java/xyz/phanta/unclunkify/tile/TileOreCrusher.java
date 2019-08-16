package xyz.phanta.unclunkify.tile;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.recipe.IRecipeList;
import io.github.phantamanta44.libnine.recipe.input.ItemStackInput;
import io.github.phantamanta44.libnine.recipe.output.ItemStackOutput;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.LazyConstant;
import net.minecraft.item.ItemStack;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.recipe.OreCrushingRecipe;
import xyz.phanta.unclunkify.tile.base.TileProcessing;

@RegisterTile(Unclunkify.MOD_ID)
public class TileOreCrusher extends TileProcessing<OreCrushingRecipe> {

    private static final LazyConstant<IRecipeList<ItemStack, ItemStackInput, ItemStackOutput, OreCrushingRecipe>> RECIPE_LIST
            = new LazyConstant<>(() -> LibNine.PROXY.getRecipeManager().getRecipeList(OreCrushingRecipe.class));

    @Override
    protected IRecipeList<ItemStack, ItemStackInput, ItemStackOutput, OreCrushingRecipe> getRecipeList() {
        return RECIPE_LIST.get();
    }

}
