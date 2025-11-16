package hackmnin.artificialconnector;

import hackmnin.artificialconnector.block.entity.ConnectorBlockEntity;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The main connector block that interacts with the AI. It requires a BlockEntity to store its
 * state.
 */
public class ConnectorBlock extends BaseEntityBlock {

    public ConnectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        // We use a custom model, so we return MODEL.
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ConnectorBlockEntity(pPos, pState);
    }
}
