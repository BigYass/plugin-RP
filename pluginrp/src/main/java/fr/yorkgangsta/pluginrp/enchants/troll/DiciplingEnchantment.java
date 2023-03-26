package fr.yorkgangsta.pluginrp.enchants.troll;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public class DiciplingEnchantment extends CustomEnchant{

  public DiciplingEnchantment(Plugin plugin, String name) {
    super(plugin, name);
  }

  public DiciplingEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
  }

  @Override
  public int getMaxLevel() {
    return 1;
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
    return false;
  }

  @Override
  public boolean isCursed() {
    return false;
  }

  @Override
  public boolean conflictsWith(Enchantment other) {
    final HashSet<Enchantment> ban = new HashSet<>();
    return ban.contains(other);
  }

  @Override
  public boolean canEnchantItem(ItemStack item) {
    return true;
  }

}
