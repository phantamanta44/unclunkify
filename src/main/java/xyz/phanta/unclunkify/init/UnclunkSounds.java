package xyz.phanta.unclunkify.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.util.SoundEvent;
import xyz.phanta.unclunkify.Unclunkify;

@SuppressWarnings("NullableProblems")
public class UnclunkSounds {

    public static SoundEvent MACHINE_CRUSHING;

    @InitMe
    public static void init() {
        MACHINE_CRUSHING = Unclunkify.INSTANCE.newSoundEvent("unclunkify.machine.crushing");
    }

}
