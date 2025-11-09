package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet; // Import for HolderSet (Fixes Error 6)
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement; // Import for CountPlacement
                                                                    // (Fixes Error 4)
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement; // Import for
                                                                       // InSquarePlacement (Fixes
                                                                       // Error 5)
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generates all worldgen related JSONs: 1. ConfiguredFeatures (The "What") 2. PlacedFeatures (The
 * "Where") 3. BiomeModifiers (The "In Which Biomes")
 */
public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

        // --- Define Keys for our Features ---
        public static final ResourceKey<ConfiguredFeature<?, ?>> ARTIFICIAL_ORE_FEATURE_KEY =
                        registerKey("artificial_ore_feature");

        // FIX (Error 1): Use the correct helper 'registerPlacedKey'
        public static final ResourceKey<PlacedFeature> ARTIFICIAL_ORE_PLACED_KEY =
                        registerPlacedKey("artificial_ore_placed");

        public static final ResourceKey<BiomeModifier> ADD_ARTIFICIAL_ORE_KEY =
                        registerBiomeModifierKey("add_artificial_ore");

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
                // FIX (Error 2): The registry is 'BIOME_MODIFIERS'
                return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation
                                .fromNamespaceAndPath(ArtificialConnectorMod.MODID, name));
        }

        // --- Registry Builder ---
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
                RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
                List<OreConfiguration.TargetBlockState> oreTargets = List.of(
                                OreConfiguration.target(stoneReplaceables, ModBlocks.ARTIFICIAL_ORE
                                                .get().defaultBlockState()));

                context.register(ARTIFICIAL_ORE_FEATURE_KEY, new ConfiguredFeature<>(Feature.ORE,
                                new OreConfiguration(oreTargets, 8)) // Vein size = 8
                );
        }

        // --- 2. Placed Features (The "Where" - WITH ENV VARS!) ---
        private static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
                var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

                int minHeight = getEnvVar("ORE_SPAWNS_MIN", -64);
                int maxHeight = getEnvVar("ORE_SPAWNS_MAX", 128);
                int veinsPerChunk = getEnvVar("ORE_SPAWNS_PER_CHUNK", 10);

                System.out.println("Generating ore with Height: [" + minHeight + " to " + maxHeight
                                + "], VeinsPerChunk: " + veinsPerChunk);

                List<PlacementModifier> placementModifiers = List.of(
                                // FIX (Error 4): Use CountPlacement.of()
                                CountPlacement.of(veinsPerChunk),
                                // FIX (Error 5): Use InSquarePlacement.spread()
                                InSquarePlacement.spread(),
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

                context.register(ADD_ARTIFICIAL_ORE_KEY,
                                new BiomeModifiers.AddFeaturesBiomeModifier(
                                                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                                                // FIX (Error 6): Use HolderSet.direct() to wrap the
                                                // feature
                                                HolderSet.direct(placedFeatures.getOrThrow(
                                                                ARTIFICIAL_ORE_PLACED_KEY)),
                                                GenerationStep.Decoration.UNDERGROUND_ORES));
        }

        /**
         * Helper function to read an Environment Variable.
         */
        private static int getEnvVar(String name, int defaultValue) {
                String value = System.getenv(name);
                if (value != null) {
                        try {
                                return Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                                System.err.println("Could not parse env var [" + name
                                                + "]. Value was [" + value + "]. Using default ["
                                                + defaultValue + "].");
                        }
                }
                return defaultValue;
        }
}
