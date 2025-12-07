package hackmnin.artificialconnector;

import com.mojang.serialization.MapCodec;
import hackmnin.artificialconnector.config.ModConfigs;
import hackmnin.artificialconnector.data.DataGenerators;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main mod class for the Artificial Connector Mod.
 */
@Mod("artificialconnector")
public class ArtificialConnectorMod {
  // Logger for the mod
  public static final Logger LOGGER = LoggerFactory.getLogger(ArtificialConnectorMod.class);
  public static final String MOD_ID = "artificialconnector";
  /**
   * Deferred Register for Configured Features (the "What").
   */
  public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
      DeferredRegister.create(Registries.CONFIGURED_FEATURE, MOD_ID);

  /**
   * Deferred Register for Placed Features (the "Where").
   */
  public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
      DeferredRegister.create(Registries.PLACED_FEATURE, MOD_ID);

  /**
   * Deferred Register for Biome Modifiers (the "In Which Biomes").
   */
  public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS =
      DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, MOD_ID);

  /**
   * Constructor for the Artificial Connector Mod. This sets up all necessary registrations and
   * event listeners.
   * 
   *
   * @param modContainer The container for this mod, used for config registration.
   * 
   */
  public ArtificialConnectorMod(ModContainer modContainer) {
    LOGGER.info("Registetring Artificial Connector Mod...");

    // Register our client-side config using the ModContainer.
    modContainer.registerConfig(ModConfig.Type.CLIENT, ModConfigs.CLIENT_SPEC);

    final IEventBus modEventBus = modContainer.getEventBus();

    // --- Call the registration methods ---
    // This tells our ModItems class to register its items.
    LOGGER.info("Registering Mod Items and Blocks...");
    ModItems.register(modEventBus);
    ModBlocks.register(modEventBus);
    ModBlockEntities.register(modEventBus);
    ModCreativeTabs.register(modEventBus);
    LOGGER.info("Registration complete.");
    LOGGER.info("Setting up data generators...");
    // Register data generators
    modEventBus.addListener(DataGenerators::gatherData);
    LOGGER.info("Data generators set up.");
    LOGGER.info("Registering world generation features...");
    CONFIGURED_FEATURES.register(modEventBus);
    PLACED_FEATURES.register(modEventBus);
    BIOME_MODIFIERS.register(modEventBus);
    LOGGER.info("World generation features registered.");
    LOGGER.info("Artificial Connector Mod setup complete.");
  }
}
