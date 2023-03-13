package fr.yorkgangsta.pluginrp;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.yorkgangsta.pluginrp.commands.CommandDebug;
import fr.yorkgangsta.pluginrp.commands.DebugTabCompletor;
import fr.yorkgangsta.pluginrp.data.PlayerInfo;
import fr.yorkgangsta.pluginrp.listeners.DrugListener;

/*
 * pluginrp java plugin
 */
public class Plugin extends JavaPlugin
{
  public static final String PREFIX = "§6[§ePluginRP§6]§r ";

  private static final Logger LOGGER=Logger.getLogger("pluginrp");
  private static Plugin INSTANCE;


  public void onEnable()
  {
    INSTANCE = this;

    getCommand("pluginrp_ping").setExecutor(new CommandDebug());
    getCommand("pluginrp_op").setExecutor(new CommandDebug());
    getCommand("pluginrp_give").setExecutor(new CommandDebug());

    getCommand("pluginrp_give").setTabCompleter(new DebugTabCompletor());

    getServer().getPluginManager().registerEvents(new DrugListener(), this);

    PlayerInfo.startTask();

    for(Player p : Bukkit.getOnlinePlayers()){
      p.setMaxHealth(20);
      p.setHealth(20);

      new PlayerInfo(p);
    }

    LOGGER.info("PluginRP Lance! v" + getDescription().getVersion());
  }

  public void onDisable()
  {
    LOGGER.info("PluginRP éteint!");
  }

  public String getVersion(){
    return getDescription().getVersion();
  }

  public static Plugin getInstance(){
    return INSTANCE;
  }
 
}
