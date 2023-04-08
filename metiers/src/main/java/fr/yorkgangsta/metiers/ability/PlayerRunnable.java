package fr.yorkgangsta.metiers.ability;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yorkgangsta.metiers.PluginMetier;

public abstract class PlayerRunnable extends BukkitRunnable{
  protected UUID id;

  public PlayerRunnable(Player player) {
    super();
    this.id = player.getUniqueId();
  }

  public PlayerRunnable() {
    super();
  }

  public abstract boolean canRun();

  @Override
  public abstract void run();

  public void launchAbility(AbilityLaunchType type){
    if(!canRun()) return;
    switch (type) {
      case ONE_TIME:
        runTask(PluginMetier.getInstance());
        break;

      case REPEAT:
        runTaskTimer(PluginMetier.getInstance(), 0, 5);
        break;
    }
  }

  public void setPlayer(Player p) { id = p.getUniqueId(); }
  public Player getPlayer() { return Bukkit.getPlayer(id); }

  public void sneakEvent() { runTaskTimer(PluginMetier.getInstance(), 0, 5); }
}
