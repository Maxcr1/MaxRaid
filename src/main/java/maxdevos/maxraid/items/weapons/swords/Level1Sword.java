package maxdevos.maxraid.items.weapons.swords;

import maxdevos.maxraid.items.RaidItemType;
import org.bukkit.enchantments.Enchantment;

public class Level1Sword extends RaidSword {

    public Level1Sword() {
        super(RaidItemType.WeaponMaterial.IRON);
        this.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        this.addEnchantment(Enchantment.KNOCKBACK, 1);
    }
}
