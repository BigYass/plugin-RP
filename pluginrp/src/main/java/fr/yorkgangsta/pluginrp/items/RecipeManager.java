package fr.yorkgangsta.pluginrp.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import fr.yorkgangsta.pluginrp.PluginRP;

public class RecipeManager {
  //private List<Recipe> allRecipes = new ArrayList<>();
  public static final ShapedRecipe WEED_RECIPE = new ShapedRecipe(new NamespacedKey(PluginRP.getInstance(), "weedRecipe"), SpecialItemStack.getSpecialItem(SpecialItemStack.WEED, 8));

  public static final ShapedRecipe COKE_RECIPE = new ShapedRecipe(new NamespacedKey(PluginRP.getInstance(), "cokeRecipe"), SpecialItemStack.COKE);



  public static final MerchantRecipe THC_TRADE = new MerchantRecipe(SpecialItemStack.THC, 0, 5, true, 6, 0, 0, 0);

  public static final MerchantRecipe COKE_TRADE = new MerchantRecipe(SpecialItemStack.COKE, 0, 2, true, 32, 0, 0, 0);

  public static void register(){
    THC_TRADE.setIngredients(
      Arrays.asList(
        new ItemStack(Material.EMERALD, 7),
        new ItemStack(Material.GLASS_BOTTLE)
      )
    );

    COKE_TRADE.setIngredients(
      Arrays.asList(
        new ItemStack(Material.EMERALD, 27),
        SpecialItemStack.SPECIAL_POWDER
      )
    );



    // Coke
    {
      COKE_RECIPE.setCategory(CraftingBookCategory.MISC);

      COKE_RECIPE.shape("ab");
      COKE_RECIPE.setIngredient('a', new RecipeChoice.ExactChoice(SpecialItemStack.EHTANOL_POWDER));
      COKE_RECIPE.setIngredient('b', new RecipeChoice.ExactChoice(SpecialItemStack.COKE_ESSENCE));

      Bukkit.getServer().addRecipe(COKE_RECIPE);
    }

    //Weed
    {
    ItemStack weed8 = new ItemStack(SpecialItemStack.WEED);
    weed8.setAmount(8);
    WEED_RECIPE.shape("WWW", "WPW", "WWW");
    WEED_RECIPE.setIngredient('W', Material.DRIED_KELP);
    WEED_RECIPE.setIngredient('P', new RecipeChoice.ExactChoice(SpecialItemStack.THC));

    WEED_RECIPE.setCategory(CraftingBookCategory.MISC);

    WEED_RECIPE.setGroup("Drogues");
    }

    // Malt Potion
    {
      ShapedRecipe maltPotionRecipe = new ShapedRecipe(new NamespacedKey(PluginRP.getInstance(), "maltPotionRecipe"), SpecialItemStack.MALT_POTION);

      maltPotionRecipe.shape("FFF", "FPF", "FFF");

      maltPotionRecipe.setIngredient('F', Material.WHEAT);
      maltPotionRecipe.setIngredient('P', Material.POTION);

      Bukkit.getServer().addRecipe(maltPotionRecipe);
    }

    //Ethanol Potion
    {
      CampfireRecipe ethanolPotionRecipe = new CampfireRecipe(new NamespacedKey(PluginRP.getInstance(), "campfireEthanolPotionRecipe"), SpecialItemStack.ETHANOL_POTION, new RecipeChoice.ExactChoice(SpecialItemStack.MALT_POTION), 3, 200);
      Bukkit.getServer().addRecipe(ethanolPotionRecipe);
    }

    // Sugar Potion
    {
      ShapedRecipe sugarPotionRecipe = new ShapedRecipe(new NamespacedKey(PluginRP.getInstance(), "sugarPotionRecipe"), SpecialItemStack.SUGAR_POTION);

      sugarPotionRecipe.shape("FFF", "FPF", "FFF");

      sugarPotionRecipe.setIngredient('F', Material.SUGAR);
      sugarPotionRecipe.setIngredient('P', Material.POTION);

      Bukkit.getServer().addRecipe(sugarPotionRecipe);
    }

    // Coke Essence
    {
      CampfireRecipe cokeEssenceRecipe = new CampfireRecipe(new NamespacedKey(PluginRP.getInstance(), "campfireCokeEssenceRecipe"), SpecialItemStack.COKE_ESSENCE, new RecipeChoice.ExactChoice(SpecialItemStack.SUGAR_POTION), 3, 200);
      Bukkit.getServer().addRecipe(cokeEssenceRecipe);
    }

    // Ethanol Powder
    {
      ShapedRecipe ethanolPowderRecipe = new ShapedRecipe(new NamespacedKey(PluginRP.getInstance(), "ethanolPowderRecipe"), SpecialItemStack.EHTANOL_POWDER);

      ethanolPowderRecipe.shape("ab");
      ethanolPowderRecipe.setIngredient('a', new RecipeChoice.ExactChoice(SpecialItemStack.SPECIAL_POWDER));
      ethanolPowderRecipe.setIngredient('b', new RecipeChoice.ExactChoice(SpecialItemStack.ETHANOL_POTION));

      Bukkit.getServer().addRecipe(ethanolPowderRecipe);
    }


    Bukkit.getServer().addRecipe(WEED_RECIPE);
  }
}
