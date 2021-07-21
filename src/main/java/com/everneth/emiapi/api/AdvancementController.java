package com.everneth.emiapi.api;

import com.everneth.emiapi.EMIAPI;
import com.everneth.emiapi.utils.FileUtils;
import org.bukkit.Bukkit;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class AdvancementController {
    public static final String ADVS_PATH = EMIAPI.getPlugin().getServer().getWorld(
            EMIAPI.getPlugin().getConfig().getString("world-folder")).getWorldFolder().getPath() + "/advancements/";

    public static Route getPlayerAdvs = (Request request, Response response) -> {
        String playerAdvancements = FileUtils.readFileAsString(ADVS_PATH + request.params(":uuid") + ".json");
        response.status(200);
        response.type("application/json");
        return playerAdvancements;
    };
}
