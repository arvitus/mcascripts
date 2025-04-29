import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.ListTag
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.util.concurrent.atomic.AtomicInteger

@Field Logger LOGGER = LogManager.getLogger("BlockEntityCounter")
@Field AtomicInteger counter = new AtomicInteger(0)

boolean filter(ChunkData data) {
    var blockEntityCount = data.region?.data?.getListOrDefault("block_entities", new ListTag())
        ?.findAll { CompoundTag it -> it.getString("id") == blockEntityId }?.size()

    counter.getAndAdd(blockEntityCount ?: 0)
    return blockEntityCount as Boolean
}

void after() {
    println("BlockEntityCounter: There are ${counter.get()} ${blockEntityId} in the world.")
    LOGGER.info("There are ${counter.get()} ${blockEntityId} in the world.")

    fileName = fileName.replaceFirst("^~/*", System.getProperty("user.home"))
    var file = new File(fileName)
    if (!file.exists()) file.createNewFile()
    file.write(counter.get() as String)
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Selects all chunks that contain the specified block entity.
 * Additionally, it outputs the total number of those
 * block entities in the world to a file.
 *
 * @type Chunk Filter (Ctrl + F)
 * @version 1.18+
 */
// must be a valid [resource location](https://minecraft.wiki/w/Resource_location)
@Field String blockEntityId = "minecraft:chest"
// The file to write the output to, relative to the mca directory
// Use `~` to refer to the user's home directory instead (MCA may need to be run as administrator for this to work)
@Field String fileName = "BlockEntityCount.txt"
