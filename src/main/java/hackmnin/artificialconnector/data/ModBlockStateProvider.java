package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Generiert BlockState- und Block-Modell-JSONs.
 */
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArtificialConnectorMod.MODID, exFileHelper);
    }

    /**
     * Hier registrieren wir alle unsere Block-Modelle.
     */
    @Override
    protected void registerStatesAndModels() {
        // Für ARTIFICIAL_ORE:
        // Sagt dem Spiel, dass es ein einfacher Würfel ist, der
        // auf allen 6 Seiten dieselbe Textur verwendet.
        simpleBlock(ModBlocks.ARTIFICIAL_ORE);
    }

    /**
     * Helfermethode für einfache Blöcke (alle Seiten gleich).
     */
    private void simpleBlock(DeferredHolder<Block, Block> block) {
        // Sagt dem BlockState: "Verwende dieses Modell für alle Zustände"
        simpleBlock(block.get(),
                // Erstellt das Block-Modell: "parent: block/cube_all"
                // und "textures.all: .../block/artificial_ore"
                models().cubeAll(block.getId().getPath(), blockTexture(block.get())));
    }
}
