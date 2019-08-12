package xyz.phanta.unclunkify.block;

import io.github.phantamanta44.libnine.block.L9BlockStated;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import io.github.phantamanta44.libnine.tile.L9TileEntity;
import io.github.phantamanta44.libnine.util.collection.Accrue;
import io.github.phantamanta44.libnine.util.world.WorldBlockPos;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import xyz.phanta.unclunkify.Unclunkify;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.init.UnclunkBlocks;
import xyz.phanta.unclunkify.init.UnclunkGuis;
import xyz.phanta.unclunkify.tile.TileHighTempFurnace;
import xyz.phanta.unclunkify.tile.base.TileMachine;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class BlockMachine extends L9BlockStated {

    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);
    public static final PropertyEnum<EnumFacing> ROTATION
            = PropertyEnum.create("rotation", EnumFacing.class, EnumFacing.HORIZONTALS);
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockMachine() {
        super(LangConst.BLOCK_MACHINE, Material.ROCK);
        setHardness(3.5F);
        setTileFactory((w, m) -> Type.getForMeta(m).createTile());
    }

    @Override
    protected void accrueProperties(Accrue<IProperty<?>> props) {
        props.accept(TYPE);
    }

    @Override
    protected void accrueVolatileProperties(Accrue<IProperty<?>> props) {
        props.acceptAll(ROTATION, ACTIVE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileMachine tile = Objects.requireNonNull(getTileEntity(world, pos));
        return state.withProperty(ROTATION, tile.getFrontFace()).withProperty(ACTIVE, tile.isActive());
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axialFace) {
        TileMachine tile = Objects.requireNonNull(getTileEntity(world, pos));
        EnumFacing.Axis axis = axialFace.getAxis();
        if (axis == EnumFacing.Axis.Y) {
            tile.setFrontFace(tile.getFrontFace().rotateAround(axis));
        } else if (tile.getFrontFace() == axialFace) {
            tile.setFrontFace(axialFace.getOpposite());
        } else {
            tile.setFrontFace(axialFace);
        }
        return true;
    }

    @Nullable
    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        return EnumFacing.HORIZONTALS;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            state.getValue(TYPE).openGui(player, new WorldBlockPos(world, pos));
        }
        return true;
    }

    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(TYPE) == Type.HIGH_TEMP_FURNACE) {
            TileMachine tile = Objects.requireNonNull(getTileEntity(world, pos));
            if (tile.isActive()) {
                Vec3d center = WorldUtils.getBlockCenter(pos);
                if (rand.nextDouble() < 0.1D) {
                    world.playSound(center.x, center.y, center.z,
                            SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }
                EnumFacing dir = tile.getFrontFace();
                Vec3d pPos = center.add(dir.getXOffset() * 0.52D, -0.125D - rand.nextDouble() * 0.25D, dir.getZOffset() * 0.52D);
                switch (dir.getAxis()) {
                    case X:
                        pPos = pPos.add(0D, 0D, rand.nextDouble() * 0.6D - 0.3D);
                        break;
                    case Z:
                        pPos = pPos.add(rand.nextDouble() * 0.6D - 0.3D, 0D, 0D);
                        break;
                }
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pPos.x, pPos.y, pPos.z, 0D, 0D, 0D);
                world.spawnParticle(EnumParticleTypes.FLAME, pPos.x, pPos.y, pPos.z, 0D, 0D, 0D);
            }
        }
    }

    public enum Type implements IStringSerializable {

        HIGH_TEMP_FURNACE(TileHighTempFurnace::new, UnclunkGuis.HIGH_TEMP_FURNACE);

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            return VALUES[meta];
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        private final Supplier<L9TileEntity> tileFactory;
        private final GuiIdentity<?, ?> guiKey;

        Type(Supplier<L9TileEntity> tileFactory, GuiIdentity<?, ?> guiKey) {
            this.tileFactory = tileFactory;
            this.guiKey = guiKey;
        }

        L9TileEntity createTile() {
            return tileFactory.get();
        }

        void openGui(EntityPlayer player, WorldBlockPos pos) {
            Unclunkify.INSTANCE.getGuiHandler().openGui(player, guiKey, pos);
        }

        public ItemStack newStack(int count) {
            return new ItemStack(UnclunkBlocks.MACHINE, count, ordinal());
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

    }

}
