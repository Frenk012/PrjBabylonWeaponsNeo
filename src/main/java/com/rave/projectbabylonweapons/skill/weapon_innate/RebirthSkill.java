package com.rave.projectbabylonweapons.skill.weapon_innate;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.entity.eventlistener.AnimationEndEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RebirthSkill extends WeaponInnateSkill {
    private static final UUID REBIRTH_END_UUID = UUID.fromString("cc2cb8c8-4ea4-4b01-b605-4c4ec87ab93a");
    private static final Set<UUID> ACTIVE_REBIRTH = ConcurrentHashMap.newKeySet();

    public RebirthSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                EventType.ANIMATION_END_EVENT,
                REBIRTH_END_UUID,
                event -> onAnimationEnd(container, event)
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.ANIMATION_END_EVENT, REBIRTH_END_UUID);
        clearRebirth(container.getExecutor().getOriginal());
        super.onRemoved(container);
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnServer(container, args);
        container.activate();
        startRebirth(container.getExecutor().getOriginal());
    }

    @Override
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        clearRebirth(container.getExecutor().getOriginal());
        container.deactivate();
        super.cancelOnServer(container, args);
    }

    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnClient(container, args);
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        if (!this.properties.isEmpty()) {
            PBAnimations.REBIRTH.get().phases[0].addProperties(this.properties.get(0).entrySet());
        }
        return this;
    }

    public static boolean triggerPassiveRebirth(LivingEntity entity) {
        return startRebirth(entity);
    }

    private static boolean startRebirth(LivingEntity entity) {
        if (entity == null || entity.level().isClientSide() || !(entity.level() instanceof ServerLevel)) {
            return false;
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) {
            return false;
        }

        ACTIVE_REBIRTH.add(entity.getUUID());
        patch.playAnimationSynchronized(PBAnimations.REBIRTH, 0.0F);
        return true;
    }

    private static void onAnimationEnd(SkillContainer container, AnimationEndEvent event) {
        if (event.getAnimation() != PBAnimations.REBIRTH.get()) {
            return;
        }

        clearRebirth(container.getExecutor().getOriginal());
        if (container.isActivated()) {
            container.deactivate();
        }
    }

    private static void clearRebirth(LivingEntity entity) {
        if (entity != null) {
            ACTIVE_REBIRTH.remove(entity.getUUID());
        }
    }

    private static boolean isRebirthActive(LivingEntity entity) {
        if (entity == null || !ACTIVE_REBIRTH.contains(entity.getUUID())) {
            return false;
        }

        if (!entity.isAlive() || !(entity.level() instanceof ServerLevel)) {
            clearRebirth(entity);
            return false;
        }

        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) {
            clearRebirth(entity);
            return false;
        }

        var currentAnimation = patch.getAnimator().getPlayerFor(null).getRealAnimation();
        if (currentAnimation == null || currentAnimation.get() == null) {
            clearRebirth(entity);
            return false;
        }

        try {
            var registryName = currentAnimation.get().getRegistryName();
            if (registryName == null || !registryName.toString().equals(PBAnimations.REBIRTH.registryName().toString())) {
                clearRebirth(entity);
                return false;
            }
        } catch (Exception ignored) {
            if (!currentAnimation.get().toString().equals(PBAnimations.REBIRTH.registryName().toString())) {
                clearRebirth(entity);
                return false;
            }
        }

        return true;
    }

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || event.isCanceled()) {
            return;
        }

        if (!isRebirthActive(entity)) {
            return;
        }

        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || event.isCanceled()) {
            return;
        }

        if (!isRebirthActive(entity)) {
            return;
        }

        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        event.setAmount(0.0F);
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide() || !ACTIVE_REBIRTH.contains(entity.getUUID())) {
            return;
        }

        isRebirthActive(entity);
    }

    @OnlyIn(Dist.CLIENT)
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }
}
