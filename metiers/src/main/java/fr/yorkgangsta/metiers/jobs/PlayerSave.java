package fr.yorkgangsta.metiers.jobs;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerSave {
  private final Job job;

  public PlayerSave(PlayerInfo info){
    this.job = info.getJob();
  }

  public PlayerSave(Job job){
    this.job = job;
  }

  public ConfigurationSection toConfig(){
    ConfigurationSection config = new YamlConfiguration();

    config.set("job", this.getJob().getName());

    return config;
  }

  public Job getJob() { return job; }
}
