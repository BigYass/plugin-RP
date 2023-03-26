package fr.yorkgangsta.pluginrp.data;

import java.util.HashMap;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.WeatherType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yorkgangsta.pluginrp.PluginRP;
import fr.yorkgangsta.pluginrp.events.DrunkRunnable;

/**
 * Classe associé à un joueur contenant des infos complémentaire pour le plugin
 * Cotient :
 *  - Niveaux d'alcool
 *  - Addiction à Cocaine
 * S'occupe de gérer ces valeurs
 */
public class PlayerInfo {
  private static final int updateRate = 40;

  private static final String nerfHealthName = "tempCokeNerf";

  private static HashMap<Player, PlayerInfo> playersInfo = new HashMap<>();

  private static final BukkitRunnable update = new BukkitRunnable() {
    public void run() {
      update();
    };
  };

  protected int alcoolLevel = 0;
  protected int cokeLevel = 0;

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
    PlayerInfo info = getPlayerInfo(p);

    info.addAlcoolLevel(count);

    int alcoolLevel = info.getAlcoolLevel();

    int duration = (alcoolLevel - 10) * updateRate;
    if(alcoolLevel > 10) p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration, 0));
    if(alcoolLevel > 20) p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration, 0));

    if(alcoolLevel > 50){
      p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (alcoolLevel - 49) * updateRate, 4));
      p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_DEATH, SoundCategory.PLAYERS, 1.0f, .3f);
    }

  }
  public static void applyCoke(final Player p, int count){
    final int cooldown = 30;
    PlayerInfo info = getPlayerInfo(p);

    info.setCokeLevel(count);

    resetHealth(p);

    p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, SoundCategory.MASTER, .5f, .6f);

    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, cooldown * 20, 2));
    p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, cooldown * 20, 1));


    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));

    p.setPlayerWeather(WeatherType.DOWNFALL);


    BukkitRunnable task = new BukkitRunnable() {
      int i = cooldown;

      @Override
      public void run() {

        p.playSound(p.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, SoundCategory.PLAYERS, .1f + i * .1f, 0f);

        i--;
        if(i <= 0){
          p.resetPlayerWeather();
          cancel();
        }
      }

    };

    task.runTaskTimer(PluginRP.getInstance(), 20, 20);

  }


  public static void startTask(){ update.runTaskTimer(PluginRP.getInstance(), 0, updateRate); }

  public static void resetHealth(Player p){
    AttributeInstance maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
    for( AttributeModifier am : maxHealth.getModifiers()){
      if(am.getName().equals(nerfHealthName)){
        maxHealth.removeModifier(am);
      }
    }

  }

  private static void update(){

    for(Player p : playersInfo.keySet()){
      if(!p.isOnline()) continue;
      PlayerInfo info = playersInfo.get(p);

      if(info.getAlcoolLevel() > 20){
        int frequence = 4;
        BukkitRunnable effect = new DrunkRunnable(p, updateRate / frequence);

        effect.runTaskTimer(PluginRP.getInstance(), 0, frequence);
      }

      if(info.getAlcoolLevel() == 20) p.setGliding(false);;

      final int cokeLevel = 600, frequence = 60, maxTime = 6;
      if(info.getCokeLevel() <= cokeLevel){
        if(info.getCokeLevel() % frequence == 0 && info.getCokeLevel() >= 5){
          if(info.getCokeLevel() > cokeLevel - (frequence * maxTime)){
            AttributeModifier nerf = new AttributeModifier(nerfHealthName, -2.0, Operation.ADD_NUMBER);
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(nerf);
          }

          p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0));

          p.playSound(p.getLocation(), Sound.ENTITY_WARDEN_HEARTBEAT, SoundCategory.PLAYERS, 2f, 0f);

          p.sendMessage("§7Besoin de §fCoke§7...§r");

          p.sendTitle("", "§7Besoin de §fCoke§7...§r", 10, 70, 20);

        }

        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, updateRate + 20, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, updateRate + 20, 0));

      }

      if(info.getAlcoolLevel() > 0)
        info.addAlcoolLevel(-1);

      if(info.getCokeLevel() == 1)
        resetHealth(p);

      if(info.getCokeLevel() > 0)
        info.addCokeLevel(-1);

    }
  }

  public int getAlcoolLevel(){ return alcoolLevel; }
  public int getCokeLevel() { return cokeLevel; }

  public void addAlcoolLevel(int diff) { alcoolLevel += diff; }
  public void addCokeLevel(int diff) { cokeLevel += diff; }

  public void setAlcoolLevel(int new_value) { alcoolLevel = new_value; }
  public void setCokeLevel(int new_value) { cokeLevel = new_value; }

}
