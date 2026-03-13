package com.rave.projectbabylonweapons.item.claws;

import com.rave.projectbabylonweapons.init.PBModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class IceClawsItem extends SwordItem {


    public static final int DURABILITY = 2178;
    public static final int ATTACK_DAMAGE_MOD = 3;
    public static final float ATTACK_SPEED_MOD = -3.0F;
    public static final float CHILL_I_PROC_CHANCE = 0.20F;
    public static final float CHILL_II_PROC_CHANCE = 0.20F;
    public static final float CHILL_III_PROC_CHANCE = 0.20F;
    public static final float FROZEN_FROM_CHILL_III_PROC_CHANCE = 0.20F;

    public static final int CHILL_I_DURATION_TICKS = 20 * 5;
    public static final int CHILL_II_DURATION_TICKS = 20 * 5;
    public static final int CHILL_III_DURATION_TICKS = 20 * 5;
    public static final int FROZEN_DURATION_TICKS = 20;

    public static final int CHILL_I_AMPLIFIER = 0;
    public static final int CHILL_II_AMPLIFIER = 1;
    public static final int CHILL_III_AMPLIFIER = 2;

    public IceClawsItem(Properties props) {

        super(Tiers.WOOD, ATTACK_DAMAGE_MOD, ATTACK_SPEED_MOD, props.durability(DURABILITY));
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            if (player.getRandom().nextFloat() < 0.05F) { // 5%
                target.addEffect(new MobEffectInstance(PBModEffects.BLEED_DEBUFF.get(), 20 * 20, 0));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.ice.line1")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip.project_babylon_weapons.passive.ice.line2")
                .withStyle(ChatFormatting.GRAY));
    }
}


