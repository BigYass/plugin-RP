package fr.yorkgangsta.metiers.attributes;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectAttribute extends JobAttribute{
  private final PotionEffect potionEffect;

  public final static EffectAttribute FAST_DIGGING_ATTRIBUTE = new EffectAttribute("EffectFastDigging", new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));

  public final static EffectAttribute NIGHT_VISION_ATTRIBUTE = new EffectAttribute("EffectNightVision", new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0));

  public final static EffectAttribute SPEED_ATTRIBUTE = new EffectAttribute("EffectSpeed", new PotionEffect(PotionEffectType.SPEED, 200, 0));

  public EffectAttribute(String name, PotionEffect potionEffect) {
    super(name, "§cDonne l'effect permanant §7: §6" + potionEffect.getType().getName());
    this.potionEffect = potionEffect;
  }

  public PotionEffect getPotionEffect() { return potionEffect; }

}
