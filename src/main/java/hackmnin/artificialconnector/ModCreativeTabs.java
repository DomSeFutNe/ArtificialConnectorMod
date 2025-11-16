package hackmnin.artificialconnector;

import hackmnin.artificialconnector.ModBlocks;
import hackmnin.artificialconnector.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTabs {
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
                        .create(Registries.CREATIVE_MODE_TAB, ArtificialConnectorMod.MODID);

        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ARTIFICIAL_CONNECTOR_TAB =
                        CREATIVE_MODE_TABS.register("artificial_connector_tab",
                                        () -> CreativeModeTab.builder().title(Component
                                                        .translatable("creativetab.artificial_connector_tab"))
                                                        .icon(() -> new ItemStack(
                                                                        ModItems.ARTIFICIAL_INGOT
                                                                                        .get()))
                                                        .displayItems((displayParameters,
                                                                        output) -> {
                                                                output.accept(ModItems.ARTIFICIAL_INGOT
                                                                                .get());
                                                                output.accept(ModItems.ARTIFICIAL_NUGGET
                                                                                .get());
                                                                output.accept(ModItems.RAW_ARTIFICIAL_ORE
                                                                                .get());
                                                                output.accept(ModItems.ARTIFICIAL_WRENCH
                                                                                .get());
                                                                output.accept(ModBlocks.ARTIFICIAL_ORE
                                                                                .get());
                                                                output.accept(ModBlocks.ARTIFICIAL_BLOCK
                                                                                .get());
                                                                output.accept(ModBlocks.CONNECTOR_BLOCK
                                                                                .get());
                                                        }).build());
}
