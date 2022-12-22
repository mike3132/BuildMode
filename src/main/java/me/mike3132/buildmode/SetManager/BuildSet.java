package me.mike3132.buildmode.SetManager;


import java.util.HashSet;
import java.util.UUID;

public class BuildSet {

    private final static HashSet<UUID> buildingPlayers = new HashSet<>();

    public static HashSet<UUID> getBuildingPlayers() {
        return buildingPlayers;
    }

    public static void addBuildingPlayers(UUID player) {
        buildingPlayers.add(player);
    }

    public static void removeBuildingPlayers(UUID player) {
        buildingPlayers.remove(player);
    }

}
