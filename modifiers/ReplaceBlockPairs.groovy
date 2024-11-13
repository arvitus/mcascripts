import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

/**
 * Specify blocks as comma-separated pairs in the format 'source:original_block': 'source:new_block'
 * Replaces the original block in each block pair with its respective destination block.
 *
 * Note: Block states are not updated, only the block name is changed.
 * @type Change NBT (Ctrl + N)
 * @difficulty INTERMEDIATE
 */
static void apply(ChunkData data) {
    def blockpairs = ['byg:red_rock': 'byg:travertine', 'byg:aspen_log': 'byg:pendorite_block']
    
    for (section in data.region()?.data?.getListTag("sections") as List<CompoundTag>) {
        var blockPalette = section.getCompoundTag("block_states")?.getListTag("palette")
        if (!blockPalette) continue
        for (block in blockPalette as List<CompoundTag>) {
            if (block.getString("Name") in blockpairs) {
                block.putString("Name", blockpairs[block.getString("Name")])
            }
        }
    }
}
