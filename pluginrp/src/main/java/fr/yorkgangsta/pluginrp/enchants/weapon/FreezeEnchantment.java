package fr.yorkgangsta.pluginrp.enchants.weapon;

import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.PluginRP;
import fr.yorkgangsta.pluginrp.data.Catalogue;
import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public class FreezeEnchantment extends CustomEnchant{

  public FreezeEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
  }

  public FreezeEnchantment(PluginRP plugin, String name) {
    super(plugin, name);
  }

  @Override
  public int getMaxLevel() {
    return 3;
  }

  @Override
  public int getStartLevel() {
    return 1;
  }

  @Override
  public EnchantmentTarget getItemTarget() {
    return EnchantmentTarget.WEAPON;
  }

  @Override
  public boolean isTreasure() {
    return false;
  }

  @Override
  public boolean isCursed() {
    return false;
  }

  @Override
  public boolean conflictsWith(Enchantment other) {
    return Catalogue.AURA_ENCHANTS.contains(other);
  }

  @Override
  public boolean canEnchantItem(ItemStack item) {
    for(Map.Entry<Enchantment, Integer> entry : item.getItemMeta().getEnchants().entrySet()){
      Enchantment ench = entry.getKey();
      if(this.conflictsWith(ench) && ench != this) return false;
    }
    return Catalogue.SWORDS.contains(item.getType());
  }

}
