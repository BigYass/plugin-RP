package fr.yorkgangsta.pluginrp.listeners;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.yorkgangsta.pluginrp.PluginRP;
import fr.yorkgangsta.pluginrp.data.Catalogue;
import fr.yorkgangsta.pluginrp.data.PlayerInfo;
import fr.yorkgangsta.pluginrp.enchants.CustomEnchant;
import fr.yorkgangsta.pluginrp.items.RecipeManager;
import fr.yorkgangsta.pluginrp.items.SpecialItemStack;
import net.md_5.bungee.api.ChatColor;

public class DrugListener implements Listener{

  @EventHandler
  public void onConsume(PlayerItemConsumeEvent event){
    if(event.isCancelled()) return;
    final Player p = event.getPlayer();
    ItemStack item = event.getItem();

    if(item.getItemMeta().hasEnchant(CustomEnchant.WEED)){
      final int level = item.getItemMeta().getEnchantLevel(CustomEnchant.WEED);
      p.damage(0.5 + ((level - 1) * 3));

      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, level * 20 * 20, level - 1, true, false, true));
      p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10 + (20 * 10 * (level - 1)), 0, false, false, false));

      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, 2f, .8f);

      Vector direction = p.getEyeLocation().getDirection().normalize();
      p.spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, p.getEyeLocation().add(direction.multiply(.2)), 10, .05, .1, .05, .03);



      BukkitRunnable task = new BukkitRunnable() {
        int i = 0;

        final int max = level * 20 * 20;

        final double prob_noise = .07 + (level - 1) * .2;
        final double prob_damage = .03 + (level - 1) * .1;
        @Override
        public void run() {

          if(Math.random() < prob_noise) p.playSound(p.getLocation(), Catalogue.getRandomScaryNoise(), SoundCategory.PLAYERS, 1.0f, 1.0f);
          if(Math.random() < prob_damage) p.damage(0.0 + (level - 1) * 1.0);

          i++;
          if (i >= max){
            cancel();
          }
        }
      };

      task.runTaskTimer(PluginRP.getInstance(), 20, 20);
    }
    if(item.getItemMeta().hasEnchant(CustomEnchant.ALCOHOLIC))
      PlayerInfo.applyDrunk(p, item.getItemMeta().getEnchantLevel(CustomEnchant.ALCOHOLIC) * 6);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event){
    final Player p = event.getPlayer();

    String message = ChatColor.YELLOW + Catalogue.getRandomFromList(Catalogue.JOIN_MESSAGES).replace("{}", p.getDisplayName());

    event.setJoinMessage(message);

    if(p.hasPlayedBefore()) return;

    Firework firework = (Firework)p.getPlayer().getWorld().spawnEntity(p.getLocation().add(0, 3, 0), EntityType.FIREWORK);

    final FireworkEffect effect = FireworkEffect.builder()
            .withColor(Color.GREEN, Color.RED, Color.BLUE, Color.WHITE)
            .withFade(Color.YELLOW)
            .with(FireworkEffect.Type.STAR)
            .flicker(true)
            .trail(true)
            .build();

    final FireworkMeta meta = firework.getFireworkMeta();

    meta.setPower(2);

    meta.addEffect(effect);

    event.setJoinMessage("§6" + p.getDisplayName() + " a rejoint pour la première fois");

    BukkitRunnable run = new BukkitRunnable() {
      @Override
      public void run() {
        final Firework fw = (Firework)p.getPlayer().getWorld().spawnEntity(p.getLocation().add(3, 3, 3), EntityType.FIREWORK);
        final Firework fw2 = (Firework)p.getPlayer().getWorld().spawnEntity(p.getLocation().add(3, 3, -3), EntityType.FIREWORK);
        final Firework fw3 = (Firework)p.getPlayer().getWorld().spawnEntity(p.getLocation().add(-3, 3, 3), EntityType.FIREWORK);
        final Firework fw4 = (Firework)p.getPlayer().getWorld().spawnEntity(p.getLocation().add(-3, 3, -3), EntityType.FIREWORK);

        fw.setFireworkMeta(meta);
        fw2.setFireworkMeta(meta);
        fw3.setFireworkMeta(meta);
        fw4.setFireworkMeta(meta);

        fw.detonate();
        fw2.detonate();
        fw3.detonate();
        fw4.detonate();
      }
    };
    run.runTaskLater(PluginRP.getInstance(), 20);
  }


  @EventHandler
  public void onPlayerSneak(PlayerToggleSneakEvent event){
    Player p = event.getPlayer();
    if(p.getName().equalsIgnoreCase("YorkGangsta"))
      p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PUFFER_FISH_FLOP, 2.0f, 1.0f);
    if(p.getName().equalsIgnoreCase("Mikokodu94"))
      p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2.0f, .8f + (float)(Math.random() * .4f));
    if(p.getName().equalsIgnoreCase("Oxdavik"))
      p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PUFFER_FISH_FLOP, 2.0f, .8f + (float)(Math.random() * .4f));

  }

  @EventHandler
  public void onPlayerInterract(PlayerInteractEvent event){
    if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
      final Player p = event.getPlayer();
      ItemStack item = event.getItem();
      if(item == null || item.getItemMeta() == null) return;
      if (item.getItemMeta().hasEnchant(CustomEnchant.COKE) && p.getCooldown(item.getType()) == 0){
        if(p.getGameMode() != GameMode.CREATIVE)
          item.setAmount(item.getAmount() - 1);

        p.setCooldown(item.getType(), 30 * 20);

        PlayerInfo.applyCoke(p, 1500);

      }
      else if(item.getItemMeta().hasEnchant(CustomEnchant.WEED)){
        int food = p.getFoodLevel();
        if (food >= 20){
          p.setFoodLevel(19);
          final BukkitRunnable reset = new BukkitRunnable() {
            @Override
            public void run() {
              p.setFoodLevel(Math.min(p.getFoodLevel() + 1, 20));
            }
          };

          reset.runTaskLater(PluginRP.getInstance(), 20);
        }

      }
      else if(item.getType() == Material.GLASS_BOTTLE){
        if(p.isSneaking() && p.getTotalExperience() >= 10 && p.getCooldown(Material.GLASS_BOTTLE) == 0){
          if(p.getGameMode() != GameMode.CREATIVE) item.setAmount(item.getAmount() - 1);
          ItemStack bottleExp = new ItemStack(Material.EXPERIENCE_BOTTLE);

          p.playSound(p.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0f, .8f + ((float)Math.random() * .4f));

          if(p.getGameMode() != GameMode.CREATIVE) p.giveExp(-10);

          p.setCooldown(Material.GLASS_BOTTLE, 7);

          p.getInventory().addItem(bottleExp);
          p.updateInventory();
        }

      }
    }
  }

  @EventHandler
  public void onPlayerDie(PlayerDeathEvent event){
    if(!(event.getEntity() instanceof Player)) return;
    Player p = (Player)event.getEntity();

    PlayerInfo info = PlayerInfo.getPlayerInfo(p);

    info.setAlcoolLevel(0);
  }

  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
    //if(event.isCancelled()) return;
    ItemStack item = null;

    if(event.getDamager() instanceof Player){
      Player p = (Player) event.getDamager();
      item = p.getInventory().getItemInMainHand();
    }
    else if(event.getDamager() instanceof LivingEntity){
      item = ((LivingEntity)event.getDamager()).getEquipment().getItemInMainHand();
    }

    if(item != null && item.hasItemMeta() && event.getCause() == DamageCause.ENTITY_ATTACK){
      if(item.getItemMeta().hasEnchant(CustomEnchant.DISCIPLINE) && (event.getEntity() instanceof Player)){
        Player victim = (Player) event.getEntity();
        item.getItemMeta().removeEnchant(CustomEnchant.DISCIPLINE);
        victim.kickPlayer("sex");
        return;
      }
      if(item.getItemMeta().hasEnchant(CustomEnchant.FROST_ASPECT)){
        Entity e = event.getEntity();

        final int level = item.getItemMeta().getEnchantLevel(CustomEnchant.FROST_ASPECT);

        if(Math.random() > .25 + (level * .25)) return;

        e.setFreezeTicks(200 + 60 * level);
        e.getWorld().playSound(e.getLocation(), Sound.ENTITY_PLAYER_HURT_FREEZE, 1.0f, 1.0f);
        e.getWorld().spawnParticle(Particle.SNOWFLAKE, e.getLocation().add(0, 1.1, 0), 10, .25, .25, .25, .05);
      }
      if(item.getItemMeta().hasEnchant(CustomEnchant.POISON_ASPECT)){
          if(!(event.getEntity() instanceof LivingEntity)) return;
          LivingEntity e = (LivingEntity) event.getEntity();

          e.getWorld().playSound(e.getLocation(), Sound.ENTITY_PLAYER_HURT_SWEET_BERRY_BUSH, 1.0f, 1.0f);
          int level = item.getItemMeta().getEnchantLevel(CustomEnchant.POISON_ASPECT);
          int duration = (level * 3) + 3;
          e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, duration * 20, 0));
      }
      if(item.getItemMeta().hasEnchant(CustomEnchant.LIFESTEAL)){
        if(!(event.getDamager() instanceof LivingEntity)) return;
        LivingEntity e = (LivingEntity)event.getDamager();


        double maxDamage = Integer.MAX_VALUE;

        if(event.getEntity() instanceof Damageable)
        maxDamage = ((Damageable)event.getEntity()).getHealth();

        double totalDamage = Math.min(event.getFinalDamage(), maxDamage);

        int level = item.getItemMeta().getEnchantLevel(CustomEnchant.LIFESTEAL);
        double ratio = level * .1 + .1;

        double healAmount = ratio * totalDamage;

        int duration = (int)(healAmount * 3);

        final int amplifier = 4;

        if(e.hasPotionEffect(PotionEffectType.REGENERATION)){
          PotionEffect effect = e.getPotionEffect(PotionEffectType.REGENERATION);
          if(amplifier == effect.getAmplifier())
            duration += effect.getDuration();
        }
        e.getWorld().playSound(e.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_STEP, 2.0f, 1.0f);
        e.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Math.min(duration, 3 * 20), amplifier));
      }
    }
  }

  // @EventHandler
  // public void onPrepareAnvilEvent(PrepareAnvilEvent event) {
  //     AnvilInventory inventory = (AnvilInventory) event.getInventory();
  //     inventory.setMaximumRepairCost(Integer.MAX_VALUE);

  //     ItemStack leftItem = inventory.getItem(0);
  //     ItemStack rightItem = inventory.getItem(1);
  //     ItemStack resultItem = event.getResult();

  //     boolean hasCustomEnchant = false;

  //     for(CustomEnchant ench : CustomEnchant.getAllCustomEnchants()){
  //       if((leftItem.hasItemMeta() && leftItem.getItemMeta().hasEnchant(ench)) || (rightItem.hasItemMeta() && rightItem.getItemMeta().hasEnchant(ench))){
  //         event.getView().getPlayer().sendMessage("Custom");
  //         hasCustomEnchant = true;
  //         break;
  //       }
  //     }

  //     if(!hasCustomEnchant) return;

  //     //check if both item slots are filled
  //     if (leftItem != null && rightItem != null) {
  //         //check to see if there is a book
  //         boolean leftIsBook = leftItem.getItemMeta() instanceof EnchantmentStorageMeta;
  //         boolean rightIsBook = rightItem.getItemMeta() instanceof EnchantmentStorageMeta;

  //         //if the items are the same type OR one of the items is a book
  //         if (leftItem.getType() == rightItem.getType() || (leftIsBook && rightIsBook) || rightIsBook) {
  //             //use the enchants on the left item as a base
  //             Map<Enchantment, Integer> resultingEnchantments;
  //             Map<Enchantment, Integer> addedEnchantments;

  //             //get enchantment map for left item
  //             if (leftIsBook) {
  //                 resultingEnchantments = new HashMap<Enchantment, Integer>(((EnchantmentStorageMeta)leftItem.getItemMeta()).getStoredEnchants());
  //             } else {
  //                 resultingEnchantments = new HashMap(leftItem.getItemMeta().getEnchants());
  //             }

  //             //get enchantment map for right item
  //             if (rightIsBook) {
  //                 addedEnchantments = new HashMap<Enchantment, Integer>(((EnchantmentStorageMeta)rightItem.getItemMeta()).getStoredEnchants());
  //             } else {
  //                 addedEnchantments = rightItem.getItemMeta().getEnchants();
  //             }

  //             //iterate over all enchants on the right item
  //             for (Map.Entry<Enchantment, Integer> entry : addedEnchantments.entrySet()) {
  //                 Enchantment rightEnchantment = entry.getKey();
  //                 int rightEnchantmentLevel = entry.getValue();
  //                 //check if the left item does not have this enchantment yet
  //                 if (!resultingEnchantments.containsKey(rightEnchantment)) {
  //                     //add the enchantment!
  //                   if(rightEnchantment.canEnchantItem(resultItem))
  //                     resultingEnchantments.put(rightEnchantment, rightEnchantmentLevel);
  //                 } else {  //both items have this enchantment
  //                     //get the enchantment level for this enchant on the left item
  //                     int leftEnchantmentLevel = resultingEnchantments.get(rightEnchantment);
  //                     //check if right item has a higher level for this enchantment
  //                     if (leftEnchantmentLevel != rightEnchantmentLevel) {
  //                         //update the result to the max level
  //                         resultingEnchantments.put(rightEnchantment, Math.max(rightEnchantmentLevel, leftEnchantmentLevel));
  //                     }
  //                     //both items have the same level
  //                     else {
  //                         resultingEnchantments.put(rightEnchantment, Math.min(rightEnchantmentLevel + 1, rightEnchantment.getMaxLevel()));
  //                     }
  //                 }
  //             }

  //             if (leftIsBook) {
  //                 //original enchantments
  //                 EnchantmentStorageMeta resultItemMeta = (EnchantmentStorageMeta)resultItem.getItemMeta();
  //                 Map<Enchantment, Integer> originalEnchantments = resultItemMeta.getStoredEnchants();
  //                 //clear enchantments from the temporary result item
  //                 for (Map.Entry<Enchantment, Integer> entry : originalEnchantments.entrySet()) {
  //                     resultItemMeta.removeStoredEnchant(entry.getKey());
  //                 }
  //                 //set new enchants
  //                 for (Map.Entry<Enchantment, Integer> entry : resultingEnchantments.entrySet()) {
  //                     resultItemMeta.addStoredEnchant(entry.getKey(), entry.getValue(), true);
  //                 }
  //                 resultItem.setItemMeta(resultItemMeta);

  //             } else {
  //               resultItem = leftItem.clone();
  //                 //clear enchantments from the temporary result item
  //                 for (Map.Entry<Enchantment, Integer> entry : resultItem.getItemMeta().getEnchants().entrySet())
  //                     resultItem.removeEnchantment(entry.getKey());

  //                 for(Map.Entry<Enchantment, Integer> entry : resultingEnchantments.entrySet()){
  //                   Enchantment ench = entry.getKey();
  //                   int level = entry.getValue().intValue();

  //                   event.getView().getPlayer().sendMessage("Tentative d'ajout " + ench.getName() + " " + CustomEnchant.intToRoman(level));

  //                   CustomEnchant.addCustomEnchantment(resultItem, ench, level);
  //                 }


  //             }

  //             //Cost is almost always 1 if you are putting things on a book, so let's change that!
  //             if (leftIsBook) {
  //                 //repair cost will be the sum total of all enchant levels on the resulting book!
  //                 int repairCost = 0;
  //                 for (Map.Entry<Enchantment, Integer> entry : resultingEnchantments.entrySet()) {
  //                     repairCost += entry.getValue();
  //                 }
  //                 inventory.setRepairCost(repairCost);
  //             }

  //             event.setResult(resultItem);




  //             int finalRepairCost = inventory.getRepairCost();

  //             inventory.setRepairCost(finalRepairCost);
  //             event.getView().setProperty(InventoryView.Property.REPAIR_COST, finalRepairCost);

  //         }
  //     }

  // }

  //Detect a player has repaired/combined an item!
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event){
    return;
      // check whether the event has been cancelled by another plugin
      /*
      if(!event.isCancelled()){
          HumanEntity humanEntity = event.getWhoClicked();

          if(humanEntity instanceof Player){
              Player player = (Player)humanEntity;
              // Check if this event fired inside an anvil.
              if(event.getInventory() instanceof AnvilInventory){
                  final AnvilInventory anvilInventory = (AnvilInventory) event.getInventory();
                  InventoryView view = event.getView();
                  int rawSlot = event.getRawSlot();

                  // check if we are in the upper inventory of the anvil
                  if(rawSlot == view.convertSlot(rawSlot)){
                      // check if we are talking about the result slot
                      if(rawSlot == 2){
                          // get all 3 items in the anvil
                          ItemStack[] items = anvilInventory.getContents();

                          // Make sure there are items in the first two anvil slots
                          if(items[0] != null && items[1] != null) {
                              // if the player clicked an empty result slot, the material will be AIR, so ignore that!
                              // Also ignore if the player clicked the items in the first two slots!
                              if (event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem() != items[0] && event.getCurrentItem() != items[1]) {
                                  // We now know the player has attempted to combine two items!
                                  // Now we make sure that the player has the levels required!
                                  if (player.getLevel() >= anvilInventory.getRepairCost()) {
                                      // Store these values before doing anything with the anvilInventory....
                                      int repairCost = anvilInventory.getRepairCost();
                                      int playerLevel = player.getLevel();
                                      int resultantLevel = playerLevel - repairCost;

                                      // clone the result item
                                      ItemStack itemToGive = event.getCurrentItem().clone();

                                      // let's make SURE that the item given is only 1!
                                      if (itemToGive.getAmount() > 1) {
                                          itemToGive.setAmount(1);
                                      }

                                      // If the first item is a stack, we should give it back (-1)
                                      if (items[0].getAmount() > 1) {
                                          ItemStack returnedStack = items[0].clone();
                                          returnedStack.setAmount(returnedStack.getAmount() - 1);
                                          if (player.getInventory().addItem(returnedStack).size() != 0) {
                                              player.getWorld().dropItem(player.getLocation(), returnedStack);
                                          }
                                      }

                                      // delete the 3 items in the anvil!
                                      anvilInventory.remove(anvilInventory.getItem(0));
                                      anvilInventory.remove(anvilInventory.getItem(1));
                                      anvilInventory.remove(anvilInventory.getItem(2));

                                      // give the player the clone of the result! (drop it on them if their inventory is full)
                                      if (player.getInventory().addItem(itemToGive).size() != 0) {
                                          player.getWorld().dropItem(player.getLocation(), itemToGive);
                                      }

                                      // Play the anvil sound on the player.
                                      player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);

                                      //let's set players exp levels to what they should be after this repair
                                      player.giveExpLevels(resultantLevel - playerLevel);
                                  }
                              }
                          }
                      }
                  }
              }
          }
      }
      */
    }

  @EventHandler
  public void onAcquireTrade(VillagerAcquireTradeEvent event){
    if(!(event.getEntity() instanceof Villager)) return;
    Villager v = (Villager)event.getEntity();
    if(v.getRecipes().size() == 1 && v.getProfession() == Profession.CLERIC && Math.random() < 0.3){
      event.setRecipe(Math.random() < .5 ? RecipeManager.THC_TRADE : RecipeManager.COKE_TRADE);
      return;
    }
  }

  @EventHandler
  public void onEntityDie(EntityDeathEvent event){
    if (event.getEntityType() == EntityType.WITCH && Math.random() < .05){
      LivingEntity e = event.getEntity();
      e.getWorld().dropItemNaturally(e.getLocation(),Math.random() < .5 ? SpecialItemStack.THC : SpecialItemStack.SPECIAL_POWDER);
    }

    if (Catalogue.NETHER_PASS_DROPS_RATE.containsKey(event.getEntityType()) && Math.random() < Catalogue.NETHER_PASS_DROPS_RATE.get(event.getEntityType())){
      LivingEntity e = event.getEntity();
      e.getWorld().dropItemNaturally(e.getLocation(), SpecialItemStack.NETHER_PASS);
    }
  }

}

