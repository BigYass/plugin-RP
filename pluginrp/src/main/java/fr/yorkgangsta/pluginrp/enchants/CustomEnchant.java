package fr.yorkgangsta.pluginrp.enchants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.PluginRP;
import fr.yorkgangsta.pluginrp.enchants.food.AlcoholicEnchantment;
import fr.yorkgangsta.pluginrp.enchants.food.WeedEnchantment;
import fr.yorkgangsta.pluginrp.enchants.special.CokeEnchantment;
import fr.yorkgangsta.pluginrp.enchants.weapon.FreezeEnchantment;
import fr.yorkgangsta.pluginrp.enchants.weapon.LifeStealEnchantment;
import fr.yorkgangsta.pluginrp.enchants.weapon.PoisonEnchantment;

public abstract class CustomEnchant extends Enchantment{

  private String name;

  private ChatColor color;

  private static List<CustomEnchant> allCustomEnchatments = new ArrayList<>();

  public static final CustomEnchant ALCOHOLIC = new AlcoholicEnchantment(PluginRP.getInstance(), "Ivresse", ChatColor.RED);

  public static final CustomEnchant WEED = new WeedEnchantment(PluginRP.getInstance(), "THC", ChatColor.RED);

  public static final CustomEnchant COKE = new CokeEnchantment(PluginRP.getInstance(), "Addiction", ChatColor.RED);

  public static final CustomEnchant FROST_ASPECT = new FreezeEnchantment(PluginRP.getInstance(), "Aura_de_Glace", ChatColor.GRAY);

  public static final CustomEnchant POISON_ASPECT = new PoisonEnchantment(PluginRP.getInstance(), "Empoisonement", ChatColor.GRAY);

  public static final CustomEnchant LIFESTEAL = new LifeStealEnchantment(PluginRP.getInstance(), "Vol_de_Vie", ChatColor.GRAY);





  public CustomEnchant(Plugin plugin, String name, ChatColor color) {
    super(new NamespacedKey(plugin, name));
    this.name = name;
    this.color = color;
    allCustomEnchatments.add(this);
  }

  public CustomEnchant(Plugin plugin, String name) {
    super(new NamespacedKey(plugin, name));
    this.name = name;
    color = ChatColor.RESET;
    allCustomEnchatments.add(this);
  }

  public static String intToRoman(int num) {
    if (num < 1 || num > 3999) {
        return "?";
    }
    String[] m = {"", "M", "MM", "MMM"};
    String[] c = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String[] x = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] i = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    return m[num/1000] + c[(num%1000)/100] + x[(num%100)/10] + i[num%10];
  }

  public static boolean addCustomEnchantment(ItemStack item, Enchantment enchant, int level) {
    if(!(enchant instanceof CustomEnchant)) {
      item.addUnsafeEnchantment(enchant, level);
      return true;
    }
    CustomEnchant ench = (CustomEnchant) enchant;

    if (!ench.canEnchantItem(item)) return false;

    if(item.getItemMeta().hasEnchant(ench)){
      item.getItemMeta().removeEnchant(ench);
    }

    int safeLevel = Math.min(ench.getMaxLevel(), Math.max(level, ench.getStartLevel()));
    item.addUnsafeEnchantment(ench, safeLevel);

    ItemMeta meta = item.getItemMeta();
    List<String> lore = meta.getLore();
    if (lore == null) lore = new ArrayList<>();

    String enchLore = ench.getColor() + ench.getName() + " " + intToRoman(safeLevel);

    for(String line : lore)
      if(line.toLowerCase().startsWith(ench.getName().toLowerCase()))
        lore.remove(line);

    lore.add(enchLore);
    meta.setLore(lore);
    item.setItemMeta(meta);


    return true;
}

  public static void register() {
    for(CustomEnchant ench : allCustomEnchatments){
    boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(ench);

    if(!registered)
      registerEnchantment(ench);

    }
  }

  public static void registerEnchantment(Enchantment enchantment){
    boolean registered = true;
    try {
      Field f = Enchantment.class.getDeclaredField("acceptingNew");
      f.setAccessible(true);
      f.set(null, true);
      Enchantment.registerEnchantment(enchantment);
    } catch(Exception e) {
      registered = false;
      e.printStackTrace();
    }

    if(!registered) Bukkit.getLogger().info(PluginRP.PREFIX + enchantment.getKey().getKey() + "n'a pas été enregistré...");

  }


  public String getName() { return name.replace("_", " "); }

  public String getRealName() { return name; }

  public ChatColor getColor() { return color; }

  public static List<CustomEnchant> getAllCustomEnchants() { return allCustomEnchatments; }

  public static CustomEnchant getCustomEnchantByName(String name){
    for(CustomEnchant ench : getAllCustomEnchants()){
      if(ench.getRealName().equalsIgnoreCase(name))
        return ench;
    }
    return null;
  }
}
