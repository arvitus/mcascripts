import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

boolean filter(ChunkData data) {
    var hasBlocks = data.region?.data?.getListTag("sections")?.any(
        { CompoundTag section ->
            var blockPalette = section.getCompoundTag("block_states")?.getListTag("palette")
            if (!blockPalette) return false
            for (block in blockPalette as List<CompoundTag>) {
                if (block.getString("Name") in blockIds) return true
            }
            return false
        }
    )
    var hasBlockEntities = data.region?.data?.getListTag("block_entities")?.any {
        CompoundTag it -> blockEntityIds.contains(it.getString("id"))
    }
    var hasEntities = data.entities?.data?.getListTag("Entities")?.any {
        CompoundTag it -> entityIds.contains(it.getString("id"))
    }

    if (hasBlocks || hasBlockEntities || hasEntities) return false
    return true
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Selects all chunks that contain none of the specified blocks, block entities or entities.
 *
 * @type Chunk Filter (Ctrl + F)
 * @version 1.18+
 */
@Field List<String> blockIds = [
    "minecraft:beacon",
    "minecraft:diamond_block",
    "minecraft:netherite_block",
]
@Field List<String> blockEntityIds = [
    "minecraft:chest",
    "minecraft:barrel",
    "minecraft:trapped_chest",
    "minecraft:shulker_box",
    "minecraft:hopper",
    "minecraft:furnace",
    "minecraft:blast_furnace",
    "minecraft:smoker",
    "minecraft:brewing_stand",
    "minecraft:dispenser",
    "minecraft:dropper",
    "minecraft:jukebox",
    "minecraft:lectern",
    "minecraft:campfire",
    "minecraft:chiseled_bookshelf",
    "minecraft:dragon_egg",
    "minecraft:nether_portal",
    "minecraft:end_portal",
    "minecraft:end_gateway",
    "minecraft:crafting_table",
    "minecraft:loom",
    "minecraft:grindstone",
    "minecraft:composter",
    "minecraft:banner",
    "minecraft:bed",
    "minecraft:skull",
    "minecraft:enchanting_table",
    "minecraft:lodestone",
    "minecraft:nether_brick",
    "minecraft:gilded_blackstone",
    "minecraft:purpur_pillar",
    "minecraft:purpur_slab",
    "minecraft:purpur_block",
    "minecraft:end_rod",
    "minecraft:end_stone_bricks"
]
@Field List<String> entityIds = [
    // Regular Boats
    "minecraft:oak_boat",
    "minecraft:spruce_boat",
    "minecraft:birch_boat",
    "minecraft:jungle_boat",
    "minecraft:acacia_boat",
    "minecraft:dark_oak_boat",
    "minecraft:mangrove_boat",
    "minecraft:bamboo_raft",
    "minecraft:pale_oak_boat",

    // Chest Boats
    "minecraft:oak_chest_boat",
    "minecraft:spruce_chest_boat",
    "minecraft:birch_chest_boat",
    "minecraft:jungle_chest_boat",
    "minecraft:acacia_chest_boat",
    "minecraft:dark_oak_chest_boat",
    "minecraft:mangrove_chest_boat",
    "minecraft:pale_oak_chest_boat",
    "minecraft:bamboo_chest_raft",

    // Minecarts
    "minecraft:chest_minecart",
    "minecraft:hopper_minecart",
    "minecraft:minecart",
    "minecraft:furnace_minecart",
    "minecraft:tnt_minecart",

    // Mobs
    "minecraft:tadpole",
    "minecraft:villager",
    "minecraft:wither",
    "minecraft:ender_dragon",
    "minecraft:snow_golem",
    "minecraft:endermite",
    "minecraft:breeze",
    "mineceaft:phantom",
    "minecraft:ravager",
    "minecraft:skeleton_horse",
    "minecraft:wandering_trader",
    "minecraft:sniffer",
    "minecraft:warden",
    "minecraft:zoglin",
    "minecraft:shulker",

    // Other Entities
    "minecraft:armor_stand",
    "minecraft:item_frame",
    "minecraft:glow_item_frame",
    "minecraft:leash_knot",
    "minecraft:trident",
    "minecraft:painting",
    "minecraft:end_crystal",
    "minecraft:ender_pearl"
]