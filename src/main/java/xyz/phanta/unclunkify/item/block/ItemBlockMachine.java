package xyz.phanta.unclunkify.item.block;

import io.github.phantamanta44.libnine.block.L9BlockStated;
import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemBlockStated;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.phanta.unclunkify.block.BlockMachine;
import xyz.phanta.unclunkify.tile.base.TileMachine;

import java.util.Objects;

public class ItemBlockMachine extends L9ItemBlockStated implements ParameterizedItemModel.IParamaterized {

    public ItemBlockMachine(L9BlockStated block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
                                EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) {
            ((TileMachine)Objects.requireNonNull(world.getTileEntity(pos)))
                    .setFrontFace(player.getHorizontalFacing().getOpposite());
            return true;
        }
        return false;
    }

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("type", BlockMachine.Type.getForStack(stack).name());
    }

}
