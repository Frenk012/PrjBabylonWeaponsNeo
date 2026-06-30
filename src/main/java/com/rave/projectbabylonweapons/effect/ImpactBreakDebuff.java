package com.rave.projectbabylonweapons.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import yesman.epicfight.registry.entries.EpicFightAttributes;

public class ImpactBreakDebuff extends MobEffect {
    private static final ResourceLocation IMPACT_REDUCTION_ID = ResourceLocation.fromNamespaceAndPath("project_babylon_weapons", "impact_break_reduction");

    public ImpactBreakDebuff() {
        super(MobEffectCategory.HARMFUL, 0x7A2D2D);
        addAttributeModifier(EpicFightAttributes.IMPACT, IMPACT_REDUCTION_ID, -0.25D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
