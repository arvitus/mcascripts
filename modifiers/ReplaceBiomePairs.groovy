//This is purely theoretical and has undergone NO testing.

import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.CompoundTag
import net.querz.nbt.StringTag

/**
 * Replaces the original biome in each biome pair with its respective destination biome.
 *
 * @type Change NBT (Ctrl + N)
 * @difficulty INTERMEDIATE
 */
static void apply(ChunkData data) {
    def biomepairs = ['minecraft:plains': 'minecraft:dark_forest', 'minecraft:ocean': 'minecraft:river']
//biome replacements are specified here as comma-separated pairs in the format 'source:original_biome': 'source:new_biome'

  for (section in data.region()?.data?.getListTag("sections") as List<CompoundTag>) {
        var biomePalette = section.getCompoundTag("biomes")?.getListTag("palette")
        if (!biomePalette) continue
        biomePalette.copy().eachWithIndex { StringTag biome, int i ->
            if (biome.value in biomepairs) {
                biomePalette.set(i, StringTag.valueOf(biomepairs[biome.value])

           }
        }
    }
}
