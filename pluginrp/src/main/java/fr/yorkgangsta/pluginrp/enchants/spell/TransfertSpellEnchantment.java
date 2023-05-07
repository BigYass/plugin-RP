package fr.yorkgangsta.pluginrp.enchants.spell;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
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

public class TransfertSpellEnchantment extends SpellEnchantment{

  public TransfertSpellEnchantment(Plugin plugin, String name, ChatColor color) {
    super(plugin, name, color);
  }

  @Override
  public void run(Player player, ItemStack item) {
    final UUID id = player.getUniqueId();
    final int cost = 3;

    if(player.getLevel() < cost){
      player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Il me faut au moins " + ChatColor.GOLD + cost + ChatColor.RED + "niveaux"));
      return;
    }

    final int frequence = 5 * 20;
    final int search_time = 60 * 20;

    BukkitRunnable search = new BukkitRunnable() {
      HashMap<UUID, Long> lastTries = new HashMap<>();
      boolean isRunning = false, success = false;
      UUID t = null;
      final int frequence = 4;
      final int tp_time = 5 * 20;
      int ticks = 0;

      BukkitRunnable teleport = new BukkitRunnable() {
        int ticks = 0;

        Location lastPosition = Bukkit.getPlayer(id).getLocation();

        @Override
        public void run() {
          Player p = Bukkit.getPlayer(id);
          if(p == null || !p.isOnline()){
            isRunning = false;
            cancel(); return;
          }

          if(!InvocationPlayerSpellEnchantment.hostingPlayers.contains(t)){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Téléportation annulé... " + ChatColor.GRAY + "(Par l'invocateur)"));
            isRunning = false; cancel(); return;
          }

          Player target = Bukkit.getPlayer(t);
          if(target == null || !target.isOnline()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Téléportation annulé... " + ChatColor.GRAY + "(Invocateur perdue)"));
            isRunning = false; cancel(); return;
          }


          if(p.getLevel() < cost){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas assez de niveaux (minimum: " + ChatColor.GOLD + cost + ChatColor.RED + ")"));
            isRunning = false;
            cancel(); return;
          }

          if(lastPosition.distanceSquared(p.getLocation()) > 1 || p.isSneaking()){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Téléportation annulé..."));
            isRunning = false;
            cancel(); return;
          }

          if(ticks >= tp_time){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Téléportation vers " + ChatColor.GOLD + target.getDisplayName() + ChatColor.RED + "..."));
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Invocation de " + ChatColor.GOLD + p.getDisplayName() + ChatColor.RED + "..."));

            Location new_loc = target.getLocation();

            p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation().add(0, 1, 0), 40, .4, .5, .4, .05);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);


            p.setLevel(p.getLevel() - cost);
            p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15 * 20, 0, false, true, true));
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0, false, false, false));
            p.damage(5.0);

            p.getWorld().spawnParticle(Particle.SQUID_INK, new_loc.add(0, 1, 0), 40, .4, .5, .4, .05);
            p.getWorld().playSound(new_loc, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);

            p.teleport(new_loc);
            isRunning = false;
            success = true;
            cancel(); return;
          }

          double n = ((double)ticks / (double)tp_time);

          p.getWorld().playSound(p.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, SoundCategory.AMBIENT, .2f + (float)(n * 2) / tp_time, 1.0f);

          p.getWorld().spawnParticle(Particle.SPELL_WITCH, p.getLocation().add(0, 1, 0), 3 + (int)(n * 30), .3, .5f, .3f);

          p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, frequence + 2, 1, false, false, false));

          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Téléportation dans " + ChatColor.RED + (int)((tp_time - ticks) / 20) + ChatColor.DARK_PURPLE + "vers " + ChatColor.GOLD + target.getDisplayName() + ChatColor.RED + " ..." + ChatColor.GRAY + " (Ne bougez pas)"));

          ticks += frequence;
        }

      };

      @Override
      public void run() {
        ticks += frequence;
        Player p = Bukkit.getPlayer(id);
        if(success || p == null || !p.isOnline()) {
          cancel(); return;
        }

        if(p.isSneaking() || (ticks >= search_time)){
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Recherche d'invocation terminé..."));
          cancel(); return;
        }

        if(isRunning){
          return;
        }

        t = null;

        // SEARCH
        for(UUID tid : InvocationPlayerSpellEnchantment.hostingPlayers){
          Player target = Bukkit.getPlayer(tid);

          if(target == null || !target.isOnline() || tid == id){
            InvocationPlayerSpellEnchantment.hostingPlayers.remove(id);
            return;
          }

          if(!(lastTries.containsKey(tid) && lastTries.get(tid) < Instant.now().getEpochSecond() + 30)){
            t = tid; break;
          }

        }

        if (t != null && !isRunning){
          Player target = Bukkit.getPlayer(t);
          if(target != null && target.isOnline() && target.getWorld() == p.getWorld()){
            isRunning = true;
            target.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Invocation de " + ChatColor.GOLD + p.getDisplayName() + ChatColor.DARK_PURPLE + " dans " + ChatColor.GOLD + (int)(tp_time / 20) + ChatColor.DARK_PURPLE + " secondes..."));
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Convocation vers " + ChatColor.GOLD + target.getDisplayName() + ChatColor.DARK_PURPLE + " dans " + ChatColor.GOLD + (int)(tp_time / 20) + ChatColor.DARK_PURPLE + " secondes..."));
            teleport.runTaskTimer(PluginRP.getInstance(), 0, frequence);
          }
        }
      }};

    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "Recherche d'invocation..."));
    search.runTaskTimer(PluginRP.getInstance(), 0, frequence);
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
