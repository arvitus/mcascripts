import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag

@Field Map<String, Map<Integer, String>> versionMappings = ["sections": [0: "Sections", 2844: "sections"]]

String getMapping(String key, int version) {
    var mapping = versionMappings[key]
    return mapping[mapping.keySet().max { it <= version }]
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
 * @version 1.13+
 */
@Field Map<String, String> blockPairs = [
    //"source:block_from": "destination:block_to",
    "minecraft:dirt" : "minecraft:diamond_block",
    "minecraft:water": "minecraft:lava"
]