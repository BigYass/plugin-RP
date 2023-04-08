package fr.yorkgangsta.metiers.jobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.potion.PotionEffect;
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
import fr.yorkgangsta.metiers.data.Catalogue;

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
    new AbillityOnSneakAttribute("OnSneakInvisibility", Ability.INVISIBILITY_ON_SNEAK),
    new BlockUseAttribute("UseEnchantingTable", Material.ENCHANTING_TABLE),
    new BlockUseAttribute("UseBrewingStand", Material.BREWING_STAND)
    ), "Alchimiste", -2.0, false, "metiers.user");

  public static final Job BLACKSMITH = new Job(Arrays.asList(
      new BlockUseAttribute("UseAnvil", Material.ANVIL),
      new BlockUseAttribute("UseChippedAnvil", Material.CHIPPED_ANVIL),
      new BlockUseAttribute("UseDamagadeAnvil", Material.DAMAGED_ANVIL),
      new BlockUseAttribute("UseGrindStone", Material.GRINDSTONE),
      new BlockUseAttribute("UseSmithingTable", Material.SMITHING_TABLE)
      ), "Forgeron", 0.0, false, "metiers.user");



  public static final Job FARMER = new Job(Arrays.asList(
    new BlockUseAttribute("UseComposter", Material.COMPOSTER),

    new ItemUseAttribute("UseBoneMeal", Material.BONE_MEAL),
    new ItemUseOnBlockAttribute("UseWoodenHoe", Material.WOODEN_HOE, Catalogue.dirtBlocks),
    new ItemUseOnBlockAttribute("UseStoneHoe", Material.STONE_HOE, Catalogue.dirtBlocks),
    new ItemUseOnBlockAttribute("UseGoldenHoe", Material.GOLDEN_HOE, Catalogue.dirtBlocks),
    new ItemUseOnBlockAttribute("UseIronHoe", Material.IRON_HOE, Catalogue.dirtBlocks),
    new ItemUseOnBlockAttribute("UseDiamondHoe", Material.DIAMOND_HOE, Catalogue.dirtBlocks),
    new ItemUseOnBlockAttribute("UseNetheriteHoe", Material.NETHERITE_HOE, Catalogue.dirtBlocks),

    new ToolUseAttribute("UseToolWoodenHoe", Material.WOODEN_HOE),
    new ToolUseAttribute("UseToolStoneHoe", Material.STONE_HOE),
    new ToolUseAttribute("UseToolGoldenHoe", Material.GOLDEN_HOE),
    new ToolUseAttribute("UseToolIronHoe", Material.IRON_HOE),
    new ToolUseAttribute("UseToolDiamondHoe", Material.DIAMOND_HOE),
    new ToolUseAttribute("UseToolNetheriteHoe", Material.NETHERITE_HOE),

    new ShearEntityAttribute("ShearSheep", EntityType.SHEEP),

    new EntityBreedAttribute("BreedSheep", EntityType.SHEEP),
    new EntityBreedAttribute("BreedCow", EntityType.COW),
    new EntityBreedAttribute("BreedMushroomCow", EntityType.MUSHROOM_COW),
    new EntityBreedAttribute("BreedChicken", EntityType.CHICKEN),
    new EntityBreedAttribute("BreedPig", EntityType.PIG),
    new EntityBreedAttribute("BreedHorse", EntityType.HORSE)
    ), "Fermier", -2.0, false, "metiers.user");

    public static final Job COOK = new Job(Arrays.asList(
    new BlockUseAttribute("UseSmoker", Material.SMOKER),
    new BlockUseAttribute("UseCampfire", Material.CAMPFIRE),
    new BlockUseAttribute("UseSoulCampfire", Material.SOUL_CAMPFIRE),

    new CraftItemAttribute("canCraftFood", Catalogue.carftedFood)
    ), "Cuistot", -0.0, false, "metiers.user");


  public static final Job MINER = new Job(Arrays.asList(
    new EffectAttribute("EffectFastMining", new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1)),
    new EffectAttribute("EffectNightVision", new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0)),


    new BlockUseAttribute("UseBlastFurnace", Material.BLAST_FURNACE),

    new ToolUseAttribute("UseDiamondPickaxe", Material.DIAMOND_PICKAXE),
    new ToolUseAttribute("UseNetheritePickaxe", Material.NETHERITE_PICKAXE),
    new ToolUseAttribute("UseDiamondAxe", Material.DIAMOND_AXE),
    new ToolUseAttribute("UseNetheriteAxe", Material.NETHERITE_AXE),
    new ToolUseAttribute("UseDiamondShovel", Material.DIAMOND_SHOVEL),
    new ToolUseAttribute("UseNetheriteShovel", Material.NETHERITE_SHOVEL)

    ), "Mineur", -2.0, false, "metiers.user");

  public static final Job WARRIOR = new Job(Arrays.asList(
    new AbillityOnSneakAttribute("OnSneakResistance", Ability.RESISTANCE_ON_SNEAK),
    new EffectAttribute("EffectNightVision", new PotionEffect(PotionEffectType.SPEED, 200, 0)),

    new WeaponUseAttribute("UseDiamondSword", Material.DIAMOND_SWORD),
    new WeaponUseAttribute("UseNetheriteSword", Material.NETHERITE_SWORD),

    new PotionEffectImmunityAttribute("immuneToWeakness", PotionEffectType.WEAKNESS)

    ), "Guerrier", +4.0, false, "metiers.user");


  public static final Job MERCHANT = new Job(Arrays.asList(
    new EntityInteractAttribute("canTradeWithVillagers", EntityType.VILLAGER),
    new EntityInteractAttribute("canTradeWithMerchant", EntityType.WANDERING_TRADER)

    ), "Marchant", +0.0, true, "metiers.user");

  public static final Job Explorer = new Job(Arrays.asList(
    new EffectAttribute("EffectNightVision", new PotionEffect(PotionEffectType.SPEED, 200, 0)),
    new BlockUseAttribute("UseCartographyTable", Material.CARTOGRAPHY_TABLE),

    new PotionEffectImmunityAttribute("immuneToPoison", PotionEffectType.POISON)

    ), "Explorateur", +0.0, false, "metiers.user");

  public static final Job REDSTONE_ENGINEER = new Job(Arrays.asList(
    new BlockUseAttribute("UseRepeater", Material.REPEATER),
    new BlockUseAttribute("UseComparator", Material.COMPARATOR),
    new BlockUseAttribute("UseDaylightDetector", Material.DAYLIGHT_DETECTOR),
    new BlockUseAttribute("UseDropper", Material.DROPPER)
    ), "Ingénieur", +0.0, false, "metiers.user");

  public static final Job BULDER = new Job(Arrays.asList(
    new BlockUseAttribute("UseStoneCutter", Material.STONECUTTER),
    new AbillityOnSneakAttribute("OnSneakSlowFalling", Ability.SLOW_FALLING_ON_SNEAK),
    new ToolUseAttribute("UseDiamondAxe", Material.DIAMOND_AXE),
    new ToolUseAttribute("UseNetheriteAxe", Material.NETHERITE_AXE),
    new ToolUseAttribute("UseDiamondShovel", Material.DIAMOND_SHOVEL),
    new ToolUseAttribute("UseNetheriteShovel", Material.NETHERITE_SHOVEL)

    ), "Builder", -2.0, false, "metiers.user");

  public static final Job MAYOR = new Job(new ArrayList<JobAttribute>(), "Maire", -4.0, false, "metiers.mayor");

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

      if (attribute instanceof BlockUseAttribute){
        blocksUsable.add(((BlockUseAttribute) attribute).getType());
      }
      else if (attribute instanceof ItemUseAttribute){
        itemUsable.add(((ItemUseAttribute) attribute).getType());
      }
      else if (attribute instanceof ItemUseOnEntityAttribute){
        itemOnEntityUsable.add(((ItemUseOnEntityAttribute) attribute).getType());
      }
      else if (attribute instanceof ItemUseOnBlockAttribute){
        ItemUseOnBlockAttribute a = (ItemUseOnBlockAttribute) attribute;
        itemOnBlockUsable.put(a.getType(), a.getForbiddenBlocks());
      }
      else if (attribute instanceof ShearEntityAttribute){
        entityShearUsable.add(((ShearEntityAttribute) attribute).getEntityType());
      }
      else if (attribute instanceof EntityInteractAttribute){
        entityAllowed.add(((EntityInteractAttribute)attribute).getEntity());
      }
      else if (attribute instanceof EntityBreedAttribute){
        entityBreedAllowed.add(((EntityBreedAttribute)attribute).getEntity());
      }
      else if (attribute instanceof ToolUseAttribute){
        toolUsable.add(((ToolUseAttribute) attribute).getType());
      }
      else if (attribute instanceof WeaponUseAttribute){
        weaponUsable.add(((WeaponUseAttribute) attribute).getType());
      }
      else if(attribute instanceof CraftItemAttribute){
        craftAllowed.addAll(((CraftItemAttribute) attribute).getItems());
      }
      else if(attribute instanceof PotionEffectImmunityAttribute){
        effectsImunity.add(((PotionEffectImmunityAttribute) attribute).getType());
      }
      else if (attribute instanceof AbillityOnSneakAttribute){
        abilities.add(((AbillityOnSneakAttribute) attribute).getAbility());
      }
      else if (attribute instanceof EffectAttribute){
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

  public boolean canUseItemOnBlock(Material type, Material block){
    return itemOnBlockUsable.containsKey(type) && itemOnBlockUsable.get(type).contains(block);
  }

  public boolean canCraftItem(Material type) {
    return craftAllowed.contains(type);
  }

  public boolean canInteractWithEntity(EntityType entity){
    return entityAllowed.contains(entity);
  }

  public boolean canShearEntity(EntityType type) {
    return entityShearUsable.contains(type);
  }

  public boolean canBreedEntity(EntityType type){
    return entityBreedAllowed.contains(type);
  }

  public boolean canUseTool(Material type){
    return toolUsable.contains(type);
  }

  public boolean canUseWeapon(Material type){
    return weaponUsable.contains(type);
  }

  public ArrayList<EffectAttribute> getEffectsAttributes() { return effects; }

  public ArrayList<Ability> getAbilities() { return abilities; }

  public Set<JobAttribute> getAttributes() { return attributes; }
  public double getHealthModifier() {
    return healthModifier;
  }

  public boolean isImmuneToPotionEffect(PotionEffectType type) { return effectsImunity.contains(type); }

  public boolean canAcessNether(){ return canAcessNether; }

  public String getPermission() { return permission; }
  public String getName() {
    return name;
  }
}
