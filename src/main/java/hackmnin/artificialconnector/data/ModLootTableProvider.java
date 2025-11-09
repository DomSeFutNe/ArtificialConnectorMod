package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ModBlocks;
import hackmnin.artificialconnector.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Generiert Loot-Tabellen (z.B. Block-Drops).
 */
public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput pOutput,
            CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        // Wichtig: Wir generieren nur BLOCK-Loot-Tabellen
        super(pOutput, Set.of(),
                List.of(new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)),
                pLookupProvider);
    }

    /**
     * Interne Klasse, die die eigentliche Logik für Block-Loot enthält.
     */
    private static class ModBlockLoot extends LootTableSubProvider {
        protected ModBlockLoot(HolderLookup.Provider pRegistries) {
            super(pRegistries, FeatureFlags.VANILLA_SET);
        }

        /**
         * Hier definieren wir unsere Block-Drops.
         */
        @Override
        protected void generate() {
            // Sagt dem Spiel: "Erstelle eine Loot-Tabelle für ARTIFICIAL_ORE,
            // die RAW_ARTIFICIAL_ORE droppt."
            // (Wir müssen eine eigene Methode 'createOreDrop' verwenden,
            // da 'dropSelf' den Block selbst droppen würde)
            add(ModBlocks.ARTIFICIAL_ORE.get(), createOreDrop(ModBlocks.ARTIFICIAL_ORE.get(),
                    ModItems.RAW_ARTIFICIAL_ORE.get()));
        }

        /**
         * Überschreibt die Standard-Methode, damit wir nur unsere eigenen Blöcke hinzufügen.
         */
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get)::iterator;
        }

        /**
         * Eine Standard-Methode, um einen "Erz-Drop" zu erstellen (1 Item, Respektiert
         * Seiden-Note). (Kopiert aus der Minecraft-Daten-Generierung)
         */
        protected LootTable.Builder createOreDrop(Block pBlock, Item pItem) {
            return createSilkTouchDispatchTable(pBlock, applyExplosionDecay(pBlock,
                    LootItem.lootTableItem(pItem).apply(ConstantValue.exactly(1.0F))));
        }
    }
}
