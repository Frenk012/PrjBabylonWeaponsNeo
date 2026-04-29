package com.rave.projectbabylonweapons.tooltip;

import com.rave.projectbabylonweapons.passive.diamond.DiamondFangBalance;
import com.rave.projectbabylonweapons.passive.diamond.DiamondFangPassive;
import com.rave.projectbabylonweapons.passive.ethereal.EtherealHolyBalance;
import com.rave.projectbabylonweapons.passive.ethereal.EtherealHolyPassive;
import com.rave.projectbabylonweapons.passive.golden.GoldenMagicBalance;
import com.rave.projectbabylonweapons.passive.golden.GoldenMagicPassive;
import com.rave.projectbabylonweapons.passive.ice.IceChillBalance;
import com.rave.projectbabylonweapons.passive.ice.IceChillPassive;
import com.rave.projectbabylonweapons.passive.netherite.NetheriteBrimstoneBalance;
import com.rave.projectbabylonweapons.passive.netherite.NetheriteBrimstonePassive;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class WeaponPassiveTooltipResolver {

    private WeaponPassiveTooltipResolver() {
    }

    @Nullable
    public static WeaponPassiveTooltipData resolve(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }

        if (DiamondFangBalance.resolve(stack) != null) {
            return DiamondFangPassive.getTooltipData();
        }

        if (IceChillBalance.resolve(stack) != null) {
            return IceChillPassive.getTooltipData();
        }

        if (GoldenMagicBalance.resolve(stack) != null) {
            return GoldenMagicPassive.getTooltipData();
        }

        if (EtherealHolyBalance.resolve(stack) != null) {
            return EtherealHolyPassive.getTooltipData();
        }

        if (NetheriteBrimstoneBalance.resolve(stack) != null) {
            return NetheriteBrimstonePassive.getTooltipData();
        }

        return null;
    }
}
