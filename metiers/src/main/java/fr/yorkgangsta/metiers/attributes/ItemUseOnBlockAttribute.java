package fr.yorkgangsta.metiers.attributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Material;

public class ItemUseOnBlockAttribute extends JobAttribute{
  private static HashMap<Material, HashSet<Material>> forbiddenBlocks = new HashMap<>();
  private final Material item;

  public ItemUseOnBlockAttribute(String name, Material item, Collection<? extends Material> blocksAllowed) {
    super(name, "§cPermet d'utiliser l'item §7: §6" + item + "§c sur certains blocks");
    forbiddenBlocks.put(item, new HashSet<>(blocksAllowed));
    this.item = item;
  }

  public Material getType() { return item; }
  public HashSet<Material> getForbiddenBlocks() { return forbiddenBlocks.get(item); }

  public static boolean isForbidden(Material type, Material block) { return forbiddenBlocks.containsKey(type) && forbiddenBlocks.get(type).contains(block); }
}
