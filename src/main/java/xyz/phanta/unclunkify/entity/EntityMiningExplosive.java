package xyz.phanta.unclunkify.entity;

import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import xyz.phanta.unclunkify.UnclunkConfig;
import xyz.phanta.unclunkify.Unclunkify;

import javax.annotation.Nullable;
import java.util.List;

public class EntityMiningExplosive extends EntityThrowable {

    public EntityMiningExplosive(World world, Vec3d pos, Vec3d vel, @Nullable EntityLivingBase thrower) {
        super(world, pos.x, pos.y, pos.z);
        initVelocity(vel);
        if (thrower != null) {
            this.thrower = thrower;
        }
    }

    public EntityMiningExplosive(World world) {
        super(world);
    }

    // adapted from vanilla's setVelocity because that only exists on the client
    private void initVelocity(Vec3d vel) {
        motionX = vel.x;
        motionY = vel.y;
        motionZ = vel.z;
        prevRotationYaw = rotationYaw = (float)(MathHelper.atan2(motionX, motionZ) * MathUtils.R2D_D);
        prevRotationPitch = rotationPitch
                = (float)(MathHelper.atan2(motionY, MathHelper.sqrt(motionX * motionX + motionZ * motionZ)) * MathUtils.R2D_D);
    }
    
    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            Vec3d explPos = result.hitVec;
            Unclunkify.PROXY.getWrappedExplosiveHandler().handle(
                    new Explosion(world, thrower, explPos.x, explPos.y, explPos.z,
                            (float)UnclunkConfig.miningExplosiveConfig.explosionStrength, false, true), this::doExplosion);
            setDead();
        }
    }

    private void doExplosion(ExplosionEvent.Detonate event) {
        Explosion explosion = event.getExplosion();
        List<BlockPos> affected = event.getAffectedBlocks();
        for (BlockPos pos : affected) {
            IBlockState state = world.getBlockState(pos);
            if (state.getMaterial() != Material.AIR) {
                Block block = state.getBlock();
                if (block.canDropFromExplosion(explosion)) {
                    block.dropBlockAsItemWithChance(world, pos, state, 1F, UnclunkConfig.miningExplosiveConfig.fortuneLevel);
                }
                block.onBlockExploded(world, pos, explosion);
            }
        }
        affected.clear();
    }

}
