package fr.yorkgangsta.pluginrp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.yorkgangsta.pluginrp.Plugin;
import fr.yorkgangsta.pluginrp.items.ItemManager;

public class CommandDebug implements CommandExecutor{

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    sender.sendMessage("Commande test : " + cmd.getName());
    
    if(cmd.getName().equalsIgnoreCase("pluginrp_ping")){
      sender.sendMessage("Version : %s");
      return true;
    }
    else if (cmd.getName().equalsIgnoreCase("pluginrp_op")){
      if(!(sender instanceof Player)) return true;
      Player p = (Player) sender;
      p.sendMessage("Tu es effectivement op");
      return true;
    }
    else if(cmd.getName().equalsIgnoreCase("pluginrp_give")){
      if (args.length <= 1) return false;

      if(!(sender instanceof Player)) return true;
      Player p = (Player) sender;

      int count = 1;

      if (args.length >= 3){
      try {
        count = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
        p.sendMessage(Plugin.PREFIX + "§cErreur: §4" + args[2] + " §cn'est pas un nombre");
        return true;
      }
    }

    p.sendMessage("Objet choisis : " + args[1]);

    ItemStack item = ItemManager.getSpecialItem(args[1], count);

    if (item == null){
      p.sendMessage(Plugin.PREFIX + "§cErreur: §4" + args[1] + " §cest introuvable");
      return true;
    }

    p = Bukkit.getPlayer(args[0]);

    p.getInventory().addItem(item);
    p.updateInventory();

    p.sendMessage(Plugin.PREFIX + "§a[" + item.getItemMeta().getDisplayName() + "§a] x" + count + " donné à " + p.getDisplayName());

    return true;
    }


    return false;
  }
  
}
