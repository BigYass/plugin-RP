package fr.yorkgangsta.metiers.attributes;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PotionEffectRunnable extends BukkitRunnable{

  private final PotionEffect effect;
  private final Player player;

  public PotionEffectRunnable(PotionEffectType type, int amplifier, Player player){
    effect = new PotionEffect(type, 400, amplifier, false, false, false);
    this.player = player;
  }

  @Override
  public void run() {
    player.addPotionEffect(effect);
  }
  
}
