package hackmnin.artificialconnector;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

/**
 * Manages and registers all Blocks for the mod.
 */
public class ModBlocks {

        // Der DeferredRegister für Blöcke
        public static final DeferredRegister<Block> BLOCKS =
                        DeferredRegister.create(Registries.BLOCK, ArtificialConnectorMod.MODID);

        // --- Block-Definitionen ---
        // ... (in ModBlocks.java)

        // ... (BLOCKS DeferredRegister) ...

        /**
         * Our Artificial Ore Block. We now use our custom ArtificialOreBlock class.
         */
        public static final DeferredHolder<Block, Block> ARTIFICIAL_ORE = registerBlock(
                        "artificial_ore",
                        () -> new ArtificialOreBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.STONE).strength(3.0f, 3.0f)
                                        .requiresCorrectToolForDrops().sound(SoundType.STONE)),
                        Rarity.EPIC); // <-- Pass rarity to the helper

        /**
         * Our Artificial Block (from Ingots). We now use our custom ArtificialBlock class.
         */
        public static final DeferredHolder<Block, Block> ARTIFICIAL_BLOCK = registerBlock(
                        "artificial_block",
                        () -> new ArtificialBlock(BlockBehaviour.Properties.of()
                                        .mapColor(MapColor.METAL).strength(5.0f, 6.0f)
                                        .requiresCorrectToolForDrops().sound(SoundType.METAL)),
                        Rarity.EPIC); // <-- Pass rarity to the helper

        /**
         * Helper method to register a block and its item, now with Rarity.
         * 
         * @param name The name of the block
         * @param block The block supplier
         * @param rarity The rarity for the BlockItem
         * @return A DeferredHolder pointing to the block
         */
        private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name,
                        Supplier<T> block, Rarity rarity) {
                // 1. Register the block
                DeferredHolder<Block, T> blockHolder = BLOCKS.register(name, block);

                // 2. Register the BlockItem, now passing the rarity
                ModItems.ITEMS.register(name, () -> new BlockItem(blockHolder.get(),
                                new Item.Properties().rarity(rarity)));

                return blockHolder;
        }

        // We keep the old helper method just in case we add non-sparkling blocks later
        /**
         * Overloaded helper for default rarity.
         */
        private static <T extends Block> DeferredHolder<Block, T> registerBlock(String name,
                        Supplier<T> block) {
                return registerBlock(name, block, Rarity.COMMON);
        }

        /**
         * Diese Methode wird in der Haupt-Mod-Klasse aufgerufen, um den Block-Register zu
         * "aktivieren".
         */
        public static void register(IEventBus eventBus) {
                BLOCKS.register(eventBus);
        }
}
