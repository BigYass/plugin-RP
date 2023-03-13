package fr.yorkgangsta.pluginrp.data;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.yorkgangsta.pluginrp.Plugin;
import fr.yorkgangsta.pluginrp.events.DrunkRunnable;

public class PlayerInfo {
  private static final int updateRate = 40;

  private static HashMap<Player, PlayerInfo> playersInfo = new HashMap<>();
  
  private static final BukkitRunnable update = new BukkitRunnable() {
    public void run() {
      for(Player p : playersInfo.keySet()){
        PlayerInfo info = playersInfo.get(p);

        if(info.getAlcoolLevel() > 20){
          double drukness = Math.min((info.getAlcoolLevel() - 15) / 20, 1.0);
          double block_multiplier = .5 * drukness; 
          p.setVelocity(p.getVelocity().add(new Vector((Math.random() - .5) * block_multiplier, .1, (Math.random() - .5) * block_multiplier)));
          p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, .7f, .8f);


          int frequence = 4;
          BukkitRunnable effect = new DrunkRunnable(p, updateRate / frequence);

          effect.runTaskTimer(Plugin.getInstance(), 0, frequence);
        }

        if(info.getAlcoolLevel() > 0)
          info.addAlcoolLevel(-1);
        

      }
    };
  };

  private int alcoolLevel = 0;

  public PlayerInfo(Player player){
    playersInfo.put(player, this);
  }
  
  public static PlayerInfo getPlayerInfo(Player p) { 
    if(playersInfo.containsKey(p))
      return playersInfo.get(p); 
    else
      return new PlayerInfo(p);
  }
  public static void applyDrunk(Player p, int count){
    PlayerInfo info;
    if(playersInfo.containsKey(p))
      info = playersInfo.get(p);
    else
      info = new PlayerInfo(p);

    info.addAlcoolLevel(count);
    if(info.getAlcoolLevel() > 10){
      if(!p.hasPotionEffect(PotionEffectType.CONFUSION)) p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1.0f, .8f);
      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 15 * 20, 0));
    }
    else if(info.getAlcoolLevel() > 20){
      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 30 * 20, 0));
      p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30 * 20, 0));
      
    }
  }
  public static void startTask(){ update.runTaskTimer(Plugin.getInstance(), 0, updateRate); }

  public int getAlcoolLevel(){ return alcoolLevel; }
  public void addAlcoolLevel(int diff) { alcoolLevel += diff; }

}
