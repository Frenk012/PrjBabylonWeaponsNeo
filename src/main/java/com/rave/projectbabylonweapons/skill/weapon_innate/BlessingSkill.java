package com.rave.projectbabylonweapons.skill.weapon_innate;

import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonweapons.world.entity.effect.HolyMagicalSealEntity;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.entity.eventlistener.AnimationEndEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.UUID;

public class BlessingSkill extends WeaponInnateSkill {
    private static final UUID END_UUID = UUID.fromString("7c59f197-4d6f-43e5-9e02-d4d9f0e59cb8");
    private static final double ALLY_RADIUS = 15.0D;
    private static final int OUTER_PARTICLE_COUNT = 18;
    private static final int INNER_PARTICLE_COUNT = 12;
    private static final double OUTER_RADIUS = 1.2D;
    private static final double INNER_RADIUS = 0.75D;

    public BlessingSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                EventType.ANIMATION_END_EVENT,
                END_UUID,
                event -> onAnimationEnd(container, event)
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.ANIMATION_END_EVENT, END_UUID);
        super.onRemoved(container);
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        if (this.isActivated(container)) {
            this.cancelOnServer(container, args);
        } else {
            super.executeOnServer(container, args);
            container.activate();
            container.getServerExecutor().modifyLivingMotionByCurrentItem(false);
            if (container.getExecutor().getOriginal() instanceof ServerPlayer serverPlayer) {
                spawnSeal(serverPlayer, serverPlayer);
                spawnHolyVortex(serverPlayer.serverLevel(), serverPlayer.position(), serverPlayer.getBbHeight());
            }
            container.getExecutor().playAnimationSynchronized(PBAnimations.BLESSING, 0.0F);
        }
    }

    @Override
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnServer(container, args);
        container.getServerExecutor().modifyLivingMotionByCurrentItem(false);
    }

    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnClient(container, args);
    }

    private static void onAnimationEnd(SkillContainer container, AnimationEndEvent event) {
        if (event.getAnimation() != PBAnimations.BLESSING.get()) {
            return;
        }

        if (!(container.getExecutor().getOriginal() instanceof ServerPlayer caster)) {
            return;
        }

        ServerLevel serverLevel = caster.serverLevel();
        AABB area = caster.getBoundingBox().inflate(ALLY_RADIUS, 4.0D, ALLY_RADIUS);
        List<ServerPlayer> nearbyPlayers = serverLevel.getEntitiesOfClass(ServerPlayer.class, area,
                player -> player.isAlive() && !player.isSpectator() && player != caster);

        for (ServerPlayer ally : nearbyPlayers) {
            spawnSeal(caster, ally);
        }
    }

    private static void spawnSeal(ServerPlayer caster, ServerPlayer target) {
        ServerLevel serverLevel = caster.serverLevel();
        discardExistingSeals(serverLevel, target);

        HolyMagicalSealEntity seal = new HolyMagicalSealEntity(serverLevel);
        seal.configure(caster, target);
        seal.setPos(target.getX(), target.getY() + 0.02D, target.getZ());
        serverLevel.addFreshEntity(seal);
    }

    private static void discardExistingSeals(ServerLevel serverLevel, ServerPlayer target) {
        AABB searchBox = target.getBoundingBox().inflate(32.0D);
        for (HolyMagicalSealEntity existingSeal : serverLevel.getEntitiesOfClass(HolyMagicalSealEntity.class, searchBox,
                seal -> seal.tracks(target))) {
            existingSeal.clearEffectsAndDiscard();
        }
    }

    private static void spawnHolyVortex(ServerLevel level, Vec3 origin, float targetHeight) {
        double groundY = origin.y + 0.1D;
        double torsoY = origin.y + targetHeight * 0.6D;
        spawnHolyRing(level, origin, groundY, OUTER_PARTICLE_COUNT, OUTER_RADIUS, 0.14D);
        spawnHolyRing(level, origin, torsoY, INNER_PARTICLE_COUNT, INNER_RADIUS, 0.1D);
    }

    private static void spawnHolyRing(ServerLevel level, Vec3 origin, double y, int count, double radius, double outwardSpeed) {
        for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2.0D * i) / count;
            double cos = Math.cos(angle);
            double sin = Math.sin(angle);
            level.sendParticles(
                    ParticleTypes.END_ROD,
                    origin.x + cos * radius,
                    y + ((i & 1) == 0 ? 0.08D : 0.0D),
                    origin.z + sin * radius,
                    1,
                    cos * outwardSpeed,
                    0.01D,
                    sin * outwardSpeed,
                    0.0D
            );
        }
    }

    @OnlyIn(Dist.CLIENT)
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }
}
