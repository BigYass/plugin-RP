package fr.yorkgangsta.pluginrp.enchants.food;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public class AlcoholicEnchantment extends CustomEnchant{


  public AlcoholicEnchantment(Plugin plugin, String name) {
    super(plugin, name);
  }

  public AlcoholicEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
  }

  @Override
  public boolean canEnchantItem(ItemStack item) {
    return item.getType().isEdible() || item.getType() == Material.POTION;
  }

  @Override
  public boolean conflictsWith(Enchantment arg0) {
    return false;
  }

  @Override
  public EnchantmentTarget getItemTarget() {
    return EnchantmentTarget.VANISHABLE;
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public int getStartLevel() {
    return 1;
  }

  @Override
  public boolean isCursed() {
    return false;
  }

  @Override
  public boolean isTreasure() {
    return true;
  }

}