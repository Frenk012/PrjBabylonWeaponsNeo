package com.rave.projectbabylonweapons.item;

import com.rave.projectbabylonweapons.handler.StaffProjectileAttackHelper;
import com.rave.projectbabylonweapons.world.entity.projectile.BasicSpellProjectileEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.AnimationManager.AnimationAccessor;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.eventlistener.AttackPhaseEndEvent;

public interface MagicProjectileStaffWeapon extends MagicMeleeWeapon {
    BasicSpellProjectileEntity createMagicProjectile(Level level);

    default float getProjectileMagicDamageMultiplier() {
        return 1.0F;
    }

    default float getMagicProjectileSpeed() {
        return 1.6F;
    }

    default float getMagicProjectileInaccuracy() {
        return 0.0F;
    }

    default int getMagicProjectileLifetime() {
        return 60;
    }

    default int getMagicProjectileTrailColor() {
        return 0xB970FF;
    }

    default double getMagicProjectileSpawnForwardOffset() {
        return 1.1D;
    }

    default double getMagicProjectileSpawnVerticalOffset() {
        return -0.1D;
    }

    default StunType getMagicProjectileStunType() {
        return StunType.SHORT;
    }

    default boolean shouldFireMagicProjectile(AnimationAccessor<? extends AttackAnimation> animation, AttackAnimation.Phase phase, int phaseOrder) {
        return phase != null;
    }

    default void fireMagicProjectiles(ServerPlayerPatch playerPatch, ItemStack weaponStack, AttackPhaseEndEvent event) {
        StaffProjectileAttackHelper.spawnProjectile(playerPatch, weaponStack, this);
    }
}

