package fr.yorkgangsta.pluginrp.items;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum ItemManager {
  WEED, COKE, BEER;

  public Material getItemMaterial(){
    switch (this) {
      case WEED:
        return Material.DRIED_KELP;
    
      case COKE:
        return Material.SUGAR;
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

        try {
          item.addEnchantment(Enchantment.MENDING, 0);
        } catch (IllegalArgumentException e) {
          Bukkit.getLogger().log(Level.FINE, "Enchantement impossible dans getSpecial Item", e);
        }

        String name = type.getItemName();

        if(name != ""){
          ItemMeta meta = item.getItemMeta();
          meta.setDisplayName(name);
          item.setItemMeta(meta);
        }
      
    return item;
  }


}
