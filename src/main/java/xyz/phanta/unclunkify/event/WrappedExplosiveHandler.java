package xyz.phanta.unclunkify.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WrappedExplosiveHandler {

    private Map<Explosion, Consumer<ExplosionEvent.Detonate>> activeExplosions = new HashMap<>();

    @SubscribeEvent
    public void onExplode(ExplosionEvent.Detonate event) {
        Consumer<ExplosionEvent.Detonate> handler = activeExplosions.get(event.getExplosion());
        if (handler != null) {
            handler.accept(event);
        }
    }

    public void handle(Explosion explosion, Consumer<ExplosionEvent.Detonate> handler) {
        if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(explosion.world, explosion)) {
            activeExplosions.put(explosion, handler);
            try {
                explosion.doExplosionA();
                explosion.doExplosionB(true);
                if (!explosion.world.isRemote) {
                    Vec3d pos = explosion.getPosition();
                    for (EntityPlayer player : explosion.world.playerEntities) {
                        if (player.getDistanceSq(pos.x, pos.y, pos.z) < 4096.0D) {
                            ((EntityPlayerMP)player).connection.sendPacket(
                                    new SPacketExplosion(pos.x, pos.y, pos.z, 2.5F, explosion.getAffectedBlockPositions(),
                                            explosion.getPlayerKnockbackMap().get(player)));
                        }
                    }
                }
            } finally {
                activeExplosions.remove(explosion);
            }
        }
    }

}
