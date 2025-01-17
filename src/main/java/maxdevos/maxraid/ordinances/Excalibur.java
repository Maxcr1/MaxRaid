package maxdevos.maxraid.ordinances;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings({"deprecation", "unused"})
public class Excalibur {

    final ItemStack i;

    public Excalibur(int allowedKills){
        i = new ItemStack(Material.DIAMOND_SWORD);
        i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,32);
        i.addUnsafeEnchantment(Enchantment.FIRE_ASPECT,32);
        i.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE,32);
        i.setAmount(1);
        i.setDurability((short)	(1562-allowedKills));
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName("Excalibur");
        i.setItemMeta(meta);
    }

    public ItemStack getItem(){
        return i;
    }

}
