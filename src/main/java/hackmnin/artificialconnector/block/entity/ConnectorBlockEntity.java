package hackmnin.artificialconnector.block.entity;

import hackmnin.artificialconnector.ModBlockEntities;
import hackmnin.artificialconnector.client.gui.menu.ConnectorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

/**
 * The BlockEntity for our ConnectorBlock. This will hold the state and logic.
 */
public class ConnectorBlockEntity extends BlockEntity implements MenuProvider {

  private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
    @Override
    protected void onContentsChanged(int slot) {
      setChanged();
    }
  };

  // This is the capability that other blocks will see.
  public final IItemHandler capability = itemHandler;

  public ConnectorBlockEntity(BlockPos pos, BlockState blockState) {
    // We pass our registered BlockEntityType here
    super(ModBlockEntities.CONNECTOR_BLOCK_ENTITY.get(), pos, blockState);
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("gui.artificialconnector.connector_block_gui_title");
  }

  @Nullable
  @Override
  public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory,
      Player player) {
    return new ConnectorMenu(containerId, playerInventory, this);
  }

  @Override
  protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.saveAdditional(tag, registries);
    // Save the item handler's contents
    tag.put("inventory", itemHandler.serializeNBT(registries));
  }

  @Override
  public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
    super.loadAdditional(tag, registries);
    // Load the item handler's contents
    itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
  }
}