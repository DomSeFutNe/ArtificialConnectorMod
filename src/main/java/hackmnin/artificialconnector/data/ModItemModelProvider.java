package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModBlocks; // Import added for the block
import hackmnin.artificialconnector.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block; // Import added for the block
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
// IMPORT THIS: This class allows us to link to a model without checking if it exists yet
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Generates all item models (.json files in assets/models/item).
 */
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArtificialConnectorMod.MODID, existingFileHelper);
    }

    /**
     * This is where we define the models for our items.
     */
    @Override
    protected void registerModels() {
        // --- Simple Items (2D Textures) ---
        simpleItem(ModItems.RAW_ARTIFICIAL_ORE);
        simpleItem(ModItems.ARTIFICIAL_INGOT);
        simpleItem(ModItems.ARTIFICIAL_NUGGET);
        simpleItem(ModItems.ARTIFICIAL_WRENCH);

        // --- Block Items (Models that point to a block model) ---
        // We use our 'itemModel' helper for blocks
        itemModel(ModBlocks.ARTIFICIAL_ORE);
        itemModel(ModBlocks.ARTIFICIAL_BLOCK);
    }

    /**
     * Helper method to register a simple 2D item model.
     * 
     * @param item The item to register a model for.
     */
    private ItemModelBuilder simpleItem(DeferredHolder<Item, Item> item) {
        String name = item.getId().getPath();
        return withExistingParent(name, "item/generated").texture("layer0", ResourceLocation
                .fromNamespaceAndPath(ArtificialConnectorMod.MODID, "item/" + name));
    }

    /**
     * Helper method to register a Block Item model. It points to the block model (in models/block/)
     * as its parent.
     *
     * @param block The block to create an item model for.
     */
    private ItemModelBuilder itemModel(DeferredHolder<Block, Block> block) {
        String name = block.getId().getPath();

        // FIX: We must use 'parent()' with an 'UncheckedModelFile'
        // 'withExistingParent()' fails because the block model hasn't been generated yet.
        // This new code creates the link without validating it, solving the crash.
        return parent(name, ResourceLocation.fromNamespaceAndPath(ArtificialConnectorMod.MODID,
                "block/" + name));
    }

    /**
     * Helper method to create a parent link without checking for the file's existence.
     */
    private ItemModelBuilder parent(String name, ResourceLocation parent) {
        return getBuilder(name).parent(new ModelFile.UncheckedModelFile(parent));
    }
}
