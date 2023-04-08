package fr.yorkgangsta.metiers.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import fr.yorkgangsta.metiers.jobs.Job;
import fr.yorkgangsta.pluginrp.items.SpecialItemStack;

public class MayorTabCompletor implements TabCompleter{

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> s = new ArrayList<>();
    if(args.length == 1){
      for(String cmd : MayorExecutor.subCommands)
        if(cmd.startsWith(args[0].toLowerCase()))
          s.add(cmd);
    }

    else if(args.length == 2){
      switch (args[0].toLowerCase()) {
        case "changerole":
          for(String name : Job.getJobNames())
            if(name.toLowerCase().startsWith(args[1], 0))
              s.add(name);
          break;
        case "imprime":
        for(SpecialItemStack item : MayorExecutor.printable)
          if(item.getNameID().toLowerCase().startsWith(args[1], 0))
            s.add(item.getNameID());
          break;
        default:
          break;
      }
    }

    else if(args.length == 3){
      switch (args[0].toLowerCase()) {
        case "changerole":
        for(Player p : Bukkit.getOnlinePlayers())
          if(p.getName().toLowerCase().startsWith(args[2].toLowerCase(), 0)) s.add(p.getName());
          break;
        default:
          break;
      }

    }

    return s;
  }

}
