import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

void apply(ChunkData data) {
    for (section in data.region?.data?.getListTag("sections") as List<CompoundTag>) {
        var blockPalette = section.getCompoundTag("block_states")?.getListTag("palette")
        if (!blockPalette) continue
        for (block in blockPalette as List<CompoundTag>) {
            if (block.getString("Name") in blockPairs) {
                block.putString("Name", blockPairs[block.getString("Name")])
            }
        }
    }
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/

/**
 * Replaces the original block in each block pair with its respective destination block.
 * Note: Block states are not updated, only the block name is changed.
 *
 * @type Change NBT (Ctrl + N)
 * @version 1.17+
 */
@Field Map<String, String> blockPairs = [
    //"source:block_from": "source:block_to",
    "minecraft:dirt" : "minecraft:diamond_block",
    "minecraft:water": "minecraft:lava"
]