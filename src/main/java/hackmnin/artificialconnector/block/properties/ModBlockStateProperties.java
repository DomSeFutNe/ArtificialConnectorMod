package hackmnin.artificialconnector.block.properties;

import net.minecraft.world.level.block.state.properties.EnumProperty;

/**
 * Contains custom BlockState properties for the mod.
 */
public class ModBlockStateProperties {
  public static final EnumProperty<ConnectorStatus> CONNECTOR_STATUS =
      EnumProperty.create("status", ConnectorStatus.class);
}