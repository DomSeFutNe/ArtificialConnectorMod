package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
// import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey; // We need this for the dynamic tags
import net.minecraft.world.level.biome.Biome; // We need this for the dynamic tags
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
// import net.minecraft.tags.BlockTags; // Make sure this one is present!
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generates all worldgen related JSONs. Now configurable via Environment Variables.
 */
public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

        // --- Keys (remain the same) ---
        public static final ResourceKey<ConfiguredFeature<?, ?>> ARTIFICIAL_ORE_FEATURE_KEY =
                        registerKey("artificial_ore_feature");
        public static final ResourceKey<PlacedFeature> ARTIFICIAL_ORE_PLACED_KEY =
                        registerPlacedKey("artificial_ore_placed");
        public static final ResourceKey<BiomeModifier> ADD_ARTIFICIAL_ORE_KEY =
                        registerBiomeModifierKey("add_artificial_ore");

        // ... (All helper methods 'registerKey', 'registerPlacedKey', 'registerBiomeModifierKey'
        // remain exactly the same) ...
        // ... (CODE OMITTED FOR BREVITY) ...

        // --- Registry Builder (remains the same) ---
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                        .add(Registries.CONFIGURED_FEATURE,
                                        ModWorldGenProvider::bootstrapConfiguredFeatures)
                        .add(Registries.PLACED_FEATURE,
                                        ModWorldGenProvider::bootstrapPlacedFeatures)
                        .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS,
                                        ModWorldGenProvider::bootstrapBiomeModifiers);

        /**
         * Standard constructor.
         */
        public ModWorldGenProvider(PackOutput output,
                        CompletableFuture<HolderLookup.Provider> registries) {
                super(output, registries, BUILDER, Set.of(ArtificialConnectorMod.MODID));
        }

        // --- 1. Configured Features (The "What") ---
        private static void bootstrapConfiguredFeatures(
                        BootstrapContext<ConfiguredFeature<?, ?>> context) {

                // --- Read Dimension ENV VAR ---
                String dimension = getEnvVar("ORE_SPAWNS_IN", "overworld").toLowerCase();

                // Set the block tag to replace based on the dimension
                RuleTest targetRuleTest = switch (dimension) {
                        case "nether" -> {
                                System.out.println("Setting ore target to: NETHER");
                                // The 'yield' keyword returns the value from the switch block
                                yield new TagMatchTest(BlockTags.BASE_STONE_NETHER);
                        }
                        case "end" -> {
                                System.out.println("Setting ore target to: END");
                                yield new BlockMatchTest(Blocks.END_STONE);
                        }
                        default -> { // This catches "overworld" or any invalid input
                                System.out.println("Setting ore target to: OVERWORLD");
                                yield new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
                        }
                };

                // This part remains the same
                List<OreConfiguration.TargetBlockState> oreTargets = List
                                .of(OreConfiguration.target(targetRuleTest, ModBlocks.ARTIFICIAL_ORE
                                                .get().defaultBlockState()));

                context.register(ARTIFICIAL_ORE_FEATURE_KEY, new ConfiguredFeature<>(Feature.ORE,
                                new OreConfiguration(oreTargets, 8)) // Vein
                                                                     // size
                                                                     // =
                                                                     // 8
                );
        }

        // --- 2. Placed Features (The "Where" - WITH ENV VARS!) ---
        private static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
                var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

                // This part remains unchanged. These ENV VARS work for all dimensions.
                int minHeight = getEnvVar("ORE_SPAWNS_MIN", -64);
                int maxHeight = getEnvVar("ORE_SPAWNS_MAX", 128);
                int veinsPerChunk = getEnvVar("ORE_SPAWNS_PER_CHUNK", 10);

                System.out.println("Generating ore with Height: [" + minHeight + " to " + maxHeight
                                + "], VeinsPerChunk: " + veinsPerChunk);

                List<PlacementModifier> placementModifiers = List.of(
                                CountPlacement.of(veinsPerChunk), InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(
                                                net.minecraft.world.level.levelgen.VerticalAnchor
                                                                .absolute(minHeight),
                                                net.minecraft.world.level.levelgen.VerticalAnchor
                                                                .absolute(maxHeight)));

                context.register(ARTIFICIAL_ORE_PLACED_KEY,
                                new PlacedFeature(
                                                configuredFeatures.getOrThrow(
                                                                ARTIFICIAL_ORE_FEATURE_KEY),
                                                placementModifiers));
        }

        // --- 3. Biome Modifiers (The "In Which Biomes") ---
        private static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
                var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
                var biomes = context.lookup(Registries.BIOME);

                // --- NEW: Read Dimension ENV VAR ---
                String dimension = getEnvVar("ORE_SPAWNS_IN", "overworld").toLowerCase();

                // Set the biome tag to spawn in based on the dimension
                TagKey<Biome> targetBiomeTag = switch (dimension) {
                        case "nether" -> {
                                System.out.println("Setting spawn biomes to: IS_NETHER");
                                yield BiomeTags.IS_NETHER;
                        }
                        case "end" -> {
                                System.out.println("Setting spawn biomes to: IS_END");
                                yield BiomeTags.IS_END;
                        }
                        default -> {
                                System.out.println("Setting spawn biomes to: IS_OVERWORLD");
                                yield BiomeTags.IS_OVERWORLD; // Default
                        }
                };

                context.register(ADD_ARTIFICIAL_ORE_KEY,
                                new BiomeModifiers.AddFeaturesBiomeModifier(
                                                biomes.getOrThrow(targetBiomeTag), // Use our
                                                                                   // dynamic tag
                                                HolderSet.direct(placedFeatures.getOrThrow(
                                                                ARTIFICIAL_ORE_PLACED_KEY)),
                                                GenerationStep.Decoration.UNDERGROUND_ORES));
        }

        // --- Helper Methods ---

        /**
         * Helper function to read an Environment Variable and parse it as an Integer.
         */
        private static int getEnvVar(String name, int defaultValue) {
                String value = System.getenv(name);
                if (value != null) {
                        try {
                                return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                                // Use System.err since LOGGER is not available in a static context
                                System.err.println("Could not parse env var [" + name
                                                + "]. Value was [" + value + "]. Using default ["
                                                + defaultValue + "].");
                        }
                }
                return defaultValue;
        }

        /**
         * NEW: Helper function to read an Environment Variable as a String.
         */
        private static String getEnvVar(String name, String defaultValue) {
                String value = System.getenv(name);
                if (value == null || value.isBlank()) {
                        return defaultValue;
                }
                return value;
        }

        // ... (All helper methods 'registerKey', 'registerPlacedKey', 'registerBiomeModifierKey'
        // remain exactly the same) ...
        // ... (CODE OMITTED FOR BREVITY) ...

        // Helper to create a ResourceKey for ConfiguredFeatures
        private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
                return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation
                                .fromNamespaceAndPath(ArtificialConnectorMod.MODID, name));
        }

        // Helper to create a ResourceKey for PlacedFeatures
        private static ResourceKey<PlacedFeature> registerPlacedKey(String name) {
                return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation
                                .fromNamespaceAndPath(ArtificialConnectorMod.MODID, name));
        }

        // Helper to create a ResourceKey for BiomeModifiers
        private static ResourceKey<BiomeModifier> registerBiomeModifierKey(String name) {
                return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation
                                .fromNamespaceAndPath(ArtificialConnectorMod.MODID, name));
        }
}
