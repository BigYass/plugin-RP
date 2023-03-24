package fr.yorkgangsta.pluginrp.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class DrunkRunnable extends BukkitRunnable{
  private final Player p;
  private final int max;

  private final static double boostRate = .1;

  private int i = 0;

  public DrunkRunnable(Player p, int maxIteration){
    this.p = p;
    this.max = maxIteration;
  }

  @Override
  public void run() {
    Location eyeLocation = p.getEyeLocation();
    Vector direction = eyeLocation.getDirection().normalize();
    p.spawnParticle(Particle.BUBBLE_POP, eyeLocation.add(direction.multiply(.2)), 4, .1, .2, .1, 0.01);
    p.setWalkSpeed(.2f);
    if(!p.isFlying() && Math.random() < boostRate)
      if(p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR)
        p.setSneaking(true);
    if(!p.isFlying() && Math.random() < boostRate)
      if(p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR)
        p.setWalkSpeed(0);

    if(i++ >= max) cancel();
  }
  
}
