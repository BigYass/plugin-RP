package fr.yorkgangsta.metiers.attributes;

import org.bukkit.potion.PotionEffect;

public class EffectAttribute extends JobAttribute{
  private final PotionEffect potionEffect;

  public EffectAttribute(String name, PotionEffect potionEffect) {
    super(name, "§cDonne l'effect permanant §7: §6" + potionEffect.getType().getName());
    this.potionEffect = potionEffect;
  }

  public PotionEffect getPotionEffect() { return potionEffect; }

}
