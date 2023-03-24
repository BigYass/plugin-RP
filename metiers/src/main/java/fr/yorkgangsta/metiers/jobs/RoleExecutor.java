package fr.yorkgangsta.metiers.jobs;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.yorkgangsta.metiers.PluginMetier;
import fr.yorkgangsta.metiers.attributes.JobAttribute;

public class RoleExecutor implements CommandExecutor{

  public static final List<String> subCommands = Arrays.asList(new String[]{
    "change",
    "info",
    "jobinfo",
    "help",
    "reload"
});

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if(args.length >= 1){
      switch (args[0].toLowerCase()) {
        case "change":
        if (!(sender instanceof Player)) return true;

        Player p = (Player) sender;
          if(args.length < 2){
            sender.sendMessage("§c/!\\ §6Erreur syntax§7!§6 Utilsation §7: /§6role change §7<§6role§7> [§6joueur§7]");
            return true;
          }

          Job newJob = Job.getJobByName(args[1].toLowerCase());

          if(newJob == null){
            p.sendMessage("§c/!\\ §6Le role §7\"§c" + args[1] + "§7\" est introuvable.\n Voici la liste des roles : §r" + Job.getJobNames());
            return true;
          }

          Player target = p;

          if(args.length >= 3){
            target = Bukkit.getPlayer(args[2]);
            if(target == null){
              p.sendMessage("§c/!\\ §6Le joueur §7\"§c" + args[2] + "§7\" est déconnecté ou n'existe pas.");
              return true;
            }
          }

          PlayerInfo info = PlayerInfo.getInfo(target);
          info.changeJob(newJob);
          p.sendMessage("§6Le joueur §e" + target.getDisplayName() + " §6est maintenant un §e" + newJob.getName());

          return true;
        case "info":
          if(!(sender instanceof Player) && args.length < 2) return false;
          Player tp;

          if(args.length >= 2){
            tp = Bukkit.getPlayer(args[1]);
            if(tp == null){
              sender.sendMessage("§7/!\\ §cLe joueur §6" + args[1] + " §c n'est pas connecté ou n'existe pas");
              return true;
            }
          }
          else{
            tp = (Player) sender;
          }

          PlayerInfo ti = PlayerInfo.getInfo(tp);

          sender.sendMessage(new String[]{
            "§7=== §cInformation sur §7: §6" + tp.getDisplayName() + "§7 ===",
            "  §6Vie §7: §c" + tp.getHealth() + "§7/§c" + tp.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue(),
            "  §6Niveau §7: §c" + tp.getLevel() + " §7(§c" + (int)tp.getExp() + "§7/§c" + tp.getExpToLevel() + "§7)",
            "  §6Job §7: §c" + ti.getJob().getName(),
            "  §6Ping §7: §c" + tp.getPing() + "ms"
          });


          return true;

        case "jobinfo":
        if(args.length < 2){
          sender.sendMessage("§c/!\\ §6Erreur syntax§7!§6 Utilsation §7: /§6role jobinfo §7<§6role§7>");
          return true;
        }

        Job job = Job.getJobByName(args[1].toLowerCase());

        if(job == null){
          sender.sendMessage("§c/!\\ §6Le role §7\"§c" + args[1] + "§7\" est introuvable.\n Voici la liste des roles : §r" + Job.getJobNames());
          return true;
        }

        sender.sendMessage("§c=== §6Information sur la classe §e" + job.getName() +"§c ===");
        for(JobAttribute attrib : job.getAttributes()){
          sender.sendMessage("  §5" + attrib.getDescription());
        }
          return true;

      case "reload":
        PluginMetier.getInstance().reload();
        sender.sendMessage("§7[CONSOLE] >> §eLe plugin metier a redémarrer.");
        return true;
          default:
          break;
      }
    }


    sender.sendMessage(new String[]{
      "§c=== §6Aide pour les §eclasses§c ===",
      "§7/§6role change §7<§6Metier§7>§7 §7[§6Joueur§7]§7 -§c Change de metier",
      "§7/§6role info §7[§6Joueur§7]§7 -§c Donnes les informations sur un joueur",
      "§7/§6role jobinfo §7<§6Metier§7>§7 -§c Donne les informations sur un metier",
      "§7/§6role help§7 -§c Affiche cette aide",
      "§7/§6role reload§7 -§c Reload le plugin",

    });
    return true;
  }

}
