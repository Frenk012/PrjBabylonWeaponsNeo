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