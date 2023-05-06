package fr.yorkgangsta.pluginrp.enchants.spell;

import java.util.HashSet;
import java.util.UUID;

import javax.swing.LayoutStyle.ComponentPlacement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportBackEnchantment extends SpellEnchantment{

  public TeleportBackEnchantment(Plugin plugin, String name) {
    super(plugin, name);
  }

  @Override
  public boolean conflictsWith(Enchantment arg0) {
    return (arg0 instanceof SpellEnchantment);
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
  public boolean isCursed() {
    return false;
  }

  @Override
  public void run(Player player, ItemStack item) {
    final UUID id = player.getUniqueId();

    if(player.getLevel() < this.getCost()){
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas assez de niveaux (minimum: " + ChatColor.GOLD + getCost() +" )"));
      return;
    }

    BukkitRunnable runnable = new BukkitRunnable() {

      @Override
      public void run() {
        Player p = Bukkit.getPlayer(id);

        if(p == null || !p.isOnline()){
          cancel(); return;
        }

      }

    };
  }

}
