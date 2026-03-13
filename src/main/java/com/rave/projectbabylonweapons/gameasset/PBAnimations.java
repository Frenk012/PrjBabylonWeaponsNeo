package com.rave.projectbabylonweapons.gameasset;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;

import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;




public class PBAnimations {
    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(ProjectBabylonWeapons.MODID, PBAnimations::buildAnimations);
        }

//SICKLE
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_ONEHAND_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_ONEHAND_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_ONEHAND_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_ONEHAND_AUTO_4;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SICKLE_ONEHAND_DASH;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SICKLE_ONEHAND_AIR_SLASH;

    public static AnimationManager.AnimationAccessor<StaticAnimation> SICKLE_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SICKLE_HOLD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SICKLE_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SICKLE_RUN;

    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_DUAL_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_DUAL_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_DUAL_AUTO_3;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> SICKLE_DUAL_AIR_SLASH;

    public static AnimationManager.AnimationAccessor<StaticAnimation> SICKLE_DUAL_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SICKLE_DUAL_HOLD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SICKLE_DUAL_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> SICKLE_DUAL_RUN;

//BATTLEHAMMER
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOLD_BATTLEHAMMER;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> BATTLEHAMMER_DASH;

//SKILLS
    public static AnimationManager.AnimationAccessor<StaticAnimation> SICKLE_READY;
    public static AnimationManager.AnimationAccessor<ActionAnimation> SICKLE_THROW;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> SICKLE_PULLING;
    public static AnimationManager.AnimationAccessor<AttackAnimation> SICKLE_HOOKING;
    public static AnimationManager.AnimationAccessor<DashAttackAnimation> UPPERCUT;
    public static AnimationManager.AnimationAccessor<AttackAnimation> TECTONIC;
    public static AnimationManager.AnimationAccessor<AttackAnimation> THE_HARVEST;
    public static AnimationManager.AnimationAccessor<AttackAnimation> BEAST_ROAR;


    public static void buildAnimations(AnimationManager.AnimationBuilder builder) {

//SICKLE
        SICKLE_DUAL_IDLE = builder.nextAccessor("biped/living/sickle_dual_idle", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
        SICKLE_DUAL_HOLD = builder.nextAccessor("biped/living/sickle_dual_hold", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));
        SICKLE_DUAL_WALK = builder.nextAccessor("biped/living/sickle_dual_walk", (accessor) -> new MovementAnimation(0.1F, true, accessor, Armatures.BIPED));
        SICKLE_DUAL_RUN = builder.nextAccessor("biped/living/sickle_dual_run", (accessor) -> new MovementAnimation(0.1F, true, accessor, Armatures.BIPED));


        SICKLE_ONEHAND_AUTO_1 = builder.nextAccessor("biped/combat/sickle_onehand_auto_1", (accessor) ->  new AttackAnimation(0.15F,  0.15F,  0.1F,  0.4F, 7.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).newTimePair(0.0F, 0.4F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F));
        SICKLE_ONEHAND_AUTO_2 = builder.nextAccessor("biped/combat/sickle_onehand_auto_2", (accessor) ->  new AttackAnimation(0.15F,  0.15F,  0.1F,  0.25F, 7.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).newTimePair(0.0F, 0.45F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F));
        SICKLE_ONEHAND_AUTO_3 = builder.nextAccessor("biped/combat/sickle_onehand_auto_3", (accessor) ->  new AttackAnimation(0.15F,  0.15F,  0.1F,  0.35F, 4.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).newTimePair(0.0F, 0.4F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F));
        SICKLE_ONEHAND_AUTO_4 = builder.nextAccessor("biped/combat/sickle_onehand_auto_4", (accessor) ->  new AttackAnimation(0.15F,  0.15F,  0.1F,  0.35F, 4.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).newTimePair(0.0F, 0.45F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F));


        SICKLE_ONEHAND_DASH = builder.nextAccessor("biped/combat/sickle_onehand_dash", (accessor) -> (DashAttackAnimation)(new DashAttackAnimation(0.12F, 0.1F, 0.25F, 0.4F, 0.65F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.3F));
        SICKLE_ONEHAND_AIR_SLASH = builder.nextAccessor("biped/combat/sickle_onehand_airslash", (accessor) -> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.5F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED));


        SICKLE_DUAL_AUTO_1 = builder.nextAccessor("biped/combat/sickle_dual_auto_1", (accessor) ->  new AttackAnimation(0.15F,  0.15F,  0.07F,  0.3F, 0.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)).newTimePair(0.0F, 0.45F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F));
        SICKLE_DUAL_AUTO_2 = builder.nextAccessor("biped/combat/sickle_dual_auto_2", (accessor) ->  new AttackAnimation(0.15F,  0.15F,  0.2F,  0.3F, 0.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).newTimePair(0.0F, 0.45F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F));
        SICKLE_DUAL_AUTO_3 = builder.nextAccessor("biped/combat/sickle_dual_auto_3", (accessor) ->  new AttackAnimation(0.15F,  0.1F,  0.04F,  0.2F, 0.667F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolL, accessor, Armatures.BIPED).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).newTimePair(0.0F, 0.45F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F));
        SICKLE_DUAL_AIR_SLASH = builder.nextAccessor("biped/combat/sickle_dual_airslash", (accessor) -> (AirSlashAnimation)(new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.5F, ColliderPreset.DUAL_SWORD_AIR_SLASH, ((HumanoidArmature)Armatures.BIPED.get()).torso, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.45F));

//BATTLEHAMMER
        BATTLEHAMMER_DASH = builder.nextAccessor("biped/combat/battlehammer_dash", (accessor) -> (DashAttackAnimation)(new DashAttackAnimation(0.1F, 0.1F, 0.25F, 0.6F, 1.25F, (Collider)null, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F));
        HOLD_BATTLEHAMMER = builder.nextAccessor("biped/living/hold_battlehammer", (accessor) -> new StaticAnimation(true, accessor, Armatures.BIPED));

//SKILLS
        SICKLE_READY = builder.nextAccessor("biped/skill/sickle_ready", (accessor) -> new StaticAnimation(0.15F, false, accessor, Armatures.BIPED).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CHARGING));
        SICKLE_THROW = builder.nextAccessor("biped/skill/sickle_throw", (accessor) -> new ActionAnimation(0.1F, accessor, Armatures.BIPED).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true));
        SICKLE_PULLING = builder.nextAccessor("biped/skill/sickle_pulling", (accessor) -> (DashAttackAnimation)(new DashAttackAnimation(0.1F, 0.0F, 0.5F, 0.7F, 1.65F, PBColliderPresets.SICKLE, ((HumanoidArmature)Armatures.BIPED.get()).legR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F));
        SICKLE_HOOKING = builder.nextAccessor("biped/skill/sickle_hooking",  (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, 0.0F, 0.2F, 0.5F, 1.65F, PBColliderPresets.SICKLE, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F));


        THE_HARVEST = builder.nextAccessor("biped/skill/the_harvest", (accessor) -> (AttackAnimation)(new AttackAnimation(0.1F, accessor, Armatures.BIPED, new AttackAnimation.Phase[]{
                new AttackAnimation.Phase(0.0F, 1.75F, 1.80F, 1.90F, 1.90F, InteractionHand.OFF_HAND, ((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)null),
                new AttackAnimation.Phase(1.90F, 2.00F, 2.05F, 2.20F, 2.20F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)null),
                new AttackAnimation.Phase(2.20F, 2.25F, 2.30F, 2.45F, 2.45F, InteractionHand.OFF_HAND, ((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)null),
                new AttackAnimation.Phase(2.45F, 2.50F, 2.55F, 2.65F, 2.65F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)null),
                new AttackAnimation.Phase(2.65F, 2.70F, 2.75F, 2.85F, 2.85F, InteractionHand.OFF_HAND, ((HumanoidArmature)Armatures.BIPED.get()).toolL, (Collider)null),
                new AttackAnimation.Phase(2.85F, 2.90F, 2.95F, 4.2F, 4.2F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).toolR, (Collider)null)
        })).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F));
        BEAST_ROAR = builder.nextAccessor("biped/skill/beast_roar", (accessor) -> (AttackAnimation)(new AttackAnimation(
                0.1F,
                accessor,
                Armatures.BIPED,
                new AttackAnimation.Phase(0.0F, 1.10F, 1.12F, 1.17F, 1.21F, 1.24F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).rootJoint, PBColliderPresets.BEAST_ROAR_FIRST),
                new AttackAnimation.Phase(1.24F, 1.26F, 1.27F, 1.32F, 1.35F, 1.38F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).rootJoint, PBColliderPresets.BEAST_ROAR_SECOND),
                new AttackAnimation.Phase(1.38F, 1.40F, 1.41F, 1.47F, 1.50F, 1.52F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).rootJoint, PBColliderPresets.BEAST_ROAR_THIRD),
                new AttackAnimation.Phase(1.52F, 1.54F, 1.55F, 1.62F, 1.64F, 1.6471F, InteractionHand.MAIN_HAND, ((HumanoidArmature)Armatures.BIPED.get()).rootJoint, PBColliderPresets.BEAST_ROAR_THIRD)
        ))
                .removeProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false)
                .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F)
                .addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(1.0f, PBReusableEvents.BIG_GROUNDSLAM, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0f, -0.24f, -2.0f))}));
        UPPERCUT = builder.nextAccessor("biped/skill/uppercut", (accessor) -> (DashAttackAnimation)(new DashAttackAnimation(0.12F, 0.1F, 0.4F, 0.6F, 2.0F, PBColliderPresets.APPERCUT_SKILL, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F));
        TECTONIC = builder.nextAccessor("biped/skill/tectonic", (accessor) -> (AttackAnimation)(new AttackAnimation(0.12F, 0.1F, 0.8F, 1.5F, 1.1F, PBColliderPresets.TECTONIC_SKILL, ((HumanoidArmature)Armatures.BIPED.get()).toolR, accessor, Armatures.BIPED)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addEvents(new AnimationEvent[]{AnimationEvent.InTimeEvent.create(1.0f, PBReusableEvents.BIG_GROUNDSLAM, AnimationEvent.Side.CLIENT).params(new Vec3f(0.0f, -0.24f, -2.0f))}));
    }
}
