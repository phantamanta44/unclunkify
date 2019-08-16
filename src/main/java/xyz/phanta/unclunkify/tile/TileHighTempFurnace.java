package xyz.phanta.unclunkify.tile;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.recipe.IRecipeList;
import io.github.phantamanta44.libnine.recipe.input.ItemStackInput;
import io.github.phantamanta44.libnine.recipe.output.ItemStackOutput;
import io.github.phantamanta44.libnine.recipe.type.SmeltingRecipe;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.LazyConstant;
import net.minecraft.item.ItemStack;
import xyz.phanta.unclunkify.UnclunkConfig;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.tile.base.TileProcessing;

@RegisterTile(Unclunkify.MOD_ID)
public class TileHighTempFurnace extends TileProcessing<SmeltingRecipe> {

    private static final LazyConstant<IRecipeList<ItemStack, ItemStackInput, ItemStackOutput, SmeltingRecipe>> RECIPE_LIST
            = new LazyConstant<>(() -> LibNine.PROXY.getRecipeManager().getRecipeList(SmeltingRecipe.class));

    @Override
    protected IRecipeList<ItemStack, ItemStackInput, ItemStackOutput, SmeltingRecipe> getRecipeList() {
        return RECIPE_LIST.get();
    }

    @Override
    protected float getFuelMultiplier() {
        return (float)UnclunkConfig.highTempFurnaceConfig.fuelMultiplier;
    }

    @Override
    protected int getMaxHeatTicks() {
        return UnclunkConfig.highTempFurnaceConfig.maxHeatTicks;
    }

    @Override
    protected float getMaxWorkRate() {
        return (float)UnclunkConfig.highTempFurnaceConfig.maxHeatWorkRate;
    }

    @Override
    protected int getHeatDecayRate() {
        return UnclunkConfig.highTempFurnaceConfig.heatDecayRate;
    }

    @Override
    protected float getWorkDecayRate() {
        return (float)UnclunkConfig.highTempFurnaceConfig.workDecayRate;
    }

}
