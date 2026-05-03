package io.sr26.creeperconfetti.platform;

import io.sr26.creeperconfetti.platform.services.IConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

public class NeoForgeConfig implements IConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.IntValue CONFETTI_CHANCE;
    public static final ModConfigSpec.IntValue CHEER_CHANCE;
    public static final ModConfigSpec.BooleanValue DAMAGE_PLAYERS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> COLOR_COMBOS;

    static {
        BUILDER.push("Creeper Confetti Config");
        CONFETTI_CHANCE = BUILDER.comment("The percentage chance a creeper will explode into confetti").defineInRange("confettiChance", 100, 0, 100);
        CHEER_CHANCE = BUILDER.comment("The percentage chance a confetti explosion will also cheer").defineInRange("cheerChance", 5, 0, 100);
        DAMAGE_PLAYERS = BUILDER.comment("If true, confetti explosions will still damage players (but not blocks)").define("damagePlayers", false);
        COLOR_COMBOS = BUILDER.comment("Curated color combos (comma separated hex strings)")
                .defineList("colorCombos", List.of(
                        "#E67E22,#00E0FF,#0FFF00,#FF00FF,#FFFF00",
                        "#FF4500,#FF8C00,#FFD700,#FFFF00,#FFFFFF",
                        "#0000FF,#00FFFF,#008080,#7FFFD4,#FFFFFF",
                        "#008000,#32CD32,#006400,#8B4513,#FFD700",
                        "#FF0000,#FF7F00,#FFFF00,#00FF00,#0000FF,#4B0082,#8B00FF",
                        "#FFB6C1,#ADD8E6,#FFFACD,#98FB98,#E6E6FA",
                        "#5BCEFA,#F5A9B8,#FFFFFF,#F5A9B8,#5BCEFA",
                        "#D60270,#9B4F96,#0038A8",
                        "#FF218C,#FFD800,#21B1FF",
                        "#FFF433,#FFFFFF,#9B59D0,#2D2D2D",
                        "#FF0018,#FFA52C,#FFFF41,#008018,#0000F9,#86007D",
                        "#FF00FF,#00FFFF,#00FF00,#FFFF00",
                        "#4B0082,#191970,#FF00FF,#C0C0C0",
                        "#FFFFFF,#C0C0C0,#808080,#404040",
                        "#D35400,#C0392B,#F1C40F,#7E5109,#F39C12"
                ), o -> o instanceof String);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    @Override
    public int getConfettiChance() { return CONFETTI_CHANCE.get(); }

    @Override
    public int getCheerChance() { return CHEER_CHANCE.get(); }

    @Override
    public boolean getDamagePlayers() { return DAMAGE_PLAYERS.get(); }

    @Override
    public List<int[]> getColorCombos() {
        List<int[]> combos = new ArrayList<>();
        for (String s : COLOR_COMBOS.get()) {
            String[] parts = s.split(",");
            int[] colors = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                try {
                    colors[i] = Integer.parseInt(parts[i].trim().replace("#", ""), 16);
                } catch (NumberFormatException e) {
                    colors[i] = 0xFFFFFF;
                }
            }
            combos.add(colors);
        }
        return combos;
    }
}
