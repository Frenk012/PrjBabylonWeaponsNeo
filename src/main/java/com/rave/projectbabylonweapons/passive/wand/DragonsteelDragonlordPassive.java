package com.rave.projectbabylonweapons.passive.wand;

import com.rave.projectbabylonmaterials.ProjectBabylonMaterials;
import com.rave.projectbabylonmaterials.tooltip.TooltipFrameStyle;
import com.rave.projectbabylonweapons.handler.StaffProjectileAttackHelper;
import com.rave.projectbabylonweapons.item.MagicProjectileStaffWeapon;
import com.rave.projectbabylonweapons.tooltip.WeaponPassiveTooltipData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.List;

public final class DragonsteelDragonlordPassive {
    private static final WeaponPassiveTooltipData TOOLTIP = new WeaponPassiveTooltipData(
            Component.translatable("tooltip.project_babylon_weapons.passive.wand_dragonsteel.name"),
            ResourceLocation.fromNamespaceAndPath(ProjectBabylonMaterials.MODID, "textures/gui/tooltip/frame/material/dragonsteel_material_frame.png"),
            ResourceLocation.fromNamespaceAndPath(ProjectBabylonMaterials.MODID, "textures/gui/tooltip/icon/material/dragonsteel_material_icon.png"),
            List.of(
                    Component.translatable("tooltip.project_babylon_weapons.passive.wand_dragonsteel.line1").withStyle(ChatFormatting.GRAY)
            ),
            TooltipFrameStyle.material("dragonsteel")
    );

    private DragonsteelDragonlordPassive() {
    }

    public static void spawnMiniProjectiles(ServerPlayerPatch playerPatch, ItemStack weaponStack, MagicProjectileStaffWeapon weapon,
                                            Vec3 direction, double forwardOffset, double verticalOffset, float damageMultiplier) {
        DragonsteelDragonlordBalance.Profile profile = DragonsteelDragonlordBalance.resolve(weaponStack);
        if (profile == null) {
            return;
        }

        float miniMultiplier = damageMultiplier * profile.miniProjectileDamageMultiplier();
        StaffProjectileAttackHelper.spawnProjectileWithoutPassives(playerPatch, weaponStack, weapon, direction, forwardOffset, -profile.miniProjectileSideOffset(), verticalOffset, miniMultiplier, 0.5F);
        StaffProjectileAttackHelper.spawnProjectileWithoutPassives(playerPatch, weaponStack, weapon, direction, forwardOffset, profile.miniProjectileSideOffset(), verticalOffset, miniMultiplier, 0.5F);
    }

    public static WeaponPassiveTooltipData getTooltipData() {
        return TOOLTIP;
    }
}

