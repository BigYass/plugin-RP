package fr.yorkgangsta.metiers.attributes;

import java.util.Collection;
import java.util.HashSet;

import org.bukkit.Material;

public class CraftItemAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenCrafts = new HashSet<>();
  private final HashSet<Material> canCraftItems;

  public CraftItemAttribute(String name, Collection<? extends Material> results) {
    super(name, "§c Permet de craft §7: §6" + results);
    this.canCraftItems = new HashSet<>(results);
    forbiddenCrafts.addAll(results);
  }

  public HashSet<Material> getItems() { return canCraftItems; }

  public static boolean isForbidden(Material type) { return forbiddenCrafts.contains(type); }

}
