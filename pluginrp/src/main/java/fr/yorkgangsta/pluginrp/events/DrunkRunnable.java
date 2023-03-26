package fr.yorkgangsta.pluginrp.events;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DrunkRunnable extends BukkitRunnable{
  private final Player p;
  private final int max;

  private int i = 0;

  public DrunkRunnable(Player p, int maxIteration){
    this.p = p;
    this.max = maxIteration;
  }

  @Override
  public void run() {
    Location eyeLocation = p.getEyeLocation();
    Vector direction = eyeLocation.getDirection().normalize();
    p.getWorld().spawnParticle(Particle.BUBBLE_POP, eyeLocation.add(direction.multiply(.2)), 4, .1, .1, .1, 0.01);

    if(i++ >= max) cancel();
  }

}
