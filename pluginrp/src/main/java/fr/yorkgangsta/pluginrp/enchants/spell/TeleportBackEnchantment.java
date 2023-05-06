package fr.yorkgangsta.pluginrp.enchants.spell;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import fr.yorkgangsta.pluginrp.PluginRP;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class TeleportBackEnchantment extends SpellEnchantment{

  public TeleportBackEnchantment(Plugin plugin, String name) {
    super(plugin, name);
  }

  public TeleportBackEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
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
  public void run(Player player, final ItemStack item) {
    final UUID id = player.getUniqueId();

    if(player.getLevel() < this.getCost()){
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas assez de niveaux (minimum: " + ChatColor.GOLD + getCost() +" )"));
      return;
    }

    final int frequence = 4;
    final int time = 10 * 20;
    final int cost = getCost();

    BukkitRunnable tpBack = new BukkitRunnable() {
      int ticks = 0;
      Location lastPosition = null;

      @Override
      public void run() {
        Player p = Bukkit.getPlayer(id);
        if(p == null || !p.isOnline()){
          cancel(); return;
        }

        if(p.getLevel() < cost){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas assez de niveaux (minimum: " + ChatColor.GOLD + getCost() +" )"));
          cancel(); return;
        }

        if((lastPosition != null && lastPosition.distanceSquared(p.getLocation()) > 20) || p.isSneaking()){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Téléportation annulé..."));
          cancel(); return;
        }

        if(ticks >= time){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + "Téléportation en cours..."));

          Location new_loc = p.getBedSpawnLocation() == null ? p.getWorld().getSpawnLocation() : p.getBedSpawnLocation();

          p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation().add(0, 1, 0), 40, .4, .5, .4, .05);
          p.getWorld().playSound(p.getLocation(), Sound.ITEM_BRUSH_BRUSH_SAND_COMPLETED, SoundCategory.PLAYERS, 2.0f, .5f);

          p.teleport(new_loc);

          p.setLevel(p.getLevel() - cost);
          item.setAmount(item.getAmount() - 1);
          p.damage(5.0);

          p.getWorld().spawnParticle(Particle.SQUID_INK, new_loc.add(0, 1, 0), 40, .4, .5, .4, .05);
          p.getWorld().playSound(new_loc, Sound.ITEM_BRUSH_BRUSH_SAND_COMPLETED, SoundCategory.PLAYERS, 2.0f, .5f);

          cancel(); return;
        }

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + "Téléportation dans " + ChatColor.RED + (int)((time - ticks) / 20) + ChatColor.WHITE + "..." + ChatColor.GRAY + " (Ne bougez pas)"));

        lastPosition = p.getLocation();

        ticks += frequence;
      }

    };

    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 2));
    player.playSound(player, Sound.BLOCK_SOUL_SOIL_STEP, SoundCategory.AMBIENT, 2.0f, .8f + (float)(Math.random() * .4));
    tpBack.runTaskTimer(PluginRP.getInstance(), 0, frequence);
  }

}
