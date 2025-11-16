package hackmnin.artificialconnector.block.properties;

import net.minecraft.util.StringRepresentable;

/**
 * Represents the different operational statuses of the Connector Block.
 */
public enum ConnectorStatus implements StringRepresentable {
    IDLE("idle"), SUCCESS("success"), ERROR("error");

    private final String name;

    ConnectorStatus(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
