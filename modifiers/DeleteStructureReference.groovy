import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.mcaselector.version.ChunkFilter
import net.querz.mcaselector.version.VersionHandler
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Field Logger LOGGER = LogManager.getLogger("DeleteStructureReference")
@Field String[] regexes

void _log(String type, String id, ChunkData chunkData) {
    LOGGER.debug("Removed structure {} {} from chunk {}", type, id, chunkData.location())
}

void before() {
    regexes = structureIds.collect { it.replaceAll(/(?<![^:])\*/, ".*") }
}

// called for every selected chunk, data can be modified
void apply(ChunkData chunkData) {
    var structures = VersionHandler.getImpl(chunkData, ChunkFilter.Structures.class)
    var references = structures.getStructureReferences(chunkData)
    var starts = structures.getStructureStarts(chunkData)

    references?.keySet()?.removeIf {
        var result = regexes.any(regex -> it.matches(regex))
        if (result) _log("reference", it, chunkData)
        return result
    }
    starts?.keySet()?.removeIf {
        var result = regexes.any(regex -> it.matches(regex))
        if (result) _log("start", it, chunkData)
        return result
    }
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Removes all structure references and starts
 * with the given structure IDs from the selected chunks.
 *
 * @type Change NBT (Ctrl + N)
 * @version any
 */

// The structure IDs to remove from the selected chunks.
// Wildcards are supported, e.g. "minecraft:*" will remove everything from the "minecraft" namespace.
// (Technically, it's just regex strings where a single '*' before or after the ':' is expanded to '.*'.)
@Field String[] structureIds = ["minecraft:some_structure", "some_mod:*"]
