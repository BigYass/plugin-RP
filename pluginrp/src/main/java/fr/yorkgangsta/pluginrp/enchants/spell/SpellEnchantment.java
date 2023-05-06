package fr.yorkgangsta.pluginrp.enchants.spell;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public abstract class SpellEnchantment extends CustomEnchant{
  private static HashSet<SpellEnchantment> allSpellEnchatments = new HashSet<>();

  public SpellEnchantment(Plugin plugin, String name) {
    super(plugin, name);
    allSpellEnchatments.add(this);
  }

  public SpellEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
    allSpellEnchatments.add(this);
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
  public boolean canEnchantItem(ItemStack item) {
    return item.getType() == Material.PAPER;
  }

  public abstract void run(Player player, ItemStack item);

  public static boolean contains(Enchantment ench){
    return allSpellEnchatments.contains(ench);
  }

}
