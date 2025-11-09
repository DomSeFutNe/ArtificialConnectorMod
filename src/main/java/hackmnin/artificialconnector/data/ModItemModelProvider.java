package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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
        // This is a simple 2D "flat" item model.
        // It automatically assumes the parent is "item/generated" and the texture
        // is in "textures/item/" and has the same name as the item.

        simpleItem(ModItems.RAW_ARTIFICIAL_ORE);
        simpleItem(ModItems.ARTIFICIAL_INGOT);
        simpleItem(ModItems.ARTIFICIAL_NUGGET);
        simpleItem(ModItems.ARTIFICIAL_BLOCK);
    }

    /**
     * Helper method to register a simple item model.
     * 
     * @param item The item to register a model for.
     * @return The ItemModelBuilder.
     */
    private ItemModelBuilder simpleItem(DeferredHolder<Item, Item> item) {
        // Gets the item's registered name (e.g., "raw_artificial_ore")
        String name = item.getId().getPath();

        return withExistingParent(name, "item/generated")
                // KORREKTUR: 'new ResourceLocation()' wird zu
                // 'ResourceLocation.fromNamespaceAndPath()'
                .texture("layer0", ResourceLocation
                        .fromNamespaceAndPath(ArtificialConnectorMod.MODID, "item/" + name));
    }
}
