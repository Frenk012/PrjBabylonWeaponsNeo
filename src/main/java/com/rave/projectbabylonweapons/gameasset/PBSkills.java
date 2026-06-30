package com.rave.projectbabylonweapons.gameasset;

import java.util.Set;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.skill.weapon_innate.*;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

public class PBSkills {
    public static final DeferredRegister<Skill> REGISTRY = DeferredRegister.create(EpicFightRegistries.Keys.SKILL, ProjectBabylonWeapons.MODID);

    public static final DeferredHolder<Skill, BeastRoarSkill> BEAST_ROAR = REGISTRY.register("beast_roar", key ->
        WeaponInnateSkill.createWeaponInnateBuilder(BeastRoarSkill::new)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .setActivateType(Skill.ActivateType.DURATION)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(5.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.9F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, SickleThrowSkill> SICKLE_THROW = REGISTRY.register("sickle_throw", key ->
        WeaponInnateSkill.createWeaponInnateBuilder(SickleThrowSkill::new)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .setActivateType(Skill.ActivateType.HELD)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, TheHarvestSkill> THE_HARVEST = REGISTRY.register("the_harvest", key ->
        SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(TheHarvestSkill::new)
            .setAnimations(PBAnimations.THE_HARVEST)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, RebirthSkill> REBIRTH = REGISTRY.register("rebirth", key ->
        WeaponInnateSkill.createWeaponInnateBuilder(RebirthSkill::new)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .setActivateType(Skill.ActivateType.HELD)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, ManaBubbleSkill> MANA_BUBBLE = REGISTRY.register("mana_bubble", key ->
        WeaponInnateSkill.createWeaponInnateBuilder(ManaBubbleSkill::new)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .setActivateType(Skill.ActivateType.HELD)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, GlacierSkill> GLACIER = REGISTRY.register("glacier", key ->
        SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(GlacierSkill::new)
            .setAnimations(PBAnimations.GLACIER)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, DragonDescendSkill> DRAGON_DESCEND = REGISTRY.register("dragon_descend", key ->
        WeaponInnateSkill.createWeaponInnateBuilder(DragonDescendSkill::new)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .setActivateType(Skill.ActivateType.DURATION)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, FireStormSkill> FIRE_STORM = REGISTRY.register("fire_storm", key ->
        SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(FireStormSkill::new)
            .setAnimations(PBAnimations.FIRE_STORM)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, BlessingSkill> BLESSING = REGISTRY.register("blessing", key ->
        WeaponInnateSkill.createWeaponInnateBuilder(BlessingSkill::new)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .setActivateType(Skill.ActivateType.DURATION)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(4.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, UppercutSkill> UPPERCUT = REGISTRY.register("uppercut", key ->
        SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(UppercutSkill::new)
            .setAnimations(PBAnimations.UPPERCUT)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(10.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.1F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));

    public static final DeferredHolder<Skill, TectonicSkill> TECTONIC = REGISTRY.register("tectonic", key ->
        SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(TectonicSkill::new)
            .setAnimations(PBAnimations.TECTONIC)
            .setCategory(SkillCategories.WEAPON_INNATE)
            .newProperty()
                .addProperty(AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(3.0F))
                .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(0.0F))
                .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE))
            .build(key));
}
