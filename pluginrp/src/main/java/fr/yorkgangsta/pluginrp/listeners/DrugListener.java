package fr.yorkgangsta.pluginrp.listeners;

import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.yorkgangsta.pluginrp.Plugin;
import fr.yorkgangsta.pluginrp.data.Catalogue;
import fr.yorkgangsta.pluginrp.data.PlayerInfo;
import fr.yorkgangsta.pluginrp.items.ItemManager;

public class DrugListener implements Listener{
  
  @EventHandler
  public void onConsume(PlayerItemConsumeEvent event){
    final Player p = event.getPlayer();
    ItemStack item = event.getItem();

    if(item.getItemMeta().getDisplayName().equals(ItemManager.WEED.getItemName())){
      p.damage(0.5);

      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 20, 0, true, false, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 0, false, false, false));

      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, 2f, .8f);

      Vector direction = p.getEyeLocation().getDirection().normalize();
      p.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, p.getEyeLocation().add(direction.multiply(.2)), 10, .05, .1, .05, .03);

      

      BukkitRunnable task = new BukkitRunnable() {
        int i = 0;

        final static double prob_noise = .07;
        final static double prob_damage = .03;
        @Override
        public void run() {

          if(Math.random() < prob_noise) p.playSound(p.getLocation(), Catalogue.getRandomScaryNoise(), SoundCategory.PLAYERS, .6f, .8f);
          if(Math.random() < prob_damage) p.damage(0.0);

          i++;
          if (i >= 20){
            cancel();
          }
        }
      };

      task.runTaskTimer(Plugin.getInstance(), 20, 20);
    }
    else if(item.getItemMeta().getDisplayName().equals(ItemManager.BEER.getItemName())){
      PlayerInfo.applyDrunk(p, 7);
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event){
    final Player p = event.getPlayer();

    BukkitRunnable task = new BukkitRunnable() {
      @Override
      public void run() {
        p.sendMessage("<YorkGangsta> Bienvenue habibi");
      }
    };

    task.runTaskLater(Plugin.getInstance(), 3*20);
    
  }

  @EventHandler
  public void onPlayerSneak(PlayerToggleSneakEvent event){
    Player p = event.getPlayer();
    if(p.getDisplayName().equalsIgnoreCase("YorkGangsta")){
      p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PUFFER_FISH_FLOP, 2.0f, 1.0f);
    }
  }

  @EventHandler
  public void onPlayerInterract(PlayerInteractEvent event){
    if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
      final Player p = event.getPlayer();
      ItemStack item = event.getItem();
      if(item == null || item.getItemMeta() == null) return;
      if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.COKE.getItemName()) && p.getCooldown(ItemManager.COKE.getItemMaterial()) == 0){
        if(p.getGameMode() != GameMode.CREATIVE)
          item.setAmount(item.getAmount() - 1);

        final int cooldown = 10;

        p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, SoundCategory.MASTER, .5f, .6f);

        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, cooldown * 20, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
        
        p.setCooldown(ItemManager.COKE.getItemMaterial(), cooldown * 20);

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

        task.runTaskTimer(Plugin.getInstance(), 20, 20);

        
      }
      else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ItemManager.WEED.getItemName())){
        int food = p.getFoodLevel();
        if (food >= 20){
          p.setFoodLevel(19);
          final BukkitRunnable reset = new BukkitRunnable() {
            @Override
            public void run() {
              p.setFoodLevel(Math.min(p.getFoodLevel() + 1, 20));
            }
          };

          reset.runTaskLater(Plugin.getInstance(), 20);
        }

      }
    }
  }

}
