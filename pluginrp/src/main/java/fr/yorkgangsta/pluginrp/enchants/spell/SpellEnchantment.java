package fr.yorkgangsta.pluginrp.enchants.spell;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.PluginRP;
import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public abstract class SpellEnchantment extends CustomEnchant{

  public static final TeleportBackEnchantment RETURN_TELEPORT = new TeleportBackEnchantment(PluginRP.getInstance(), "Retour", ChatColor.RED);

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
