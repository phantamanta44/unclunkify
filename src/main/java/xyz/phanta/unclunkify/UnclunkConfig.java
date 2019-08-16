package xyz.phanta.unclunkify;

import net.minecraftforge.common.config.Config;

@Config(modid = Unclunkify.MOD_ID)
public class UnclunkConfig {

    public static final BreakSpeed breakSpeedConfig = new BreakSpeed();
    public static final HighTempFurnace highTempFurnaceConfig = new HighTempFurnace();
    public static final OreCrusher oreCrusherConfig = new OreCrusher();
    public static final MiningExplosive miningExplosiveConfig = new MiningExplosive();
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

    public static class OreCrusher {

        @Config.Comment("The multiplier for furnace fuel value.")
        @Config.RangeDouble(min = 0)
        public double fuelMultiplier = 1D;

        @Config.Comment("The number of ticks required to reach max spin-up.")
        @Config.RangeInt(min = 1)
        public int maxSpinUpTicks = 1800;

        @Config.Comment("The percentage of work done per tick at max spin-up.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double maxSpinUpWorkRate = 0.01D;

        @Config.Comment("The amount of spin-up that decays each tick the crusher is not fueled.")
        @Config.RangeInt(min = 1)
        public int spinUpDecayRate = 5;

        @Config.Comment("The percentage of work that decays each tick the crusher is not spun-up.")
        @Config.RangeDouble(min = 0D, max = 1D)
        public double workDecayRate = 0.01D;

    }

    public static class MiningExplosive {

        @Config.Comment({
                "The speed at which the explosive is thrown. For comparison, vanilla snowballs have speed 1.5.",
                "Note that this also determines how far the projectile can travel."
        })
        public double throwSpeed = 1.0D;

        @Config.Comment("The strength of the explosion. For comparison, vanilla TNT has strength 4.0.")
        @Config.RangeDouble(min = 0)
        public double explosionStrength = 2.5D;

        @Config.Comment("The level of fortune used by mining explosive drops. Set to 0 to disable fortune effect.")
        @Config.RangeInt(min = 0, max = 3)
        public int fortuneLevel = 2;

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
