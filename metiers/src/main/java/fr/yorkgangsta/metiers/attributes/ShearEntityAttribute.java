package fr.yorkgangsta.metiers.attributes;

import java.util.HashSet;

import org.bukkit.entity.EntityType;

public class ShearEntityAttribute extends JobAttribute{
  private static HashSet<EntityType> forbiddenItems = new HashSet<>();
  private final EntityType entityType; 

  public ShearEntityAttribute(String name, EntityType entityType) {
    super(name, "§cPermet de tondre l'entité §7: §6" + entityType);
    forbiddenItems.add(entityType);
    this.entityType = entityType;
  }

  public EntityType getEntityType() { return entityType; }
  public static boolean isForbidden(EntityType type) { return forbiddenItems.contains(type); }
}
