package fr.yorkgangsta.pluginrp.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public enum ItemManager {
  WEED, COKE, BEER;

  public Material getItemMaterial(){
    switch (this) {
      case WEED:
        return Material.DRIED_KELP;
    
      case COKE:
        return Material.SUGAR;

      case BEER:
        return Material.POTION;
      default:
        break;
    }

    return Material.DIRT;
  }

  
  public String getItemName(){
    switch (this) {
      case WEED:
        return "§2Weed";
      
      case COKE:
        return "§fCoke";

      case BEER:
        return "§eBière";

      default:
      break;
      
    }
    return "";
  }
  
  public static ItemStack getSpecialItem(String itemName, int count){

    for (ItemManager i : ItemManager.values()){
      if(i.name().equalsIgnoreCase(itemName))
        return getSpecialItem(i, count);
    }

    return null;
  }
  

  public static ItemStack getSpecialItem(ItemManager type, int count){
    ItemStack item = null ;
        item = new ItemStack(type.getItemMaterial(), count);

        String name = type.getItemName();
        ItemMeta meta = item.getItemMeta();

        if(name != ""){
          meta.setDisplayName(name);
        }
        
        if (type == BEER){
          PotionData potionData = new PotionData(PotionType.UNCRAFTABLE, false, false);

          PotionEffect effect = new PotionEffect(PotionEffectType.ABSORPTION, 8 * 20, 0);

          PotionMeta potionMeta = (PotionMeta) meta;

          potionMeta.addCustomEffect(effect, false);
          potionMeta.setBasePotionData(potionData);
          potionMeta.setColor(Color.YELLOW);

          item.setItemMeta(potionMeta);

          return item;
        }
        
        
        item.setItemMeta(meta);
    return item;
  }


}
