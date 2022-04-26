package us.dragonma.minecraft.mod.fabric.notwits

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.village.VillagerDataContainer
import net.minecraft.village.VillagerProfession

@Suppress("unused")
object Notwits : ModInitializer {
    private const val MOD_ID = "notwits"

    override fun onInitialize() {
        ServerEntityEvents.ENTITY_LOAD.register { entity, world ->
            if (world.isClient) return@register

            if (entity is VillagerDataContainer && entity.villagerData.profession == VillagerProfession.NITWIT) {
                entity.villagerData = entity.villagerData.withProfession(VillagerProfession.NONE)
                if (entity is VillagerEntity) {
                    entity.reinitializeBrain(world)
                }
            }
        }
    }
}
