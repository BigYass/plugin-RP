package fr.yorkgangsta.pluginrp.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public class SpecialItemStack extends ItemStack{
  private static HashMap<String, SpecialItemStack> allSpecialItemStack = new HashMap<>();

  public static SpecialItemStack WEED = new SpecialItemStack(Material.DRIED_KELP, "§2Canabis", null, CustomEnchant.WEED, 1);

  public static SpecialItemStack COKE = new SpecialItemStack(Material.SUGAR, "§fCoke", null, CustomEnchant.COKE, 1);

  public static SpecialItemStack BEER = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 0)
  }), Color.ORANGE, "§6Bière", null, CustomEnchant.ALCOHOLIC, 1);

  public static SpecialItemStack THC = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.POISON, 60 * 20, 0)
  }), Color.GREEN, "§2THC Concentré", null, CustomEnchant.WEED, 3);



  private final String nameID;

  public SpecialItemStack(Material type, String customName, List<String> lore, CustomEnchant ench, int level){
    super(type);
    this.nameID = convertNameToID(customName);


    ItemMeta meta = getItemMeta();

    meta.setDisplayName(customName);
    meta.setLore(lore);

    setItemMeta(meta);

    CustomEnchant.addCustomEnchantment(this, ench, level);
    allSpecialItemStack.put(convertNameToID(customName), this);
  }

  private SpecialItemStack(List<PotionEffect> effects, Color color, String customName, List<String> lore, CustomEnchant ench, int level){
    super(Material.POTION);
    this.nameID = convertNameToID(customName);


    PotionMeta meta = (PotionMeta)getItemMeta();

    for(PotionEffect effect : effects)
    meta.addCustomEffect(effect, true);

    meta.setColor(color);

    meta.setDisplayName(customName);
    meta.setLore(lore);

    setItemMeta(meta);
    CustomEnchant.addCustomEnchantment(this, ench, level);

    allSpecialItemStack.put(convertNameToID(customName), this);
  }

  public String getNameID() { return nameID; }

  public static ItemStack getSpecialItemByName(String name, int amount){ return getSpecialItem(allSpecialItemStack.get(name), amount); }

  public static ItemStack getSpecialItem(SpecialItemStack type, int amount){
    ItemStack result = new ItemStack(type);
    result.setAmount(amount);
    return result;
  }

  public static Set<String> getSpecialItemNames() { return allSpecialItemStack.keySet(); }

  private static String convertNameToID(String name){ return name.replaceAll("§.", "").replace(" ", "_"); }
}
