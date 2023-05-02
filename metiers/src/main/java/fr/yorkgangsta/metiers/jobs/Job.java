package fr.yorkgangsta.metiers.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

import fr.yorkgangsta.metiers.ability.Ability;
import fr.yorkgangsta.metiers.attributes.AbillityOnSneakAttribute;
import fr.yorkgangsta.metiers.attributes.BlockUseAttribute;
import fr.yorkgangsta.metiers.attributes.CraftItemAttribute;
import fr.yorkgangsta.metiers.attributes.EffectAttribute;
import fr.yorkgangsta.metiers.attributes.EntityBreedAttribute;
import fr.yorkgangsta.metiers.attributes.EntityInteractAttribute;
import fr.yorkgangsta.metiers.attributes.ItemUseAttribute;
import fr.yorkgangsta.metiers.attributes.ItemUseOnBlockAttribute;
import fr.yorkgangsta.metiers.attributes.ItemUseOnEntityAttribute;
import fr.yorkgangsta.metiers.attributes.JobAttribute;
import fr.yorkgangsta.metiers.attributes.PotionEffectImmunityAttribute;
import fr.yorkgangsta.metiers.attributes.ShearEntityAttribute;
import fr.yorkgangsta.metiers.attributes.ToolUseAttribute;
import fr.yorkgangsta.metiers.attributes.WeaponUseAttribute;

public class Job {
  private static HashMap<String, Job> jobs = new HashMap<>();

  private Set<JobAttribute> attributes = new HashSet<>();
  private HashSet<Material> blocksUsable = new HashSet<>();
  private HashSet<Material> itemUsable = new HashSet<>();
  private HashSet<Material> itemOnEntityUsable = new HashSet<>();
  private HashMap<Material, HashSet<Material>> itemOnBlockUsable = new HashMap<>();
  private HashSet<EntityType> entityAllowed = new HashSet<>();
  private HashSet<EntityType> entityBreedAllowed = new HashSet<>();
  private HashSet<Material> craftAllowed = new HashSet<>();

  private HashSet<EntityType> entityShearUsable = new HashSet<>();
  private HashSet<Material> toolUsable = new HashSet<>();
  private HashSet<Material> weaponUsable = new HashSet<>();

  private HashSet<PotionEffectType> effectsImunity = new HashSet<>();
  private ArrayList<EffectAttribute> effects = new ArrayList<>();
  private ArrayList<Ability> abilities = new ArrayList<>();

  private final boolean canAcessNether;
  private final double healthModifier;
  private final String name;

  private final String permission;

  public static final Job ALCHIMISTE = new Job(Arrays.asList(
      AbillityOnSneakAttribute.INVISIBILITY,
      BlockUseAttribute.ENCHANTING_TABLE,
      BlockUseAttribute.BREWING_STAND), "Alchimiste", -2.0, false, "metiers.user");

  public static final Job BLACKSMITH = new Job(Arrays.asList(
      BlockUseAttribute.ANVIL,
      BlockUseAttribute.CHIPPED_ANVIL,
      BlockUseAttribute.DAMAGED_ANVIL,
      BlockUseAttribute.GRINDSTONE,
      BlockUseAttribute.SMITHING_TABLE), "Forgeron", 0.0, false, "metiers.user");

  public static final Job FARMER = new Job(Arrays.asList(
      BlockUseAttribute.COMPOSTER,

      ItemUseAttribute.BONE_MEAL,

      ItemUseOnBlockAttribute.WOODEN_HOE_ON_DIRT,
      ItemUseOnBlockAttribute.STONE_HOE_ON_DIRT,
      ItemUseOnBlockAttribute.GOLDEN_HOE_ON_DIRT,
      ItemUseOnBlockAttribute.IRON_HOE_ON_DIRT,
      ItemUseOnBlockAttribute.DIAMOND_HOE_ON_DIRT,
      ItemUseOnBlockAttribute.NETHERITE_HOE_ON_DIRT,

      ToolUseAttribute.WOODEN_HOE,
      ToolUseAttribute.STONE_HOE,
      ToolUseAttribute.GOLDEN_HOE,
      ToolUseAttribute.IRON_HOE,
      ToolUseAttribute.DIAMOND_HOE,
      ToolUseAttribute.NETHERITE_HOE,

      ShearEntityAttribute.SHEEP,

      EntityBreedAttribute.SHEEP,
      EntityBreedAttribute.COW,
      EntityBreedAttribute.MUSHROOM_COW,
      EntityBreedAttribute.CHICKEN,
      EntityBreedAttribute.PIG,
      EntityBreedAttribute.HORSE), "Fermier", -2.0, false, "metiers.user");

  public static final Job COOK = new Job(Arrays.asList(
      BlockUseAttribute.SMOKER,
      BlockUseAttribute.CAMPFIRE,
      BlockUseAttribute.SOUL_CAMPFIRE,

      CraftItemAttribute.FOOD), "Cuistot", -0.0, false, "metiers.user");

  public static final Job MINER = new Job(Arrays.asList(
      EffectAttribute.FAST_DIGGING_ATTRIBUTE,
      EffectAttribute.NIGHT_VISION_ATTRIBUTE,

      BlockUseAttribute.BLAST_FURNACE,

      ToolUseAttribute.DIAMOND_PICKAXE,
      ToolUseAttribute.NETHERITE_PICKAXE,
      ToolUseAttribute.DIAMOND_AXE,
      ToolUseAttribute.NETHERITE_AXE,
      ToolUseAttribute.DIAMOND_SHOVEL,
      ToolUseAttribute.NETHERITE_SHOVEL

  ), "Mineur", -2.0, false, "metiers.user");

  public static final Job WARRIOR = new Job(Arrays.asList(
      AbillityOnSneakAttribute.RESISTANCE,
      EffectAttribute.SPEED_ATTRIBUTE,

      WeaponUseAttribute.DIAMOND_SWORD,
      WeaponUseAttribute.NETHERITE_SWORD,

      PotionEffectImmunityAttribute.WEAKNESS

  ), "Guerrier", +4.0, false, "metiers.user");

  public static final Job MERCHANT = new Job(Arrays.asList(
      EntityInteractAttribute.VILLAGER,
      EntityInteractAttribute.WANDERING_TRADER

  ), "Marchant", +0.0, true, "metiers.user");

  public static final Job Explorer = new Job(Arrays.asList(
      EffectAttribute.SPEED_ATTRIBUTE,
      BlockUseAttribute.CARTOGRAPHY_TABLE,

      PotionEffectImmunityAttribute.POISON

  ), "Explorateur", +0.0, false, "metiers.user");

  public static final Job BULDER = new Job(Arrays.asList(
      BlockUseAttribute.STONECUTTER,

      EffectAttribute.FAST_DIGGING_ATTRIBUTE,

      AbillityOnSneakAttribute.SLOW_FALLING,

      ToolUseAttribute.DIAMOND_AXE,
      ToolUseAttribute.NETHERITE_AXE,
      ToolUseAttribute.DIAMOND_PICKAXE,

      ToolUseAttribute.DIAMOND_SHOVEL,
      ToolUseAttribute.NETHERITE_SHOVEL

  ), "Builder", -2.0, false, "metiers.user");

  public static final Job MAYOR = new Job(new ArrayList<JobAttribute>(), "Maire", -4.0, true, "metiers.mayor");

  public static final Job NO_JOB = new Job(new ArrayList<JobAttribute>(), "Chomeur", -4.0, false, "metiers.user");

  protected Job(Collection<? extends JobAttribute> attributes, String name,
      double healthModifier, boolean canAcessNether, String permission) {
    jobs.put(name.toLowerCase(), this);

    this.name = name;
    this.permission = permission;
    this.healthModifier = healthModifier;
    this.canAcessNether = canAcessNether;

    for (JobAttribute attribute : attributes) {
      this.attributes.add(attribute);

      if (attribute instanceof BlockUseAttribute) {
        blocksUsable.add(((BlockUseAttribute) attribute).getType());
      } else if (attribute instanceof ItemUseAttribute) {
        itemUsable.add(((ItemUseAttribute) attribute).getType());
      } else if (attribute instanceof ItemUseOnEntityAttribute) {
        itemOnEntityUsable.add(((ItemUseOnEntityAttribute) attribute).getType());
      } else if (attribute instanceof ItemUseOnBlockAttribute) {
        ItemUseOnBlockAttribute a = (ItemUseOnBlockAttribute) attribute;
        itemOnBlockUsable.put(a.getType(), a.getForbiddenBlocks());
      } else if (attribute instanceof ShearEntityAttribute) {
        entityShearUsable.add(((ShearEntityAttribute) attribute).getEntityType());
      } else if (attribute instanceof EntityInteractAttribute) {
        entityAllowed.add(((EntityInteractAttribute) attribute).getEntity());
      } else if (attribute instanceof EntityBreedAttribute) {
        entityBreedAllowed.add(((EntityBreedAttribute) attribute).getEntity());
      } else if (attribute instanceof ToolUseAttribute) {
        toolUsable.add(((ToolUseAttribute) attribute).getType());
      } else if (attribute instanceof WeaponUseAttribute) {
        weaponUsable.add(((WeaponUseAttribute) attribute).getType());
      } else if (attribute instanceof CraftItemAttribute) {
        craftAllowed.addAll(((CraftItemAttribute) attribute).getItems());
      } else if (attribute instanceof PotionEffectImmunityAttribute) {
        effectsImunity.add(((PotionEffectImmunityAttribute) attribute).getType());
      } else if (attribute instanceof AbillityOnSneakAttribute) {
        abilities.add(((AbillityOnSneakAttribute) attribute).getAbility());
      } else if (attribute instanceof EffectAttribute) {
        effects.add((EffectAttribute) attribute);
      }

    }

  }

  public static Job getJobByName(String name) {
    return jobs.get(name.toLowerCase());
  }

  public static Job getDefaultJob() {
    return NO_JOB;
  }

  public static Set<String> getJobNames() {
    return jobs.keySet();
  }

  public boolean canUseBlock(Material type) {
    return blocksUsable.contains(type);
  }

  public boolean canUseItem(Material type) {
    return itemUsable.contains(type);
  }

  public boolean canUseItemOnEntity(Material type) {
    return itemOnEntityUsable.contains(type);
  }

  public boolean canUseItemOnBlock(Material type, Material block) {
    return itemOnBlockUsable.containsKey(type) && itemOnBlockUsable.get(type).contains(block);
  }

  public boolean canCraftItem(Material type) {
    return craftAllowed.contains(type);
  }

  public boolean canInteractWithEntity(EntityType entity) {
    return entityAllowed.contains(entity);
  }

  public boolean canShearEntity(EntityType type) {
    return entityShearUsable.contains(type);
  }

  public boolean canBreedEntity(EntityType type) {
    return entityBreedAllowed.contains(type);
  }

  public boolean canUseTool(Material type) {
    return toolUsable.contains(type);
  }

  public boolean canUseWeapon(Material type) {
    return weaponUsable.contains(type);
  }

  public ArrayList<EffectAttribute> getEffectsAttributes() {
    return effects;
  }

  public ArrayList<Ability> getAbilities() {
    return abilities;
  }

  public Set<JobAttribute> getAttributes() {
    return attributes;
  }

  public double getHealthModifier() {
    return healthModifier;
  }

  public boolean isImmuneToPotionEffect(PotionEffectType type) {
    return effectsImunity.contains(type);
  }

  public boolean canAcessNether() {
    return canAcessNether;
  }

  public String getPermission() {
    return permission;
  }

  public String getName() {
    return name;
  }
}
