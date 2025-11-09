package hackmnin.artificialconnector;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hackmnin.artificialconnector.data.DataGenerators;

@Mod("artificialconnector")
public class ArtificialConnectorMod {
    // Logger for the mod
    public static final Logger Lo = LoggerFactory.getLogger("ArtificialConnectorMod");
    public static final String MODID = "artificialconnector";

    public ArtificialConnectorMod(IEventBus modEventBus) {
        Lo.info("Registetring Artificial Connector Mod...");
        // --- Call the registration methods ---
        // This tells our ModItems class to register its items.
        ModItems.register(modEventBus);
        Lo.info("Registration complete.");
        Lo.info("Setting up event listeners...");
        // Add an event listener for building creative mode tabs
        modEventBus.addListener(this::addCreative);
        Lo.info("Event listeners set up.");
        Lo.info("Setting up data generators...");
        // Register data generators
        modEventBus.addListener(DataGenerators::gatherData);
        Lo.info("Data generators set up.");
        Lo.info("Artificial Connector Mod setup complete.");
    }

    /**
     * Event listener that adds our items to the creative mode tabs.
     * 
     * @param event The event fired when tabs are being built.
     */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Check if we are currently building the "Ingredients" tab
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            Lo.debug("Adding items to Ingredients creative tab.");
            // Add our raw ore to this tab
            Lo.debug("Adding Raw Artificial Ore to Ingredients tab.");
            event.accept(ModItems.RAW_ARTIFICIAL_ORE.get());
            // Add our ingot to this tab
            Lo.debug("Adding Artificial Ingot to Ingredients tab.");
            event.accept(ModItems.ARTIFICIAL_INGOT.get());
            // Add our nugget to this tab
            Lo.debug("Adding Artificial Nugget to Ingredients tab.");
            event.accept(ModItems.ARTIFICIAL_NUGGET.get());
            // Add our block to this tab
            Lo.debug("Adding Artificial Block to Ingredients tab.");
            event.accept(ModItems.ARTIFICIAL_BLOCK.get());
        }
    }
}
