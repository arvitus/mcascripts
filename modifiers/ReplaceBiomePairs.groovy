import groovy.transform.Field
import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.StringTag

void apply(ChunkData data) {
    for (section in data.region?.data?.getListTag("sections") as List<CompoundTag>) {
        var biomePalette = section.getCompoundTag("biomes")?.getListTag("palette")
        if (!biomePalette) continue
        biomePalette.copy().eachWithIndex { StringTag biome, int i ->
            if (biome.value in biomePairs) {
                biomePalette.set(i, StringTag.valueOf(biomePairs[biome.value]))
            }
        }
    }
}

/**                !! CODE ABOVE !!                **/
/** Usually, you don't need to edit anything here. **/


/**
 * Replaces the original biome in each biome pair with its respective destination biome.
 *
 * @type Change NBT (Ctrl + N)
 * @version 1.18+
 */
@Field Map<String, String> biomePairs = [
    //"minecraft:biome_from": "minecraft:biome_to",
    "minecraft:plains": "minecraft:dark_forest",
    "minecraft:ocean" : "minecraft:river"
]