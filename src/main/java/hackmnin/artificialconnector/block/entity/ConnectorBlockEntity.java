package hackmnin.artificialconnector.block.entity;

import hackmnin.artificialconnector.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The BlockEntity for our ConnectorBlock. This will hold the state and logic.
 */
public class ConnectorBlockEntity extends BlockEntity {

  public ConnectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
    // We pass our registered BlockEntityType here
    super(ModBlockEntities.CONNECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
  }
}