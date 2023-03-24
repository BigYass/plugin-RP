package fr.yorkgangsta.metiers.attributes;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import fr.yorkgangsta.metiers.jobs.Job;
import fr.yorkgangsta.metiers.jobs.PlayerInfo;
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
  public void onPlayerUsePortal(PlayerPortalEvent event){
    Player p = event.getPlayer();
    Job job = PlayerInfo.getJob(p);
    if ((p.getGameMode() != GameMode.CREATIVE && p.getGameMode() != GameMode.SPECTATOR) && event.getTo().getWorld().getName().endsWith("nether") && !job.canAcessNether()){
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
}
