import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.StringTag

boolean filter(ChunkData data) {
    for (section in data.region?.data?.getListTag("sections") as List<CompoundTag>) {
        for (biome in section?.getCompoundTag("biomes")?.getListTag("palette") as List<StringTag>) {
            if (biome.value in biomes) return true
        }
    }
    return false
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Selects all chunks that contain any of the specified biomes.
 *
 * @type Chunk Filter (Ctrl + F)
 * @version 1.18+
 */
@Field List<String> biomes = ["minecraft:forest", "minecraft:birch_forest"]