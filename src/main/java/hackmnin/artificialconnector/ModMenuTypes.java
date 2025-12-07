package hackmnin.artificialconnector;

import hackmnin.artificialconnector.client.gui.menu.ConnectorMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Manages and registers all MenuTypes for the mod.
 */
public class ModMenuTypes {

  public static final DeferredRegister<MenuType<?>> MENUS =
      DeferredRegister.create(Registries.MENU, ArtificialConnectorMod.MOD_ID);

  public static final DeferredHolder<MenuType<?>, MenuType<ConnectorMenu>> CONNECTOR_MENU =
      MENUS.register("connector_menu",
          () -> IMenuTypeExtension.create(ConnectorMenu::new));


  public static void register(IEventBus eventBus) {
    MENUS.register(eventBus);
  }
}