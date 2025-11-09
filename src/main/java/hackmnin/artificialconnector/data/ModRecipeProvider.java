package hackmnin.artificialconnector.data;

import hackmnin.artificialconnector.ModBlocks;
import hackmnin.artificialconnector.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

import java.util.concurrent.CompletableFuture;

/**
 * Generates all recipes for the mod.
 */
public class ModRecipeProvider extends RecipeProvider {

        public ModRecipeProvider(PackOutput pOutput,
                        CompletableFuture<HolderLookup.Provider> pLookupProvider) {
                super(pOutput, pLookupProvider);
        }

        /**
         * This is where we build all our recipes.
         * 
         * @param pRecipeOutput The output consumer for our generated recipes.
         */
        @Override
        protected void buildRecipes(RecipeOutput pRecipeOutput) {

                // --- SMELTING RECIPE (Standard Oven) ---
                SimpleCookingRecipeBuilder
                                .smelting(Ingredient.of(ModItems.RAW_ARTIFICIAL_ORE.get()),
                                                RecipeCategory.MISC,
                                                ModItems.ARTIFICIAL_INGOT.get(), 0.7f, 200 // 10
                                                                                           // seconds
                                )
                                .unlockedBy("has_raw_artificial_ore",
                                                has(ModItems.RAW_ARTIFICIAL_ORE.get()))
                                .save(pRecipeOutput,
                                                "artificial_ingot_from_smelting_raw_artificial_ore");

                // --- BLASTING RECIPE (Blast Furnace) ---
                // This is the new part
                SimpleCookingRecipeBuilder
                                .blasting(Ingredient.of(ModItems.RAW_ARTIFICIAL_ORE.get()), // Same
                                                                                            // Input
                                                RecipeCategory.MISC,
                                                ModItems.ARTIFICIAL_INGOT.get(), // Same Output
                                                0.7f, // Same Experience
                                                100 // Half the time (5 seconds)
                                )
                                .unlockedBy("has_raw_artificial_ore",
                                                has(ModItems.RAW_ARTIFICIAL_ORE.get()))
                                // IMPORTANT: A different save name!
                                .save(pRecipeOutput,
                                                "artificial_ingot_from_blasting_raw_artificial_ore");

                // Rezept: 1 Ingot -> 9 Nuggets
                ShapelessRecipeBuilder
                                .shapeless(RecipeCategory.MISC, ModItems.ARTIFICIAL_NUGGET.get(), 9)
                                .requires(ModItems.ARTIFICIAL_INGOT.get()) // Input
                                .unlockedBy("has_artificial_ingot",
                                                has(ModItems.ARTIFICIAL_INGOT.get()))
                                .save(pRecipeOutput, "artificial_nugget_from_ingot");

                // Rezept: 9 Nuggets -> 1 Ingot
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ARTIFICIAL_INGOT.get())
                                .pattern("NNN") // 3x3 Grid
                                .pattern("NNN").pattern("NNN")
                                .define('N', ModItems.ARTIFICIAL_NUGGET.get()) // 'N' ist unser
                                                                               // Nugget
                                .unlockedBy("has_artificial_nugget",
                                                has(ModItems.ARTIFICIAL_NUGGET.get()))
                                .save(pRecipeOutput, "artificial_ingot_from_nuggets");

                // Rezept: 9 Ingots -> 1 Block
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ARTIFICIAL_BLOCK.get())
                                .pattern("III").pattern("III").pattern("III")
                                .define('I', ModItems.ARTIFICIAL_INGOT.get())
                                .unlockedBy("has_artificial_ingot",
                                                has(ModItems.ARTIFICIAL_INGOT.get()))
                                .save(pRecipeOutput, "artificial_block_from_ingots");

                // Rezept: 1 Block -> 9 Ingots
                ShapelessRecipeBuilder
                                .shapeless(RecipeCategory.MISC, ModItems.ARTIFICIAL_INGOT.get(), 9)
                                .requires(ModBlocks.ARTIFICIAL_BLOCK.get())
                                .unlockedBy("has_artificial_block",
                                                has(ModBlocks.ARTIFICIAL_BLOCK.get()))
                                .save(pRecipeOutput, "artificial_ingot_from_block");
        }
}
