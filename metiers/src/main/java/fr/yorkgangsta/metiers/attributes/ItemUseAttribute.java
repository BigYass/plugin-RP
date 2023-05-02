package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class ItemUseAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenItems = new HashSet<>();
  private final Material item;

  public static final ItemUseAttribute BONE_MEAL = new ItemUseAttribute("UseBoneMeal", Material.BONE_MEAL);

  public ItemUseAttribute(String name, Material item) {
    super(name, "§cPermet d'utiliser l'objet §7: §6" + item);
    forbiddenItems.add(item);
    this.item = item;
  }

  public Material getType() { return item; }
  public static boolean isForbidden(Material type) { return forbiddenItems.contains(type); }

}
