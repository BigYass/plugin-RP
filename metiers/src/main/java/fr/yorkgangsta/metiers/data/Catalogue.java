package fr.yorkgangsta.metiers.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Catalogue {

  public static final List<PotionEffect> badEffects = new ArrayList<PotionEffect>(Arrays.asList(
    new PotionEffect(PotionEffectType.SLOW, 60, 0, false, false, false),
    new PotionEffect(PotionEffectType.HUNGER, 60, 20, false, false, false),
    new PotionEffect(PotionEffectType.DARKNESS, 60, 0, false, false, false),
    new PotionEffect(PotionEffectType.SLOW_DIGGING, 60, 0, false, false, false),
    new PotionEffect(PotionEffectType.WEAKNESS, 60, 0, false, false, false),
    new PotionEffect(PotionEffectType.BLINDNESS, 60, 0, false, false, false)
  ));

  public static final HashSet<Material> dirtBlocks = new HashSet<>(
    Arrays.asList(
      Material.DIRT,
      Material.DIRT_PATH,
      Material.GRASS_BLOCK,
      Material.ROOTED_DIRT,
      Material.COARSE_DIRT,
      Material.DIRT_PATH
    )
  );

  public static final HashSet<Material> cookedFood = new HashSet<>(
    Arrays.asList(
      Material.COOKED_BEEF,
      Material.COOKED_CHICKEN,
      Material.COOKED_MUTTON,
      Material.COOKED_PORKCHOP,
      Material.COOKED_COD,
      Material.COOKED_RABBIT,
      Material.COOKED_SALMON,
      Material.DRIED_KELP,
      Material.BAKED_POTATO
    )
  );

  public static final HashMap<Material, Material> cookedFoodToRaw = new HashMap<Material, Material>(){{
    put(Material.COOKED_BEEF, Material.BEEF);
    put(Material.COOKED_CHICKEN, Material.CHICKEN);
    put(Material.COOKED_RABBIT, Material.RABBIT);
    put(Material.COOKED_MUTTON, Material.MUTTON);
    put(Material.COOKED_PORKCHOP, Material.PORKCHOP);
  }};

  public static final HashSet<Material> carftedFood = new HashSet<>(
    Arrays.asList(
      Material.COOKIE,
      Material.RABBIT_STEW,
      Material.BREAD,
      Material.CAKE,
      Material.PUMPKIN_PIE,
      Material.MUSHROOM_STEW,
      Material.BEETROOT_SOUP,
      Material.GOLDEN_APPLE,
      Material.GOLDEN_CARROT
    )
  );

  public static final HashSet<Material> cropsResults = new HashSet<>(
    Arrays.asList(
      Material.WHEAT,
      Material.BEETROOTS,
      Material.BEETROOT,
      Material.CARROTS,
      Material.CARROT,
      Material.POTATOES,
      Material.POTATO
    )
  );

  public static final HashSet<Material> hoes = new HashSet<>(
    Arrays.asList(
      Material.WOODEN_HOE,
      Material.STONE_HOE,
      Material.IRON_HOE,
      Material.GOLDEN_HOE,
      Material.DIAMOND_HOE,
      Material.NETHERITE_HOE
    )
  );

  public static final HashSet<Material> books = new HashSet<>(
    Arrays.asList(
      Material.PAPER,
      Material.BOOK,
      Material.WRITABLE_BOOK,
      Material.WRITTEN_BOOK
    )
  );



  public static String intToRoman(int num) {
    if (num < 1 || num > 3999) {
        return "?";
    }
    String[] m = {"", "M", "MM", "MMM"};
    String[] c = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    String[] x = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    String[] i = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    return m[num/1000] + c[(num%1000)/100] + x[(num%100)/10] + i[num%10];
  }

}
