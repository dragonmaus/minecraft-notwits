package us.dragonma.minecraft.mod.fabric.notwits

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.entity.mob.ZombieVillagerEntity
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.village.VillagerProfession

@Suppress("unused")
object Notwits : ModInitializer {
    private const val MOD_ID = "notwits"

    override fun onInitialize() {
        ServerEntityEvents.ENTITY_LOAD.register { entity, world ->
            when (entity) {
                is VillagerEntity -> {
                    if (entity.villagerData.profession == VillagerProfession.NITWIT) {
                        entity.villagerData = entity.villagerData.withProfession(VillagerProfession.NONE)
                        entity.reinitializeBrain(world)
                    }
                }
                is ZombieVillagerEntity -> {
                    if (entity.villagerData.profession == VillagerProfession.NITWIT) {
                        entity.villagerData = entity.villagerData.withProfession(VillagerProfession.NONE)
                    }
                }
            }
        }
    }
}
