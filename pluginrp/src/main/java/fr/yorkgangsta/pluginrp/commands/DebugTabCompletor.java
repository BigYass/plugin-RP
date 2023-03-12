package fr.yorkgangsta.pluginrp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.yorkgangsta.pluginrp.items.ItemManager;

public class DebugTabCompletor implements TabCompleter{

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
    if(!(sender instanceof Player)) return null;

    List<String> s = new ArrayList<String>();
    
    switch (cmd.getName()) {
      case "pluginrp_give":
        if(args.length == 1){

          for (Player players : Bukkit.getOnlinePlayers())
            s.add(players.getDisplayName());
        }
        else if(args.length == 2){
          for(ItemManager i : ItemManager.values()){
            s.add(i.name().toLowerCase());
          }
        }

        break;
    
      default:
        break;
    }

    return s;
  }
  
}
