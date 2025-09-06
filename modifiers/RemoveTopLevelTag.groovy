import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData

void apply(ChunkData data) {
    var regionData = data.region?.data

    var version = regionData?.getInt("DataVersion") ?: 0
    if (version < 2844)  // 2844 = Level tag removal (1.18)
        regionData?.getCompoundTag("Level")?.remove(tagName)
    else
        regionData?.remove(tagName)
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Removes the specified NBT Tag from the top level of the chunk content.
 * Content being the actual data, pre 1.18 this was inside the "Level" tag.
 *
 * @type Change NBT (Ctrl + N)
 * @version any
 */
// The tag to remove
@Field String tagName = "blending_data"