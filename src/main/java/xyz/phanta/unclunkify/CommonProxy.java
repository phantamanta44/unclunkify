package xyz.phanta.unclunkify;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.recipe.IRecipeList;
import io.github.phantamanta44.libnine.recipe.input.ItemStackInput;
import io.github.phantamanta44.libnine.recipe.output.ItemStackOutput;
import io.github.phantamanta44.libnine.recipe.type.SmeltingRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;
import xyz.phanta.unclunkify.event.CreeperExplosionHandler;
import xyz.phanta.unclunkify.event.WrappedExplosiveHandler;
import xyz.phanta.unclunkify.recipe.OreDoublingRecipe;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CommonProxy {

    private final WrappedExplosiveHandler wrappedExplosiveHandler = new WrappedExplosiveHandler();

    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CreeperExplosionHandler());
        MinecraftForge.EVENT_BUS.register(wrappedExplosiveHandler);
        LibNine.PROXY.getRecipeManager().addType(OreDoublingRecipe.class);
    }

    public void onInit(FMLInitializationEvent event) {
        if (UnclunkConfig.breakSpeedConfig.blockHardnessFactor != 1D) {
            Unclunkify.LOGGER.info("Applying block hardness tweak...");
            float factor = (float)UnclunkConfig.breakSpeedConfig.blockHardnessFactor;
            ForgeRegistries.BLOCKS.forEach(b -> {
                if (b.blockHardness >= 0) {
                    b.blockHardness *= factor;
                }
            });
        }
        if (UnclunkConfig.breakSpeedConfig.toolEfficiencyFactor > 0D) {
            Unclunkify.LOGGER.info("Applying tool material efficiency tweak...");
            float factor = (float)UnclunkConfig.breakSpeedConfig.toolEfficiencyFactor;
            float thresh = (float)UnclunkConfig.breakSpeedConfig.toolEfficiencyThreshold;
            for (Item.ToolMaterial mat : Item.ToolMaterial.values()) {
                if (mat.efficiency < thresh) {
                    mat.efficiency += (thresh - mat.efficiency) * factor;
                }
            }
        }
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        // NO-OP
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        Unclunkify.LOGGER.info("Collecting ore smelting recipes...");
        IRecipeList<ItemStack, ItemStackInput, ItemStackOutput, OreDoublingRecipe> odrl
                = LibNine.PROXY.getRecipeManager().getRecipeList(OreDoublingRecipe.class);
        for (SmeltingRecipe recipe : LibNine.PROXY.getRecipeManager().getRecipeList(SmeltingRecipe.class).recipes()) {
            ItemStack input = recipe.input().getMatcher().getVisual();
            ItemStack output = recipe.mapToOutput(input).getOutput();
            Set<String> oreNames = Arrays.stream(OreDictionary.getOreIDs(output))
                    .mapToObj(OreDictionary::getOreName)
                    .filter(o -> o.startsWith("ingot"))
                    .map(o -> o.substring(5))
                    .collect(Collectors.toSet());
            if (Arrays.stream(OreDictionary.getOreIDs(input))
                    .mapToObj(OreDictionary::getOreName)
                    .filter(o -> o.startsWith("ore"))
                    .map(o -> o.substring(3))
                    .anyMatch(oreNames::contains)) {
                odrl.add(new OreDoublingRecipe(input, output));
            }
        }
    }

    public WrappedExplosiveHandler getWrappedExplosiveHandler() {
        return wrappedExplosiveHandler;
    }

}
