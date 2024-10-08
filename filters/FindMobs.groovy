import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.DoubleTag
import net.querz.nbt.ListTag
import net.querz.nbt.NBTUtil

static void before() {
    def file = new File("mobs.txt") // also edit the name below!
    if (!file.exists()) file.createNewFile()
    else file.write("")
}

/**
 * Selects all chunks that contain any of the specified entity types.
 * Additionally, it outputs the coordinates and id of every matching entity to a file.
 *
 * @type Chunk Filter (Ctrl + F)
 * @difficulty INTERMEDIATE
 * @return true if any of the specified entity types were found
 */
static boolean filter(ChunkData data) {
    def file = new File("mobs.txt") // also edit the name above!
    // entities to check for; as snbt strings
    def entities = [
        '{"id": "donkey", "ChestedHorse": true}',
        // more entity types ...
    ]

    def entity_types = entities.collect {
        def compound = NBTUtil.fromSNBT(it) as CompoundTag
        def id = compound.getStringOrDefault("id", ":")
        if (!id.contains(":")) compound.putString("id", "minecraft:$id")
        return compound
    }

    def success = false
    for (entity in data.entities()?.data?.getListOrDefault("Entities", new ListTag()) as List<CompoundTag>) {
        if (!entity_types.any { it.partOf(entity) }) continue

        def intPos = (entity.getList("Pos") as List<DoubleTag>).collect { it.asInt() }
        file.append("${intPos.join(" ")} | ${entity.getString("id")}\n")
        success = true
    }

    return success
}