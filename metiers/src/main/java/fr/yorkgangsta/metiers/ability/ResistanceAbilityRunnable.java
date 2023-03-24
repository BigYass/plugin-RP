package fr.yorkgangsta.metiers.ability;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ResistanceAbilityRunnable extends PlayerRunnable{

  public ResistanceAbilityRunnable(Player player) {
    super(player);
  }

  @Override
  public void run() {
    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 0, false, false, false));
    if(!player.isSneaking()) cancel();
  }

  
}
