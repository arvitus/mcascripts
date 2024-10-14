import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.ListTag

static void before() {
    def file = new File("worldChestCount.txt") // also edit the name below!
    if (!file.exists()) file.createNewFile()
    else file.write("0")
}

/**
 * Selects all chunks that contain the specified block entity.
 * Additionally, it outputs the total number of those block entities in the world to a file.
 *
 * @type Chunk Filter (Ctrl + F)
 * @difficulty EASY
 * @return true if the specified block entity was found
 */
static boolean filter(ChunkData data) {
    // must be a valid [resource location](https://minecraft.wiki/w/Resource_location)
    def block_entity_id = "minecraft:chest"
    def file = new File("worldChestCount.txt") // also edit the name above!

    def worldChestCount = file.text as Integer
    def chunkChestCount = data.region?.data?.getListOrDefault("block_entities", new ListTag())
        ?.findAll { CompoundTag it -> it.getString("id") == block_entity_id }?.size()
    worldChestCount += chunkChestCount
    file.write(worldChestCount as String)
    return chunkChestCount as Boolean
}
