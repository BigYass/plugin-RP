package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.entity.EntityType;

public class EntityInteractAttribute extends JobAttribute{
  private static HashSet<EntityType> forbiddenEntities = new HashSet<>();
  private final EntityType entity;

  public static final EntityInteractAttribute VILLAGER = new EntityInteractAttribute("canTradeWithVillager", EntityType.VILLAGER);
  public static final EntityInteractAttribute WANDERING_TRADER = new EntityInteractAttribute("canTradeWithMerchant", EntityType.WANDERING_TRADER);

  public EntityInteractAttribute(String name, EntityType entity) {
    super(name, "§c Permet d'interragir avec l'entité §7: §6" + entity);
    this.entity = entity;
    forbiddenEntities.add(entity);
  }

  public EntityType getEntity() { return entity; }

  public HashSet<EntityType> getForbiddenEntities() { return forbiddenEntities; }

  public static boolean isForbidden(EntityType entity) { return forbiddenEntities.contains(entity); }

}
