package fr.yorkgangsta.metiers.ability;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InvisibilityAirAbilityRunnable extends PlayerRunnable{

  public InvisibilityAirAbilityRunnable(Player p) {
    super(p);

    Player player = Bukkit.getPlayer(id);

    if(canRun() && !player.isInvisible() && player.getFoodLevel() > 6 && !player.hasPotionEffect(PotionEffectType.INVISIBILITY)){
      player.getWorld().spawnParticle(Particle.SQUID_INK, player.getLocation().add(0, 1, 0), 40, .4, .5, .4, .05);
      player.getWorld().playSound(player.getLocation(), Sound.ITEM_BRUSH_BRUSH_SAND_COMPLETED, SoundCategory.PLAYERS, 2.0f, .5f);
    }

  }

  @Override
  public void run() {
    Player player = Bukkit.getPlayer(id);
    if(player == null || !player.isOnline()){
      cancel();
      return;
    }

    if(canRun() && player.getFoodLevel() > 6 && player.isSneaking()){
      if(player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 6, 0, false, false, false)) && player.getGameMode() != GameMode.CREATIVE)
        player.setExhaustion(player.getExhaustion() + .5f);
        player.getWorld().playSound(player.getLocation(), Sound.PARTICLE_SOUL_ESCAPE, SoundCategory.PLAYERS, .4f, 1.0f);
    }
    else {
      cancel();
    }
  }

  @Override
  public boolean canRun() {
    Player player = Bukkit.getPlayer(id);
    if(!player.isOnline()){
      return false;
    }
    PlayerInventory inv = player.getInventory();
    return player.getFoodLevel() > 6
    && inv.getItemInMainHand().getType() == Material.AIR
    && inv.getItemInOffHand().getType() == Material.AIR
    && inv.getBoots() == null
    && inv.getChestplate() == null
    && inv.getLeggings() == null
    && inv.getHelmet() == null
    ;
  }


}
