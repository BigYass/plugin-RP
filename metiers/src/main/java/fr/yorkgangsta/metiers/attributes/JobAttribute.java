package fr.yorkgangsta.metiers.attributes;

import java.util.HashMap;

import org.bukkit.Bukkit;

public abstract class JobAttribute {
  private final static HashMap<String, JobAttribute> attributes = new HashMap<>();

  private final String name;
  private final String description;


  public JobAttribute(String name, String description){
    this.description = description;
    this.name = name;

    if(attributes.containsKey(name)){
      Bukkit.getLogger().warning("There is multiple instance of " + name);
    }
    attributes.put(name, this);
  }

  public JobAttribute(String name){
    this.description = "Pas de description...";
    this.name = name;
    attributes.put(name, this);
  }
  public String getName() { return name; }
  public String getDescription() { return description; }

  public static JobAttribute JobAttributeByName(String name) { return attributes.get(name); }
}
