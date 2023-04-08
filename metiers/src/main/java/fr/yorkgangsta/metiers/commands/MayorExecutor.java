package fr.yorkgangsta.metiers.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.yorkgangsta.metiers.data.Catalogue;
import fr.yorkgangsta.metiers.jobs.Job;
import fr.yorkgangsta.metiers.jobs.PlayerInfo;
import fr.yorkgangsta.pluginrp.items.SpecialItemStack;

public class MayorExecutor implements CommandExecutor{

  public static final List<String> subCommands = Arrays.asList(new String[]{
    "rename",
    "changerole",
    "imprime",
    "help"
  });

  public static final List<SpecialItemStack> printable = Arrays.asList(
    SpecialItemStack.ONE_DOLLAR,
    SpecialItemStack.TEN_DOLLARS,
    SpecialItemStack.HUNDREAD_DOLLARS
  );



  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if(sender.hasPermission("metiers.mayor") && sender.isPermissionSet("metiers.mayor")){
      if(!(sender instanceof Player)) return true;

      Player mayor = (Player) sender;

      if(args.length >= 1){
        if(args[0].equalsIgnoreCase("changerole")){
          if(args.length < 3){
            sender.sendMessage("§c/!\\ §6Erreur syntax§7!§6 Utilsation §7: /§6maire changerole §7<§6role§7> <§6joueur§7>");
            return true;
          }

          Job newJob = Job.getJobByName(args[1].toLowerCase());

          if(newJob == null){
            sender.sendMessage("§c/!\\ §6Le role §7\"§c" + args[1] + "§7\" est introuvable.");
            return true;
          }

          Player target = Bukkit.getPlayer(args[2]);
          if(target == null){
            sender.sendMessage("§c/!\\ §6Le joueur §7\"§c" + args[2] + "§7\" est déconnecté ou n'existe pas.");
            return true;
          }

          if(target == mayor){
            sender.sendMessage("§c/!\\ §6Vous ne pouvez pas changer votre propre role... Mettez quelqu'un d'autre Maire pour perdre le votre");
            return true;
          }

          if(target.getLocation().distanceSquared(mayor.getLocation()) > 250){
            sender.sendMessage("§c/!\\ §6Vous ne pouvez pas changer le rôle de §c" + target.getDisplayName() + "§6. La cible est trop loin");
            return true;
          }

          final int cost = 10;

          if(mayor.getLevel() < cost){
            sender.sendMessage("§c/!\\ §6Vous n'avez pas assez d'expérience pour changer le rôle d'un joueur. Il faut être au moins niveau §c" + cost + " §6pour faire cette commande");
            return true;
          }

          mayor.setLevel(mayor.getLevel() - cost);
          PlayerInfo info = PlayerInfo.getInfo(target);
          info.changeJob(newJob);
          sender.sendMessage("§6Le joueur §e" + target.getDisplayName() + " §6est maintenant un §e" + newJob.getName());

          Job mayorJob = PlayerInfo.getJob(mayor);

          if(mayorJob == newJob){
            PlayerInfo mayorInfo = PlayerInfo.getInfo(mayor);
            mayorInfo.changeJob(Job.getDefaultJob());
            sender.sendMessage("§6Vous avez perdu votre rôle");
          }

          return true;
        }

        if(args[0].equalsIgnoreCase("rename")){
          if(args.length < 2){
            sender.sendMessage("§c/!\\ §6Erreur syntax§7!§6 Utilsation §7/§6maire rename §7<§6Nouveau nom§7>");
            return true;
          }

          String new_name = "";

          for(int i = 1; i < args.length; i++){
            if(i != 1)
              new_name += " ";
            new_name += args[i];
          }

          ItemStack item = mayor.getInventory().getItemInMainHand();

          final int cost = 1;

          if(mayor.getLevel() < cost){
            sender.sendMessage("§c/!\\ §6Vous n'avez pas assez d'expérience pour changer le nom. Il faut être au moins niveau §c" + cost + " §6pour faire cette commande");
            return true;
          }

          if(item.getType() == Material.AIR){
            sender.sendMessage("§c/!\\ §7!§6 Il vous faut un objet en main pour le renommer");
            return true;
          }

          if(!Catalogue.books.contains(item.getType())){
            sender.sendMessage("§c/!\\ §7!§6 Vous ne pouvez pas renommer cette item");
            return true;
          }


          ItemMeta meta = item.getItemMeta();

          if(meta == null){
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
            if(meta == null){
              sender.sendMessage("§c/!\\ §7!§6 Les métadonné de l'item " + item.getType() + " est inaccessible. Contacte BigYass avant que je t'encule");
              return true;
            }
          }

          meta.setDisplayName(new_name.replace("&", "§"));

          item.setItemMeta(meta);

          mayor.getInventory().setItemInMainHand(item);
          mayor.playSound(mayor, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.AMBIENT, 1.0f, 1.0f);

          mayor.updateInventory();

          mayor.setLevel(mayor.getLevel() - cost);

          return true;
        }

        if(args[0].equalsIgnoreCase("imprime")){
            if(args.length < 2){
              sender.sendMessage("§c/!\\ §6Erreur syntax§7!§6 Utilsation §7: /§6maire imprime §7<§6valeur§7> [§6quantité§7]");
              return true;
            }

            ItemStack result = SpecialItemStack.getSpecialItemByName(args[1], 1);


            if(result == null){
              sender.sendMessage("§c/!\\ §6Erreur§7!§c " + args[1] + " §6est introuvable");
              return true;
            }

            if(!printable.contains(result)){
              sender.sendMessage("§c/!\\ §6Erreur§7!§c " + args[1] + " §6est introuvable! Etes vous sur d'avoir bien écrit ?");
              return true;
            }

            int quantity = 1;

            if(args.length >= 3){
              try {
                quantity = Integer.parseInt(args[2]);
              } catch (Exception e) {
                sender.sendMessage("§c/!\\ §6Erreur§7!§c " + args[2] + " §6n'est pas nombre entier");
                e.printStackTrace();
                return true;
              }
            }

            ItemStack item = mayor.getInventory().getItemInMainHand();

            if(item.getType() == Material.AIR){
              sender.sendMessage("§c/!\\ §7!§6 Il vous faut un objet en main pour imprimer dessus");
              return true;
            }

            if(item.getType() != result.getType()){
              sender.sendMessage("§c/!\\ §6Erreur§7!§6 Vous ne pouvez pas imprimez sur cet objet!");
              return true;
            }

            if(item.isSimilar(result)){
              sender.sendMessage("§c/!\\ §cIl n'est pas très judicieux d'imprimer la même chose");
              return true;
            }

            quantity = Math.min(item.getAmount(), quantity);

            final int cost = Math.max(1, (int)(quantity * .2));

            if(mayor.getLevel() < cost){
              sender.sendMessage("§c/!\\ §6Erreur§7!§6 Pour imprimer une quantité de §c" + quantity + "§7, §6Il vous faut §c" + cost + " §6niveaux");
              return true;
            }

            if(mayor.getInventory().firstEmpty() == -1){
              sender.sendMessage("§c/!\\ §6Erreur§7!§6 Ton inventaire est plein!");
              return true;
            }

            mayor.setLevel(mayor.getLevel() - cost);

            item.setAmount(item.getAmount() - quantity);

            result.setAmount(quantity);

            mayor.getInventory().addItem(result);

            mayor.getInventory().setItemInMainHand(item);

            mayor.updateInventory();

            sender.sendMessage("§7(§cMaire§7) §cImpression de §6x" + quantity + " §cpour le prix de §6" + cost + " §cniveaux");

            return true;
          }

      }
      sender.sendMessage(new String[]{
        "§c=== §6Aide pour les commandes de §emaire§c ===",
        "§7/§6maire change §7<§6Metier§7>§7 §7<§6Joueur§7>§7 -§c Change le metier d'un joueur",
        "§7/§6maire rename §7<§6Nouveau nom§7> -§c Change le nom d'un livre dans votre main",
        "§7/§6maire imprime §7<§6Impression§7> -§c Imprime des choses",
        "§7/§6role help§7 -§c Affiche cette aide"
      });

      return true;
    }
    return false;
  }
}
