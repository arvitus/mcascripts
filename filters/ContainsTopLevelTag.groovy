import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData

boolean filter(ChunkData data) {
    var regionData = data.region?.data

    var version = regionData?.getInt("DataVersion") ?: 0
    if (version < 1451) { // 1451 = The Flattening (1.13)
        return regionData?.getCompoundTag("Level")?.containsKey(tagName)
    }
    return regionData?.containsKey(tagName)
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Selects all chunks that contain the specified NBT Tag
 * at the top level of the chunk content.
 * Content being the actual data, pre 1.18 this was inside the "Level" tag.
 *
 * @type Chunk Filter (Ctrl + F)
 * @version any
 */
// The tag to check for
@Field String tagName = "blending_data"