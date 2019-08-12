package xyz.phanta.unclunkify.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.unclunkify.UnclunkConfig;

import java.util.Objects;

public class CreeperExplosionHandler {

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            Explosion explosion = event.getExplosion();
            EntityLivingBase entity = explosion.getExplosivePlacedBy();
            if (entity instanceof EntityCreeper) {
                double prob = UnclunkConfig.creeperConfig.explodeDropRate;
                if (prob > 0D && world.rand.nextDouble() < prob) {
                    EntityItem dropped = Objects.requireNonNull(entity.entityDropItem(
                            new ItemStack(Items.GUNPOWDER, 1 + world.rand.nextInt(UnclunkConfig.creeperConfig.explodeDropMax)),
                            entity.getEyeHeight()));
                    dropped.setVelocity(
                            world.rand.nextGaussian() * 0.08D,
                            0.08D + world.rand.nextGaussian() * 0.05D,
                            world.rand.nextGaussian() * 0.08D);
                }
            }
        }
    }

}
