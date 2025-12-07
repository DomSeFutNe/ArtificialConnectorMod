package hackmnin.artificialconnector;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Manages and registers all Creative Mode Tabs for the mod.
 */
public class ModCreativeTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArtificialConnectorMod.MOD_ID);

  /**
   * The main creative tab for the mod.
   */
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ARTIFICIAL_CONNECTOR_TAB =
      CREATIVE_MODE_TABS.register("artificial_connector_tab",
          () -> CreativeModeTab.builder()
              .icon(() -> new ItemStack(ModItems.ARTIFICIAL_INGOT.get()))
              .title(Component.translatable("creativetab.artificial_connector_tab"))
              .displayItems((params, output) -> {
                output.accept(ModItems.ARTIFICIAL_INGOT.get());
                output.accept(ModBlocks.ARTIFICIAL_ORE.get());
                output.accept(ModBlocks.ARTIFICIAL_BLOCK.get());
                output.accept(ModBlocks.CONNECTOR_BLOCK.get());
              }).build());

  /**
   * This method registers all creative tabs to the event bus.
   * 
   *
   * @param eventBus The mod event bus
   */
  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
  }
}