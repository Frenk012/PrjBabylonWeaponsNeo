package com.rave.projectbabylonweapons.skill.weapon_innate;

import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonweapons.handler.MagicMeleeWeaponHelper;
import com.rave.projectbabylonweapons.handler.StaffMagicArmorHelper;
import com.rave.projectbabylonweapons.handler.WeaponVisualEffectHelper;
import com.rave.projectbabylonweapons.init.PBWSounds;
import com.rave.projectbabylonweapons.item.MagicMeleeWeapon;
import com.rave.projectbabylonweapons.world.entity.effect.FireMagicalSealEntity;
import com.rave.projectbabylonweapons.world.entity.effect.FireStormEntity;
import net.minecraft.client.KeyMapping;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class FireStormSkill extends SimpleWeaponInnateSkill {
    private static final UUID FIRE_STORM_BEGIN_UUID = UUID.fromString("8df94a6e-a458-44d8-b98b-d4ab8894f530");
    private static final UUID FIRE_STORM_CONTACT_UUID = UUID.fromString("9edb2eb6-0529-44ad-8df9-74b68e60754e");
    private static final UUID FIRE_STORM_END_UUID = UUID.fromString("dcaec719-7208-49c5-ab0b-3f7976da17b0");
    private static final double SPAWN_FORWARD_OFFSET = 1.1D;
    private static final int TORNADO_LIFETIME_TICKS = 20 * 20;
    private static final int SEAL_FALLBACK_DURATION_TICKS = 60;
    private static final float DAMAGE_PER_SECOND_MULTIPLIER = 0.5F;

    public FireStormSkill(SimpleWeaponInnateSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecutor().getEventListener().addEventListener(
                EventType.ANIMATION_BEGIN_EVENT,
                FIRE_STORM_BEGIN_UUID,
                event -> {
                    if (container.getExecutor().isLogicalClient()) {
                        return;
                    }

                    if (event.getAnimation() != PBAnimations.FIRE_STORM.get()) {
                        return;
                    }

                    LivingEntity caster = container.getExecutor().getOriginal();
                    WeaponVisualEffectHelper.startFireStormCast(caster);
                    if (caster instanceof ServerPlayer serverPlayer) {
                        spawnSeal(serverPlayer, TORNADO_LIFETIME_TICKS);
                    }
                }
        );

        container.getExecutor().getEventListener().addEventListener(
                EventType.ATTACK_PHASE_END_EVENT,
                FIRE_STORM_CONTACT_UUID,
                event -> {
                    if (container.getExecutor().isLogicalClient()) {
                        return;
                    }

                    if (event.getAnimation() != PBAnimations.FIRE_STORM) {
                        return;
                    }

                    if (event.getPhaseOrder() != 0) {
                        return;
                    }

                    ServerPlayerPatch playerPatch = event.getPlayerPatch();
                    if (playerPatch == null) {
                        return;
                    }

                    WeaponVisualEffectHelper.burstFireStormCast(playerPatch.getOriginal());
                    playFireStormStart(playerPatch.getOriginal());
                    spawnTornado(playerPatch, playerPatch.getOriginal().getMainHandItem());
                }
        );

        container.getExecutor().getEventListener().addEventListener(
                EventType.ANIMATION_END_EVENT,
                FIRE_STORM_END_UUID,
                event -> {
                    if (event.getAnimation() != PBAnimations.FIRE_STORM.get()) {
                        return;
                    }

                    LivingEntity caster = container.getExecutor().getOriginal();
                    WeaponVisualEffectHelper.stopFireStormCast(caster);
                }
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.ANIMATION_BEGIN_EVENT, FIRE_STORM_BEGIN_UUID);
        container.getExecutor().getEventListener().removeListener(EventType.ATTACK_PHASE_END_EVENT, FIRE_STORM_CONTACT_UUID);
        container.getExecutor().getEventListener().removeListener(EventType.ANIMATION_END_EVENT, FIRE_STORM_END_UUID);
        WeaponVisualEffectHelper.stopFireStormCast(container.getExecutor().getOriginal());
        super.onRemoved(container);
    }

    private static void playFireStormStart(LivingEntity caster) {
        caster.level().playSound(
                null,
                caster.getX(),
                caster.getY(),
                caster.getZ(),
                PBWSounds.FIRE_STORM_START.get(),
                SoundSource.PLAYERS,
                1.2F,
                1.0F
        );
    }
    private static void spawnTornado(ServerPlayerPatch playerPatch, ItemStack weaponStack) {
        LivingEntity caster = playerPatch.getOriginal();
        if (!(caster.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!(weaponStack.getItem() instanceof MagicMeleeWeapon magicWeapon)) {
            return;
        }

        float damagePerSecond = MagicMeleeWeaponHelper.calculateRawMagicDamage(caster, weaponStack, magicWeapon, 1.0F, DAMAGE_PER_SECOND_MULTIPLIER);
        if (damagePerSecond <= 0.0F) {
            return;
        }

        Vec3 forward = getFlatForward(caster);
        if (forward.lengthSqr() < 1.0E-6D) {
            return;
        }

        Vec3 spawnPos = caster.position().add(forward.scale(SPAWN_FORWARD_OFFSET));
        FireStormEntity tornado = new FireStormEntity(serverLevel);
        tornado.configure(
                caster,
                weaponStack,
                magicWeapon.getMagicDamageType(),
                damagePerSecond,
                StaffMagicArmorHelper.resolveWeaponMagicArmorNegation(caster, weaponStack),
                StaffMagicArmorHelper.resolveWeaponImpact(caster, weaponStack),
                TORNADO_LIFETIME_TICKS,
                forward
        );
        tornado.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        serverLevel.addFreshEntity(tornado);
    }

    private static void spawnSeal(ServerPlayer player, int durationTicks) {
        ServerLevel serverLevel = player.serverLevel();
        clearExistingSeals(serverLevel, player, false);

        FireMagicalSealEntity seal = new FireMagicalSealEntity(serverLevel);
        int sealDuration = Math.max(durationTicks, SEAL_FALLBACK_DURATION_TICKS);
        seal.configure(player, sealDuration);
        seal.setPos(player.getX(), player.getY() + 0.02D, player.getZ());
        serverLevel.addFreshEntity(seal);
    }

    private static void despawnSeal(ServerPlayer player) {
        clearExistingSeals(player.serverLevel(), player, true);
    }

    private static void clearExistingSeals(ServerLevel serverLevel, ServerPlayer player, boolean despawnOnly) {
        AABB searchBox = player.getBoundingBox().inflate(32.0D);
        for (FireMagicalSealEntity existingSeal : serverLevel.getEntitiesOfClass(FireMagicalSealEntity.class, searchBox,
                seal -> seal.tracks(player))) {
            if (despawnOnly) {
                existingSeal.beginDespawnNow();
            } else {
                existingSeal.clearAndDiscard();
            }
        }
    }

    private static Vec3 getFlatForward(LivingEntity caster) {
        Vec3 look = caster.getLookAngle();
        Vec3 flatForward = new Vec3(look.x, 0.0D, look.z);
        if (flatForward.lengthSqr() < 1.0E-6D) {
            return Vec3.ZERO;
        }
        return flatForward.normalize();
    }

    @OnlyIn(Dist.CLIENT)
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }
}








