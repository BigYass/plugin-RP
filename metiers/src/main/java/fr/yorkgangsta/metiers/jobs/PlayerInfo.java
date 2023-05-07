package fr.yorkgangsta.metiers.jobs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yorkgangsta.metiers.PluginMetier;
import fr.yorkgangsta.metiers.ability.Ability;
import fr.yorkgangsta.metiers.attributes.EffectAttribute;
import fr.yorkgangsta.metiers.attributes.PotionEffectRunnable;
import fr.yorkgangsta.pluginrp.PluginRP;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerInfo {
  private static HashMap<UUID, PlayerInfo> playersInfo = new HashMap<>();
  private static HashMap<UUID, PlayerSave> savedInfos = new HashMap<>();


  private ArrayList<BukkitRunnable> jobEffects = new ArrayList<>();
  private ArrayList<Ability> sneakAbilities = new ArrayList<>();


  private final UUID player_id;
  private Job currentJob = Job.NO_JOB;

  private PlayerInfo(Player p){
    this.currentJob = Job.NO_JOB;
    playersInfo.put(p.getUniqueId(), this);
    player_id = p.getUniqueId();

    if(!savedInfos.containsKey(p.getUniqueId())){
      savedInfos.put(p.getUniqueId(), new PlayerSave(this));
    }

    setModifiers();
  }

  public PlayerInfo(Player p, PlayerSave save){
    this.currentJob = save.getJob();
    playersInfo.put(p.getUniqueId(), this);
    player_id = p.getUniqueId();
    setModifiers();
  }


  public static Job getJob(Player p){ return getInfo(p).getJob(); }

  public static PlayerInfo getInfo(Player p) {
    if(playersInfo.containsKey(p.getUniqueId()))
      return playersInfo.get(p.getUniqueId());
    else if(savedInfos.containsKey(p.getUniqueId()))
      return new PlayerInfo(p, savedInfos.get(p.getUniqueId()));
    else
      return new PlayerInfo(p);
  }

  public static void load(){
    File file = new File(PluginMetier.getInstance().getDataFolder(), "playersInfo.yml");

    YamlConfiguration configuration = new YamlConfiguration();

    try {
      configuration.load(file);
    } catch (Exception e) {
      e.printStackTrace();
    }

    for(String name : configuration.getKeys(false)){
      UUID id = UUID.fromString(name);
      OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(id);

      if(offlinePlayer == null){
        PluginRP.getInstance().getLogger().log(Level.WARNING, "L'id {" + id + "} est introuvable dans la base de donnée");
        continue;
      }

      ConfigurationSection playerInfo = configuration.getConfigurationSection(name);

      String jobname = playerInfo.getString("job");

      if(jobname == null){
        PluginRP.getInstance().getLogger().log(Level.WARNING, "La section de donnée ne contien pas la sous-categorie job ?");
        continue;
      }


      Job job = Job.getJobByName(jobname);

      if(job == null){
        PluginRP.getInstance().getLogger().log(Level.WARNING, "(§c" + offlinePlayer.getName() + "§r) Le job §6" + jobname + " n'a pas été trouvé...\nListe des job : §c" + Job.getJobNames());
        continue;
      }

      savedInfos.put(id, new PlayerSave(job));

    }
  }

  public static void save(){
    for(Map.Entry<UUID, PlayerInfo> entry : playersInfo.entrySet()){
      UUID id = entry.getKey();

      PlayerInfo info = entry.getValue();

      if(savedInfos.containsKey(id)){
        savedInfos.replace(id, new PlayerSave(info));
      }
      else {
        savedInfos.put(id, new PlayerSave(info));
      }
    }

    File file = new File(PluginMetier.getInstance().getDataFolder(), "playersInfo.yml");

    YamlConfiguration configuration = new YamlConfiguration();
    for(Map.Entry<UUID,PlayerSave> entry : savedInfos.entrySet()){
      configuration.set(entry.getKey().toString(), entry.getValue().toConfig());
    }

    try {
      configuration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected ConfigurationSection toConfig(){
    ConfigurationSection config = new YamlConfiguration();

    config.set("job", this.getJob().getName());

    return config;
  }

  public void changeJob(Job newJob) {
    Player player  = Bukkit.getPlayer(player_id);

    player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.RECORDS, 2.0f, 1.0f);
    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Tu es maintenant un " + ChatColor.GOLD + newJob.getName()));
    player.sendMessage(ChatColor.GREEN + "Tu es devenu un " + ChatColor.GOLD + newJob.getName() + "§7!");

    player.addAttachment(PluginMetier.getInstance(), currentJob.getPermission(), false);

    currentJob = newJob;
    player.addAttachment(PluginMetier.getInstance(), newJob.getPermission(), true);

    player.recalculatePermissions();
    player.updateCommands();

    setModifiers();
  }
  public void setModifiers(){
    final String healthModifierName = "roleHealthModifier";

    Player player = Bukkit.getPlayer(player_id);

    AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

    for(AttributeModifier am : maxHealth.getModifiers()){
      if(am.getName().equals(healthModifierName))
        maxHealth.removeModifier(am);
    }

    maxHealth.addModifier(
      new AttributeModifier(healthModifierName, currentJob.getHealthModifier(), Operation.ADD_NUMBER)
    );

    player.setHealth(maxHealth.getValue());

    for(BukkitRunnable runnable : jobEffects){
      runnable.cancel();
    }

    for(EffectAttribute attribute : currentJob.getEffectsAttributes()){
      PotionEffect eff = attribute.getPotionEffect();
      if(eff == null) break;
      PotionEffectRunnable runnable = new PotionEffectRunnable(eff.getType(), eff.getAmplifier(), player);
      jobEffects.add(runnable);
      runnable.runTaskTimer(PluginMetier.getInstance(), 0, 40);
    }

    sneakAbilities.clear();
    for(Ability ability : currentJob.getAbilities()){
      sneakAbilities.add(ability);
    }

    player.addAttachment(PluginMetier.getInstance(), getJob().getPermission(), true);

    player.recalculatePermissions();
    player.updateCommands();

  }
  public Job getJob() { return currentJob; }

  public void sneakEvent() {
    Player player = Bukkit.getPlayer(player_id);

    for(Ability ability : sneakAbilities)
      ability.launchAbility(player);
  }

}
