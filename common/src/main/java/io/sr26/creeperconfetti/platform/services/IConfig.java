package io.sr26.creeperconfetti.platform.services;

import java.util.List;

public interface IConfig {
    int getConfettiChance();
    int getCheerChance();
    boolean getDamagePlayers();
    List<int[]> getColorCombos();
}
