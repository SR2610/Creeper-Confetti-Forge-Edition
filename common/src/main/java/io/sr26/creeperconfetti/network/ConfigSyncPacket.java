package io.sr26.creeperconfetti.network;

import io.sr26.creeperconfetti.Constants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ConfigSyncPacket(int confettiChance, boolean damagePlayers) implements CustomPacketPayload {
    public static final Type<ConfigSyncPacket> TYPE = new Type<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "config_sync"));
    public static final StreamCodec<FriendlyByteBuf, ConfigSyncPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, ConfigSyncPacket::confettiChance,
        ByteBufCodecs.BOOL, ConfigSyncPacket::damagePlayers,
        ConfigSyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
