package fr.yorkgangsta.metiers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.yorkgangsta.metiers.commands.MayorExecutor;
import fr.yorkgangsta.metiers.commands.MayorTabCompletor;
import fr.yorkgangsta.metiers.commands.RoleExecutor;
import fr.yorkgangsta.metiers.commands.RoleTabCompleter;
import fr.yorkgangsta.metiers.jobs.PlayerInfo;
import fr.yorkgangsta.metiers.listeners.JobAttributeListener;

/*
 * metiers java plugin
 */
public class PluginMetier extends JavaPlugin
{
  private static PluginMetier INSTANCE;

  public void onEnable()
  {
    INSTANCE = this;

    register();

    Bukkit.getLogger().info("§eMetiers enabled!");

  }

  public void onDisable()
  {
    PlayerInfo.save();
    Bukkit.getLogger().info("§cmetiers disabled");
  }

  private void register(){
    PlayerInfo.load();

    getServer().getPluginManager().registerEvents(new JobAttributeListener(), this);

    getCommand("role").setExecutor(new RoleExecutor());
    getCommand("maire").setExecutor(new MayorExecutor());

    getCommand("role").setTabCompleter(new RoleTabCompleter());
    getCommand("maire").setTabCompleter(new MayorTabCompletor());



  }

  public void reload(){
    Bukkit.getPluginManager().disablePlugin(this);
    Bukkit.getPluginManager().enablePlugin(this);
  }

  public static PluginMetier getInstance(){
    return INSTANCE;
  }
}
