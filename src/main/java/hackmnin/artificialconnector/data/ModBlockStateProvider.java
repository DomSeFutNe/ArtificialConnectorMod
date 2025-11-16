package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModBlocks;
import hackmnin.artificialconnector.block.properties.ConnectorStatus;
import hackmnin.artificialconnector.block.properties.ModBlockStateProperties;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Generates BlockState and Block Model JSONs.
 */
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArtificialConnectorMod.MODID, exFileHelper);
    }

    /**
     * Here we register all our block models.
     */
    @Override
    protected void registerStatesAndModels() { // For simple blocks that use the same texture on all
                                               // 6
                                               // sides.
        simpleBlock(ModBlocks.ARTIFICIAL_ORE.get());
        simpleBlock(ModBlocks.ARTIFICIAL_BLOCK.get());

        // Generate state-dependent models for the Connector Block
        registerConnectorBlock();
    }

    /**
     * Generates the blockstate and models for the Connector Block based on its status property.
     */
    private void registerConnectorBlock() {
        // This generates the blockstate JSON that maps each state to a model.
        getVariantBuilder(ModBlocks.CONNECTOR_BLOCK.get()).forAllStates(state -> {
            ConnectorStatus status = state.getValue(ModBlockStateProperties.CONNECTOR_STATUS);
            String modelName = "block/connector_block_" + status.getSerializedName();

            // Define textures for each side. The front will change based on status.
            ResourceLocation topTexture = modLoc("block/connector_block_top");
            ResourceLocation bottomTexture = modLoc("block/connector_block_bottom");
            ResourceLocation sideTexture = modLoc("block/connector_block_side");

            // Use a specific, animated texture for the 'processing' state.
            ResourceLocation frontTexture = (status == ConnectorStatus.PROCESSING)
                    ? modLoc("block/connector_block_front_processing_0")
                    : modLoc("block/connector_block_front_" + status.getSerializedName());

            // Use the standard 'cube' model which allows for different textures per side.
            var model = models().cube(modelName, bottomTexture, topTexture, frontTexture,
                    sideTexture, sideTexture, sideTexture).texture("particle", frontTexture);

            return ConfiguredModel.builder().modelFile(model).build();
        });
    }
}
