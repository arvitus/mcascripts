import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.DoubleTag
import net.querz.nbt.ListTag
import net.querz.nbt.NBTUtil
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Field Logger LOGGER = LogManager.getLogger("FindEntities")
@Field List<String> foundEntities = Collections.synchronizedList(new ArrayList<String>())
@Field List<CompoundTag> entity_types

void before() {
    entity_types = entities.collect {
        var compound = NBTUtil.fromSNBT(it) as CompoundTag
        var id = compound.getStringOrDefault("id", ":")
        if (!id.contains(":")) compound.putString("id", "minecraft:$id")
        return compound
    }
}

boolean filter(ChunkData data) {
    var foundAny = false
    for (entity in data.entities?.data?.getListOrDefault("Entities", new ListTag()) as List<CompoundTag>) {
        if (!entity_types.any { it.partOf(entity) }) continue

        var intPos = (entity.getList("Pos") as List<DoubleTag>).collect { it.asInt() }
        foundEntities.addLast("${intPos.join(" ")} | ${entity.getString("id")}")
        foundAny = true
    }

    return foundAny
}

void after() {
    println("FindEntities: There have been ${foundEntities.size()} matching foundEntities found in the world.")
    LOGGER.info("There have been ${foundEntities.size()} matching foundEntities found in the world.")
    
    fileName = fileName.replaceFirst("^~/*", System.getProperty("user.home"))
    var file = new File(fileName)
    if (!file.exists()) file.createNewFile()
    file.write(foundEntities.join("\n"))
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Selects all chunks that contain any of the specified entity types.
 * Additionally, it outputs the coordinates and id of every matching entity to a file.
 *
 * @type Chunk Filter (Ctrl + F)
 * @version 1.17+
 */
// The file to write the output to, relative to the mca directory
// Use `~` to refer to the user's home directory instead
@Field String fileName = "Entities.txt"
// entities to check for, as snbt strings
@Field List<String> entities = [
    '{"id": "donkey", "ChestedHorse": true}',
    // more entity types ...
]
