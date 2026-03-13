package com.rave.projectbabylonweapons.item.tachi;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Properties;

public class GoldenTachiItem extends SwordItem {

    public static final int DURABILITY = 1402;
    public static final int ATTACK_DAMAGE_MOD = 3;
    public static final float ATTACK_SPEED_MOD = -3.0F;
    public static final float BONUS_MAGIC_DAMAGE_PERCENT = 0.25F;

    public GoldenTachiItem(Properties props) {
        super(Tiers.WOOD, ATTACK_DAMAGE_MOD, ATTACK_SPEED_MOD, props.durability(DURABILITY));
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.golden.line1")
                .withStyle(ChatFormatting.GRAY));
    }
}

