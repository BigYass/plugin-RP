package fr.yorkgangsta.metiers.attributes;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.Material;

import fr.yorkgangsta.metiers.data.Catalogue;

public class ItemUseOnBlockAttribute extends JobAttribute{
  private static HashMap<Material, HashSet<Material>> forbiddenBlocks = new HashMap<>();
  private final Material item;

  public static final ItemUseOnBlockAttribute WOODEN_HOE_ON_DIRT = new ItemUseOnBlockAttribute("UseWoodenHoe", Material.WOODEN_HOE, Catalogue.dirtBlocks);
  public static final ItemUseOnBlockAttribute STONE_HOE_ON_DIRT = new ItemUseOnBlockAttribute("UseStoneHoe", Material.STONE_HOE, Catalogue.dirtBlocks);
  public static final ItemUseOnBlockAttribute GOLDEN_HOE_ON_DIRT = new ItemUseOnBlockAttribute("UseGoldenHoe", Material.GOLDEN_HOE, Catalogue.dirtBlocks);
  public static final ItemUseOnBlockAttribute IRON_HOE_ON_DIRT = new ItemUseOnBlockAttribute("UseIronHoe", Material.IRON_HOE, Catalogue.dirtBlocks);
  public static final ItemUseOnBlockAttribute DIAMOND_HOE_ON_DIRT = new ItemUseOnBlockAttribute("UseDiamondHoe", Material.DIAMOND_HOE, Catalogue.dirtBlocks);
  public static final ItemUseOnBlockAttribute NETHERITE_HOE_ON_DIRT = new ItemUseOnBlockAttribute("UseNetheriteHoe", Material.NETHERITE_HOE, Catalogue.dirtBlocks);

  public ItemUseOnBlockAttribute(String name, Material item, Collection<? extends Material> blocksAllowed) {
    super(name, "§cPermet d'utiliser l'item §7: §6" + item + "§c sur certains blocks");
    forbiddenBlocks.put(item, new HashSet<>(blocksAllowed));
    this.item = item;
  }

  public Material getType() { return item; }
  public HashSet<Material> getForbiddenBlocks() { return forbiddenBlocks.get(item); }

  public static boolean isForbidden(Material type, Material block) { return forbiddenBlocks.containsKey(type) && forbiddenBlocks.get(type).contains(block); }
}
