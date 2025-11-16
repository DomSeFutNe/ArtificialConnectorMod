package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ModBlocks;
import hackmnin.artificialconnector.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Generates loot tables (e.g., block drops).
 */
public class ModLootTableProvider extends LootTableProvider {

    public ModLootTableProvider(PackOutput pOutput,
            CompletableFuture<HolderLookup.Provider> pLookupProvider) {
        super(pOutput, Set.of(),
                List.of(new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)),
                pLookupProvider);
    }

    /**
     * Inner class that contains the actual logic for block loot.
     */
    private static class ModBlockLoot extends BlockLootSubProvider {

        protected ModBlockLoot(HolderLookup.Provider pRegistries) {
            super(Set.of(), FeatureFlags.VANILLA_SET, pRegistries);
        }

        /**
         * This is where we define our block drops.
         */
        @Override
        protected void generate() {
            this.add(ModBlocks.ARTIFICIAL_ORE.get(), createOreDrop(ModBlocks.ARTIFICIAL_ORE.get(),
                    ModItems.RAW_ARTIFICIAL_ORE.get()));
            this.dropSelf(ModBlocks.ARTIFICIAL_BLOCK.get());
            this.dropSelf(ModBlocks.CONNECTOR_BLOCK.get());
        }

        /**
         * Overrides the default method so we only add our own blocks.
         */
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get)
                    .collect(Collectors.toList());
        }

        /**
         * A standard method to create an "ore drop" (1 item, respects Silk Touch).
         */
        @Override
        protected LootTable.Builder createOreDrop(Block pBlock, Item pItem) {
            return createSilkTouchDispatchTable(pBlock,
                    applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))));
        }
    }
}
