package xyz.phanta.unclunkify;

import net.minecraftforge.common.config.Config;

@Config(modid = Unclunkify.MOD_ID)
public class UnclunkConfig {

    public static final BreakSpeed breakSpeedConfig = new BreakSpeed();
    public static final HighTempFurnace highTempFurnaceConfig = new HighTempFurnace();
    public static final Creeper creeperConfig = new Creeper();

    public static class BreakSpeed {

        @Config.Comment("Causes all block hardness values to be multiplied by a given factor. Set to 1.0 to disable.")
        @Config.RangeDouble(min = 0D)
        public double blockHardnessFactor = 0.75D;

        @Config.Comment({
                "Causes all tool material efficiency values below a threshold to be moved towards the threshold.",
                "newEfficiency = efficiency + factor * (threshold - efficiency)",
                "Set to 0.0 to disable."
        })
        @Config.RangeDouble(min = 0D, max = 1D)
        public double toolEfficiencyFactor = 0.33D;

        @Config.Comment("The threshold for the tool efficiency tweak. For reference, vanilla diamond's efficiency is 8.0.")
        @Config.RangeDouble(min = 0D)
        public double toolEfficiencyThreshold = 8D;

    }

    public static class HighTempFurnace {

        @Config.Comment("The multiplier for furnace fuel value.")
        @Config.RangeDouble(min = 0)
        public double fuelMultiplier = 0.75D;

        @Config.Comment("The number of ticks required to reach max heat.")
        @Config.RangeInt(min = 1)
        public int maxHeatTicks = 1800;

        @Config.Comment("The percentage of work done per tick at max heat.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double maxHeatWorkRate = 0.01D;

        @Config.Comment("The amount of heat that decays each tick the furnace is not fueled.")
        @Config.RangeInt(min = 1)
        public int heatDecayRate = 5;

        @Config.Comment("The percentage of work that decays each tick the furnace is not heated.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double workDecayRate = 0.01D;

    }

    public static class Creeper {

        @Config.Comment("The probability that a creeper will drop gunpowder upon exploding. Set to 0 to disable.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double explodeDropRate = 1D;

        @Config.Comment("The maximum number of gunpowder a creeper will drop.")
        @Config.RangeInt(min = 1)
        public int explodeDropMax = 1;

    }

}
