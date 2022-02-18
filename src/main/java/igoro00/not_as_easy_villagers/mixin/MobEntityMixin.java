/*
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this file,
You can obtain one at https://mozilla.org/MPL/2.0/.

Copyright (c) 2020, lucaargolo lucaargolo@gmail.com

Alternatively, the contents of this file may be used under the terms
of the GNU General Public License Version XX, as described below:

This file is free software: you may copy, redistribute and/or modify
it under the terms of the GNU General Public License as published by the
Free Software Foundation, either version XX of the License, or (at your
option) any later version.

This file is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see http://www.gnu.org/licenses/.
*/

//Most of the code here was taken from the Kibe mod (https://github.com/lucaargolo/kibe)


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

                // it'll generate new UUID on spawn. 
                //It allows to spawn multiple villagers from one item in creative.
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