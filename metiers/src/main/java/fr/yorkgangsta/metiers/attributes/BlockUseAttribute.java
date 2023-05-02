package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.Material;

public class BlockUseAttribute extends JobAttribute{
  private static HashSet<Material> forbiddenBlocks = new HashSet<>();
  private final Material block;

  public static final BlockUseAttribute ENCHANTING_TABLE = new BlockUseAttribute("UseEnchantingTable", Material.ENCHANTING_TABLE);

  public static final BlockUseAttribute BREWING_STAND = new BlockUseAttribute("UseBrewingStand", Material.BREWING_STAND);

  public static final BlockUseAttribute ANVIL = new BlockUseAttribute("UseAnvil", Material.ANVIL);

  public static final BlockUseAttribute CHIPPED_ANVIL = new BlockUseAttribute("UseChippedAnvil", Material.CHIPPED_ANVIL);

  public static final BlockUseAttribute DAMAGED_ANVIL = new BlockUseAttribute("UseDamagadeAnvil", Material.DAMAGED_ANVIL);

  public static final BlockUseAttribute GRINDSTONE = new BlockUseAttribute("UseGrindStone", Material.GRINDSTONE);

  public static final BlockUseAttribute SMITHING_TABLE = new BlockUseAttribute("UseSmithingTable", Material.SMITHING_TABLE);

  public static final BlockUseAttribute COMPOSTER = new BlockUseAttribute("UseComposter", Material.COMPOSTER);

  public static final BlockUseAttribute SMOKER = new BlockUseAttribute("UseSmoker", Material.SMOKER);

  public static final BlockUseAttribute CAMPFIRE = new BlockUseAttribute("UseCampfire", Material.CAMPFIRE);

  public static final BlockUseAttribute SOUL_CAMPFIRE = new BlockUseAttribute("UseSoulCampfire", Material.SOUL_CAMPFIRE);

  public static final BlockUseAttribute BLAST_FURNACE = new BlockUseAttribute("UseBlastFurnace", Material.BLAST_FURNACE);

  public static final BlockUseAttribute CARTOGRAPHY_TABLE = new BlockUseAttribute("UseCartographyTable", Material.CARTOGRAPHY_TABLE);

  public static final BlockUseAttribute STONECUTTER = new BlockUseAttribute("UseStoneCutter", Material.STONECUTTER);

  public BlockUseAttribute(String name, Material type){
    super(name, "§cPermet d'utiliser le block §7: §6" + type);
    block = type;
    forbiddenBlocks.add(type);
  }

  public Material getType() { return block; }
  public static boolean isForbidden(Material type) { return forbiddenBlocks.contains(type); }
}
