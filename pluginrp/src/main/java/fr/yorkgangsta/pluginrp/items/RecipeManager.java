package fr.yorkgangsta.pluginrp.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import fr.yorkgangsta.pluginrp.PluginRP;

public class RecipeManager {
  //private List<Recipe> allRecipes = new ArrayList<>();
  public static final ShapedRecipe WEED_RECIPE = new ShapedRecipe(new NamespacedKey(PluginRP.getInstance(), "weedRecipe"), SpecialItemStack.getSpecialItem(SpecialItemStack.WEED, 8));

  public static final MerchantRecipe THC_TRADE = new MerchantRecipe(SpecialItemStack.THC, 0, 5, true, 6, 0, 0, 0);

  public static void register(){
    THC_TRADE.setIngredients(
      Arrays.asList(
        new ItemStack(Material.EMERALD, 12),
        new ItemStack(Material.GLASS_BOTTLE)
      )
    );

    CampfireRecipe cokeRecipe = new CampfireRecipe(new NamespacedKey(PluginRP.getInstance(), "campfireCokeFromSugar"),
     SpecialItemStack.COKE, Material.SUGAR_CANE, 10, 60 * 20);

    ItemStack weed8 = new ItemStack(SpecialItemStack.WEED);
    weed8.setAmount(8);
    WEED_RECIPE.shape("WWW", "WPW", "WWW");
    WEED_RECIPE.setIngredient('W', Material.DRIED_KELP);
    WEED_RECIPE.setIngredient('P', SpecialItemStack.THC.getData());

    WEED_RECIPE.setCategory(CraftingBookCategory.MISC);

    WEED_RECIPE.setGroup("Drogues");

    Bukkit.getServer().addRecipe(cokeRecipe);
    Bukkit.getServer().addRecipe(WEED_RECIPE);
  }
}
