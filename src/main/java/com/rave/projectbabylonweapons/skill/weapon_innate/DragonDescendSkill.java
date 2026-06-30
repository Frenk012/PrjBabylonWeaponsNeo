package com.rave.projectbabylonweapons.skill.weapon_innate;

import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonweapons.handler.MagicMeleeWeaponHelper;
import com.rave.projectbabylonweapons.handler.StaffMagicArmorHelper;
import com.rave.projectbabylonweapons.handler.WeaponVisualEffectHelper;
import com.rave.projectbabylonweapons.item.MagicProjectileStaffWeapon;
import com.rave.projectbabylonweapons.world.entity.projectile.DragonDescendProjectileEntity;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DragonDescendSkill extends WeaponInnateSkill {
    private static final UUID CONTACT_UUID = UUID.fromString("277c7359-6857-4ef3-bc44-f7297f94c22a");
    private static final Set<UUID> ACTIVE_CASTER_IDS = ConcurrentHashMap.newKeySet();
    private static final float DIRECT_DAMAGE_MULTIPLIER = 1.0F;
    private static final float TRAIL_DAMAGE_MULTIPLIER = 0.25F;
    private static final float RENDER_SCALE = 5.0F;
    private static final double LAUNCH_FORWARD_OFFSET = 1.35D;
    private static final double LAUNCH_HEIGHT_FACTOR = 0.72D;

    public DragonDescendSkill(SkillBuilder<? extends WeaponInnateSkill> builder) {
        super(builder);
    }

    public static boolean isDragonDescendActive(LivingEntity entity) {
        return entity != null && ACTIVE_CASTER_IDS.contains(entity.getUUID());
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecutor().getEventListener().addEventListener(
                EventType.ATTACK_PHASE_END_EVENT,
                CONTACT_UUID,
                event -> {
                    if (container.getExecutor().isLogicalClient()) {
                        return;
                    }

                    if (event.getAnimation() != PBAnimations.DRAGON_DESCEND || event.getPhaseOrder() != 0) {
                        return;
                    }

                    ServerPlayerPatch playerPatch = event.getPlayerPatch();
                    if (playerPatch == null) {
                        return;
                    }

                    WeaponVisualEffectHelper.burstDragonDescendCast(playerPatch.getOriginal());
                    spawnAndLaunchProjectile(playerPatch, playerPatch.getOriginal().getMainHandItem());
                }
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.ATTACK_PHASE_END_EVENT, CONTACT_UUID);
        ACTIVE_CASTER_IDS.remove(container.getExecutor().getOriginal().getUUID());
        WeaponVisualEffectHelper.stopDragonDescendCast(container.getExecutor().getOriginal());
        super.onRemoved(container);
    }

    @Override
    public void executeOnServer(SkillContainer container, FriendlyByteBuf args) {
        if (this.isActivated(container)) {
            this.cancelOnServer(container, args);
        } else {
            super.executeOnServer(container, args);
            container.activate();
            ACTIVE_CASTER_IDS.add(container.getExecutor().getOriginal().getUUID());
            container.getServerExecutor().modifyLivingMotionByCurrentItem(false);
            WeaponVisualEffectHelper.startDragonDescendCast(container.getExecutor().getOriginal());
            container.getExecutor().playAnimationSynchronized(PBAnimations.DRAGON_DESCEND, 0.0F);
        }
    }

    @Override
    public void executeOnClient(SkillContainer container, FriendlyByteBuf args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    @Override
    public void cancelOnServer(SkillContainer container, FriendlyByteBuf args) {
        ACTIVE_CASTER_IDS.remove(container.getExecutor().getOriginal().getUUID());
        WeaponVisualEffectHelper.stopDragonDescendCast(container.getExecutor().getOriginal());
        container.deactivate();
        super.cancelOnServer(container, args);
        container.getServerExecutor().modifyLivingMotionByCurrentItem(false);
    }

    @Override
    public void cancelOnClient(SkillContainer container, FriendlyByteBuf args) {
        container.deactivate();
        super.cancelOnClient(container, args);
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        if (!this.properties.isEmpty()) {
            PBAnimations.DRAGON_DESCEND.get().phases[0].addProperties(this.properties.get(0).entrySet());
        }
        return this;
    }

    private static void spawnAndLaunchProjectile(ServerPlayerPatch playerPatch, ItemStack weaponStack) {
        LivingEntity caster = playerPatch.getOriginal();
        if (!(caster.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (!(weaponStack.getItem() instanceof MagicProjectileStaffWeapon weapon)) {
            return;
        }

        float directDamage = MagicMeleeWeaponHelper.calculateRawMagicDamage(caster, weaponStack, weapon, 1.0F, DIRECT_DAMAGE_MULTIPLIER);
        if (directDamage <= 0.0F) {
            return;
        }

        Vec3 forward = getFlatForward(caster);
        Vec3 spawnPos = caster.position()
                .add(forward.scale(LAUNCH_FORWARD_OFFSET))
                .add(0.0D, caster.getBbHeight() * LAUNCH_HEIGHT_FACTOR, 0.0D);

        DragonDescendProjectileEntity projectile = new DragonDescendProjectileEntity(serverLevel);
        projectile.configureProjectile(
                caster,
                weaponStack,
                weapon.getMagicDamageType(),
                directDamage,
                directDamage * TRAIL_DAMAGE_MULTIPLIER,
                StaffMagicArmorHelper.resolveWeaponMagicArmorNegation(caster, weaponStack),
                StaffMagicArmorHelper.resolveWeaponImpact(caster, weaponStack),
                weapon.getMagicProjectileStunType(),
                Math.max(weapon.getMagicProjectileLifetime(), 70),
                weapon.getMagicProjectileTrailColor(),
                RENDER_SCALE
        );
        projectile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        projectile.launch(spawnPos, forward, weapon.getMagicProjectileSpeed());
        serverLevel.addFreshEntity(projectile);
    }

    private static Vec3 getFlatForward(LivingEntity caster) {
        Vec3 look = caster.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0.0D, look.z);
        if (flat.lengthSqr() < 1.0E-6D) {
            return new Vec3(0.0D, 0.0D, 1.0D);
        }
        return flat.normalize();
    }

    @OnlyIn(Dist.CLIENT)
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }
}
