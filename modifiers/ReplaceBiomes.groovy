import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.StringTag

void apply(ChunkData data) {
    for (section in data.region?.data?.getListTag("sections") as List<CompoundTag>) {
        var biomePalette = section.getCompoundTag("biomes")?.getListTag("palette")
        if (!biomePalette) continue
        biomePalette.copy().eachWithIndex { StringTag biome, int i ->
            if (biome.value in biomesFrom) {
                biomePalette.set(i, StringTag.valueOf(biomeTo))
            }
        }
    }
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Replaces all biomes in `biomesFrom` with `biomeTo`.
 *
 * @type Change NBT (Ctrl + N)
 * @version 1.18+
 */
@Field List<String> biomesFrom = ["minecraft:forest", "minecraft:birch_forest"]
@Field String biomeTo = "minecraft:plains"