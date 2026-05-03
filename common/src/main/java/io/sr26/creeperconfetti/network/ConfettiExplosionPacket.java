package io.sr26.creeperconfetti.network;

import io.sr26.creeperconfetti.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ConfettiExplosionPacket(int entityId) implements CustomPacketPayload {
    public static final Type<ConfettiExplosionPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "confetti_explosion"));
    public static final StreamCodec<FriendlyByteBuf, ConfettiExplosionPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, ConfettiExplosionPacket::entityId, ConfettiExplosionPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
