# mcascripts

A collection of [custom filter](https://github.com/Querz/mcaselector/wiki/Custom-Filter-Tutorial) scripts for the
Minecraft world editor [MCASelector](https://github.com/Querz/mcaselector).

## Contents

### Filters

Filters do not modify the world, they only select the chunks where the filter is true.
They can be used in the `Filter chunks (Ctrl + F)` dialog of MCA.

- [BlockEntityCounter](./filters/BlockEntityCounter.groovy) | Output the amount of a certain block entity to a file
- [ChestFiller](./modifiers/ChestFiller.groovy) | Select all chunks where changes would occur, when ran as a modifier
- [ChunkCleanup](./filters/ChunkCleanup.groovy) by \_adamo_ |
  Select all chunks that contain none of the specified blocks, block entities or entities
- [ContainsBiomes](./filters/ContainsBiomes.groovy) | Select all chunks that contain any of the specified biomes
- [ContainsTopLevelTag](./filters/ContainsTopLevelTag.groovy) |
  Select all chunks that contain the specified top level tag
- [FindMobs](./filters/FindMobs.groovy) | Output the location of all mobs of the specified type to a file

### Modifiers (Change NBT)

Modifiers DO modify the world and don't select any chunks.
They can be used in the `Change NBT (Ctrl + N)` dialog of MCA.
> [!WARNING]  
> As always when using MCA Selector, make a backup before using!

- [ChestFiller](./modifiers/ChestFiller.groovy) | Fill all chests with the specified item(s)
- [RemoveEntities](./modifiers/RemoveEntities.groovy) | Remove specific entities from chunks
- [RemoveLightingInfo](./modifiers/RemoveLightingInfo.groovy) by [@Querz](https://github.com/querz) |
  Remove all lighting information from chunks, which can help with lighting issues
- [RemoveTopLevelTag](./modifiers/RemoveTopLevelTag.groovy) |
  Remove the specified top level tag from all chunks
- [ReplaceBiomePairs](./modifiers/ReplaceBiomePairs.groovy) by [@Royal-Terrapin](https://github.com/Royal-Terrapin) |
  Perform multiple biome replacements with unique source & destination biomes
- [ReplaceBiomes](./modifiers/ReplaceBiomes.groovy) | Replace one or more biomes with another biome
- [ReplaceBlockPairs](./modifiers/ReplaceBlockPairs.groovy) by [@Royal-Terrapin](https://github.com/Royal-Terrapin) |
  Perform multiple block replacements with unique source & destination blocks
- [ReplaceBlocks](./modifiers/ReplaceBlocks.groovy) | Replace one or more blocks with another block

## Note About Supported Versions

The scripts may support different Minecraft versions. In the script description you can find the versions that are
supposed to work. However, not all versions have always been tested and due to multiple changes over the years to how
chunk data is stored it's often difficult to track and cover all changes correctly. So if you encounter a script that
does not seem to work on a version it's supposed to, please [open an issue](/issues), so I can fix it, thanks :)
*Also, if you want a script to support a version that it currently does not, please open an issue as well.*

## What about a filter that does xyz?

If you need a filter that does xyz, please [open an issue](/issues), and I'll take it on when I have time.  
You can also just create a PR if you want to add your own filters.
