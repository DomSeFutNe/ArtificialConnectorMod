package hackmnin.artificialconnector;

import hackmnin.artificialconnector.block.entity.ConnectorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class ConnectorMenu extends AbstractContainerMenu {

  public final ConnectorBlockEntity blockEntity;
  private final Level level;

  public ConnectorMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
    this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
  }

  public ConnectorMenu(int containerId, Inventory inv, BlockEntity entity) {
    super(ModMenuTypes.CONNECTOR_MENU.get(), containerId);
    checkContainerSize(inv, 1);
    blockEntity = (ConnectorBlockEntity) entity;
    this.level = inv.player.level();

    addPlayerInventory(inv);
    addPlayerHotbar(inv);

    // Add the fuel slot
    this.addSlot(new SlotItemHandler(blockEntity.capability, 0, 80, 58));
  }

  // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
  private static final int HOTBAR_SLOT_COUNT = 9;
  private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
  private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
  private static final int PLAYER_INVENTORY_SLOT_COUNT =
      PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
  private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
  private static final int VANILLA_FIRST_SLOT_INDEX = 0;
  private static final int TE_INVENTORY_FIRST_SLOT_INDEX =
      VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

  @Override
  public ItemStack quickMoveStack(Player playerIn, int index) {
    Slot sourceSlot = slots.get(index);
    if (sourceSlot == null || !sourceSlot.hasItem()) {
      return ItemStack.EMPTY;
    }
    ItemStack sourceStack = sourceSlot.getItem();
    ItemStack copyOfSourceStack = sourceStack.copy();

    if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
      // Player inventory -> Fuel slot
      if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX,
          TE_INVENTORY_FIRST_SLOT_INDEX + 1, false)) {
        return ItemStack.EMPTY;
      }
    } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + 1) {
      // Fuel slot -> Player inventory
      if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,
          VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
        return ItemStack.EMPTY;
      }
    } else {
      System.out.println("Invalid slotIndex:" + index);
      return ItemStack.EMPTY;
    }

    if (sourceStack.getCount() == 0) {
      sourceSlot.set(ItemStack.EMPTY);
    } else {
      sourceSlot.setChanged();
    }
    sourceSlot.onTake(playerIn, sourceStack);
    return copyOfSourceStack;
  }

  @Override
  public boolean stillValid(Player player) {
    return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player,
        ModBlocks.CONNECTOR_BLOCK.get());
  }

  private void addPlayerInventory(Inventory playerInventory) {
    for (int i = 0; i < 3; ++i) {
      for (int l = 0; l < 9; ++l) {
        this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
      }
    }
  }

  private void addPlayerHotbar(Inventory playerInventory) {
    for (int i = 0; i < 9; ++i) {
      this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
    }
  }
}