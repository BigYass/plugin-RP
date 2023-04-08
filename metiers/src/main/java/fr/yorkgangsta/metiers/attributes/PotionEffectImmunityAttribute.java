package fr.yorkgangsta.metiers.attributes;

import org.bukkit.potion.PotionEffectType;

public class PotionEffectImmunityAttribute extends JobAttribute{
  private final PotionEffectType type;

  public PotionEffectImmunityAttribute(String name, PotionEffectType type) {
    super(name, "§cDonne l'immunité à l'effet §7: §6" + type);
    this.type = type;
  }

  public PotionEffectType getType() { return type; }

}
