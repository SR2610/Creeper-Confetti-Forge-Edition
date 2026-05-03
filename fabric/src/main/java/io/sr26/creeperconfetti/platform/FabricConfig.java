package io.sr26.creeperconfetti.platform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.sr26.creeperconfetti.Constants;
import io.sr26.creeperconfetti.platform.services.IConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FabricConfig implements IConfig {
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), Constants.MOD_ID + ".json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private ConfigData data = new ConfigData();

    private static FabricConfig INSTANCE;

    public FabricConfig() {
        INSTANCE = this;
        load();
    }

    public static void setSyncedValues(int confettiChance, boolean damagePlayers) {
        if (INSTANCE != null) {
            INSTANCE.data.confettiChance = confettiChance;
            INSTANCE.data.damagePlayers = damagePlayers;
            Constants.LOG.info("Synced config from server: confettiChance=" + confettiChance + ", damagePlayers=" + damagePlayers);
        }
    }

    private void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                data = GSON.fromJson(reader, ConfigData.class);
            } catch (Exception e) {
                Constants.LOG.error("Failed to load config", e);
            }
        } else {
            save();
        }
    }

    private void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(data, writer);
        } catch (Exception e) {
            Constants.LOG.error("Failed to save config", e);
        }
    }

    @Override
    public int getConfettiChance() { return data.confettiChance; }

    @Override
    public int getCheerChance() { return data.cheerChance; }

    @Override
    public boolean getDamagePlayers() { return data.damagePlayers; }

    @Override
    public List<int[]> getColorCombos() {
        List<int[]> combos = new ArrayList<>();
        for (List<String> comboStrings : data.colorCombos) {
            int[] colors = new int[comboStrings.size()];
            for (int i = 0; i < comboStrings.size(); i++) {
                try {
                    colors[i] = Integer.parseInt(comboStrings.get(i).replace("#", ""), 16);
                } catch (NumberFormatException e) {
                    colors[i] = 0xFFFFFF;
                }
            }
            combos.add(colors);
        }
        return combos;
    }

    private static class ConfigData {
        int confettiChance = 100;
        int cheerChance = 5;
        boolean damagePlayers = false;
        List<List<String>> colorCombos = List.of(
                List.of("#E67E22", "#00E0FF", "#0FFF00", "#FF00FF", "#FFFF00"),
                List.of("#FF4500", "#FF8C00", "#FFD700", "#FFFF00", "#FFFFFF"),
                List.of("#0000FF", "#00FFFF", "#008080", "#7FFFD4", "#FFFFFF"),
                List.of("#008000", "#32CD32", "#006400", "#8B4513", "#FFD700"),
                List.of("#FF0000", "#FF7F00", "#FFFF00", "#00FF00", "#0000FF", "#4B0082", "#8B00FF"),
                List.of("#FFB6C1", "#ADD8E6", "#FFFACD", "#98FB98", "#E6E6FA"),
                List.of("#5BCEFA", "#F5A9B8", "#FFFFFF", "#F5A9B8", "#5BCEFA"),
                List.of("#D60270", "#9B4F96", "#0038A8"),
                List.of("#FF218C", "#FFD800", "#21B1FF"),
                List.of("#FFF433", "#FFFFFF", "#9B59D0", "#2D2D2D"),
                List.of("#FF0018", "#FFA52C", "#FFFF41", "#008018", "#0000F9", "#86007D"),
                List.of("#FF00FF", "#00FFFF", "#00FF00", "#FFFF00"),
                List.of("#4B0082", "#191970", "#FF00FF", "#C0C0C0"),
                List.of("#FFFFFF", "#C0C0C0", "#808080", "#404040"),
                List.of("#D35400", "#C0392B", "#F1C40F", "#7E5109", "#F39C12")
        );
    }
}
