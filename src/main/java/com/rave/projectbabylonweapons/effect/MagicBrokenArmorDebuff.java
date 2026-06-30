package com.rave.projectbabylonweapons.effect;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MagicBrokenArmorDebuff extends MobEffect {
    private static final ResourceLocation SPELL_RESIST_REDUCTION_ID = ResourceLocation.fromNamespaceAndPath("project_babylon_weapons", "magic_broken_armor_reduction");

    public MagicBrokenArmorDebuff() {
        super(MobEffectCategory.HARMFUL, 0x4A2F4A);
        addAttributeModifier(AttributeRegistry.SPELL_RESIST, SPELL_RESIST_REDUCTION_ID, -0.20D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
