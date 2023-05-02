package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class ToolUseAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenTools = new HashSet<>();
  private final Material type;

  public static final ToolUseAttribute WOODEN_HOE = new ToolUseAttribute("UseToolWoodenHoe", Material.WOODEN_HOE);
  public static final ToolUseAttribute STONE_HOE = new ToolUseAttribute("UseToolStoneHoe", Material.STONE_HOE);
  public static final ToolUseAttribute GOLDEN_HOE = new ToolUseAttribute("UseToolGoldenHoe", Material.GOLDEN_HOE);
  public static final ToolUseAttribute IRON_HOE = new ToolUseAttribute("UseToolIronHoe", Material.IRON_HOE);
  public static final ToolUseAttribute DIAMOND_HOE = new ToolUseAttribute("UseToolDiamondHoe", Material.DIAMOND_HOE);
  public static final ToolUseAttribute NETHERITE_HOE = new ToolUseAttribute("UseToolNetheriteHoe", Material.NETHERITE_HOE);

  public static final ToolUseAttribute DIAMOND_PICKAXE = new ToolUseAttribute("UseDiamondPickaxe", Material.DIAMOND_PICKAXE);
  public static final ToolUseAttribute NETHERITE_PICKAXE = new ToolUseAttribute("UseNetheritePickaxe", Material.NETHERITE_PICKAXE);
  public static final ToolUseAttribute DIAMOND_AXE = new ToolUseAttribute("UseDiamondAxe", Material.DIAMOND_AXE);
  public static final ToolUseAttribute NETHERITE_AXE = new ToolUseAttribute("UseNetheriteAxe", Material.NETHERITE_AXE);
  public static final ToolUseAttribute DIAMOND_SHOVEL = new ToolUseAttribute("UseDiamondShovel", Material.DIAMOND_SHOVEL);
  public static final ToolUseAttribute NETHERITE_SHOVEL = new ToolUseAttribute("UseNetheriteShovel", Material.NETHERITE_SHOVEL);

  public ToolUseAttribute(String name, Material type){
    super(name, "§cPermet d'utiliser l'outil §7: §6" + type);
    this.type = type;
    forbiddenTools.add(type);
  }

  public Material getType() { return type; }
  public static boolean isForbidden(Material type) { return forbiddenTools.contains(type); }
}
