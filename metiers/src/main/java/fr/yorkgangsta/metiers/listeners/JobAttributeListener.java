package fr.yorkgangsta.metiers.listeners;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.yorkgangsta.metiers.PluginMetier;
import fr.yorkgangsta.metiers.attributes.BlockUseAttribute;
import fr.yorkgangsta.metiers.attributes.CraftItemAttribute;
import fr.yorkgangsta.metiers.attributes.EntityBreedAttribute;
import fr.yorkgangsta.metiers.attributes.EntityInteractAttribute;
import fr.yorkgangsta.metiers.attributes.ItemUseAttribute;
import fr.yorkgangsta.metiers.attributes.ItemUseOnBlockAttribute;
import fr.yorkgangsta.metiers.attributes.ItemUseOnEntityAttribute;
import fr.yorkgangsta.metiers.attributes.ShearEntityAttribute;
import fr.yorkgangsta.metiers.attributes.ToolUseAttribute;
import fr.yorkgangsta.metiers.attributes.WeaponUseAttribute;
import fr.yorkgangsta.metiers.data.Catalogue;
import fr.yorkgangsta.metiers.jobs.Job;
import fr.yorkgangsta.metiers.jobs.PlayerInfo;
import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class JobAttributeListener implements Listener{

  @EventHandler
  public void onPlayerUseBlock(PlayerInteractEvent event){
    if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasBlock()){
      Player p = event.getPlayer();
      Material blockType = event.getClickedBlock().getType();
      if(BlockUseAttribute.isForbidden(blockType) && p.getGameMode() != GameMode.CREATIVE){
        Job job = PlayerInfo.getJob(p);
        if(!job.canUseBlock(blockType) && !(p.isSneaking() && event.isBlockInHand())){
          p.playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, .8f, .5f + (float)Math.random() * .4f);
          p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas utiliser ce block..."));
          event.setCancelled(true);
        }
      }
    }
  }

  @EventHandler
  public void onPlayerUseItem(PlayerInteractEvent event){
    if(!event.isBlockInHand() && event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
      Player p = event.getPlayer();
      ItemStack item = event.getItem();

      Job job = PlayerInfo.getJob(p);
      if(p.getGameMode() != GameMode.CREATIVE && ItemUseAttribute.isForbidden(item.getType()) && !job.canUseItem(item.getType())){
        p.playSound(p.getLocation(), Sound.ITEM_AXE_STRIP, SoundCategory.BLOCKS, .7f, .7f);
        event.setCancelled(true);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas utiliser cet objet..."));
      }
    }
  }

  @EventHandler
  public void onPlayerUseItemOnEntity(PlayerInteractAtEntityEvent event){
    EquipmentSlot slot = event.getHand();
    Player p = event.getPlayer();
    if(!(slot == EquipmentSlot.HAND || slot == EquipmentSlot.OFF_HAND)) return;

    ItemStack item = null;

    if(slot == EquipmentSlot.HAND)
      item = p.getInventory().getItemInMainHand();
    else if(slot == EquipmentSlot.OFF_HAND)
      item = p.getInventory().getItemInOffHand();

    Job job = PlayerInfo.getJob(p);

    if(item != null && ItemUseOnEntityAttribute.isForbidden(item.getType()) && job.canUseItemOnEntity(item.getType())){
      p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas utiliser cet objet sur cette entité..."));
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerInteractOnEntity(PlayerInteractEntityEvent event){
    if(event.isCancelled()) return;
    Player p = event.getPlayer();

    Job job = PlayerInfo.getJob(p);

    Entity e = event.getRightClicked();

    if(p.getGameMode() != GameMode.CREATIVE &&  EntityInteractAttribute.isForbidden(e.getType()) && !job.canInteractWithEntity(e.getType())){
      p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas communiquer avec cette entité..."));
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerBreedEntity(EntityBreedEvent event){
    if(event.getBreeder() instanceof Player){
      Player p = (Player)event.getBreeder();

      Job job = PlayerInfo.getJob(p);

      if(p.getGameMode() != GameMode.CREATIVE && EntityBreedAttribute.isForbidden(event.getEntityType()) && !job.canBreedEntity(event.getEntityType())){
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas comment nourir ces animaux proprement..."));
        event.setCancelled(true);
      }
    }
  }

  @EventHandler
  public void onPlayerUseItemOnBlock(PlayerInteractEvent event){
    Player p = event.getPlayer();

    if(event.getAction() != Action.RIGHT_CLICK_BLOCK || !event.hasBlock())
      return;

    ItemStack item = event.getItem();
    Block block = event.getClickedBlock();

    Job job = PlayerInfo.getJob(p);

    if(p.getGameMode() != GameMode.CREATIVE && item != null && ItemUseOnBlockAttribute.isForbidden(item.getType(), block.getType()) && !job.canUseItemOnBlock(item.getType(), block.getType())){
      p.playSound(p.getLocation(), Sound.ITEM_AXE_STRIP, SoundCategory.BLOCKS, .7f, .7f);
      p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas utiliser cet outil sur ce block..."));
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerShearEntity(PlayerShearEntityEvent event){
    Player p = event.getPlayer();
    Job job = PlayerInfo.getJob(p);
    if(p.getGameMode() != GameMode.CREATIVE && ShearEntityAttribute.isForbidden(event.getEntity().getType()) && !(job.canShearEntity(event.getEntity().getType()))){
      p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas tondre cette entité..."));
      p.playSound(event.getEntity().getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, .8f, .5f + (float)Math.random() * .4f);
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerUseTool(PlayerInteractEvent event){
    if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;

    Player p = event.getPlayer();
    ItemStack item = event.getItem();

    Job job = PlayerInfo.getJob(p);

    if(item != null && p.getGameMode() != GameMode.CREATIVE && ToolUseAttribute.isForbidden(item.getType()) && !job.canUseTool(item.getType())){
      event.setCancelled(true);
      p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas utiliser cet outil..."));
    }
  }


  @EventHandler
  public void onPlayerUsePortal(final PlayerPortalEvent event){
    Player p = event.getPlayer();
    Job job = PlayerInfo.getJob(p);
    if ((p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) && event.getTo().getWorld().getName().endsWith("nether") && !job.canAcessNether()){
        for(ItemStack paper : p.getInventory().getStorageContents()){
          if(paper != null && paper.hasItemMeta() && paper.getItemMeta().hasEnchant(CustomEnchant.NETHER_PASS)){
            paper.setAmount(paper.getAmount() - 1);

            final UUID id = p.getUniqueId();

            BukkitRunnable netherCost = new BukkitRunnable() {
              boolean teleport = false;
              @Override
              public void run() {
                Player p = Bukkit.getPlayer(id);
                final Location loc = event.getFrom();

                if(!p.getWorld().getName().endsWith("nether")) {cancel();return;}

                for(ItemStack paper : p.getInventory().getStorageContents())
                  if(paper != null && paper.hasItemMeta() && paper.getItemMeta().hasEnchant(CustomEnchant.NETHER_PASS)){
                    paper.setAmount(paper.getAmount() - 1);
                    return;
                  }

                  if(teleport){
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je suis explusé du nether"));
                    BukkitRunnable tpBack = new BukkitRunnable() {
                      Player p = Bukkit.getPlayer(id);
                      public void run(){
                        p.teleport(loc);
                        cancel();
                      }
                    };
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 2));
                    p.playSound(p, Sound.BLOCK_PORTAL_TRIGGER, SoundCategory.AMBIENT, 2.0f, .8f + (float)(Math.random() * .4));
                    tpBack.runTaskLater(PluginMetier.getInstance(), 80);
                    cancel();
                    return;
                  }
                  teleport = true;

                  p.playSound(p, Sound.ENTITY_SKELETON_AMBIENT, SoundCategory.AMBIENT, 2.0f, .5f);
                  p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai plus de ticket" + ChatColor.GRAY + " (téléportation dans 1 minute)"));

                }
            };

            final int duration = 5 * 60;

            netherCost.runTaskTimer(PluginMetier.getInstance(), 60, duration * 20);

            return;

          }
      }
      p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je n'ai pas d'autorisation pour accéder à cette dimension..."));
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event){
    PlayerInfo.getJob(event.getPlayer());
  }

  @EventHandler void onHopperMoveItem(InventoryMoveItemEvent event){
    if (event.getSource().getType() == InventoryType.HOPPER && event.getDestination().getHolder() instanceof BlockState && BlockUseAttribute.isForbidden(((BlockState) event.getDestination().getHolder()).getType())){
      event.setCancelled(true);
    }
  }

  @EventHandler void onSneaking(PlayerToggleSneakEvent event){
    Player p = event.getPlayer();
    if(!p.isSneaking()){
      PlayerInfo info = PlayerInfo.getInfo(p);
      info.sneakEvent();
    }
  }

  @EventHandler
  private void onPotionEffect(EntityPotionEffectEvent event){
    if(event.getEntity() instanceof Player && event.getAction() == EntityPotionEffectEvent.Action.ADDED){
      Job job = PlayerInfo.getJob((Player)event.getEntity());

      event.setCancelled(job.isImmuneToPotionEffect(event.getModifiedType()));
    }
  }



  @EventHandler
  private void onStartSmelting(FurnaceSmeltEvent event){
    Material type = event.getResult().getType();

    if (event.getBlock().getType() != Material.SMOKER && Catalogue.cookedFood.contains(type)){
      ItemStack new_result = new ItemStack(event.getSource().getType(), 1);
      event.setResult(new_result);
    }
  }

  @EventHandler
  private void onCraftPrepare(PrepareItemCraftEvent event){
    if(event.getRecipe() == null || event.getRecipe().getResult() == null) return;

    Material type = event.getRecipe().getResult().getType();


    if(CraftItemAttribute.isForbidden(type)){

      for(HumanEntity h : event.getViewers()){
        if(h instanceof Player){
          Player p = (Player)h;

          Job job = PlayerInfo.getJob(p);

          if(job.canCraftItem(type) || p.getGameMode() == GameMode.CREATIVE){
            return;
          }
        }
      }
      event.getInventory().setResult(new ItemStack(Material.AIR));
    }
  }

  @EventHandler
  private void onEntityDie(EntityDeathEvent event){

    List<ItemStack> drops = event.getDrops();

    for(int i = 0; i < drops.size(); i++){
      ItemStack item = drops.get(i);
      if(Catalogue.cookedFoodToRaw.containsKey(item.getType())){
        drops.set(i, new ItemStack(Catalogue.cookedFoodToRaw.get(item.getType()), item.getAmount()));
      }
    }
  }

  @EventHandler
  private void onBlockDestroy(BlockDropItemEvent event){
    if(event.isCancelled()) return;
    Bukkit.broadcastMessage("Block cassé");
    if(event.getPlayer() == null || !(Catalogue.hoes.contains(event.getPlayer().getInventory().getItemInMainHand().getType())) ){
      for(int i = 0; i < event.getItems().size(); i++){
        Item item = event.getItems().get(i);
        if(Catalogue.cropsResults.contains(item.getItemStack().getType())){
          event.getItems().clear();
          if(event.getPlayer() != null){
            event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Il faudrait une houe pour récolter correctement"));
          }
          break;
        }
      }
    }
  }

  @EventHandler
  private void onPlayerDeath (EntityDamageEvent event){
    if(event.isCancelled()) return;
    if(!(event.getEntity() instanceof Player)) return;

    Player p = (Player)event.getEntity();

    if(p.getHealth() > event.getFinalDamage()) return;

    if(p.getGameMode() == GameMode.SPECTATOR){
      event.setCancelled(true);
      return;
    }

    for(ItemStack i : p.getInventory().getContents()){
      if(i != null){
        p.getWorld().dropItemNaturally(p.getLocation(), i);
        p.getInventory().remove(i);
      }
    }

    p.getInventory().clear();

    int total_xp = Math.min(100, p.getTotalExperience());

    p.getWorld().spawn(p.getLocation(), ExperienceOrb.class).setExperience(total_xp);

    p.setLevel(0);
    p.setExp(.0f);

    p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    p.setFoodLevel((int)(p.getFoodLevel() * .7));

    p.setArrowsInBody(0);
    p.setTicksLived(1);

    p.sendTitle("", ChatColor.DARK_RED + "TU ES MORT...", 0, 70, 20);

    p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_DEATH, 1.0f, 1.0f);
    p.playSound(p.getLocation(), Sound.ITEM_TRIDENT_THUNDER, SoundCategory.PLAYERS, 1.0f, .5f);
    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 0));
    p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation().add(0, 1, 0), 40, .3, .5, .3, .2);

    final GameMode gameMode = p.getGameMode();

    final UUID id = p.getUniqueId();

    final Location deathLocation = p.getLocation();
    p.setGameMode(GameMode.SPECTATOR);

    PlayerInfo info = PlayerInfo.getInfo(p);

    int timeLived = p.getTicksLived() / 1200;
    final int cooldown = 5 + (10 * info.getRecentDeaths());

    info.setRecentDeaths(Math.max(0, info.getRecentDeaths() - ((timeLived - 10) / 10)));


    BukkitRunnable respawn = new BukkitRunnable() {
      private int i = cooldown;
      @Override
      public void run() {
        Player p = Bukkit.getPlayer(id);
        final PlayerInfo info = PlayerInfo.getInfo(p);
          if(p.isOnline()) {
          if(i-- <= 0){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "Je suis mort " + ChatColor.GOLD + info.getRecentDeaths() + ChatColor.DARK_RED + " fois récemment..."));
            p.teleport(p.getBedSpawnLocation() != null ? p.getBedSpawnLocation() : p.getWorld().getSpawnLocation());

            p.getWorld().playSound(p.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_FALL, 1.0f, .2f);
            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0));
            p.getWorld().spawnParticle(Particle.SQUID_INK, p.getLocation().add(0, 1, 0), 40, .3, .5, .3, .2);

            p.setGameMode(gameMode);

            final int index = info.getRecentDeaths() - 2;
            BukkitRunnable deathCurse = new BukkitRunnable() {
              final int maxTick = 6000;
              @Override
              public void run() {
                Player p = Bukkit.getPlayer(id);

                if(p.isOnline()){
                  if(p.getTicksLived() > maxTick){

                    cancel();
                  }
                  else {

                      PotionEffect eff = Catalogue.badEffects.get(index);
                      p.addPotionEffect(new PotionEffect(eff.getType(), maxTick - p.getTicksLived(), eff.getAmplifier(), eff.isAmbient(), eff.hasParticles(), eff.hasIcon()));

                  }
                }
              }

            };
            if(index >= 0 && index < Catalogue.badEffects.size())
              deathCurse.runTaskTimer(PluginMetier.getInstance(), 0, 400);
            cancel();
          }
          else {

          if(p.getLocation().distanceSquared(deathLocation) > 1000){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "Vous êtes trop éloigné"));
            p.playSound(p.getLocation(), Sound.ENTITY_ALLAY_DEATH, SoundCategory.PLAYERS, 1.0f, .3f);

            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
            Vector new_vel = deathLocation.toVector().subtract(p.getLocation().toVector()).normalize().multiply(3);
            p.setVelocity(p.getVelocity().add(new_vel));

          }
          else {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED + "Réapparition dans " + ChatColor.GOLD + i + ChatColor.DARK_RED + " secondes"));
          }
        }
        }
      }

    };
    event.setCancelled(true);
    if(respawn != null)
      respawn.runTaskTimer(PluginMetier.getInstance(), 0, 20);
  }

  @EventHandler
  private void onPlayerShootBow (EntityShootBowEvent event){
    if(event.isCancelled()) return;

    if(!(event.getEntity() instanceof Player))return;

    Player p = (Player)event.getEntity();

    p.setCooldown(event.getBow().getType(), 100);
  }

  // @EventHandler
  // private void onPlayerDisconnect(PlayerQuitEvent event){
  //   Player p = event.getPlayer();

  // }

  @EventHandler
  private void onPlayerHurt(EntityDamageByEntityEvent event){
    if(event.isCancelled()) return;
    if(!(event.getEntity() instanceof Player)) return;

    if(event.getCause() == DamageCause.ENTITY_EXPLOSION && event.getDamager().getType() == EntityType.FIREWORK){
      event.setDamage(event.getDamage() * .4);
    }
  }

  @EventHandler
  private void onPlayerHitAnEntity(EntityDamageByEntityEvent event){
    if(event.getDamager() instanceof Player){
      Player p = (Player) event.getDamager();
      ItemStack item = p.getInventory().getItemInMainHand();

      Job job = PlayerInfo.getJob(p);

      if(item != null && p.getGameMode() != GameMode.CREATIVE && WeaponUseAttribute.isForbidden(item.getType()) && !job.canUseWeapon(item.getType())){
        event.setCancelled(true);
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Je ne sais pas utiliser cette arme..."));
      }
    }
  }
}
