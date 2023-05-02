package fr.yorkgangsta.metiers.attributes;

import fr.yorkgangsta.metiers.ability.Ability;

public class AbillityOnSneakAttribute extends JobAttribute{
  private final Ability ability;

  public static final AbillityOnSneakAttribute INVISIBILITY = new AbillityOnSneakAttribute("OnSneakInvisibility", Ability.INVISIBILITY_ON_SNEAK);

  public static final AbillityOnSneakAttribute RESISTANCE = new AbillityOnSneakAttribute("OnSneakResistance", Ability.RESISTANCE_ON_SNEAK);

  public static final AbillityOnSneakAttribute SLOW_FALLING = new AbillityOnSneakAttribute("OnSneakSlowFalling", Ability.SLOW_FALLING_ON_SNEAK);

  public AbillityOnSneakAttribute(String name, Ability ability) {
    super(name, "§cPermet d'utiliser la compétence §7: §6" + ability);
    this.ability = ability;
  }


  public Ability getAbility(){ return ability; }

}
