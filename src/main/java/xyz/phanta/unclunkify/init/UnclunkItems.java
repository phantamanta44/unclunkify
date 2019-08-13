package xyz.phanta.unclunkify.init;

import io.github.phantamanta44.libnine.InitMe;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.item.ItemMisc;

@SuppressWarnings("NullableProblems")
public class UnclunkItems {

    public static ItemMisc MISC;

    @InitMe(Unclunkify.MOD_ID)
    public static void init() {
        MISC = new ItemMisc();
    }

}
