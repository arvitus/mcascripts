# mcascripts

A collection of [custom filter](https://github.com/Querz/mcaselector/wiki/Custom-Filter-Tutorial) scripts for the
Minecraft world editor [MCASelector](https://github.com/Querz/mcaselector).

## Contents

### Filters

Filters do not modify the world, they only select the chunks where the filter is true.
They can be used in the `Filter chunks (Ctrl + F)` dialog of MCA.

- [BlockEntityCounter](./filters/BlockEntityCounter.groovy) | Output the amount of a certain block entity to a file
- [ChunkCleanup](./filters/ChunkCleanup.groovy) by \_adamo_ |
  Select all chunks that contain none of the specified blocks, block entities or entities
- [ContainsBiomes](./filters/ContainsBiomes.groovy) | Select all chunks that contain any of the specified biomes
- [FindMobs](./filters/FindMobs.groovy) | Output the location of all mobs of the specified type to a file

### Modifiers (Change NBT)

Modifiers DO modify the world and don't select any chunks.
They can be used in the `Change NBT (Ctrl + N)` dialog of MCA.
> [!WARNING]  
> As always when using MCA Selector, make a backup before using!

- [ChestFiller](./modifiers/ChestFiller.groovy) | Fill all chests with the specified item(s)
- [ReplaceBiomePairs](./modifiers/ReplaceBiomePairs.groovy) by [@Royal-Terrapin](https://github.com/Royal-Terrapin) |
  Perform multiple biome replacements with unique source & destination biomes
- [ReplaceBiomes](./modifiers/ReplaceBiomes.groovy) | Replace one or more biomes with another biome
- [ReplaceBlockPairs](./modifiers/ReplaceBlockPairs.groovy) by [@Royal-Terrapin](https://github.com/Royal-Terrapin) |
  Perform multiple block replacements with unique source & destination blocks
- [ReplaceBlocks](./modifiers/ReplaceBlocks.groovy) | Replace one or more blocks with another block

## What about a filter that does xyz?

If you need a filter that does xyz, please open an issue, and I'll take it on when I have time.  
You can also just create a PR if you want to add your own filters.
