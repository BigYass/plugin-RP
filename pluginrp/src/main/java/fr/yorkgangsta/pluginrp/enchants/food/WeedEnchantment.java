package fr.yorkgangsta.pluginrp.enchants.food;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public class WeedEnchantment extends CustomEnchant{

  public WeedEnchantment(Plugin plugin, String name) {
    super(plugin, name);
  }

  public WeedEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
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
    return EnchantmentTarget.VANISHABLE;
  }

  @Override
  public boolean isTreasure() {
    return true;
  }

  @Override
  public boolean isCursed() {
    return true;
  }

  @Override
  public boolean conflictsWith(Enchantment other) {
    return false;
  }

  @Override
  public boolean canEnchantItem(ItemStack item) {
    return item.getType().isEdible() || item.getType() == Material.POTION;
  }

}
