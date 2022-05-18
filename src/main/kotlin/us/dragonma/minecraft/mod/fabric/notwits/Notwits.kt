package us.dragonma.minecraft.mod.fabric.notwits

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.util.Language
import net.minecraft.village.VillagerDataContainer
import net.minecraft.village.VillagerProfession
import org.slf4j.LoggerFactory

@Suppress("unused")
object Notwits : ModInitializer {
    private const val MOD_ID = "$[name]"
    private val LOGGER = LoggerFactory.getLogger(this.javaClass)
    private val DEBUG = System.getProperty("$[name].debug", "false").toBoolean()

    override fun onInitialize() {
        LOGGER.info("[$[displayName]] Loading version $[version].")

        ServerEntityEvents.ENTITY_LOAD.register { entity, world ->
            if (world.isClient) return@register

            if (entity is VillagerDataContainer && entity.villagerData.profession == VillagerProfession.NITWIT) {
                if (DEBUG) LOGGER.info("[$[displayName]] Forcing ${Language.getInstance()[entity.type.translationKey]} at [${entity.x}, ${entity.y}, ${entity.z}] to become a productive member of society.")

                entity.villagerData = entity.villagerData.withProfession(VillagerProfession.NONE)
                if (entity is VillagerEntity) {
                    entity.reinitializeBrain(world)
                }
            }
        }
    }
}
