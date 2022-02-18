package igoro00.not_as_easy_villagers

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

class NotAsEasyVillagers : ModInitializer {
    val MOD_ID="not_as_easy_villagers";

    override fun onInitialize() {
        val SLAVE = Registry.register(Registry.ITEM,Identifier(MOD_ID, "villager"),  VillagerItem(FabricItemSettings().maxCount(1)))


        println("Not As Easy Villagers loaded.");
    }
}