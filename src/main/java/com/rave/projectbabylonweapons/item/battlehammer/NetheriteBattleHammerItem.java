package com.rave.projectbabylonweapons.item.battlehammer;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class NetheriteBattleHammerItem extends SwordItem {


    public static final int DURABILITY = 3654;
    public static final int ATTACK_DAMAGE_MOD = 3;
    public static final float ATTACK_SPEED_MOD = -3.0F;

    public static final float IGNITE_PROC_CHANCE = 0.40F;
    public static final int IGNITE_DURATION_SECONDS = 8;

    public static final float BRIMSTONE_FLAMES_PROC_CHANCE = 0.40F;
    public static final int BRIMSTONE_FLAMES_DURATION_TICKS = 20 * 8;

    public static final float BRIMSTONE_FIRE_PROC_CHANCE = 0.40F;
    public static final int BRIMSTONE_FIRE_DURATION_TICKS = 20 * 8;

    public static final float BRIMSTONE_BLAST_PROC_CHANCE = 0.50F;
    public static final float BRIMSTONE_BLAST_DAMAGE_MULTIPLIER = 0.50F;
    public static final float BRIMSTONE_BLAST_RADIUS_BLOCKS = 3.0F;

    public NetheriteBattleHammerItem(Properties props) {

        super(Tiers.WOOD, ATTACK_DAMAGE_MOD, ATTACK_SPEED_MOD, props.durability(DURABILITY));
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

