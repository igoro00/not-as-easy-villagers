package igoro00.not_as_easy_villagers.mixin;

import com.google.gson.Gson;
import igoro00.not_as_easy_villagers.CustomName;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.registry.Registry;

import java.util.Random;



@SuppressWarnings("ConstantConditions")
@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(at = @At("HEAD"), method = "interactWithItem", cancellable = true)
    public void interactWithItem(PlayerEntity playerEntity, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ItemStack stack = playerEntity.getStackInHand(hand);

        if(stack.isEmpty()) {
            MobEntity entity = (MobEntity) ((Object) this);
            if (entity.getType() == EntityType.VILLAGER && playerEntity.isSneaking()) {
                if (entity.isLeashed()) entity.detachLeash(true, true);
                entity.fallDistance = 0;
                NbtCompound tag = new NbtCompound();
                entity.saveSelfNbt(tag);
                tag.remove("UUID");

                ItemStack villagerItemStack = new ItemStack(Registry.ITEM.get(
                        new Identifier("not_as_easy_villagers","villager")
                ));

                NbtCompound villagerItemNbt = villagerItemStack.getOrCreateNbt();
                villagerItemNbt.put("Entity", tag);
                if(tag.contains("CustomName")){
                    Gson gson = new Gson();
                    CustomName name = gson.fromJson(tag.getString("CustomName"), CustomName.class);
                    villagerItemStack.setCustomName(new LiteralText(name.text));
                }
                villagerItemStack.setNbt(villagerItemNbt);

                playerEntity.getInventory().addPickBlock(villagerItemStack);
                entity.remove(Entity.RemovalReason.DISCARDED);
                info.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}