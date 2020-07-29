package de.devnytake.skrimeparty.util;

import de.devnytake.skrimeparty.games.tntrun.TNTRun;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:26
 */

public class LocationUtil {

    private Location location;
    private String root;

    private File file;
    private YamlConfiguration cfg;

    public LocationUtil(Location location, String root){
        this.location = location;
        this.root = root;

        this.file = new File("plugins//PartyGames", "maps.yml");
        this.cfg = YamlConfiguration.loadConfiguration(this.file);
    }

    public LocationUtil(String root){
        this(null, root);
    }

    public void saveLocation(){
        cfg.set(root + ".World", location.getWorld().getName());
        cfg.set(root + ".X", location.getX());
        cfg.set(root + ".Y", location.getY());
        cfg.set(root + ".Z", location.getZ());
        cfg.set(root + ".Yaw", location.getYaw());
        cfg.set(root + ".Pitch", location.getPitch());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Location loadLocation(){
        if(cfg.contains(root)) {
            World world = Bukkit.getWorld(cfg.getString(root + ".World"));
            double x = cfg.getDouble(root + ".X");
            double y = cfg.getDouble(root + ".Y");
            double z = cfg.getDouble(root + ".Z");
            float yaw = (float) cfg.getDouble(root + ".Yaw");
            float pitch = (float) cfg.getDouble(root + ".Pitch");
            return new Location(world, x, y, z, yaw, pitch);
        }else{
            return null;
        }
    }
}
