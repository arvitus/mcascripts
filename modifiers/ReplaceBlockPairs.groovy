import groovy.transform.Field
import groovyjarjarantlr4.v4.runtime.misc.Nullable
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.Tag

static @Field Map<String, Map<Integer, String>> versionMappings = [
    "sections": [0: "Level.Sections", 2844: "sections"],
    "palette" : [1451: "Palette", 2836: "block_states.palette"]
]

static String getMapping(String key, int version) {
    var mapping = versionMappings[key]
    return mapping[mapping.keySet().findAll { it <= version }.max()]
}

static @Nullable
Tag getPath(CompoundTag data, String path) {
    Tag current = data
    path.tokenize(".").forEach { current = current.get(it) }
    return current
}

static @Nullable
Tag getMappedTag(CompoundTag data, String key, Integer version = null) {
    getPath(data, getMapping(key, version ?: data.getInt("DataVersion")))
}

void apply(ChunkData chunkData) {
    var regionData = chunkData.region?.data
    if (!regionData) return

    var version = regionData?.getInt("DataVersion") ?: 0
    if (version < 1451) throw new IllegalStateException("Unsupported DataVersion: $version")

    for (section in getMappedTag(regionData, "sections") as List<CompoundTag>) {
        var blockPalette = getMappedTag(section, "palette", version) as List<CompoundTag>
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