package xyz.phanta.unclunkify.item;

import io.github.phantamanta44.libnine.item.L9ItemSubs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import xyz.phanta.unclunkify.constant.LangConst;
import xyz.phanta.unclunkify.entity.EntityMiningExplosive;
import xyz.phanta.unclunkify.init.UnclunkItems;

public class ItemMisc extends L9ItemSubs {

    public ItemMisc() {
        super(LangConst.ITEM_MISC, Type.VALUES.length);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (Type.getForStack(stack) == Type.MINING_EXPLOSIVE) {
            if (!world.isRemote) {
                stack.shrink(1);
                EntityMiningExplosive entity = new EntityMiningExplosive(
                        world, player.getPositionEyes(1F), player.getLookVec(), player);
                world.spawnEntity(entity);
                world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW,
                        SoundCategory.NEUTRAL, 0.5F, 0.75F);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    public enum Type {

        MINING_EXPLOSIVE;

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            return VALUES[meta];
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public ItemStack newStack(int count) {
            return new ItemStack(UnclunkItems.MISC, count, ordinal());
        }

    }

}
