package xyz.phanta.unclunkify.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.block.Block;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.block.BlockMachine;

@SuppressWarnings("NullableProblems")
public class UnclunkBlocks {

    public static Block MACHINE;

    @InitMe(Unclunkify.MOD_ID)
    public static void init() {
        MACHINE = new BlockMachine();
    }

}
