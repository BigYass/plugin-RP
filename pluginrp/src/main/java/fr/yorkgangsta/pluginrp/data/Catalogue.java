package fr.yorkgangsta.pluginrp.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;

public class Catalogue {
  private static final ArrayList<Sound> SCARY_NOISE = new ArrayList<>(Arrays.asList(
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

  public static final HashSet<Material> SWORDS = new HashSet<>(
    Arrays.asList(
      Material.WOODEN_SWORD,
      Material.STONE_SWORD,
      Material.IRON_SWORD,
      Material.GOLDEN_SWORD,
      Material.DIAMOND_SWORD,
      Material.NETHERITE_SWORD
    )
  );

  public static final HashSet<Enchantment> AURA_ENCHANTS = new HashSet<>(
    Arrays.asList(
      Enchantment.FIRE_ASPECT,
      CustomEnchant.FROST_ASPECT,
      CustomEnchant.POISON_ASPECT
    )
  );



  private static Random randomGenerator = new Random();

  public static Sound getRandomScaryNoise(){
    int i = randomGenerator.nextInt(SCARY_NOISE.size());
    return SCARY_NOISE.get(i);
  }


}
