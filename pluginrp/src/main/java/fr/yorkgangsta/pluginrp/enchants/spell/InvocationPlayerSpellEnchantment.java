package fr.yorkgangsta.pluginrp.enchants.spell;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yorkgangsta.pluginrp.PluginRP;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class InvocationPlayerSpellEnchantment extends SpellEnchantment{

  public static ArrayList<UUID> hostingPlayers = new ArrayList<>();

  public InvocationPlayerSpellEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
  }

  @Override
  public void run(Player player, ItemStack item) {
    final UUID id = player.getUniqueId();

    final int freq = 4;

    BukkitRunnable cooldown = new BukkitRunnable() {
      int ticks = 0;
      @Override
      public void run() {
        ticks += freq;

        if(!hostingPlayers.contains(id)){
          cancel(); return;
        }

        Player p = Bukkit.getPlayer(id);
        if(p == null || !p.isOnline() || p.getGameMode() == GameMode.SPECTATOR){
          hostingPlayers.remove(id);
          cancel(); return;
        }

        p.playSound(p.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, SoundCategory.AMBIENT, 1.0f, 1.0f);
        p.getWorld().spawnParticle(Particle.SOUL, p.getLocation().add(0, 1, 0), 15, .3, .5, .3, 0);

        p.setExhaustion(p.getExhaustion() + .1f + Math.max(0, ((float)ticks / 1200) - 1.0f));

        if(p.getFoodLevel() <= 6){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Il faut de l'énergie pour utiliser ce parchemin, mangez un coup..."));
          hostingPlayers.remove(id);
          cancel(); return;
        }
      }

    };


    if(hostingPlayers.contains(id)){
      hostingPlayers.remove(id);
      player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.BLOCKS, 1.0f, 1.0f);
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Invocation annulé..."));

      return;
    }

    if(hostingPlayers.size() > 0){
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Une invocation est déjà en cours veuillez patientez"));
      return;
    }

    if(player.getFoodLevel() < 6){
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Il faut de l'énergie pour utiliser ce parchemin, mangez un coup..."));
      return;
    }

    hostingPlayers.add(id);
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Invocation en cours... " + ChatColor.GRAY + "(En attente de joueur...)"));
    cooldown.runTaskTimer(PluginRP.getInstance(), 0, freq);
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

}
