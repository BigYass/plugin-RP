package fr.yorkgangsta.pluginrp.enchants.spell;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public abstract class SpellEnchantment extends CustomEnchant{

  private int COST_LEVEL;

  public SpellEnchantment(Plugin plugin, String name) {
    super(plugin, name);
  }

  public SpellEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
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
  public boolean canEnchantItem(ItemStack item) {
    return true;
  }

  public int getCost() { return COST_LEVEL; }

  public abstract void run(Player player, ItemStack item);

}
