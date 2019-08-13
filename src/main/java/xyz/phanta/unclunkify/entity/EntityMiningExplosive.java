package xyz.phanta.unclunkify.entity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import xyz.phanta.unclunkify.Unclunkify;

import javax.annotation.Nullable;
import java.util.List;

public class EntityMiningExplosive extends EntityThrowable {

    public EntityMiningExplosive(World world, Vec3d pos, Vec3d vel, @Nullable EntityLivingBase thrower) {
        super(world, pos.x, pos.y, pos.z);
        setVelocity(vel.x, vel.y, vel.z);
        if (thrower != null) {
            this.thrower = thrower;
        }
    }

    public EntityMiningExplosive(World world) {
        super(world);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            Vec3d explPos = result.hitVec;
            Unclunkify.PROXY.getWrappedExplosiveHandler().handle(
                    new Explosion(world, thrower, explPos.x, explPos.y, explPos.z, 2.5F, false, true), this::doExplosion);
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
                    block.dropBlockAsItemWithChance(world, pos, state, 1F, 3);
                }
                block.onBlockExploded(world, pos, explosion);
            }
        }
        affected.clear();
    }

}
