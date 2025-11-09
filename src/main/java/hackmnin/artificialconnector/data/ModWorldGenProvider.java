package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
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

    // Helper class to hold our registry keys
    public static class Registries {
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE,
                        ModWorldGenProvider::bootstrapConfiguredFeatures)
                .add(Registries.PLACED_FEATURE, ModWorldGenProvider::bootstrapPlacedFeatures)
                .add(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS,
                        ModWorldGenProvider::bootstrapBiomeModifiers);
    }

    /**
     * Standard constructor.
     */
    public ModWorldGenProvider(PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Registries.BUILDER, Set.of(ArtificialConnectorMod.MODID));
    }

    // --- 1. Configured Features (The "What") ---
    private static void bootstrapConfiguredFeatures(HolderLookup.Provider registries,
            RegistrySetBuilder.RegistryBootstrap<ConfiguredFeature<?, ?>> context) {
        // Defines the ore vein that replaces stone
        List<OreConfiguration.TargetBlockState> oreTargets =
                List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                        ModBlocks.ARTIFICIAL_ORE.get().defaultBlockState()));

        // Registers a "Configured Feature" JSON
        FeatureUtils.register(context, "artificial_ore_feature", Feature.ORE,
                // Vein size = 8
                new OreConfiguration(oreTargets, 8));
    }

    // --- 2. Placed Features (The "Where" - WITH ENV VARS!) ---
    private static void bootstrapPlacedFeatures(HolderLookup.Provider registries,
            RegistrySetBuilder.RegistryBootstrap<PlacedFeature> context) {
        // Get the "Configured Feature" we just defined
        var configuredFeatures = registries.lookupOrThrow(Registries.CONFIGURED_FEATURE);

        // --- READ ENV VARS ---
        // We read your requested variables from the system environment
        // We provide sane defaults (e.g., -64 to 128) if the variables are not set.
        int minHeight = getEnvVar("ORE_SPAWNS_MIN", -64);
        int maxHeight = getEnvVar("ORE_SPAWNS_MAX", 128);
        // We also add one for "veins per chunk"
        int veinsPerChunk = getEnvVar("ORE_SPAWNS_PER_CHUNK", 10);

        ArtificialConnectorMod.LOGGER.info(
                "Generating ore with Height: [{} to {}], VeinsPerChunk: {}", minHeight, maxHeight,
                veinsPerChunk);

        // Defines the placement rules
        List<PlacementModifier> placementModifiers = List.of(PlacementUtils.count(veinsPerChunk), // How
                                                                                                  // many
                                                                                                  // veins
                                                                                                  // per
                                                                                                  // chunk
                PlacementUtils.inSquare(), // Spread horizontally
                HeightRangePlacement.uniform( // Height range
                        net.minecraft.world.level.levelgen.VerticalAnchor.absolute(minHeight),
                        net.minecraft.world.level.levelgen.VerticalAnchor.absolute(maxHeight)));

        // Registers a "Placed Feature" JSON
        PlacementUtils.register(context, "artificial_ore_placed",
                configuredFeatures.getOrThrow(BuiltInRegistries.CONFIGURED_FEATURE
                        .getResourceKey(BuiltInRegistries.CONFIGURED_FEATURE.get(
                                ResourceLocation.fromNamespaceAndPath(ArtificialConnectorMod.MODID,
                                        "artificial_ore_feature")))
                        .orElseThrow()),
                placementModifiers);
    }

    // --- 3. Biome Modifiers (The "In Which Biomes") ---
    private static void bootstrapBiomeModifiers(HolderLookup.Provider registries,
            RegistrySetBuilder.RegistryBootstrap<BiomeModifier> context) {
        var placedFeatures = registries.lookupOrThrow(Registries.PLACED_FEATURE);

        // Registers a "Biome Modifier" JSON
        context.register(BuiltInRegistries.BIOME_MODIFIER
                .getResourceKey(BuiltInRegistries.BIOME_MODIFIER.get(ResourceLocation
                        .fromNamespaceAndPath(ArtificialConnectorMod.MODID, "add_artificial_ore")))
                .orElseThrow(),
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        // Selects all biomes tagged as "IS_OVERWORLD"
                        registries.lookupOrThrow(Registries.BIOME)
                                .getOrThrow(BiomeTags.IS_OVERWORLD),
                        // Adds our Placed Feature
                        Set.of(placedFeatures
                                .getOrThrow(
                                        BuiltInRegistries.PLACED_FEATURE
                                                .getResourceKey(BuiltInRegistries.PLACED_FEATURE
                                                        .get(ResourceLocation.fromNamespaceAndPath(
                                                                ArtificialConnectorMod.MODID,
                                                                "artificial_ore_placed")))
                                                .orElseThrow())),
                        // In the "UNDERGROUND_ORES" generation step
                        GenerationStep.Decoration.UNDERGROUND_ORES));
    }

    /**
     * Helper function to read an Environment Variable and parse it as an Integer. Provides a
     * default value if the variable is missing or invalid.
     */
    private static int getEnvVar(String name, int defaultValue) {
        String value = System.getenv(name);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                ArtificialConnectorMod.LOGGER.warn(
                        "Could not parse env var [{}]. Value was [{}]. Using default [{}].", name,
                        value, defaultValue);
            }
        }
        return defaultValue;
    }
}
