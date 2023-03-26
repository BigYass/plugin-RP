package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.entity.EntityType;

public class EntityBreedAttribute extends JobAttribute{
  private static final HashSet<EntityType> forbiddenEntities = new HashSet<>();
  private final EntityType entity;

  public EntityBreedAttribute(String name, EntityType entity) {
    super(name, "§cPermet d'accoupler l'entité : §6" + entity);
    forbiddenEntities.add(entity);
    this.entity = entity;
  }

  public HashSet<EntityType> getForbiddenEntities() { return forbiddenEntities; }

  public static boolean isForbidden(EntityType entity) { return forbiddenEntities.contains(entity); }

  public EntityType getEntity() { return entity; }
}
