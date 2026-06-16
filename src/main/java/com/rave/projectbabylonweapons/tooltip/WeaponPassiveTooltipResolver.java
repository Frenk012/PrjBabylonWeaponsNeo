package com.rave.projectbabylonweapons.tooltip;

import com.rave.projectbabylonweapons.passive.data.WeaponPassiveIds;
import com.rave.projectbabylonweapons.passive.data.WeaponPassivePatchManager;
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
import com.rave.projectbabylonweapons.passive.wand.DiamondRicochetBalance;
import com.rave.projectbabylonweapons.passive.wand.DiamondRicochetPassive;
import com.rave.projectbabylonweapons.passive.wand.DragonsteelDragonlordBalance;
import com.rave.projectbabylonweapons.passive.wand.DragonsteelDragonlordPassive;
import com.rave.projectbabylonweapons.passive.wand.EtherealSanctuaryBalance;
import com.rave.projectbabylonweapons.passive.wand.EtherealSanctuaryPassive;
import com.rave.projectbabylonweapons.passive.wand.GoldenBloodPactBalance;
import com.rave.projectbabylonweapons.passive.wand.GoldenBloodPactPassive;
import com.rave.projectbabylonweapons.passive.wand.IceFrostTouchBalance;
import com.rave.projectbabylonweapons.passive.wand.IceFrostTouchPassive;
import com.rave.projectbabylonweapons.passive.wand.NetheriteAshenBalance;
import com.rave.projectbabylonweapons.passive.wand.NetheriteAshenPassive;
import net.minecraft.resources.ResourceLocation;
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

        if (DiamondRicochetBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.WAND_DIAMOND_RICOCHET, DiamondRicochetPassive.getTooltipData());
        }
        if (GoldenBloodPactBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.WAND_GOLDEN_BLOOD_PACT, GoldenBloodPactPassive.getTooltipData());
        }
        if (NetheriteAshenBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.WAND_NETHERITE_ASHEN, NetheriteAshenPassive.getTooltipData());
        }
        if (EtherealSanctuaryBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.WAND_ETHEREAL_SANCTUARY, EtherealSanctuaryPassive.getTooltipData());
        }
        if (IceFrostTouchBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.WAND_ICE_FROST_TOUCH, IceFrostTouchPassive.getTooltipData());
        }
        if (DragonsteelDragonlordBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.WAND_DRAGONSTEEL_DRAGONLORD, DragonsteelDragonlordPassive.getTooltipData());
        }
        if (DiamondFangBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.DIAMOND_FANG, DiamondFangPassive.getTooltipData());
        }
        if (IceChillBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.ICE_CHILL, IceChillPassive.getTooltipData());
        }
        if (GoldenMagicBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.GOLDEN_MAGIC, GoldenMagicPassive.getTooltipData());
        }
        if (EtherealHolyBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.ETHEREAL_HOLY, EtherealHolyPassive.getTooltipData());
        }
        if (NetheriteBrimstoneBalance.resolve(stack) != null) {
            return resolveTooltip(stack, WeaponPassiveIds.NETHERITE_BRIMSTONE, NetheriteBrimstonePassive.getTooltipData());
        }
        return null;
    }

    private static WeaponPassiveTooltipData resolveTooltip(ItemStack stack, ResourceLocation passiveId, WeaponPassiveTooltipData fallback) {
        WeaponPassiveTooltipData patchTooltip = WeaponPassivePatchManager.INSTANCE.resolveTooltipData(passiveId, stack);
        return patchTooltip != null ? patchTooltip : fallback;
    }
}
