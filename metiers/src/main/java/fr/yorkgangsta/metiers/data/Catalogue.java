package fr.yorkgangsta.metiers.data;

import java.util.Arrays;
import java.util.HashSet;

import org.bukkit.Material;

public class Catalogue {

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
