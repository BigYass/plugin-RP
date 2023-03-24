package fr.yorkgangsta.pluginrp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.yorkgangsta.pluginrp.PluginRP;
import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import fr.yorkgangsta.pluginrp.items.SpecialItemStack;

public class CommandDebug implements CommandExecutor{

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

    if(cmd.getName().equalsIgnoreCase("pluginrp_give")){
      if (args.length <= 1) return false;

      if(!(sender instanceof Player)) return true;
      Player p = (Player) sender;

      int count = 1;

      if (args.length >= 3){
      try {
        count = Integer.parseInt(args[2]);
      } catch (NumberFormatException e) {
        p.sendMessage(PluginRP.PREFIX + "§cErreur: §6" + args[2] + " §cn'est pas un nombre");
        return true;
      }
    }

    ItemStack item = SpecialItemStack.getSpecialItemByName(args[1], count);

    if (item == null){
      p.sendMessage(PluginRP.PREFIX + "§cErreur: §6" + args[1] + " §cest introuvable");
      return true;
    }

    Player target = Bukkit.getPlayer(args[0]);

    if (target == null){
      p.sendMessage(PluginRP.PREFIX + "§cErreur : le joueur §6" + args[0] + " §cn'a pas été trouvé...");
      return true;
    }
    target.getInventory().addItem(item);
    target.updateInventory();

    p.sendMessage(PluginRP.PREFIX + "§a[" + item.getItemMeta().getDisplayName() + "§a] x" + count + " donné à " + target.getDisplayName());

    return true;
    }
    else if(cmd.getName().equalsIgnoreCase("pluginrp_enchant")){
      if (args.length < 1) return false;

      if(!(sender instanceof Player)) return true;
      Player p = (Player) sender;


      CustomEnchant ench = CustomEnchant.getCustomEnchantByName(args[0]);

      if (ench == null){
        p.sendMessage(PluginRP.PREFIX + "§cL'enchantement §6" + args[0] + " §cn'a pas été trouvé...");
        return true;
      }

      int level = ench.getStartLevel();
      if(args.length > 1){
        try {
          level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
          p.sendMessage(PluginRP.PREFIX + "§cErreur: §6" + args[1] + " §cn'est pas un nombre entier");
          return true;
        }
      }

      ItemStack item = p.getInventory().getItemInMainHand();

      if(item.getType() == Material.AIR){
        p.sendMessage(PluginRP.PREFIX + "§cErreur : Il faut un objet dans ta main sale hmar...");
        return true;
      }

      if(!ench.canEnchantItem(item)){
        p.sendMessage(PluginRP.PREFIX + "§cErreur §7: §c" + item.getType().getTranslationKey() + " ne pas être enchanté avec l'enchantement §6" + ench.getName());
        return true;
      }

      if(ench.getMaxLevel() < level) p.sendMessage(PluginRP.PREFIX + "§cAttention §7: §rLe niveau maximum est §6" + ench.getMaxLevel());
      if(ench.getStartLevel() > level) p.sendMessage(PluginRP.PREFIX + "§cAttention §7: §rLe niveau minimum est §6" + ench.getStartLevel());


      if(!CustomEnchant.addCustomEnchantment(item, ench, level)){
        p.sendMessage(PluginRP.PREFIX + "§cErreur lors de l'ajout de l'enchantement §6" + ench.getName() + "§c...");
      }

      p.getInventory().setItemInMainHand(item);
      p.updateInventory();

      return true;

    }

    return false;
  }

}
