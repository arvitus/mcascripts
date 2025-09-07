import groovy.transform.Field
import groovyjarjarantlr4.v4.runtime.misc.Nullable
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.Tag

static @Field Map<String, Map<Integer, String>> versionMappings = [
    "data"    : [0: "Level", 2844: ""], // 2844 = Level tag removal (1.18)
    "sections": [0: "Level.Sections", 2844: "sections"]
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
    return getPath(data, getMapping(key, version ?: data.getInt("DataVersion")))
}

void apply(ChunkData chunkData) {
    var regionData = chunkData.region?.data
    if (!regionData) return

    var data = getMappedTag(regionData, "data") as CompoundTag
    data.remove("LightPopulated")
    data.remove("isLightOn") // since 1.14, I think, maybe even 1.13 e.g. The Flattening

    for (section in getMappedTag(regionData, "sections") as List<CompoundTag>) {
        section.remove("SkyLight")
        section.remove("BlockLight")
    }
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Removes lighting information,
 * causing it to be recalculated when the chunk is loaded.
 *
 * @type Change NBT (Ctrl + N)
 * @version probably any, but definitely 1.14+
 */