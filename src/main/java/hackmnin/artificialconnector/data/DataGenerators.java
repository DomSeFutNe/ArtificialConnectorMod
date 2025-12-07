package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * Main class that hooks into the GatherDataEvent to register all our data generators.
 */
public class DataGenerators {

        /**
         * This method is called by the NeoForge event bus when the 'runData' task is
         * executed.
         * 
         *
         * @param event The event containing helper objects.
         */
        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
                ArtificialConnectorMod.LOGGER.info("Gathering data...");

                final DataGenerator generator = event.getGenerator();
                final PackOutput packOutput = generator.getPackOutput();
                final ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
                final CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

                ArtificialConnectorMod.LOGGER.info("Creating server side providers...");
                ArtificialConnectorMod.LOGGER.info("Creating recipe provider...");
                generator.addProvider(event.includeServer(),
                                (DataProvider.Factory<ModRecipeProvider>) (PackOutput output) -> new ModRecipeProvider(
                                                output, lookupProvider));

                ArtificialConnectorMod.LOGGER.info("Creating block state provider...");
                generator.addProvider(event.includeServer(),
                                (DataProvider.Factory<LootTableProvider>) (PackOutput p) -> new ModLootTableProvider(p,
                                                lookupProvider));

                ArtificialConnectorMod.LOGGER.info("Creating client side providers...");
                ArtificialConnectorMod.LOGGER.info("Creating item model provider...");
                generator.addProvider(event.includeClient(),
                                (DataProvider.Factory<ModItemModelProvider>) (PackOutput output) -> new ModItemModelProvider(
                                                output, existingFileHelper));

                ArtificialConnectorMod.LOGGER.info("Creating block state provider...");
                generator.addProvider(event.includeClient(),
                                (DataProvider.Factory<ModBlockStateProvider>) (PackOutput p) -> new ModBlockStateProvider(
                                                p, existingFileHelper));

                ArtificialConnectorMod.LOGGER.info("Creating language provider...");
                generator.addProvider(event.includeClient(),
                                (DataProvider.Factory<ModLangProvider>) (PackOutput output) -> new ModLangProvider(
                                                output, "en_us"));

                ArtificialConnectorMod.LOGGER.info("Creating both side providers...");
                ArtificialConnectorMod.LOGGER.info("Creating world generation provider...");
                generator.addProvider(event.includeServer() || event.includeClient(),
                                new ModWorldGenProvider(packOutput, lookupProvider));

                ArtificialConnectorMod.LOGGER.info("Data gathering complete.");
        }
}
