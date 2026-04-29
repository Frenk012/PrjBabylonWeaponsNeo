package com.rave.projectbabylonweapons.passive.netherite;

import com.rave.projectbabylonmaterials.ProjectBabylonMaterials;
import com.rave.projectbabylonmaterials.tooltip.TooltipFrameStyle;
import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.init.PBModEffects;
import com.rave.projectbabylonweapons.init.PBWSounds;
import com.rave.projectbabylonweapons.tooltip.WeaponPassiveTooltipData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class NetheriteBrimstonePassive {
    private static final ThreadLocal<Set<UUID>> PROCESSING_ENTITIES = ThreadLocal.withInitial(HashSet::new);
    private static final ThreadLocal<Boolean> PROCESSING_BRIMSTONE_BLAST = ThreadLocal.withInitial(() -> false);
    private static final WeaponPassiveTooltipData TOOLTIP = new WeaponPassiveTooltipData(
            Component.translatable("tooltip.project_babylon_weapons.passive.netherite.name"),
            ResourceLocation.fromNamespaceAndPath(ProjectBabylonMaterials.MODID, "textures/gui/tooltip/frame/material/dragonsteel_material_frame.png"),
            ResourceLocation.fromNamespaceAndPath(ProjectBabylonMaterials.MODID, "textures/gui/tooltip/icon/material/dragonsteel_material_icon.png"),
            List.of(
                    Component.translatable("tooltip.project_babylon_weapons.passive.netherite.line1").withStyle(ChatFormatting.GRAY),
                    Component.translatable("tooltip.project_babylon_weapons.passive.netherite.line2").withStyle(ChatFormatting.GRAY),
                    Component.translatable("tooltip.project_babylon_weapons.passive.netherite.line3").withStyle(ChatFormatting.GRAY)
            ),
            TooltipFrameStyle.material("netherite")
    );

    private NetheriteBrimstonePassive() {
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.isCanceled() || event.getAmount() <= 0.0F) {
            return;
        }

        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
            return;
        }

        if (attacker.level().isClientSide) {
            return;
        }

        if (PROCESSING_BRIMSTONE_BLAST.get()) {
            return;
        }

        ItemStack weapon = attacker.getMainHandItem();
        NetheriteBrimstoneBalance.Profile profile = NetheriteBrimstoneBalance.resolve(weapon);
        if (profile == null) {
            return;
        }

        LivingEntity target = event.getEntity();
        UUID targetId = target.getUUID();
        if (PROCESSING_ENTITIES.get().contains(targetId)) {
            return;
        }

        if (target.hasEffect(PBModEffects.BRIMSTONE_FIRE.get())) {
            if (rollChance(attacker, profile.brimstoneBlastProcChance())) {
                triggerBrimstoneBlast(attacker, target, event.getSource(), profile);
                clearBrimstoneEffects(target);
            }
            return;
        }

        if (target.hasEffect(PBModEffects.BRIMSTONE_FLAMES.get())) {
            if (rollChance(attacker, profile.brimstoneFireProcChance())) {
                target.addEffect(new MobEffectInstance(
                        PBModEffects.BRIMSTONE_FIRE.get(),
                        profile.brimstoneFireDurationTicks()
                ));
            }
            return;
        }

        if (target.isOnFire()) {
            if (rollChance(attacker, profile.brimstoneFlamesProcChance())) {
                target.addEffect(new MobEffectInstance(
                        PBModEffects.BRIMSTONE_FLAMES.get(),
                        profile.brimstoneFlamesDurationTicks()
                ));
            }
            return;
        }

        if (rollChance(attacker, profile.igniteProcChance())) {
            target.setSecondsOnFire(8);
        }
    }

    public static WeaponPassiveTooltipData getTooltipData() {
        return TOOLTIP;
    }

    public static void appendTooltip(List<Component> tooltip) {
        TOOLTIP.appendTooltip(tooltip);
    }

    private static void triggerBrimstoneBlast(LivingEntity attacker, LivingEntity centerTarget, DamageSource source, NetheriteBrimstoneBalance.Profile profile) {
        double attackDamage = attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float blastDamage = (float) (attackDamage * profile.brimstoneBlastDamageMultiplier());

        AABB area = centerTarget.getBoundingBox().inflate(
                profile.brimstoneBlastRadiusBlocks(),
                1.0D,
                profile.brimstoneBlastRadiusBlocks()
        );

        if (attacker.level() instanceof ServerLevel serverLevel) {
            PROCESSING_BRIMSTONE_BLAST.set(true);
            PROCESSING_ENTITIES.get().add(centerTarget.getUUID());
            try {
                for (LivingEntity victim : serverLevel.getEntitiesOfClass(LivingEntity.class, area, entity -> entity.isAlive() && entity != attacker)) {
                    if (!victim.isAlive()) {
                        continue;
                    }

                    int originalInvulnerableTime = victim.invulnerableTime;
                    victim.invulnerableTime = 0;
                    try {
                        victim.hurt(source, blastDamage);
                    } finally {
                        victim.invulnerableTime = originalInvulnerableTime;
                    }
                }
            } finally {
                PROCESSING_ENTITIES.get().remove(centerTarget.getUUID());
                PROCESSING_BRIMSTONE_BLAST.set(false);
            }

            spawnBlastParticles(serverLevel, centerTarget.position());
            serverLevel.playSound(
                    null,
                    centerTarget.getX(),
                    centerTarget.getY(),
                    centerTarget.getZ(),
                    PBWSounds.FIRE_BLAST.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
    }

    private static void spawnBlastParticles(ServerLevel level, Vec3 center) {
        int ringPoints = 20;
        double radius = 1.5D;
        for (int i = 0; i < ringPoints; i++) {
            double angle = (Math.PI * 2.0D * i) / ringPoints;
            double x = center.x + Math.cos(angle) * radius;
            double z = center.z + Math.sin(angle) * radius;
            level.sendParticles(ParticleTypes.FLAME, x, center.y + 0.1D, z, 2, 0.0D, 0.02D, 0.0D, 0.01D);
        }

        level.sendParticles(ParticleTypes.FLAME, center.x, center.y + 0.5D, center.z, 24, 0.35D, 0.2D, 0.35D, 0.03D);
        level.sendParticles(ParticleTypes.LAVA, center.x, center.y + 0.3D, center.z, 8, 0.25D, 0.15D, 0.25D, 0.01D);
        level.sendParticles(ParticleTypes.SMOKE, center.x, center.y + 0.4D, center.z, 10, 0.25D, 0.2D, 0.25D, 0.01D);
    }

    private static void clearBrimstoneEffects(LivingEntity target) {
        target.removeEffect(PBModEffects.BRIMSTONE_FIRE.get());
        target.removeEffect(PBModEffects.BRIMSTONE_FLAMES.get());
    }

    private static boolean rollChance(LivingEntity attacker, float chance) {
        return attacker.getRandom().nextFloat() < chance;
    }
}
