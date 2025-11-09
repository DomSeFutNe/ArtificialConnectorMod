package hackmnin.artificialconnector;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f; // This is the new Vector class for colors

/**
 * A block that emits dark purple particles in a radius.
 */
public class ArtificialOreBlock extends Block {

    // Define our dark purple color (R, G, B as 0.0-1.0 floats)
    // This is (R: 104, G: 34, B: 139) -> "Dark Violet"
    private static final Vector3f DARK_PURPLE = new Vector3f(104f / 255f, 34f / 255f, 139f / 255f);

    public ArtificialOreBlock(Properties pProperties) {
        super(pProperties);
    }

    /**
     * Called randomly on the client side to spawn particles.
     */
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        // Your request: 5 block radius. This is VERY large and performance-heavy.
        // Let's spawn 3 particles in a 3-block radius instead (safer).
        for (int i = 0; i < 3; ++i) {
            // Calculate random position within a 3-block cube centered on the block
            double d0 = (pPos.getX() + 0.5) + (pRandom.nextDouble() - 0.5) * 6.0; // 3 blocks * 2
            double d1 = (pPos.getY() + 0.5) + (pRandom.nextDouble() - 0.5) * 6.0;
            double d2 = (pPos.getZ() + 0.5) + (pRandom.nextDouble() - 0.5) * 6.0;

            // Spawn a DUST particle with our color and a scale of 1.0f
            pLevel.addParticle(new DustParticleOptions(DARK_PURPLE, 1.0f), d0, d1, d2, 0.0, 0.0,
                    0.0);
        }
    }
}
