package de.devnytake.skrimeparty.map;

import de.devnytake.skrimeparty.util.LocationUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:32
 */

public class Map {

    File file;
    YamlConfiguration cfg;

    private String name;
    private String spielModi;
    private String builder;
    private Location spawnLocation, highLocation;

    public Map(String spielModi, String name) {
        this.name = name;
        this.spielModi = spielModi;
        file = new File("plugins//PartyGames", "maps.yml");
        cfg = YamlConfiguration.loadConfiguration(file);

        if (exists()) {
            builder = cfg.getString("Maps." + spielModi + ".Builder");
        }
    }

    public void create(String builder) {
        cfg.set(spielModi + "." + "Maps." + name + ".Builder", builder);
        saveConfig();
    }

    public boolean exists() {
        return cfg.getString(spielModi + ".Maps." + name + ".Builder") != null;
    }

    public void setSpawn(Location location) {
        spawnLocation = location;
        new LocationUtil(location, spielModi + "." + "Maps." + name + ".Spawn").saveLocation();
    }

    public void setHigh(Location location){
        highLocation = location;
        new LocationUtil(location, spielModi + "." + "Maps." + name + ".High").saveLocation();
    }

    private void saveConfig() {
        try {
            cfg.save(file);
        } catch (IOException e) {

        }
    }

    public String getName() {
        return name;
    }

    public String getBuilder() {
        return builder;
    }

    public String getSpielModi() {
        return spielModi;
    }

}
