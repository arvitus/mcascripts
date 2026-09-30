import groovy.transform.Field
import groovyjarjarantlr4.v4.runtime.misc.Nullable
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.ListTag
import net.querz.nbt.Tag

static @Field Map<String, Map<Integer, String>> versionMappings = [
    "entities": [0: "Level.Entities", 2844: "entities"], // 2844 = Level tag removal (1.18)
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

static void removeVillagerOffers(@Nullable ListTag entities) {
    for (entity in entities as List<CompoundTag>) {
        var id = entity.getString("id")
        if (id != "minecraft:villager") continue
        entity.remove("Offers")
    }
}

void apply(ChunkData chunkData) {
    var regionData = chunkData.region?.data
    if (regionData) removeVillagerOffers(getMappedTag(regionData, "entities") as ListTag)

    var entityData = chunkData.entities?.data
    if (entityData) removeVillagerOffers(entityData.getListTag("Entities"))
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Removes the `Offers` tag from Villagers, causing them to be regenerated.
 *
 * @type Change NBT (Ctrl + N)
 * @version 1.14+
 */