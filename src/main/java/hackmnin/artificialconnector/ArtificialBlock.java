package hackmnin.artificialconnector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

/**
 * A block that emits light purple particles nearby.
 */
public class ArtificialBlock extends Block {

    // Define our light purple color
    // This is (R: 221, G: 160, B: 221) -> "Plum"
    private static final Vector3f LIGHT_PURPLE =
            new Vector3f(221f / 255f, 160f / 255f, 221f / 255f);

    public ArtificialBlock(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called randomly on the client side to spawn particles.
     */
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        // Your request: 1 block radius. We spawn particles just outside the block.
        double d0 = pPos.getX() + 0.5 + (pRandom.nextDouble() - 0.5);
        double d1 = pPos.getY() + 0.5 + (pRandom.nextDouble() - 0.5);
        double d2 = pPos.getZ() + 0.5 + (pRandom.nextDouble() - 0.5);

        // Spawn a DUST particle with our color
        pLevel.addParticle(new DustParticleOptions(LIGHT_PURPLE, 1.0f), d0, d1, d2, 0.0, 0.0, 0.0);
    }
}
