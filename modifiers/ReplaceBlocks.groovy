import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

void apply(ChunkData data) {
    for (section in data.region?.data?.getListTag("sections") as List<CompoundTag>) {
        var blockPalette = section.getCompoundTag("block_states")?.getListTag("palette")
        if (!blockPalette) continue
        for (block in blockPalette as List<CompoundTag>) {
            if (block.getString("Name") in blocksFrom) {
                block.putString("Name", blockTo)
            }
        }
    }
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Replaces all blocks in `blocksFrom` with `blockTo`.
 * Note: Block states are not updated, only the block name is changed.
 *
 * @type Change NBT (Ctrl + N)
 * @version 1.17+
 */
@Field List<String> blocksFrom = ["minecraft:dirt", "minecraft:coarse_dirt"]
@Field String blockTo = "minecraft:stone"