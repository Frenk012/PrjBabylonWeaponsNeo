package com.rave.projectbabylonweapons.network;

import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonweapons.skill.weapon_innate.SickleThrowSkill;
import com.rave.projectbabylonweapons.world.entity.projectile.SickleProjectileEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public class CPPullTargetToOwner {

    public CPPullTargetToOwner() {}

    public CPPullTargetToOwner(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buf) {}

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            if (playerPatch == null) return;

            SickleProjectileEntity projectile = SickleThrowSkill.getActiveProjectilePublic(player);
            if (projectile == null || !projectile.isTethered()) return;

            // Притягиваем врага к игроку
            playerPatch.playAnimationSynchronized(PBAnimations.SICKLE_HOOKING, 0.0f);
            projectile.pullTargetToOwner();
        });
        context.setPacketHandled(true);
    }
}
