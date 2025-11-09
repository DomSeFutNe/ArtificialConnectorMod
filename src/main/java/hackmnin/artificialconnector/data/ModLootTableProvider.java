package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ModBlocks;
import hackmnin.artificialconnector.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
// KORREKTUR: Wir brauchen BlockLootSubProvider, nicht nur LootTableSubProvider
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
// KORREKTUR: Import für SetItemCountFunction
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
// KORREKTUR: Import für ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors; // KORREKTUR: Fehlender Import

/**
 * Generiert Loot-Tabellen (z.B. Block-Drops).
 */
public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput pOutput,
            CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        super(pOutput, Set.of(), List.of(
                // Diese Zeile ist jetzt korrekt, da ModBlockLoot die richtige Superklasse hat
                new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)),
                pLookupProvider);
    }

    /**
     * Interne Klasse, die die eigentliche Logik für Block-Loot enthält. KORREKTUR: Erbt von
     * BlockLootSubProvider, nicht LootTableSubProvider
     */
    private static class ModBlockLoot extends BlockLootSubProvider {

        protected ModBlockLoot(HolderLookup.Provider pRegistries) {
            // KORREKTUR: FeatureFlags.VANILLA_SET wird hier übergeben
            super(Set.of(), FeatureFlags.VANILLA_SET, pRegistries);
        }

        /**
         * Hier definieren wir unsere Block-Drops. (Diese @Override ist jetzt korrekt)
         */
        @Override
        protected void generate() {
            this.add(ModBlocks.ARTIFICIAL_ORE.get(), createOreDrop(ModBlocks.ARTIFICIAL_ORE.get(),
                    ModItems.RAW_ARTIFICIAL_ORE.get()));
            this.dropSelf(ModBlocks.ARTIFICIAL_BLOCK.get());
        }

        /**
         * Überschreibt die Standard-Methode, damit wir nur unsere eigenen Blöcke hinzufügen.
         * (Diese @Override ist jetzt korrekt)
         */
        @Override
        protected Iterable<Block> getKnownBlocks() {
            // KORREKTUR: Wir konvertieren den Stream in eine Liste
            return ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get)
                    .collect(Collectors.toList());
        }

        /**
         * Eine Standard-Methode, um einen "Erz-Drop" zu erstellen (1 Item, Respektiert
         * Seiden-Note).
         */
        protected LootTable.Builder createOreDrop(Block pBlock, Item pItem) {
            return createSilkTouchDispatchTable(pBlock,
                    applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                            // KORREKTUR: Wir müssen SetItemCountFunction verwenden
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))));
        }
    }
}
