package me.mike3132.buildmode.SetManager;


import java.util.HashSet;
import java.util.UUID;

public class BuildSet {

    private final static HashSet<UUID> jrBuildingPlayers = new HashSet<>();
    private final static HashSet<UUID> srBuildingPlayers = new HashSet<>();

    public static HashSet<UUID> getBuildingPlayers(String string) {
        return (string.equalsIgnoreCase("Jr")) ? jrBuildingPlayers : srBuildingPlayers;
    }

    public static void addBuildingPlayers(UUID player, String string) {
        getBuildingPlayers(string).add(player);
    }

    public static void removeBuildingPlayers(UUID player, String string) {
        getBuildingPlayers(string).remove(player);
    }

}
