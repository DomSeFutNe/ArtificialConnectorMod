package hackmnin.artificialconnector.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hackmnin.artificialconnector.ArtificialConnectorMod;
import hackmnin.artificialconnector.client.gui.menu.ConnectorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ConnectorScreen extends AbstractContainerScreen<ConnectorMenu> {

  private static final ResourceLocation TEXTURE =
      ResourceLocation.fromNamespaceAndPath(ArtificialConnectorMod.MOD_ID, "textures/gui/connector_gui.png");

  private EditBox inputField;

  public ConnectorScreen(ConnectorMenu menu, Inventory playerInventory, Component title) {
    super(menu, playerInventory, title);
    this.imageWidth = 176; // The width of the GUI texture
    this.imageHeight = 214; // The height of the GUI texture
  }

  @Override
  protected void init() {
    super.init();
    // Create the text input field
    this.inputField = new EditBox(this.font, this.leftPos + 8, this.topPos + 20, 160, 18,
        Component.translatable("gui.artificialconnector.input_field"));
    this.inputField.setMaxLength(256);
    this.addRenderableWidget(this.inputField);
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    RenderSystem.setShaderTexture(0, TEXTURE);
    int x = (width - imageWidth) / 2;
    int y = (height - imageHeight) / 2;
    guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
    super.render(guiGraphics, mouseX, mouseY, delta);
    this.inputField.render(guiGraphics, mouseX, mouseY, delta);
    renderTooltip(guiGraphics, mouseX, mouseY);

    // Render Output Field (as text for now)
    guiGraphics.drawString(this.font, "Output:", this.leftPos + 8, this.topPos + 44, 0x404040,
        false);

    // Render Status Indicator
    // TODO: Get status from BlockEntity/Menu
    String status = "Status: IDLE";
    int statusColor = 0x00FF00; // Green
    guiGraphics.drawString(this.font, status, this.leftPos + 8, this.topPos + 60, statusColor,
        true);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    // Render the title of the GUI
    guiGraphics.drawString(this.font, this.title, this.leftPos + 8, this.topPos + 6, 0x404040,
        false);
    // Render the player inventory title
    // Adjust Y position based on the new height
    guiGraphics.drawString(this.font, this.playerInventoryTitle, this.leftPos + 8, this.topPos + 120, 0x404040, false);
  }

  // This is needed to make the input field clickable
  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (this.inputField.mouseClicked(mouseX, mouseY, button)) {
      return true;
    }
    return super.mouseClicked(mouseX, mouseY, button);
  }
}
