package ca.rttv.malum;

import ca.rttv.malum.emi.BlockTransmutationEmiRecipe;
import ca.rttv.malum.emi.SpiritFocusingEmiRecipe;
import ca.rttv.malum.emi.SpiritInfusionEmiRecipe;
import ca.rttv.malum.emi.SpiritRepairEmiRecipe;
import ca.rttv.malum.recipe.*;
import ca.rttv.malum.registry.MalumItemRegistry;
import ca.rttv.malum.registry.MalumRecipeTypeRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

import static ca.rttv.malum.Malum.MODID;

public final class MalumEmiPlugin implements EmiPlugin {
    public static final EmiStack SOULWOOD_TOTEM_BASE = EmiStack.of(MalumItemRegistry.SOULWOOD_TOTEM_BASE);
    public static final EmiRecipeCategory BLOCK_TRANSMUTATION = new EmiRecipeCategory(new Identifier(MODID, "block_transmutation"), SOULWOOD_TOTEM_BASE, new EmiTexture(new Identifier(MODID, "textures/gui/emi/block_transmutation_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));

    public static final EmiStack SPIRIT_ALTAR = EmiStack.of(MalumItemRegistry.SPIRIT_ALTAR);
    public static final EmiRecipeCategory SPIRIT_INFUSION = new EmiRecipeCategory(new Identifier(MODID, "spirit_infusion"), SPIRIT_ALTAR, new EmiTexture(new Identifier(MODID, "textures/gui/emi/spirit_infusion_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));

    public static final EmiStack SPIRIT_CRUCIBLE = EmiStack.of(MalumItemRegistry.SPIRIT_CRUCIBLE);
    public static final EmiRecipeCategory SPIRIT_FOCUSING = new EmiRecipeCategory(new Identifier(MODID, "spirit_focusing"), SPIRIT_CRUCIBLE, new EmiTexture(new Identifier(MODID, "textures/gui/emi/spirit_focusing_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));

    public static final EmiStack TWISTED_TABLET = EmiStack.of(MalumItemRegistry.TWISTED_TABLET);
    public static final EmiRecipeCategory SPIRIT_REPAIR = new EmiRecipeCategory(new Identifier(MODID, "spirit_repair"), SPIRIT_CRUCIBLE, new EmiTexture(new Identifier(MODID, "textures/gui/emi/spirit_repair_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        final RecipeManager manager = registry.getRecipeManager();

        registry.addCategory(BLOCK_TRANSMUTATION);
        registry.addWorkstation(BLOCK_TRANSMUTATION, SOULWOOD_TOTEM_BASE);
        for (BlockTransmutationRecipe recipe : manager.listAllOfType(MalumRecipeTypeRegistry.BLOCK_TRANSMUTATION)) {
            registry.addRecipe(new BlockTransmutationEmiRecipe(recipe));
        }

        for (SavedNbtRecipe recipe : manager.listAllOfType(MalumRecipeTypeRegistry.SAVED_NBT)) {
            registry.addRecipe(new EmiCraftingRecipe(recipe.getIngredients().stream().map(EmiIngredient::of).toList(), EmiStack.of(recipe.getOutput()), recipe.getId(), false));
        }

        registry.addCategory(SPIRIT_INFUSION);
        registry.addWorkstation(SPIRIT_INFUSION, SPIRIT_ALTAR);
        for (SpiritInfusionRecipe recipe : manager.listAllOfType(MalumRecipeTypeRegistry.SPIRIT_INFUSION)) {
            registry.addRecipe(new SpiritInfusionEmiRecipe(recipe));
        }

        registry.addCategory(SPIRIT_FOCUSING);
        registry.addWorkstation(SPIRIT_FOCUSING, SPIRIT_CRUCIBLE);
        for (SpiritFocusingRecipe recipe : manager.listAllOfType(MalumRecipeTypeRegistry.SPIRIT_FOCUSING)) {
            registry.addRecipe(new SpiritFocusingEmiRecipe(recipe));
        }

        registry.addCategory(SPIRIT_REPAIR);
        registry.addWorkstation(SPIRIT_REPAIR, SPIRIT_CRUCIBLE);
        registry.addWorkstation(SPIRIT_REPAIR, TWISTED_TABLET);
        for (SpiritRepairRecipe recipe : manager.listAllOfType(MalumRecipeTypeRegistry.SPIRIT_REPAIR)) {
            registry.addRecipe(new SpiritRepairEmiRecipe(recipe));
        }
    }
}
