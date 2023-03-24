package fr.yorkgangsta.pluginrp.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import fr.yorkgangsta.pluginrp.items.ItemManager;

public class DebugTabCompletor implements TabCompleter{

  @Override
  public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
    if(!(sender instanceof Player)) return null;

    List<String> s = new ArrayList<String>();
    
    switch (cmd.getName()) {
      case "pluginrp_give":
        if(args.length == 1){

          for (Player p : Bukkit.getOnlinePlayers())
            if(p.getName().toLowerCase().startsWith(args[0].toLowerCase())) s.add(p.getName());
        }
        else if(args.length == 2){
          for(ItemManager i : ItemManager.values()){
            if (i.name().toLowerCase().startsWith(args[1].toLowerCase()))s.add(i.name().toUpperCase().substring(0, 1) + i.name().toLowerCase().substring(1).toLowerCase());
          }
        }

        break;
    
      case "pluginrp_enchant":
        if(args.length == 1){
          for(CustomEnchant e : CustomEnchant.getAllCustomEnchants()){
            s.add(e.getName());
          }
        }
        break;

      default:
        break;
    }

    return s;
  }
  
}
