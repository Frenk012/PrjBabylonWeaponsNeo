package com.rave.projectbabylonweapons.world.capabilities.item;

import java.util.function.Function;

import com.rave.projectbabylonweapons.gameasset.PBColliderPresets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonweapons.gameasset.PBSkills;

@Mod.EventBusSubscriber(
        modid = "project_babylon_weapons",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class PBWeaponCapabilityPresets {

    public static final Function<Item, CapabilityItem.Builder> SICKLE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(PBWeaponCategories.PB_SICKLE)
                .styleProvider((playerpatch) -> {
                    if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == PBWeaponCategories.PB_SICKLE) {
                        return CapabilityItem.Styles.TWO_HAND;
                    }
                    return CapabilityItem.Styles.ONE_HAND;
                })
                .collider(PBColliderPresets.SICKLE)
                .canBePlacedOffhand(true)
                .weaponCombinationPredicator((entitypatch) ->
                        EpicFightCapabilities.getItemStackCapability(((LivingEntity) entitypatch.getOriginal()).getOffhandItem()).getWeaponCategory() == PBWeaponCategories.PB_SICKLE
                );

        if (item instanceof TieredItem tieredItem) {
            if (tieredItem.getTier() == Tiers.WOOD) {
                builder.hitSound(EpicFightSounds.BLUNT_HIT.get())
                        .hitParticle(EpicFightParticles.HIT_BLUNT.get());
            } else {
                builder.hitSound(EpicFightSounds.BLADE_HIT.get())
                        .hitParticle(EpicFightParticles.HIT_BLADE.get());
            }
        } else {
            builder.hitSound(EpicFightSounds.BLADE_HIT.get())
                    .hitParticle(EpicFightParticles.HIT_BLADE.get());
        }

        builder

                .newStyleCombo(CapabilityItem.Styles.ONE_HAND, new AnimationManager.AnimationAccessor[]{
                        PBAnimations.SICKLE_ONEHAND_AUTO_1,
                        PBAnimations.SICKLE_ONEHAND_AUTO_2,
                        PBAnimations.SICKLE_ONEHAND_AUTO_3,
                        PBAnimations.SICKLE_ONEHAND_AUTO_4,
                        PBAnimations.SICKLE_ONEHAND_DASH,
                        PBAnimations.SICKLE_ONEHAND_AIR_SLASH
                })

                .newStyleCombo(CapabilityItem.Styles.TWO_HAND, new AnimationManager.AnimationAccessor[]{
                        PBAnimations.SICKLE_DUAL_AUTO_1,
                        PBAnimations.SICKLE_DUAL_AUTO_2,
                        PBAnimations.SICKLE_DUAL_AUTO_3,
                        Animations.SWORD_DUAL_DASH,
                        PBAnimations.SICKLE_DUAL_AIR_SLASH
                })

                .newStyleCombo(CapabilityItem.Styles.MOUNT, new AnimationManager.AnimationAccessor[]{
                        Animations.SWORD_MOUNT_ATTACK
                })

                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> PBSkills.SICKLE_THROW)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> PBSkills.THE_HARVEST)

                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, PBAnimations.SICKLE_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.KNEEL, PBAnimations.SICKLE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, PBAnimations.SICKLE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.CHASE, PBAnimations.SICKLE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, PBAnimations.SICKLE_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SNEAK, PBAnimations.SICKLE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SWIM, PBAnimations.SICKLE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.FLOAT, PBAnimations.SICKLE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.FALL, PBAnimations.SICKLE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)

                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, PBAnimations.SICKLE_DUAL_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, PBAnimations.SICKLE_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, PBAnimations.SICKLE_DUAL_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, PBAnimations.SICKLE_DUAL_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, PBAnimations.SICKLE_DUAL_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, PBAnimations.SICKLE_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, PBAnimations.SICKLE_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, PBAnimations.SICKLE_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, PBAnimations.SICKLE_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> ARCLIGHT = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(PBWeaponCategories.ARCLIGHT)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
                .collider(PBColliderPresets.SICKLE)
                .canBePlacedOffhand(true)
                .weaponCombinationPredicator((entitypatch) ->
                        EpicFightCapabilities.getItemStackCapability(((LivingEntity) entitypatch.getOriginal()).getOffhandItem()).getWeaponCategory() == PBWeaponCategories.ARCLIGHT
                );

        if (item instanceof TieredItem tieredItem) {
            if (tieredItem.getTier() == Tiers.WOOD) {
                builder.hitSound(EpicFightSounds.BLUNT_HIT.get())
                        .hitParticle(EpicFightParticles.HIT_BLUNT.get());
            } else {
                builder.hitSound(EpicFightSounds.BLADE_HIT.get())
                        .hitParticle(EpicFightParticles.HIT_BLADE.get());
            }
        } else {
            builder.hitSound(EpicFightSounds.BLADE_HIT.get())
                    .hitParticle(EpicFightParticles.HIT_BLADE.get());
        }

        builder

                .newStyleCombo(CapabilityItem.Styles.TWO_HAND, new AnimationManager.AnimationAccessor[]{
                        PBAnimations.ARCLIGHT_AUTO_1,
                        PBAnimations.ARCLIGHT_AUTO_2,
                        PBAnimations.ARCLIGHT_AUTO_3,
                        PBAnimations.ARCLIGHT_AUTO_4,
                        PBAnimations.ARCLIGHT_DASH,
                        PBAnimations.ARCLIGHT_AIRSlASH

                })

                .newStyleCombo(CapabilityItem.Styles.MOUNT, new AnimationManager.AnimationAccessor[]{
                        Animations.SWORD_MOUNT_ATTACK
                })

                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> PBSkills.THE_HARVEST)


                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, PBAnimations.ARCLIGHT_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, PBAnimations.ARCLIGHT_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, PBAnimations.ARCLIGHT_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, PBAnimations.ARCLIGHT_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, PBAnimations.ARCLIGHT_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, PBAnimations.ARCLIGHT_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, PBAnimations.ARCLIGHT_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, PBAnimations.ARCLIGHT_IDLE);

        return builder;
    };
    @SubscribeEvent
    public static void registerWeaponPresets(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(
                ResourceLocation.fromNamespaceAndPath("project_babylon_weapons", "pb_sickle"),
                SICKLE);

                event.getTypeEntry().put(
                        ResourceLocation.fromNamespaceAndPath("project_babylon_weapons", "arclight"),
                        ARCLIGHT
        );
    }
}
