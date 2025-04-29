import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData

boolean filter(ChunkData data) {
    return data.region?.data?.containsKey(tagName)
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Selects all chunks that contain the specified NBT Tag
 * at the top level of the chunk data.
 *
 * @type Chunk Filter (Ctrl + F)
 * @version any
 */
// The tag to check for
@Field String tagName = "blending_data"