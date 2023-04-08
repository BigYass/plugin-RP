package fr.yorkgangsta.metiers.commands;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.yorkgangsta.metiers.jobs.PlayerInfo;

public class UserExecutor implements CommandExecutor{

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] argv) {
    if(cmd.getName().equalsIgnoreCase("ping")){
      if(!(sender instanceof Player)) return true;

      Player player = (Player) sender;

      PlayerInfo info = PlayerInfo.getInfo(player);

      sender.sendMessage(new String[]{
        "§7=== §cInformation sur §7: §6" + player.getDisplayName() + "§7 ===",
        "  §6Niveau §7: §c" + player.getLevel() + " §7(§c" + (int)player.getExp() + "§7/§c" + player.getExpToLevel() + "§7)",
        "  §6Job §7: §c" + info.getJob().getName(),
        "  §6Ping §7: §c" + player.getPing() + "ms",
        "  §6Expérience total §7: §c" + player.getTotalExperience() + "ms",
      });

      return true;
    }

    return false;
  }

}
