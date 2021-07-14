package com.everneth.emiapi.api;

import com.everneth.emiapi.EMIAPI;
import com.everneth.emiapi.utils.FileUtils;
import spark.Request;
import spark.Response;
import spark.Route;

public class StatisticsController {
    public static final String STATS_PATH = EMIAPI.getPlugin().getServer().getWorld(
            EMIAPI.getPlugin().getConfig().getString("world-folder")).getWorldFolder().getPath() + "/stats/";

    public static Route getPlayerStats = (Request request, Response response) -> {
        String playerStats = FileUtils.readFileAsString(STATS_PATH + request.params(":uuid") + ".json");
        response.status(200);
        response.type("application/json");
        return playerStats;
    };
}
