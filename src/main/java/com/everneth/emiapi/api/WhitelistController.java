package com.everneth.emiapi.api;

import com.everneth.emiapi.EMIAPI;
import com.everneth.emiapi.models.ApiToken;
import com.everneth.emiapi.models.CommandResponse;
import com.everneth.emiapi.models.EMIPlayer;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

public class WhitelistController {
    public static Route runWhitelistCommand = (Request request, Response response) -> {
        Gson gson = new Gson();
        String commandMessageReturn = null;
        CommandResponse commandResponse = new CommandResponse();
        switch(request.params(":wlcommand"))
        {
            case "remove":
                commandMessageReturn = "/whitelist remove";
                break;
            case "add":
                commandMessageReturn = "/whitelist add";
        }

        EMIPlayer player = EMIPlayer.getEmiPlayer(request.params(":player"));
        commandResponse.setPlayerName(player.getName());
        commandResponse.setCommandRun(commandMessageReturn);
        if(!EMIAPI.getTokens().contains(gson.fromJson(request.params(":token"), ApiToken.class)))
        {
            commandResponse.setMessage("No token or invalid token supplied for this request");
            return commandResponse;
        }

        if(player.isEmpty())
        {
            commandResponse.setMessage("Player not found.");
        }
        if(request.params(":wlcommand").equals("add")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + player.getName());
                    commandResponse.setMessage(player.getName() + " has been added to the whitelist successfully.");
                    if(player.getAltName() != null)
                    {
                        EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist add " + player.getAltName());
                        commandResponse.setMessage(player.getName() + "and alt " + player.getAltName() + " has been added to the whitelist successfully.");
                    }


                }
            }.runTask(EMIAPI.getPlugin());
        }
        else if(request.params("wlcommand").equals("remove"))
        {
            new BukkitRunnable() {
                @Override
                public void run() {
                    EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + player.getName());
                    commandResponse.setMessage(player.getName() + " has been removed from the whitelist successfully.");
                    if(player.getAltName() != null)
                    {
                        EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "whitelist remove " + player.getAltName());
                        commandResponse.setMessage(player.getName() + "and alt " + player.getAltName() + " has been removed from the whitelist successfully.");
                    }
                }
            }.runTask(EMIAPI.getPlugin());
        }
        else
        {
            commandResponse.setMessage("Invalid command requested from the API. Only add and remove parameters are supported.");
        }

        response.status(200);
        response.type("application/json");
        return commandResponse;
    };
}
