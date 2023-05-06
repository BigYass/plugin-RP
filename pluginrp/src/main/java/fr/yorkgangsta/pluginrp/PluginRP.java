package fr.yorkgangsta.pluginrp;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.yorkgangsta.pluginrp.commands.CommandDebug;
import fr.yorkgangsta.pluginrp.commands.DebugTabCompletor;
import fr.yorkgangsta.pluginrp.data.PlayerInfo;
import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import fr.yorkgangsta.pluginrp.items.RecipeManager;
import fr.yorkgangsta.pluginrp.listeners.DrugListener;

/*
 * pluginrp java plugin
 */
public class PluginRP extends JavaPlugin
{
  public static final String PREFIX = "§6[§cPluginRP§6]§r ";

  private static final Logger LOGGER=Logger.getLogger("pluginrp");
  private static PluginRP INSTANCE;


  public void onEnable()
  {
    INSTANCE = this;

    getCommand("pluginrp_give").setExecutor(new CommandDebug());
    getCommand("pluginrp_enchant").setExecutor(new CommandDebug());
    getCommand("pluginrp_teleport").setExecutor(new CommandDebug());


    getCommand("pluginrp_give").setTabCompleter(new DebugTabCompletor());
    getCommand("pluginrp_enchant").setTabCompleter(new DebugTabCompletor());
    getCommand("pluginrp_teleport").setTabCompleter(new DebugTabCompletor());


    getServer().getPluginManager().registerEvents(new DrugListener(), this);

    CustomEnchant.register();

    PlayerInfo.startTask();

    RecipeManager.register();

    LOGGER.info("PluginRP Lance! v" + getDescription().getVersion());
  }

  public void onDisable()
  {
    for(Player p : Bukkit.getOnlinePlayers()){
      PlayerInfo.resetHealth(p);
    }

    CustomEnchant.unregister();

    LOGGER.info("PluginRP eteint!");
  }

  public String getVersion(){
    return getDescription().getVersion();
  }

  public static PluginRP getInstance(){
    return INSTANCE;
  }

}
