package fr.yorkgangsta.metiers.ability;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yorkgangsta.metiers.PluginMetier;

public abstract class PlayerRunnable extends BukkitRunnable{
  protected Player player;

  public PlayerRunnable(Player player) {
    super();
    this.player = player;
  }

  public PlayerRunnable() {
    super();
  }

  @Override
  public abstract void run();

  public void launchAbility(AbilityLaunchType type){
    switch (type) {
      case ONE_TIME:
        runTask(PluginMetier.getInstance());
        break;
    
      case REPEAT:
        runTaskTimer(PluginMetier.getInstance(), 0, 5);
        break;
    }
  }

  public void setPlayer(Player p) { player = p; }
  public Player getPlayer() { return player; }

  public void sneakEvent() { runTaskTimer(PluginMetier.getInstance(), 0, 5); }
}
