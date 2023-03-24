package fr.yorkgangsta.metiers.ability;

import org.bukkit.entity.Player;

public class Ability {
  public static final Ability RESISTANCE_ON_SNEAK = new Ability(AbilityLaunchType.REPEAT, AbilityType.RESISTANCE_ON_SNEAK);

  private enum AbilityType{
    RESISTANCE_ON_SNEAK;

    public String toString(){
      switch (this) {
        case RESISTANCE_ON_SNEAK:
          return "Appuie solide";
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
    }
    return null;
  }

  public void launchAbility(Player p){
    createRunnable(p).launchAbility(getType());
  }
}
