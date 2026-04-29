package com.rave.projectbabylonweapons.passive.ethereal;

import com.rave.projectbabylonweapons.item.battleaxe.EtherealBattleAxeItem;
import com.rave.projectbabylonweapons.item.battlehammer.EtherealBattleHammerItem;
import com.rave.projectbabylonweapons.item.battlescythe.EtherealBattleScytheItem;
import com.rave.projectbabylonweapons.item.claws.EtherealClawsItem;
import com.rave.projectbabylonweapons.item.dagger.EtherealDaggerItem;
import com.rave.projectbabylonweapons.item.greatsword.EtherealGreatswordItem;
import com.rave.projectbabylonweapons.item.longsword.EtherealLongswordItem;
import com.rave.projectbabylonweapons.item.rapier.EtherealRapierItem;
import com.rave.projectbabylonweapons.item.shortsword.EtherealShortswordItem;
import com.rave.projectbabylonweapons.item.sickle.EtherealSickleItem;
import com.rave.projectbabylonweapons.item.spear.EtherealSpearItem;
import com.rave.projectbabylonweapons.item.staff.EtherealStaffItem;
import com.rave.projectbabylonweapons.item.tachi.EtherealTachiItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class EtherealHolyBalance {
    private static final Profile DEFAULT = new Profile(0.75F, 0.25F);

    private EtherealHolyBalance() {
    }

    public static Profile resolve(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }

        Item item = stack.getItem();
        if (item instanceof EtherealBattleAxeItem
                || item instanceof EtherealBattleHammerItem
                || item instanceof EtherealBattleScytheItem
                || item instanceof EtherealClawsItem
                || item instanceof EtherealDaggerItem
                || item instanceof EtherealGreatswordItem
                || item instanceof EtherealLongswordItem
                || item instanceof EtherealRapierItem
                || item instanceof EtherealShortswordItem
                || item instanceof EtherealSickleItem
                || item instanceof EtherealSpearItem
                || item instanceof EtherealStaffItem
                || item instanceof EtherealTachiItem) {
            return DEFAULT;
        }

        return null;
    }

    public record Profile(float holyDamagePercent, float physicalDamagePercent) {
    }
}
