package com.rave.projectbabylonweapons.item.tool;

import com.rave.projectbabylonweapons.item.material.PBToolTiers;
import com.rave.projectbabylonweapons.passive.ice.IceChillPassive;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class IceHoeItem extends HoeItem {

    public IceHoeItem(Properties props) {
        super(PBToolTiers.ICE, -3, 0.0F, props);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        IceChillPassive.appendTooltip(tooltip);
    }
}


