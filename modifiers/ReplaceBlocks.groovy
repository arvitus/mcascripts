import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

@Field Map<String, Map<Integer, String>> versionMappings = ["sections": [0: "Sections", 2844: "sections"]]

String getMapping(String key, int version) {
    var mapping = versionMappings[key]
    return mapping[mapping.keySet().findAll { it <= version }.max()]
}

void apply(ChunkData data) {
    var version = data.region?.data?.getInt("DataVersion") ?: 0
    for (section in data.region?.data?.getListTag(getMapping("sections", version)) as List<CompoundTag>) {
        var blockPalette = switch (version) {
            case 2836..Integer.MAX_VALUE -> section.getCompoundTag("block_states")?.getListTag("palette")
            case 1415..<2836 -> section.getListTag("Palette")
            default -> throw new IllegalStateException("Unsupported DataVersion: $version")
        }
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
 * @version 1.13+
 */
@Field List<String> blocksFrom = ["minecraft:dirt", "minecraft:coarse_dirt"]
@Field String blockTo = "minecraft:stone"