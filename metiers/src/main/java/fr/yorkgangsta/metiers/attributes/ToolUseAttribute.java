package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class ToolUseAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenTools = new HashSet<>();
  private final Material type;


  public ToolUseAttribute(String name, Material type){
    super(name, "§cPermet d'utiliser l'outil §7: §6" + type.getItemTranslationKey());
    this.type = type;
    forbiddenTools.add(type);
  }

  public Material getType() { return type; }
  public static boolean isForbidden(Material type) { return forbiddenTools.contains(type); }
}
