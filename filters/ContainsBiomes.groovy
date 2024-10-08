import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.StringTag

/**
 * Selects all chunks that contain the specified biome.
 *
 * @type Chunk Filter (Ctrl + F)
 * @difficulty EASY
 */
static boolean filter(ChunkData data) {
    var biomes = ["minecraft:forest", "minecraft:birch_forest"]

    for (section in data.region()?.data?.getListTag("sections") as List<CompoundTag>) {
        for (biome in section?.getCompoundTag("biomes")?.getListTag("palette") as List<StringTag>) {
            if (biome.value in biomes) return true
        }
    }
    return false
}