package hackmnin.artificialconnector.block;

import com.mojang.serialization.MapCodec;
import hackmnin.artificialconnector.block.entity.ConnectorBlockEntity;
import hackmnin.artificialconnector.block.properties.ConnectorStatus;
import hackmnin.artificialconnector.block.properties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

/**
 * The main connector block that interacts with the AI. It requires a BlockEntity to store its
 * state.
 */
public class ConnectorBlock extends BaseEntityBlock {

  public static final MapCodec<ConnectorBlock> CODEC = simpleCodec(ConnectorBlock::new);

  public ConnectorBlock(Properties properties) {
    super(properties);
    // Set the default state for our block. It will be 'idle' when placed.
    this.registerDefaultState(this.stateDefinition.any()
        .setValue(ModBlockStateProperties.CONNECTOR_STATUS, ConnectorStatus.IDLE));
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
    return new ConnectorBlockEntity(pPos, pState);
  }

  @Override
  protected void createBlockStateDefinition(
      StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> pBuilder) {
    pBuilder.add(ModBlockStateProperties.CONNECTOR_STATUS);
  }

  @Override
  public RenderShape getRenderShape(BlockState pState) {
    return RenderShape.MODEL;
  }
}