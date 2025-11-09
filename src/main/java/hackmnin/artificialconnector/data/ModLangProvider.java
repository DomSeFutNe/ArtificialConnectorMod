package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.ModItems;
import hackmnin.artificialconnector.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Generates the language file (en_us.json) for the mod.
 */
public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output, String locale) {
        super(output, ArtificialConnectorMod.MODID, locale);
    }

    /**
     * This is where we add all our translations.
     */
    @Override
    protected void addTranslations() {
        // Items
        add(ModItems.RAW_ARTIFICIAL_ORE.get(), "Raw Artificial Ore");
        add(ModItems.ARTIFICIAL_INGOT.get(), "Artificial Ingot");
        add(ModItems.ARTIFICIAL_NUGGET.get(), "Artificial Nugget");

        // Blocks
        add(ModBlocks.ARTIFICIAL_ORE.get(), "Artificial Ore");
        add(ModBlocks.ARTIFICIAL_BLOCK.get(), "Artificial Block");
    }
}
