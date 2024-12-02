# mcascripts

A collection of [custom filter](https://github.com/Querz/mcaselector/wiki/Custom-Filter-Tutorial) scripts for the
Minecraft world editor [MCASelector](https://github.com/Querz/mcaselector).

## Contents

### Filters

Filters do not modify the world, they only select the chunks where the filter is true.

- [ContainsBiomes](./filters/ContainsBiomes.groovy) | Select all chunks that contain any of the specified biomes
- [FindMobs](./filters/FindMobs.groovy) | Output the location of all the mobs of the specified type to a file

### Modifiers

Modifiers DO modify the world and don't select any chunks.
> [!WARNING]  
> As always when using MCA Selector, make a backup before using!

- [ChestFiller](./modifiers/ChestFiller.groovy) | Fill all chests with the specified item(s)
- [ReplaceBiomes](./modifiers/ReplaceBiomes.groovy) | Replace one or more biomes with another biome
- [ReplaceBiomePairs](./modifiers/ReplaceBiomePairs.groovy) | Perform multiple biome replacements with unique source & destination biomes
- [ReplaceBlocks](./modifiers/ReplaceBlocks.groovy) | Replace one or more blocks with another block
- [ReplaceBlockPairs](./modifiers/ReplaceBlockPairs.groovy) | Perform multiple block replacements with unique source & destination blocks

## What about a filter that does xyz?

If you need a filter that does xyz, please open an issue, and I'll take it on when I have time.  
You can also just create a PR if you want to add your own filters.
