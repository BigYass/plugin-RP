package fr.yorkgangsta.pluginrp.enchants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.PluginRP;

public abstract class CustomEnchant extends Enchantment{

  private String name;

  private static final List<CustomEnchant> allCustomEnchatments = new ArrayList<>();
  
  public static final CustomEnchant ALCOHOLIC = new AlcoholicEnchantment(PluginRP.getInstance(), "Ivresse");

  public CustomEnchant(Plugin plugin, String name) {
    super(new NamespacedKey(plugin, name));
    this.name = name;

    allCustomEnchatments.add(this);
  }

  private static String intToRoman(int num) {
    if (num < 1 || num > 3999) {
        return "?";
    }
    String[] m = {"", "M", "MM", "MMM"};
    String[] c = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String[] x = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] i = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    return m[num/1000] + c[(num%1000)/100] + x[(num%100)/10] + i[num%10];
  }

  public static boolean addCustomEnchantment(ItemStack item, CustomEnchant enchant, int level) {
    if (!enchant.canEnchantItem(item)) return false;

    int safeLevel = Math.min(enchant.getMaxLevel(), Math.max(level, enchant.getStartLevel()));
    item.addUnsafeEnchantment(enchant, safeLevel);

    ItemMeta meta = item.getItemMeta();
    List<String> lore = meta.getLore();
    if (lore == null) lore = new ArrayList<>();


    Bukkit.broadcastMessage("Niveau = " + item.getEnchantmentLevel(enchant));
    String enchantLore = "§7" + enchant.getName() + " " + intToRoman(item.getEnchantmentLevel(enchant) + 1);
    if (!lore.contains(enchantLore)) {
      lore.add(enchantLore);
      meta.setLore(lore);
      item.setItemMeta(meta);
    }

    return true;
}
    
  public static void register() {
    boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(ALCOHOLIC);

    if(!registered)
      registerEnchantment(ALCOHOLIC);
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

  
  public String getName() { return name; }
  
  public static List<CustomEnchant> getAllCustomEnchants() { return allCustomEnchatments; } 

  public static CustomEnchant getCustomEnchantByName(String name){
    for(CustomEnchant ench : getAllCustomEnchants()){
      if(ench.getName().equalsIgnoreCase(name))
        return ench;
    }
    return null;
  }
}
