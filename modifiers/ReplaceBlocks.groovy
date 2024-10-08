import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

/**
 * Replaces all blocks in `blocksFrom` with `blockTo`.
 * Note: Block states are not updated, only the block name is changed.
 *
 * @type Change NBT (Ctrl + N)
 * @difficulty INTERMEDIATE
 */
static void apply(ChunkData data) {
    var blocksFrom = ["minecraft:dirt", "minecraft:coarse_dirt"]
    var blockTo = "minecraft:stone"

    for (section in data.region()?.data?.getListTag("sections") as List<CompoundTag>) {
        var blockPalette = section.getCompoundTag("block_states")?.getListTag("palette")
        if (!blockPalette) continue
        for (block in blockPalette as List<CompoundTag>) {
            if (block.getString("Name") in blocksFrom) {
                block.putString("Name", blockTo)
            }
        }
    }
}