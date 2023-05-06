package fr.yorkgangsta.metiers.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;

public class Catalogue {

  public static final List<String> JOIN_MESSAGES = Arrays.asList(
    "{nickname} le {job} a rejoint la street",
    "{nickname} le {job} rejoint le serveur en Y",
    "{nickname} le {job} arrive sur le serveur, qui l'eut cru ?",
    "Mais qui vois-je ? {nickname} le {job} et sa grosse §cbite",
    "{nickname} le {job} apparait",
    "{nickname} le {job} arrive à §agroove §estreet",
    "{nickname} le {job} a rejoint le §kserveur",
    "{nickname} le {job} arrive sur le serveur",
    "{nickname} le {job} vient d'arriver.",
    "Un nouveau combatant fait son apprition: {nickname} le {job}",
    "{nickname} le {job} est maintenant présent",
    "Le {job} {nickname} nous rejoint. D'ailleur voici son ip : 82.159.24.129!"
  );

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
      Material.WRITTEN_BOOK,
      Material.NAME_TAG
    )
  );

  private static Random randomGenerator = new Random();

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

  public static <T> T getRandomFromList(List<T> collection){
    int i = randomGenerator.nextInt(collection.size());
    return collection.get(i);
  }

}
