package hackmnin.artificialconnector;

import hackmnin.artificialconnector.block.entity.ConnectorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Manages and registers all BlockEntityTypes for the mod.
 */
public class ModBlockEntities {

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ArtificialConnectorMod.MODID);

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ConnectorBlockEntity>>
      CONNECTOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("connector_block_entity",
          () -> BlockEntityType.Builder
              .of(ConnectorBlockEntity::new, ModBlocks.CONNECTOR_BLOCK.get()).build(null));

  /**
   * This method registers all block entities to the event bus.
   */
  public static void register(IEventBus eventBus) {
    BLOCK_ENTITIES.register(eventBus);
  }
}