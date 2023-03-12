package fr.yorkgangsta.pluginrp.data;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInfo {
  private static HashMap<Player, PlayerInfo> playersInfo = new HashMap<>();
  private static final BukkitRunnable update = new BukkitRunnable() {
    public void run() {
      for(Player p : playersInfo.keySet()){
        PlayerInfo info = playersInfo.get(p);

        if(info.getAlcoolLevel() > 0)
          info.addAlcoolLevel(-1);
      }
    };
  };

  private final Player player;

  private int alcoolLevel = 0;

  public PlayerInfo(Player player){
    this.player = player;
    playersInfo.put(player, this);
  }
  
  public static PlayerInfo getPlayerInfo(Player p) { return playersInfo.get(p); }
  public static void applyDrunk(Player p, int count){
    PlayerInfo info = playersInfo.get(p);

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

  public int getAlcoolLevel(){ return alcoolLevel; }
  public void addAlcoolLevel(int diff) { alcoolLevel += diff; }

}
