package com.everneth.emiapi;

import co.aikar.commands.BukkitCommandManager;
import com.everneth.emiapi.api.*;
import com.everneth.emiapi.models.ApiToken;
import com.everneth.emiapi.utils.FileUtils;
import com.google.gson.Gson;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Spark;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static spark.Spark.*;

public class EMIAPI extends JavaPlugin {
    private static EMIAPI plugin;
    private static BukkitCommandManager commandManager;
    FileConfiguration config = getConfig();
    String configPath = getDataFolder() + System.getProperty("file.separator") + "config.yml";
    File configFile = new File(configPath);

    public static List<ApiToken> tokens;

    @Override
    public void onEnable()
    {
        plugin = this;
        if(!configFile.exists())
        {
            this.saveDefaultConfig();
            config = getConfig();
        }

        Gson gson = new Gson();
        try {
            tokens = gson.fromJson(FileUtils.readFileAsString(EMIAPI.getPlugin().getDataFolder() + System.getProperty("file.separator") + "tokens.json"), List.class);
        }
        catch (Exception e)
        { EMIAPI.getPlugin().getLogger().severe(e.getMessage()); }



        port(this.getConfig().getInt("api-port"));
        get(Path.Web.ONE_STATS, StatisticsController.getPlayerStats);
        get(Path.Web.ONE_ADV, AdvancementController.getPlayerAdvs);
        post(Path.Web.WHITELIST_COMMAND, WhitelistController.runWhitelistCommand);
        post(Path.Web.BAN_COMMAND, BanlistController.runBanCommand);
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

    public static List<ApiToken> getTokens()
    {
        return tokens;
    }
}
