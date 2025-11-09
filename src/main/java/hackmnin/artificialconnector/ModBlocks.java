package hackmnin.artificialconnector;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

/**
 * Manages and registers all Blocks for the mod.
 */
public class ModBlocks {

    // Der DeferredRegister für Blöcke
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ArtificialConnectorMod.MODID);

    // --- Block-Definitionen ---

    /**
     * Unser Artificial Ore Block. Wir definieren hier seine Eigenschaften (z.B. wie Stein, Sound,
     * Härte).
     */
    public static final DeferredHolder<Block, Block> ARTIFICIAL_ORE =
            registerBlock("artificial_ore",
                    () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
                            .strength(3.0f, 3.0f) // Härte & Explosionsresistenz (wie Stein)
                            .requiresCorrectToolForDrops() // Braucht eine Spitzhacke
                            .sound(SoundType.STONE))); // Sound von Stein


    // --- Hilfsmethoden ---

    /**
     * Eine Helfermethode, um einen Block und sein zugehöriges Item automatisch zu registrieren.
     * 
     * @param name Der Name des Blocks (z.B. "artificial_ore")
     * @param block Der Block (Supplier)
     * @return Ein DeferredHolder, der auf den Block verweist
     */
    private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name,
            Supplier<T> block) {
        // 1. Den Block registrieren
        DeferredHolder<Block, T> blockHolder = BLOCKS.register(name, block);

        // 2. Das "BlockItem" registrieren (damit es im Inventar existiert)
        // Wir benutzen die registerBlockItem-Helfermethode
        ModItems.ITEMS.register(name,
                () -> new BlockItem(blockHolder.get(), new Item.Properties()));

        return blockHolder;
    }

    /**
     * Diese Methode wird in der Haupt-Mod-Klasse aufgerufen, um den Block-Register zu "aktivieren".
     */
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
