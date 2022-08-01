package igoro00.not_as_easy_villagers.mixin;

import igoro00.not_as_easy_villagers.VillagerItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.VillagerEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    private VillagerEntityRenderer renderer;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    public void renderItem(ItemStack stack, ModelTransformation.Mode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int seed, CallbackInfo ci) {
        if (stack.getItem() instanceof VillagerItem) {
            if (!stack.hasNbt()) return;
            MinecraftClient minecraft = MinecraftClient.getInstance();
            if (renderer == null) {
                EntityRendererFactory.Context entityRendererContext = new EntityRendererFactory.Context(minecraft.getEntityRenderDispatcher(), minecraft.getItemRenderer(), minecraft.getBlockRenderManager(), minecraft.gameRenderer.firstPersonRenderer, minecraft.getResourceManager(), minecraft.getEntityModelLoader(), minecraft.textRenderer);
                renderer = new VillagerEntityRenderer(entityRendererContext);
            }
            NbtCompound newTag = stack.getOrCreateNbt().getCompound("Entity");
            if(stack.hasCustomName()){
                newTag.putString("CustomName", "{\"text\":\"" + stack.getName().toString() + "\"}");
            }

            VillagerEntity villagerEntity = (VillagerEntity) EntityType.loadEntityWithPassengers(newTag, stack.getHolder().getWorld(), (it) -> {
                return null;
            });
            renderer.render(villagerEntity, 0F, 1F, matrices, vertexConsumers, 0);
            if (villagerEntity != null)
                villagerEntity.kill();
            ci.cancel();
        }
    }
}
