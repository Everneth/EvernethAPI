package com.everneth.emiapi;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.idb.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class EMIAPI extends JavaPlugin {
    private static EMIAPI plugin;
    private static BukkitCommandManager commandManager;
    FileConfiguration config = getConfig();
    String configPath = getDataFolder() + System.getProperty("file.separator") + "config.yml";
    File configFile = new File(configPath);

    @Override
    public void onEnable()
    {

    }
    @Override
    public void onDisable()
    {

    }

    public static EMIAPI getPlugin()
    {
        return plugin;
    }
}
