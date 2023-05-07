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
    return false;
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
    final int cost = 2;

    if(player.getLevel() < cost){
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas assez de niveaux (minimum: " + ChatColor.GOLD + cost + ChatColor.RED + ")"));
      return;
    }

    final int frequence = 4;
    final int time = 5 * 20;

    BukkitRunnable tpBack = new BukkitRunnable() {
      int ticks = 0;
      Location lastPosition = Bukkit.getPlayer(id).getLocation();

      @Override
      public void run() {
        Player p = Bukkit.getPlayer(id);
        if(p == null || !p.isOnline()){
          cancel(); return;
        }

        if(p.getLevel() < cost){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas assez de niveaux (minimum: " + ChatColor.GOLD + cost + ChatColor.RED + ")"));
          cancel(); return;
        }

        if(lastPosition.distanceSquared(p.getLocation()) > 1 || p.isSneaking()){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Téléportation annulé..."));
          cancel(); return;
        }

        if(ticks >= time){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.WHITE + "Téléportation..."));

          Location new_loc = p.getBedSpawnLocation() == null ? p.getWorld().getSpawnLocation() : p.getBedSpawnLocation();

          p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation().add(0, 1, 0), 40, .4, .5, .4, .05);
          p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);


          p.setLevel(p.getLevel() - cost);
          p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60 * 20, 0, false, true, true));
          p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0, false, false, false));
          p.damage(5.0);

          p.getWorld().spawnParticle(Particle.SQUID_INK, new_loc.add(0, 1, 0), 40, .4, .5, .4, .05);
          p.getWorld().playSound(new_loc, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

          p.teleport(new_loc);
          cancel(); return;
        }

        double n = ((double)ticks / (double)time);

        p.getWorld().playSound(p.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, SoundCategory.AMBIENT, .2f + (float)(n * 2) / time, 1.0f);

        p.getWorld().spawnParticle(Particle.SPELL_WITCH, p.getLocation().add(0, 1, 0), 3 + (int)(n * 30), .3, .5f, .3f);

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, frequence + 2, 1, false, false, false));

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Téléportation dans " + ChatColor.RED + (int)((time - ticks) / 20) + ChatColor.DARK_PURPLE + " ..." + ChatColor.GRAY + " (Ne bougez pas)"));

        ticks += frequence;
      }

    };

    if(Math.random()  < .4){
      item.setAmount(item.getAmount() - 1);
      player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }
    else {
      player.playSound(player, Sound.BLOCK_SOUL_SOIL_STEP, SoundCategory.AMBIENT, 2.0f, .8f + (float)(Math.random() * .4));
    }

    tpBack.runTaskTimer(PluginRP.getInstance(), 0, frequence);
    return;
  }

}
