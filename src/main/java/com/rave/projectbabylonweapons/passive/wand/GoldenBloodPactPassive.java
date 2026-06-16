package com.rave.projectbabylonweapons.passive.wand;

import com.rave.projectbabylonmaterials.ProjectBabylonMaterials;
import com.rave.projectbabylonmaterials.tooltip.TooltipFrameStyle;
import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.tooltip.WeaponPassiveTooltipData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class GoldenBloodPactPassive {
    private static final UUID MAX_HEALTH_MODIFIER_ID = UUID.fromString("5afdf51c-92d3-4c50-a5ef-d927b3c83b53");
    private static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("84c4ee46-f7c7-4f56-95e7-174fcbf4e031");
    private static final Map<UUID, PactState> ACTIVE_STATES = new ConcurrentHashMap<>();
    private static final WeaponPassiveTooltipData TOOLTIP = new WeaponPassiveTooltipData(
            Component.translatable("tooltip.project_babylon_weapons.passive.wand_golden.name"),
            ResourceLocation.fromNamespaceAndPath(ProjectBabylonMaterials.MODID, "textures/gui/tooltip/frame/material/gold_material_frame.png"),
            ResourceLocation.fromNamespaceAndPath(ProjectBabylonMaterials.MODID, "textures/gui/tooltip/icon/material/gold_material_icon.png"),
            List.of(
                    Component.translatable("tooltip.project_babylon_weapons.passive.wand_golden.line1").withStyle(ChatFormatting.GRAY),
                    Component.translatable("tooltip.project_babylon_weapons.passive.wand_golden.line2").withStyle(ChatFormatting.GRAY)
            ),
            TooltipFrameStyle.material("golden")
    );

    private GoldenBloodPactPassive() {
    }

    public static void onBattleWandAttack(LivingEntity attacker, ItemStack weaponStack) {
        GoldenBloodPactBalance.Profile profile = GoldenBloodPactBalance.resolve(weaponStack);
        if (profile == null || attacker.level().isClientSide) {
            return;
        }

        long gameTime = attacker.level().getGameTime();
        PactState state = ACTIVE_STATES.computeIfAbsent(attacker.getUUID(), ignored -> new PactState());
        state.stackCount = Math.min(maxStacks(profile), state.stackCount + 1);
        state.expiresAt = gameTime + profile.durationTicks();
        applyState(attacker, state, profile);
    }

    public static float getProjectileDamageMultiplier(LivingEntity attacker, ItemStack weaponStack) {
        GoldenBloodPactBalance.Profile profile = GoldenBloodPactBalance.resolve(weaponStack);
        if (profile == null) {
            return 1.0F;
        }

        PactState state = ACTIVE_STATES.get(attacker.getUUID());
        if (state == null || attacker.level().getGameTime() > state.expiresAt) {
            return 1.0F;
        }

        return (float) (1.0D + (state.stackCount * profile.stackPercent()));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) {
            return;
        }

        PactState state = ACTIVE_STATES.get(event.player.getUUID());
        if (state == null) {
            return;
        }

        if (event.player.level().getGameTime() > state.expiresAt) {
            clearState(event.player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        clearState(event.getEntity());
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            clearState(player);
        }
    }

    public static WeaponPassiveTooltipData getTooltipData() {
        return TOOLTIP;
    }

    private static int maxStacks(GoldenBloodPactBalance.Profile profile) {
        return Math.max(1, (int) Math.round(profile.maxPercent() / profile.stackPercent()));
    }

    private static void applyState(LivingEntity attacker, PactState state, GoldenBloodPactBalance.Profile profile) {
        double totalPercent = Math.min(profile.maxPercent(), state.stackCount * profile.stackPercent());
        AttributeInstance maxHealth = attacker.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.removeModifier(MAX_HEALTH_MODIFIER_ID);
            maxHealth.addTransientModifier(new AttributeModifier(MAX_HEALTH_MODIFIER_ID, "golden_blood_pact_health", -totalPercent, AttributeModifier.Operation.MULTIPLY_TOTAL));
            if (attacker.getHealth() > attacker.getMaxHealth()) {
                attacker.setHealth(attacker.getMaxHealth());
            }
        }

        AttributeInstance attackSpeed = attacker.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed != null) {
            attackSpeed.removeModifier(ATTACK_SPEED_MODIFIER_ID);
            attackSpeed.addTransientModifier(new AttributeModifier(ATTACK_SPEED_MODIFIER_ID, "golden_blood_pact_speed", totalPercent, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    private static void clearState(LivingEntity attacker) {
        ACTIVE_STATES.remove(attacker.getUUID());

        AttributeInstance maxHealth = attacker.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.removeModifier(MAX_HEALTH_MODIFIER_ID);
        }

        AttributeInstance attackSpeed = attacker.getAttribute(Attributes.ATTACK_SPEED);
        if (attackSpeed != null) {
            attackSpeed.removeModifier(ATTACK_SPEED_MODIFIER_ID);
        }
    }

    private static final class PactState {
        private int stackCount;
        private long expiresAt;
    }
}
