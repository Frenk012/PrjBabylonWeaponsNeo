package com.rave.projectbabylonweapons.handler;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonmaterials.init.PBMEffects;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Bus.FORGE)
public class MarkedEffectHandler {
    private static final float MARKED_DAMAGE_MULTIPLIER = 1.15f;

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity().hasEffect(PBMEffects.MARKED.get())) {
            event.setAmount(event.getAmount() * MARKED_DAMAGE_MULTIPLIER);
        }
    }
}

