package com.rave.projectbabylonweapons.item.tool;

import java.util.List;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import com.rave.projectbabylonweapons.passive.diamond.DiamondFangPassive;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;

public class DiamondPickaxeItem extends PickaxeItem {

    public DiamondPickaxeItem(Properties props) {
        super(Tiers.DIAMOND, 1, -2.8F, props);
    }
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        DiamondFangPassive.appendTooltip(tooltip);
    }
}
