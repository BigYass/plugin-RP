package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class ItemUseOnEntityAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenItems = new HashSet<>();
  private final Material item; 

  public ItemUseOnEntityAttribute(String name, Material item) {
    super(name, "§cPermet d'utiliser l'item §7: §6" + item);
    forbiddenItems.add(item);
    this.item = item;
  }

  public Material getType() { return item; }
  public static boolean isForbidden(Material type) { return forbiddenItems.contains(type); }
}
