import net.querz.mcaselector.io.mca.ChunkData
import net.querz.nbt.*
import org.apache.logging.log4j.LogManager
import org.codehaus.groovy.runtime.typehandling.GroovyCastException

/**
 * Adds the items in `itemsToAdd` to all chests.
 * By default, the items are added to random empty slots and will not change existing items.
 *
 * By editing this script, it is possible to add items to other containers
 * and to specify exactly how the item(s) should be added.
 *
 * IMPORTANT: When used as chunk filter, this will not modify the chest contents!
 *
 * @type Change NBT (Ctrl + N) or Chunk Filter (Ctrl + F)
 * @difficulty ADVANCED
 * @return true if any chests were modified
 */
static boolean run(ChunkData data) {
    var LOGGER = LogManager.getLogger("ChestFiller")
    LOGGER.debug("Filling Chests in Chunk {}", data.region()?.getAbsoluteLocation())
    var clearChests = false // if true, clears all items in chests before adding new items
    var itemsToAdd = [ // example items to add
                       new ContainerItem("minecraft:diamond", 8),
                       new ContainerItem("minecraft:elytra"),
                       ContainerItem
                           .of('{"id":"minecraft:diamond_sword"}'), // 'count', 'Slot' and 'components' are optional
                       ContainerItem
                           .of(
                               '{"id":"minecraft:enchanted_book","count":3,' +
                               '"components":{"minecraft:stored_enchantments":' +
                               '{"levels":{"minecraft:blast_protection":4,"minecraft:depth_strider":3}}}'
                           ),
                       ContainerItem.of(
                           new CompoundTag(
                               [
                                   "id"        : StringTag.valueOf("minecraft:enchanted_golden_apple"),
                                   "Slot"      : ByteTag.valueOf(20 as byte),
                                   "components": new CompoundTag(
                                       [
                                           "minecraft:custom_name": StringTag.valueOf('{"text":"God Apple"}'),
                                       ]
                                   )
                               ]
                           )
                       )
    ]

    var changed = false
    var blockEntities = data.region()?.data?.getListTag("block_entities")
    if (!blockEntities) return false
    for (blockEntity in blockEntities as List<CompoundTag>) {
        if (blockEntity.getString("id") != "minecraft:chest") continue
        LOGGER
            .debug(
                "Chest found at {}, {}, {}",
                blockEntity.getIntTag("x"),
                blockEntity.getIntTag("y"),
                blockEntity.getIntTag("z")
            )
        if (clearChests) blockEntity.getListTag("Items")?.clear()
        for (item in itemsToAdd) {
            LOGGER.debug("Adding ${item.count} '${item.id}' to chest")
            addContainerItem(blockEntity, item.copy(), ConflictType.SKIP, InsertionType.RANDOM)
        }
        changed = true
    }
    return changed
}

static void apply(ChunkData data) {
    filter(data)
}

static boolean filter(ChunkData data) {
    try {
        return run(data)
    } catch (e) {
        LogManager.getLogger("ChestFiller").error("Error running ChestFiller script", e)
        throw e
    }
}

static boolean addContainerItem(
    CompoundTag container,
    ContainerItem item,
    ConflictType conflictType = ConflictType.SKIP,
    InsertionType insertionType = InsertionType.RANDOM,
    IntRange containerIndexRange = 0..26 // index range of a single chest
) {
    if (!container.containsKey("Items")) container.put("Items", new ListTag())
    var items = container.getListTag("Items") as List<CompoundTag>

    var occupiedSlots = items.collect { (it as CompoundTag).getByte("Slot").intValue() }.toSet()
    if (item.slot.is(null)) {
        var emptySlots = containerIndexRange.toSet() - occupiedSlots
        var rnd = new Random()
        var choices = emptySlots ?: containerIndexRange
        switch (insertionType) {
            case InsertionType.FIRST:
                item.slot = choices.min()
                break
            case InsertionType.LAST:
                item.slot = choices.max()
                break
            case InsertionType.RANDOM:
                item.slot = choices[rnd.nextInt(choices.size())]
                break
        }
    }

    if (item.slot < 0) item.slot = containerIndexRange[Math.max(item.slot, -containerIndexRange.size())]
    if (item.slot in occupiedSlots) {
        switch (conflictType) {
            case ConflictType.SKIP: return false
            case ConflictType.ADD:
                var existing = items.find { it.getByte("Slot").intValue() == item.slot } as CompoundTag
                if (!item.equalsType(existing)) return false
                item.count += existing.getInt("count")
                items.remove(existing)
                break
            case ConflictType.REPLACE:
                items.removeIf { it.getByte("Slot").intValue() == item.slot }
                break
        }
    }
    items.add(item as CompoundTag)
    return true
}

class ContainerItem {
    private CompoundTag data = new CompoundTag()

    ContainerItem(String id, int count = 1, Integer slot = null, CompoundTag components = null) {
        this.id = id
        this.count = count
        this.slot = slot
        this.components = components
    }

    String getId() { this.data.getString("id") }

    void setId(String id) { this.data.putString("id", id) }

    int getCount() { this.data.getInt("count") }

    void setCount(int count) { this.data.putInt("count", count) }

    Integer getSlot() { this.data.getByteTag("Slot")?.asInt() }

    void setSlot(Integer slot) {
        slot.is(null) ? this.data.remove("Slot") : this.data.putByte("Slot", slot.byteValue())
    }

    CompoundTag getComponents() { this.data.getCompoundTag("components") }

    void setComponents(CompoundTag components) {
        components.is(null) || !components.size() ? this.data.remove("components") : this.data.put(
            "components",
            components
        )
    }

    ContainerItem copy() {
        return of(this.data.copy())
    }

    String toString() {
        return "ContainerItem{id='${this.id}', count=${this.count}, slot=${this.slot}, components=${this.components}}"
    }

    Object asType(Class clazz) {
        if (clazz == this.class) return this
        if (clazz == CompoundTag) return this.data.copy()
        throw new GroovyCastException(this, clazz)
    }

    boolean equalsType(Object other) {
        if (other instanceof ContainerItem) {
            return this.id == other.id && this.components == other.components
        }
        if (other instanceof CompoundTag) {
            return this.id == other.getString("id") && this.components == other.getCompoundTag("components")
        }
        return false
    }

    static ContainerItem of(CompoundTag tag) {
        return new ContainerItem(
            tag.getString("id"),
            tag.getIntTag("count")?.asInt() ?: 1,
            tag.getByteTag("Slot")?.asInt(),
            tag.getCompoundTag("components")
        )
    }

    static ContainerItem of(String snbt) {
        return of(NBTUtil.fromSNBT(snbt) as CompoundTag)
    }
}

enum ConflictType {
    SKIP, // skip if occupied
    ADD, // adds count to existing item if item type matches, may fail
    REPLACE, // overwrites existing item, always succeeds
}

enum InsertionType {
    FIRST, // first empty slot, else first slot
    LAST, // last empty slot, else last slot
    RANDOM // random empty slot, else random slot
}