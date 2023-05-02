package fr.yorkgangsta.metiers.data;

import java.io.File;

import fr.yorkgangsta.metiers.PluginMetier;

public class Language {
  private static final String LANG_FOLDER = "lang";

  public static File getLangFolder() {
    return new File(PluginMetier.getInstance().getDataFolder(), LANG_FOLDER);
  }

  private static String getLanguageFileName(String lang){
    return "lang" + ".yml";
  }

  public static String getLanguageFilePath(String lang){
    return LANG_FOLDER + "/" + getLanguageFileName(lang);
  }

  public static String getDefaultLanguageFilePath() {
		return getLanguageFilePath(Config.lang);
	}
}
