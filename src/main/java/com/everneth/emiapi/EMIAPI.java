package com.everneth.emiapi;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.idb.*;
import com.everneth.emiapi.api.AdvancementController;
import com.everneth.emiapi.api.Path;
import com.everneth.emiapi.api.StatisticsController;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Spark;

import java.io.File;

import static spark.Spark.get;
import static spark.Spark.port;

public class EMIAPI extends JavaPlugin {
    private static EMIAPI plugin;
    private static BukkitCommandManager commandManager;
    FileConfiguration config = getConfig();
    String configPath = getDataFolder() + System.getProperty("file.separator") + "config.yml";
    File configFile = new File(configPath);

    @Override
    public void onEnable()
    {
        plugin = this;
        if(!configFile.exists())
        {
            this.saveDefaultConfig();
        }

        port(this.getConfig().getInt("api-port"));
        get(Path.Web.ONE_STATS, StatisticsController.getPlayerStats);
        get(Path.Web.ONE_ADV, AdvancementController.getPlayerAdvs);
        get("*", (request, response) -> "404 not found!!");

        Spark.exception(Exception.class, (exception, request, response) -> {exception.printStackTrace();});
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
