package fr.yorkgangsta.pluginrp.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import fr.yorkgangsta.pluginrp.enchants.spell.SpellEnchantment;

public class SpecialItemStack extends ItemStack{
  private static HashMap<String, SpecialItemStack> allSpecialItemStack = new HashMap<>();

  public static SpecialItemStack WEED = new SpecialItemStack(Material.DRIED_KELP, "§2Canabis", null, CustomEnchant.WEED, 1);

  public static SpecialItemStack COKE = new SpecialItemStack(Material.SUGAR, "§fCokaine", null, CustomEnchant.COKE, 1);

  public static SpecialItemStack BEER = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 0)
  }), Color.ORANGE, "§6Bière", null, CustomEnchant.ALCOHOLIC, 1);

  public static SpecialItemStack THC = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.POISON, 60 * 20, 0)
  }), Color.GREEN, "§2THC Concentré", Arrays.asList("§7Ingrédient Spécial"), CustomEnchant.WEED, 3);

  public static SpecialItemStack MALT_POTION = new SpecialItemStack(Arrays.asList(new PotionEffect[]{

  }), Color.BLUE, "§fSolution de Malt", Arrays.asList("§7Ingrédient Spécial"), null, 0);

  public static SpecialItemStack ETHANOL_POTION = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.POISON, 2 * 20, 4)
  }), Color.BLUE, "§fEthanol", Arrays.asList("§7Ingrédient Spécial"), CustomEnchant.ALCOHOLIC, 2);


  public static SpecialItemStack SPECIAL_POWDER = new SpecialItemStack(Material.GUNPOWDER, "§fPoudre Special", Arrays.asList("§7Ingrédient Spécial"), Enchantment.VANISHING_CURSE, 1);

  public static SpecialItemStack SUGAR_POTION = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.SPEED, 5 * 20, 4),
    new PotionEffect(PotionEffectType.SLOW, 30 * 20, 1),
    new PotionEffect(PotionEffectType.HUNGER, 30 * 20, 4)
  }), Color.WHITE, "§fEau Saturé en Sucre", Arrays.asList("§7Ingrédient Spécial"), null, 0);

  public static SpecialItemStack COKE_ESSENCE = new SpecialItemStack(Arrays.asList(new PotionEffect[]{
    new PotionEffect(PotionEffectType.HARM, 20, 2)
  }), Color.WHITE, "§fEssence de Cokaine", Arrays.asList("§7Ingrédient Spécial"), null, 0);

  public static SpecialItemStack EHTANOL_POWDER = new SpecialItemStack(Material.GUNPOWDER, "§fPoudre Imbibé", Arrays.asList("§7Ingrédient Spécial"), CustomEnchant.VANISHING_CURSE, 1);

  public static SpecialItemStack ONE_DOLLAR = new SpecialItemStack(Material.PAPER, "§e1$",Arrays.asList("§7Un billet de §51$") , null, 1);

  public static SpecialItemStack TEN_DOLLARS = new SpecialItemStack(Material.PAPER, "§c10$",Arrays.asList("§7Un billet de §510$") , null, 1);

  public static SpecialItemStack HUNDREAD_DOLLARS = new SpecialItemStack(Material.PAPER, "§a100$",Arrays.asList("§7Un billet de §5100$") , null, 1);

  public static SpecialItemStack NETHER_PASS = new SpecialItemStack(Material.PAPER, "§cTicket §4Nether",null , CustomEnchant.NETHER_PASS, 1);

  public static SpecialItemStack DICIPLINE_STICK = new SpecialItemStack(Material.STICK, "§r§cLe baton de la discipline <3",null , CustomEnchant.DISCIPLINE, 1);

  public static SpecialItemStack RETURN_SCROLL = new SpecialItemStack(Material.PAPER, "§r§dParchemin de retour", null, SpellEnchantment.RETURN_TELEPORT, 1);
  public static SpecialItemStack INVOCATION_SCROLL = new SpecialItemStack(Material.PAPER, "§r§dParchemin d'invocation", null, SpellEnchantment.INVOCATION, 1);
  public static SpecialItemStack CONVOCATION_SCROLL = new SpecialItemStack(Material.PAPER, "§r§dParchemin de convocation", null, SpellEnchantment.CONVOCATION, 1);




  private final String nameID;

  public SpecialItemStack(Material type, String customName, List<String> lore, Enchantment ench, int level){
    super(type);
    this.nameID = convertNameToID(customName);


    ItemMeta meta = getItemMeta();

    meta.setDisplayName(customName);
    meta.setLore(lore);

    setItemMeta(meta);
    if(ench != null)
      CustomEnchant.addCustomEnchantment(this, ench, level);

    allSpecialItemStack.put(this.nameID, this);
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

  public static ItemStack getSpecialItemByName(String name, int amount){
    if(allSpecialItemStack.containsKey(name))
      return getSpecialItem(allSpecialItemStack.get(name), amount);
    else
      return null;
  }

  public static ItemStack getSpecialItem(SpecialItemStack type, int amount){
    ItemStack result = new ItemStack(type);
    result.setAmount(amount);
    return result;
  }

  public static Set<String> getSpecialItemNames() { return allSpecialItemStack.keySet(); }

  private static String convertNameToID(String name){ return name.replaceAll("§.", "").replace(" ", "_"); }
}
