package fr.yorkgangsta.pluginrp.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

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
    Sound.ENTITY_GENERIC_BURN,
    Sound.ENTITY_GENERIC_EXPLODE,
    Sound.ENTITY_GENERIC_HURT,
    Sound.ENTITY_SKELETON_AMBIENT
  ));

  public static final List<String> JOIN_MESSAGES = Arrays.asList(
    "{} a rejoint la street",
    "{} rejoint le serveur en Y",
    "{} arrive sur le serveur, qui l'eut cru ?",
    "Mais qui vois-je ? {} et sa grosse §cbite",
    "{} apparait",
    "{} arrive à §agroove §estreet",
    "{} a rejoint le §kserveur",
    "{} arrive sur le serveur",
    "{} vient d'arriver.",
    "Un nouveau combatant fait son apprition: {}",
    "{} est maintenant présent",
    "{} nous rejoint. D'ailleur voici son ip : 82.159.24.129!"
  );

  public static final List<String> LEAVE_MESSAGES = Arrays.asList(
    "{} quitte la street...",
    "{} quitte le serveur",
    "{} quitte le serveur, qui l'eut cru ?",
    "Mais qui vois-je ? {} ? Eh bah non il est partit",
    "{} disparait!",
    "{} se taille de §agroove §estreet",
    "{} a quitté le §kserveur",
    "{} vient de partir...",
    "{} n'est plus présent",
    "{} nous quitte. Pour la peine voici son ip : 85.154.24.129!"
  );



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

  public static final HashMap<EntityType, Double> NETHER_PASS_DROPS_RATE = new HashMap<EntityType, Double>(){{
    put(EntityType.ZOMBIE, .005);
    put(EntityType.ZOMBIE_VILLAGER, .015);
    put(EntityType.PILLAGER, .01);
    put(EntityType.SKELETON, .01);
    put(EntityType.WITCH, .025);
    put(EntityType.PHANTOM, .1);
    put(EntityType.CREEPER, .04);
    put(EntityType.ENDERMAN, .03);
    put(EntityType.ZOMBIE_VILLAGER, .03);
    put(EntityType.VINDICATOR, .03);
    put(EntityType.ILLUSIONER, .01);
    put(EntityType.VEX, .05);
    put(EntityType.ENDERMITE, .02);
    put(EntityType.BLAZE, .01);
    put(EntityType.GHAST, .04);
    put(EntityType.WITHER_SKELETON, .08);
    put(EntityType.DROWNED, .008);
    put(EntityType.PIGLIN, .01);
  }};

  private static Random randomGenerator = new Random();

  public static Sound getRandomScaryNoise(){
    int i = randomGenerator.nextInt(SCARY_NOISE.size());
    return SCARY_NOISE.get(i);
  }

  public static <T> T getRandomFromList(List<T> collection){
    int i = randomGenerator.nextInt(collection.size());
    return collection.get(i);
  }


}
