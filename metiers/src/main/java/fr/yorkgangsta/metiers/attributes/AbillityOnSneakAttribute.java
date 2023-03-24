package fr.yorkgangsta.metiers.attributes;

import fr.yorkgangsta.metiers.ability.Ability;

public class AbillityOnSneakAttribute extends JobAttribute{
  private final Ability ability;

  public AbillityOnSneakAttribute(String name, Ability ability) {
    super(name, "§cPermet d'utiliser la compétence §7: §6" + ability);
    this.ability = ability;
  }

  
  public Ability getAbility(){ return ability; }
  
}
