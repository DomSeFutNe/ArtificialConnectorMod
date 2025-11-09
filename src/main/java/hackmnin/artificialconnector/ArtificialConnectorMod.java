package hackmnin.artificialconnector;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hackmnin.artificialconnector.data.DataGenerators;
import com.mojang.serialization.MapCodec;

@Mod("artificialconnector")
public class ArtificialConnectorMod {
    // Logger for the mod
    public static final Logger Lo = LoggerFactory.getLogger("ArtificialConnectorMod");
    public static final String MODID = "artificialconnector";
    /**
     * Deferred Register for Configured Features (the "What").
     */
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registries.CONFIGURED_FEATURE, MODID);

    /**
     * Deferred Register for Placed Features (the "Where").
     */
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, MODID);

    /**
     * Deferred Register for Biome Modifiers (the "In Which Biomes").
     */
    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, MODID);

    public ArtificialConnectorMod(IEventBus modEventBus) {
        Lo.info("Registetring Artificial Connector Mod...");
        // --- Call the registration methods ---
        // This tells our ModItems class to register its items.
        Lo.info("Registering Mod Items and Blocks...");
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        Lo.info("Registration complete.");
        Lo.info("Setting up event listeners...");
        // Add an event listener for building creative mode tabs
        modEventBus.addListener(this::addCreative);
        Lo.info("Event listeners set up.");
        Lo.info("Setting up data generators...");
        // Register data generators
        modEventBus.addListener(DataGenerators::gatherData);
        Lo.info("Data generators set up.");
        Lo.info("Registering world generation features...");
        CONFIGURED_FEATURES.register(modEventBus);
        PLACED_FEATURES.register(modEventBus);
        BIOME_MODIFIERS.register(modEventBus);
        Lo.info("World generation features registered.");
        Lo.info("Artificial Connector Mod setup complete.");
    }

    /**
     * Event listener that adds our items to the creative mode tabs.
     * 
     * @param event The event fired when tabs are being built.
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Check if we are currently building the "Ingredients" tab
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            Lo.debug("Adding items to Ingredients creative tab.");
            Lo.debug("Adding Raw Artificial Ore to Ingredients tab.");
            event.accept(ModItems.RAW_ARTIFICIAL_ORE.get());
            Lo.debug("Adding Artificial Ingot to Ingredients tab.");
            event.accept(ModItems.ARTIFICIAL_INGOT.get());
            Lo.debug("Adding Artificial Nugget to Ingredients tab.");
            event.accept(ModItems.ARTIFICIAL_NUGGET.get());


            // Add Block to Building Blocks tab
            Lo.debug("Adding Artificial Block to Building Blocks tab.");
            Lo.debug("Adding Artificial Ore to Building Blocks tab.");
            event.accept(ModBlocks.ARTIFICIAL_ORE.get());
            Lo.debug("Adding Artificial Block to Building Blocks tab.");
            event.accept(ModBlocks.ARTIFICIAL_BLOCK.get());
        }
    }
}
