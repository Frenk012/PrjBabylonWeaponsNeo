package com.rave.projectbabylonweapons.item.tool;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class NetheritePickaxeItem extends PickaxeItem {

    public NetheritePickaxeItem(Properties props) {
        super(Tiers.NETHERITE, 1, -2.8F, props);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.netherite.line1")
                .withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.netherite.line2")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.netherite.line3")
                .withStyle(ChatFormatting.GRAY));
    }
}
