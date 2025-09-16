import groovy.transform.Field
import groovyjarjarantlr4.v4.runtime.misc.Nullable
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.ListTag
import net.querz.nbt.Tag

static @Field Map<String, Map<Integer, String>> versionMappings = [
    "entities": [0: "Level.Entities", 2844: "Entities"], // 2844 = Level tag removal (1.18)
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

void filterEntities(@Nullable ListTag entities) {
    var entitiesCopy = entities?.copy() as ListTag
    entities?.clear()
    for (entity in entitiesCopy as List<CompoundTag>) {
        var keep = entity.getString("id") !in removeIds
        if (inverted) keep = !keep
        if (keep) entities.add(entity)
    }
}

void apply(ChunkData chunkData) {
    var regionData = chunkData.region?.data
    if (regionData) filterEntities(getMappedTag(regionData, "entities") as ListTag)

    var entityData = chunkData.entities?.data
    if (entityData) filterEntities(entityData.getListTag("Entities"))
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Removes the specified entities.
 * If "inverted" is true, it keeps only the specified entities.
 *
 * @type Change NBT (Ctrl + N)
 * @version probably any
 */
// The entity ids to remove
@Field List<String> removeIds = ["minecraft:cow", "minecraft:pig"]
@Field boolean inverted = false