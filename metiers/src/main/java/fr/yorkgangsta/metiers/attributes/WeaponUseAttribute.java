package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class WeaponUseAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenWeapons = new HashSet<>();
  private final Material type;

  public static final WeaponUseAttribute DIAMOND_SWORD = new WeaponUseAttribute("UseDiamondSword", Material.DIAMOND_SWORD);
  public static final WeaponUseAttribute NETHERITE_SWORD = new WeaponUseAttribute("UseNetheriteSword", Material.NETHERITE_SWORD);



  public WeaponUseAttribute(String name, Material type){
    super(name, "§cPermet d'utiliser l'arme §7: §6" + type);
    this.type = type;
    forbiddenWeapons.add(type);
  }

  public Material getType() { return type; }
  public static boolean isForbidden(Material type) { return forbiddenWeapons.contains(type); }
}
