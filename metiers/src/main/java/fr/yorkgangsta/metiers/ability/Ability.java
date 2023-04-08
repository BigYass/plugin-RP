package fr.yorkgangsta.metiers.ability;

import org.bukkit.entity.Player;

public class Ability {
  public static final Ability RESISTANCE_ON_SNEAK = new Ability(AbilityLaunchType.REPEAT, AbilityType.RESISTANCE_ON_SNEAK);

  public static final Ability SLOW_FALLING_ON_SNEAK = new Ability(AbilityLaunchType.REPEAT, AbilityType.SLOW_FALLING_ON_SNEAK);

  public static final Ability INVISIBILITY_ON_SNEAK = new Ability(AbilityLaunchType.REPEAT, AbilityType.INVISIBILITY_ON_SNEAK);

  private enum AbilityType{
    RESISTANCE_ON_SNEAK, SLOW_FALLING_ON_SNEAK, INVISIBILITY_ON_SNEAK;

    public String toString(){
      switch (this) {
        case RESISTANCE_ON_SNEAK:
          return "Appuie solide";
        case SLOW_FALLING_ON_SNEAK:
          return "Maitrise de la chute";
        case INVISIBILITY_ON_SNEAK:
          return "Furtivité";

        default:
          return "UNDIFINED";
      }
    }
  }

  private final AbilityLaunchType type;
  private final AbilityType ability;

  public Ability(AbilityLaunchType type, AbilityType ability) {
    this.type = type;
    this.ability = ability;
  }

  public AbilityLaunchType getType() {
    return type;
  }

  public String toString(){ return ability.toString(); }

  public PlayerRunnable createRunnable(Player p) {
    switch (ability) {
      case RESISTANCE_ON_SNEAK:
        return new ResistanceAbilityRunnable(p);
      case SLOW_FALLING_ON_SNEAK:
        return new SlowFallingAbilityRunnable(p);
      case INVISIBILITY_ON_SNEAK:
        return new InvisibilityAirAbilityRunnable(p);
    }
    return null;
  }

  public void launchAbility(Player p){

      createRunnable(p).launchAbility(getType());
  }
}
