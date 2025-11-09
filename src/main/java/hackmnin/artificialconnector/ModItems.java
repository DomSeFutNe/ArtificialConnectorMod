package hackmnin.artificialconnector;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Manages and registers all items for the Artificial Connector mod. This class uses Deferred
 * Registers to safely register items at the correct time.
 */
public class ModItems {

        /**
         * The Deferred Register for all items in our mod. We attach this to the game's central item
         * registry.
         */
        public static final DeferredRegister<Item> ITEMS =
                        DeferredRegister.create(Registries.ITEM, ArtificialConnectorMod.MODID);

        // --- ITEM DEFINITIONS ---

        /**
         * The registration for "Raw Artificial Ore".
         */
        public static final DeferredHolder<Item, Item> RAW_ARTIFICIAL_ORE =
                        ITEMS.register("raw_artificial_ore", () -> new Item(new Item.Properties()));

        /**
         * The registration for "Artificial Ingot".
         */
        public static final DeferredHolder<Item, Item> ARTIFICIAL_INGOT =
                        ITEMS.register("artificial_ingot", () -> new Item(new Item.Properties()));

        /**
         * The registration for "Artificial Block".
         */
        public static final DeferredHolder<Item, Item> ARTIFICIAL_BLOCK =
                        ITEMS.register("artificial_block", () -> new Item(new Item.Properties()));

        /**
         * The registration for "Artificial Nugget".
         */
        public static final DeferredHolder<Item, Item> ARTIFICIAL_NUGGET =
                        ITEMS.register("artificial_nugget", () -> new Item(new Item.Properties()));

        /**
         * Registers all items with the mod's event bus. This method is called from the main mod
         * class constructor.
         *
         * @param eventBus The mod's event bus.
         */
        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}
