package hackmnin.artificialconnector;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The BlockEntity for the Connector Block. It will hold the state and logic for AI interaction.
 */
public class ConnectorBlockEntity extends BlockEntity {

    public ConnectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CONNECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
}
