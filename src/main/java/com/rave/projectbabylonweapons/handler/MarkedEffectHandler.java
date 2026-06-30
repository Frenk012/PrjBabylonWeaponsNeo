package com.rave.projectbabylonweapons.handler;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.init.PBModEffects;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Bus.GAME)
public class MarkedEffectHandler {
    private static final float MARKED_DAMAGE_MULTIPLIER = 1.15f;

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (event.getEntity().hasEffect(PBModEffects.MARKED)) {
            event.setAmount(event.getAmount() * MARKED_DAMAGE_MULTIPLIER);
        }
    }
}
