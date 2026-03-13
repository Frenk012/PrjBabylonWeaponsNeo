package com.rave.projectbabylonweapons.item.tool;

import com.rave.projectbabylonweapons.item.material.PBToolTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GoldenPickaxeItem extends PickaxeItem {

    public GoldenPickaxeItem(Properties props) {
        super(PBToolTiers.GOLDEN_DURABLE, 1, -2.8F, props);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.golden.line1")
                .withStyle(ChatFormatting.GRAY));
    }
}
