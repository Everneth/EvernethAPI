package com.everneth.emiapi.api;

import com.everneth.emiapi.EMIAPI;
import com.everneth.emiapi.models.ApiToken;
import com.everneth.emiapi.models.CommandResponse;
import com.everneth.emiapi.models.EMIPlayer;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import spark.Route;

public class BanlistController {
    public static Route runBanCommand = (request, response) -> {
        Gson gson = new Gson();
        String commandMessageReturn = null;
        CommandResponse commandResponse = new CommandResponse();
        switch(request.params(":blcommand"))
        {
            case "ban":
                commandMessageReturn = "/ban";
                break;
            case "pardon":
                commandMessageReturn = "/pardon";
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
        if(request.params(":blcommand").equals("ban")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName());
                    commandResponse.setMessage(player.getName() + " has been banned on " + EMIAPI.getPlugin().getConfig().getString("server-name") + ".");
                    if(player.getAltName() != null)
                    {
                        EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getAltName());
                        commandResponse.setMessage(player.getName() + " --- ALT Account " + player.getAltName() +
                                " has been banned on " + EMIAPI.getPlugin().getConfig().getString("server-name") + ".");
                    }


                }
            }.runTask(EMIAPI.getPlugin());
        }
        else if(request.params(":blcommand").equals("pardon"))
        {
            new BukkitRunnable() {
                @Override
                public void run() {
                    EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + player.getName());
                    commandResponse.setMessage(player.getName() + " has been pardoned on " + EMIAPI.getPlugin().getConfig().getString("server-name") + ".");
                    if(player.getAltName() != null)
                    {
                        EMIAPI.getPlugin().getServer().dispatchCommand(Bukkit.getConsoleSender(), "pardon " + player.getAltName());
                        commandResponse.setMessage(player.getName() + " --- ALT Account " + player.getAltName() +
                                " has been pardoned on " + EMIAPI.getPlugin().getConfig().getString("server-name") + ".");
                    }
                }
            }.runTask(EMIAPI.getPlugin());
        }
        else
        {
            commandResponse.setMessage("Invalid command requested from the API. Only ban and pardon parameters are supported.");
        }

        response.status(200);
        response.type("application/json");
        return commandResponse;
    };
}
