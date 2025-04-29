import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData

void apply(ChunkData data) {
    data.region?.data?.remove(tagName)
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Removes the specified NBT Tag from the top level of the chunk data.
 *
 * @type Change NBT (Ctrl + N)
 * @version any
 */
// The tag to remove
@Field String tagName = "blending_data"