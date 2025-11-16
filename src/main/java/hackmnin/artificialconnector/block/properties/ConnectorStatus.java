package hackmnin.artificialconnector.block.properties;

import net.minecraft.util.StringRepresentable;

/**
 * Represents the different operational states of the Connector Block.
 */
public enum ConnectorStatus implements StringRepresentable {
  IDLE("idle"),
  PROCESSING("processing"),
  SUCCESS("success"),
  FAILED("failed");

  private final String name;

  ConnectorStatus(String name) {
    this.name = name;
  }

  public String getSerializedName() {
    return this.name;
  }
}