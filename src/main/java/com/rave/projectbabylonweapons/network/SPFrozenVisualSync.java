package com.rave.projectbabylonweapons.network;

import com.rave.projectbabylonweapons.client.FrozenEffectRenderHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SPFrozenVisualSync {
    private final int entityId;
    private final boolean frozen;

    public SPFrozenVisualSync(int entityId, boolean frozen) {
        this.entityId = entityId;
        this.frozen = frozen;
    }

    public SPFrozenVisualSync(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.frozen = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeBoolean(this.frozen);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> FrozenEffectRenderHandler.updateFrozenVisualState(this.entityId, this.frozen));
        context.setPacketHandled(true);
    }
}
