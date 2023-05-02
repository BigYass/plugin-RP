package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.entity.EntityType;

public class EntityBreedAttribute extends JobAttribute{
  private static final HashSet<EntityType> forbiddenEntities = new HashSet<>();
  private final EntityType entity;

  public final static EntityBreedAttribute SHEEP = new EntityBreedAttribute("BreedSheep", EntityType.SHEEP);
  public final static EntityBreedAttribute COW = new EntityBreedAttribute("BreedCow", EntityType.COW);
  public final static EntityBreedAttribute MUSHROOM_COW = new EntityBreedAttribute("BreedMushroomCow", EntityType.MUSHROOM_COW);
  public final static EntityBreedAttribute CHICKEN = new EntityBreedAttribute("BreedChicken", EntityType.CHICKEN);
  public final static EntityBreedAttribute PIG = new EntityBreedAttribute("BreedPig", EntityType.PIG);
  public final static EntityBreedAttribute HORSE = new EntityBreedAttribute("BreedHorse", EntityType.HORSE);

  public EntityBreedAttribute(String name, EntityType entity) {
    super(name, "§cPermet d'accoupler l'entité : §6" + entity);
    forbiddenEntities.add(entity);
    this.entity = entity;
  }

  public HashSet<EntityType> getForbiddenEntities() { return forbiddenEntities; }

  public static boolean isForbidden(EntityType entity) { return forbiddenEntities.contains(entity); }

  public EntityType getEntity() { return entity; }
}
