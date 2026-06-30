package com.rave.projectbabylonweapons.item.claws;

import java.util.List;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import com.rave.projectbabylonweapons.passive.diamond.DiamondFangPassive;

import com.rave.projectbabylonweapons.init.PBModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

public class DiamondClawsItem extends SwordItem {

    // Итог: урон 4.0, скорость 3.4, прочность 3460.
    public static final int   DURABILITY = 1458;
    public static final int   ATTACK_DAMAGE_MOD = 3;     // +3.0 → 1.0 базовое = 4.0
    public static final float ATTACK_SPEED_MOD = -3.0F; // 4.0 - 0.6 = 3.4

    public DiamondClawsItem(Properties props) {
        // ВАЖНО: без stacksTo(...). Только durability(...)
        super(Tiers.WOOD, (props.durability(DURABILITY)).attributes(SwordItem.createAttributes(Tiers.WOOD, ATTACK_DAMAGE_MOD, ATTACK_SPEED_MOD)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            if (player.getRandom().nextFloat() < 0.05F) { // 5%
                target.addEffect(new MobEffectInstance(PBModEffects.BLEED_DEBUFF, 20 * 20, 0));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        DiamondFangPassive.appendTooltip(tooltip);
    }
}
