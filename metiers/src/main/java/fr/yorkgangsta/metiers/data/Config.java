package fr.yorkgangsta.metiers.data;

import org.bukkit.configuration.file.FileConfiguration;

import fr.yorkgangsta.metiers.PluginMetier;

public class Config {
  private final static FileConfiguration config = PluginMetier.getInstance().getConfig();
  private static final String DEFAULT_LANGUAGE = "fr_FR";

  public static String lang = DEFAULT_LANGUAGE;

  public static void load(){
    lang = config.getString("lang");
  }
}
