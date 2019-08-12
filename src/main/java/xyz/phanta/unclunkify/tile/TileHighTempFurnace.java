package xyz.phanta.unclunkify.tile;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBrokerDirectional;
import io.github.phantamanta44.libnine.recipe.IRecipeList;
import io.github.phantamanta44.libnine.recipe.input.ItemStackInput;
import io.github.phantamanta44.libnine.recipe.output.ItemStackOutput;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.LazyConstant;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import io.github.phantamanta44.libnine.util.data.serialization.IDatum;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import xyz.phanta.unclunkify.UnclunkConfig;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.recipe.OreDoublingRecipe;
import xyz.phanta.unclunkify.tile.base.TileMachine;

import javax.annotation.Nullable;
import java.util.Objects;

@RegisterTile(Unclunkify.MOD_ID)
public class TileHighTempFurnace extends TileMachine {

    private static final LazyConstant<IRecipeList<ItemStack, ItemStackInput, ItemStackOutput, OreDoublingRecipe>> RECIPE_LIST
            = new LazyConstant<>(() -> LibNine.PROXY.getRecipeManager().getRecipeList(OreDoublingRecipe.class));

    @AutoSerialize
    private final L9AspectSlot inputSlot = new L9AspectSlot.Observable(
            s -> RECIPE_LIST.get().findRecipe(s) != null, (i, o, n) -> invalidateRecipe());
    @AutoSerialize
    private final L9AspectSlot fuelSlot = new L9AspectSlot.Observable(
            TileEntityFurnace::isItemFuel, (i, o, n) -> setDirty());
    @AutoSerialize
    private final L9AspectSlot outputSlot = new L9AspectSlot.Observable(
            s -> false, (i, o, n) -> invalidateRecipe());

    @AutoSerialize
    private final IDatum.OfInt burnTime = IDatum.ofInt(0);
    @AutoSerialize
    private final IDatum.OfInt maxBurnTime = IDatum.ofInt(0);
    @AutoSerialize
    private final IDatum.OfInt heat = IDatum.ofInt(0);
    @AutoSerialize
    private final IDatum.OfFloat work = IDatum.ofFloat(0);

    private boolean recipeDirty = true;
    @Nullable
    private OreDoublingRecipe cachedRecipe = null;
    @Nullable
    private ItemStackOutput cachedOutput = null;
    private boolean recipeValid = false;

    @Override
    protected ICapabilityProvider initCapabilities() {
        CapabilityBrokerDirectional broker = new CapabilityBrokerDirectional()
                .with(EnumFacing.UP, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSlot)
                .with(EnumFacing.DOWN, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, outputSlot);
        for (EnumFacing face : EnumFacing.HORIZONTALS) {
            broker.with(face, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, fuelSlot);
        }
        return broker;
    }

    public IItemHandlerModifiable getInputSlot() {
        return inputSlot;
    }

    public IItemHandlerModifiable getFuelSlot() {
        return fuelSlot;
    }

    public IItemHandlerModifiable getOutputSlot() {
        return outputSlot;
    }

    public float getBurnFraction() {
        int maxTicks = maxBurnTime.getInt();
        return maxTicks == 0 ? 0F : burnTime.getInt() / (float)maxTicks;
    }

    public int getBurnTicks() {
        return burnTime.getInt();
    }

    public float getHeatFraction() {
        return heat.getInt() / (float)UnclunkConfig.highTempFurnaceConfig.maxHeatTicks;
    }

    public float getWorkFraction() {
        return work.get();
    }

    @Override
    public boolean isActive() {
        return burnTime.get() > 0;
    }

    @Override
    protected void tick() {
        if (!world.isRemote) {
            boolean dirty = updateCachedRecipe();
            int currentHeat = heat.getInt();
            if (burnTime.getInt() > 0) {
                burnTime.postincrement(-1);
                currentHeat = Math.min(currentHeat + 1, UnclunkConfig.highTempFurnaceConfig.maxHeatTicks);
            } else {
                int newBurnTime = TileEntityFurnace.getItemBurnTime(fuelSlot.getStackInSlot());
                if (newBurnTime > 0) {
                    newBurnTime *= (float)UnclunkConfig.highTempFurnaceConfig.fuelMultiplier;
                    fuelSlot.getStackInSlot().shrink(1);
                    burnTime.setInt(newBurnTime);
                    maxBurnTime.setInt(newBurnTime);
                    currentHeat = Math.min(currentHeat + 1, UnclunkConfig.highTempFurnaceConfig.maxHeatTicks);
                    dirty = true;
                } else {
                    currentHeat = Math.max(currentHeat - UnclunkConfig.highTempFurnaceConfig.heatDecayRate, 0);
                }
            }
            if (heat.getInt() != currentHeat) {
                heat.setInt(currentHeat);
                dirty = true;
            }
            if (recipeValid) {
                Objects.requireNonNull(cachedRecipe);
                Objects.requireNonNull(cachedOutput);
                float currentWork = work.getFloat();
                if (currentHeat > 0) {
                    currentWork += (currentHeat / (float)UnclunkConfig.highTempFurnaceConfig.maxHeatTicks)
                            * (float)UnclunkConfig.highTempFurnaceConfig.maxHeatWorkRate;
                    if (currentWork >= 1F) {
                        currentWork = 0F;
                        inputSlot.setStackInSlot(cachedRecipe.input().consume(inputSlot.getStackInSlot()));
                        ItemStack outputStack = outputSlot.getStackInSlot();
                        if (outputStack.isEmpty()) {
                            outputSlot.setStackInSlot(cachedOutput.getOutput().copy());
                        } else {
                            outputStack.grow(cachedOutput.getOutput().getCount());
                        }
                        dirty = true;
                    }
                } else {
                    currentWork = Math.max(currentWork - (float)UnclunkConfig.highTempFurnaceConfig.workDecayRate, 0F);
                }
                if (currentWork != work.getFloat()) {
                    work.setFloat(currentWork);
                    dirty = true;
                }
            }
            if (dirty) {
                setDirty();
            }
        }
    }

    private void invalidateRecipe() {
        setDirty();
        recipeDirty = true;
    }

    private boolean updateCachedRecipe() {
        if (recipeDirty) {
            recipeDirty = false;
            boolean resetWork = false;
            ItemStack input = inputSlot.getStackInSlot();
            OreDoublingRecipe newRecipe = RECIPE_LIST.get().findRecipe(input);
            if (!Objects.equals(cachedRecipe, newRecipe)) {
                cachedRecipe = newRecipe;
                cachedOutput = newRecipe != null ? newRecipe.mapToOutput(input) : null;
                resetWork = true;
            } else if (cachedRecipe != null) {
                ItemStackOutput newOutput = cachedRecipe.mapToOutput(input);
                if (cachedOutput != newOutput) {
                    cachedOutput = newOutput;
                    resetWork = true;
                } else if (input.isEmpty()) {
                    resetWork = true;
                }
            }
            recipeValid = cachedOutput != null && cachedOutput.isAcceptable(outputSlot.getStackInSlot());
            if (resetWork || !recipeValid) {
                work.setFloat(0F);
                return true;
            }
        }
        return false;
    }

}
