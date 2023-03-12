package fr.yorkgangsta.pluginrp.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Sound;

public class Catalogue {
  private static ArrayList<Sound> scaryNoise = new ArrayList<>(Arrays.asList(
    Sound.ENTITY_CREEPER_PRIMED,
    Sound.ENTITY_TNT_PRIMED,
    Sound.ENTITY_ZOMBIE_STEP,
    Sound.ENTITY_ZOMBIE_VILLAGER_AMBIENT,
    Sound.ENTITY_WITHER_SHOOT,
    Sound.ENTITY_BLAZE_AMBIENT,
    Sound.ENTITY_ZOMBIE_AMBIENT,
    Sound.ENTITY_BLAZE_SHOOT,
    Sound.ENTITY_EVOKER_CAST_SPELL,
    Sound.ENTITY_SKELETON_SHOOT,
    Sound.ENTITY_WARDEN_EMERGE,
    Sound.ENTITY_GENERIC_BURN,
    Sound.ENTITY_GENERIC_EXPLODE,
    Sound.ENTITY_GENERIC_HURT
  ));

  private static Random randomGenerator = new Random();

  public static Sound getRandomScaryNoise(){
    int i = randomGenerator.nextInt(scaryNoise.size());
    return scaryNoise.get(i);
  }


}
