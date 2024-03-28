package io.sr26.creeperconfetti;


import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigHandler {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ModConfigSpec spec = BUILDER.build();

    public static class General {
        public final ModConfigSpec.ConfigValue<Boolean> DamagePlayers;
        public final ModConfigSpec.ConfigValue<Integer> ConfettiChance;
        public final ModConfigSpec.ConfigValue<Integer> CheerChance;

        public General(ModConfigSpec.Builder builder) {
            builder.push("General");
            DamagePlayers = builder
                    .comment("Confetti Explosions Damage Players [false/true|default:false]")
                    .translation("damageplayers.creeperconfetti.config")
                    .define("damagePlayers", false);
            ConfettiChance = builder
                    .comment("The %chance that any given creeper will explode into confetti  [0..100|default:100]")
                    .translation("confettichance.creeperconfetti.config")
                    .defineInRange("confettiChance", 100, 0,100);
            CheerChance = builder
                    .comment("The %chance that any given creeper that explodes into confetti will play the cheer sound.  Set to 0 to disable.  [0..100|default:100]")
                    .translation("cheerchance.creeperconfetti.config")
                    .defineInRange("cheerChance", 5, 0,100);
            builder.pop();
        }
    }
}