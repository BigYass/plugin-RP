package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class BlockUseAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenBlocks = new HashSet<>();
  private final Material block;

  public BlockUseAttribute(String name, Material type){
    super(name, "§cPermet d'utiliser le block §7: §6" + type);
    block = type;
    forbiddenBlocks.add(type);
  }

  public Material getType() { return block; }
  public static boolean isForbidden(Material type) { return forbiddenBlocks.contains(type); }
}
