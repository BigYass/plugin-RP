package fr.yorkgangsta.pluginrp.enchants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
import fr.yorkgangsta.pluginrp.enchants.special.NetherPassEnchant;
import fr.yorkgangsta.pluginrp.enchants.spell.InvocationPlayerSpellEnchantment;
import fr.yorkgangsta.pluginrp.enchants.spell.SpellEnchantment;
import fr.yorkgangsta.pluginrp.enchants.spell.TeleportBackEnchantment;
import fr.yorkgangsta.pluginrp.enchants.spell.TransfertSpellEnchantment;
import fr.yorkgangsta.pluginrp.enchants.troll.DiciplingEnchantment;
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

  public static final CustomEnchant NETHER_PASS = new NetherPassEnchant(PluginRP.getInstance(), "Pass_Nether", ChatColor.BLUE);

  public static final CustomEnchant FROST_ASPECT = new FreezeEnchantment(PluginRP.getInstance(), "Aura_de_Glace", ChatColor.GRAY);

  public static final CustomEnchant POISON_ASPECT = new PoisonEnchantment(PluginRP.getInstance(), "Empoisonement", ChatColor.GRAY);

  public static final CustomEnchant LIFESTEAL = new LifeStealEnchantment(PluginRP.getInstance(), "Vol_de_Vie", ChatColor.GRAY);

  public static final CustomEnchant DISCIPLINE = new DiciplingEnchantment(PluginRP.getInstance(), "Discipline", ChatColor.BLACK);

  public static final SpellEnchantment RETURN_TELEPORT = new TeleportBackEnchantment(PluginRP.getInstance(), "Rappel", ChatColor.GRAY);
  public static final SpellEnchantment INVOCATION = new InvocationPlayerSpellEnchantment(PluginRP.getInstance(), "Invocation", ChatColor.GRAY);
  public static final SpellEnchantment CONVOCATION = new TransfertSpellEnchantment(PluginRP.getInstance(), "Convocation", ChatColor.GRAY);


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
    if(enchant == null) return false;
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

    ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
    List<String> lore = new ArrayList<>();

    String enchLore = ench.getColor() + ench.getName() + " " + intToRoman(safeLevel);
    lore.add(enchLore);

    if(meta != null && meta.hasLore())
      for(String line : meta.getLore())
        if(!line.toLowerCase().startsWith(ench.getName().toLowerCase()))
          lore.add(line);

    if(meta != null)
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

    if(!registered) Bukkit.getLogger().info(enchantment.getKey().getKey() + "n'a pas été enregistré...");
    else Bukkit.getLogger().info(enchantment.getKey().getKey() + " a été enregistré!");
  }

  public static void unregister() {
    if(PluginRP.getPlugin(PluginRP.class).isEnabled()) return;

    try{
    Field f = Enchantment.class.getDeclaredField("byKey");
    f.setAccessible(true);

    Map<NamespacedKey, Enchantment> byKey = (Map<NamespacedKey, Enchantment>) f.get(Enchantment.class);

    f = Enchantment.class.getDeclaredField("byName");
    f.setAccessible(true);

    Map<String, Enchantment> byName = (Map<String, Enchantment>) f.get(Enchantment.class);

    if (byKey == null || byName == null) return;

    for (CustomEnchant enchant : allCustomEnchatments) {
      byKey.remove(enchant.getKey());
      byName.remove(enchant.getName());
    }

  } catch(Exception e) {
    e.printStackTrace();
  }
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
