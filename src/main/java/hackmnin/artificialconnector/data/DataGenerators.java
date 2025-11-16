package hackmnin.artificialconnector.data;

// import hackmnin.artificialconnector.data.ModWorldGenProvider;
// import hackmnin.artificialconnector.data.ModBlockStateProvider;
// import hackmnin.artificialconnector.data.ModLootTableProvider;

import net.minecraft.data.DataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.concurrent.CompletableFuture;

import hackmnin.artificialconnector.ArtificialConnectorMod;

/**
 * Main class that hooks into the GatherDataEvent to register all our data generators.
 */
public class DataGenerators {

        /**
         * This method is called by the NeoForge event bus when the 'runData' task is executed.
         * 
         * @param event The event containing helper objects.
         */
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
                ArtificialConnectorMod.Lo.info("Gathering data...");

                DataGenerator generator = event.getGenerator();
                PackOutput packOutput = generator.getPackOutput();
                ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
                CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

                ArtificialConnectorMod.Lo.info("Creating server side providers...");
                ArtificialConnectorMod.Lo.info("Creating recipe provider...");
                generator.addProvider(event.includeServer(),
                                (DataProvider.Factory<ModRecipeProvider>) (
                                                PackOutput output) -> new ModRecipeProvider(output,
                                                                lookupProvider));

                ArtificialConnectorMod.Lo.info("Creating block state provider...");
                generator.addProvider(event.includeServer(),
                                (DataProvider.Factory<LootTableProvider>) (
                                                PackOutput p) -> new ModLootTableProvider(p,
                                                                lookupProvider));

                ArtificialConnectorMod.Lo.info("Creating client side providers...");
                ArtificialConnectorMod.Lo.info("Creating item model provider...");
                generator.addProvider(event.includeClient(),
                                (DataProvider.Factory<ModItemModelProvider>) (
                                                PackOutput output) -> new ModItemModelProvider(
                                                                output, existingFileHelper));

                ArtificialConnectorMod.Lo.info("Creating block state provider...");
                generator.addProvider(event.includeClient(),
                                (DataProvider.Factory<ModBlockStateProvider>) (
                                                PackOutput p) -> new ModBlockStateProvider(p,
                                                                existingFileHelper));

                ArtificialConnectorMod.Lo.info("Creating language provider...");
                generator.addProvider(event.includeClient(),
                                (DataProvider.Factory<ModLangProvider>) (
                                                PackOutput output) -> new ModLangProvider(output,
                                                                "en_us"));

                ArtificialConnectorMod.Lo.info("Creating both side providers...");
                ArtificialConnectorMod.Lo.info("Creating world generation provider...");
                generator.addProvider(event.includeServer() || event.includeClient(),
                                new ModWorldGenProvider(packOutput, lookupProvider));

                ArtificialConnectorMod.Lo.info("Data gathering complete.");
        }
}
