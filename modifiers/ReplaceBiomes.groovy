import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.StringTag

/**
 * Replaces all biomes in `biomesFrom` with `biomeTo`.
 *
 * @type Change NBT (Ctrl + N)
 * @difficulty INTERMEDIATE
 */
static void apply(ChunkData data) {
    var biomesFrom = ["minecraft:forest", "minecraft:birch_forest"]
    var biomeTo = "minecraft:plains"

    for (section in data.region()?.data?.getListTag("sections") as List<CompoundTag>) {
        var biomePalette = section.getCompoundTag("biomes")?.getListTag("palette")
        if (!biomePalette) continue
        biomePalette.copy().eachWithIndex { StringTag biome, int i ->
            if (biome.value in biomesFrom) {
                biomePalette.set(i, StringTag.valueOf(biomeTo))
            }
        }
    }
}