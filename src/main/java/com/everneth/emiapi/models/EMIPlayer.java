package com.everneth.emiapi.models;

import co.aikar.idb.DB;
import co.aikar.idb.DbRow;
import com.everneth.emiapi.EMIAPI;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class EMIPlayer {
    private UUID uuid;
    private String name;
    private UUID altUuid;
    private String altName;
    private int id;
    private long discordId;
    private LocalDateTime dateAltAdded;

    public EMIPlayer() {}
    public EMIPlayer(UUID uuid, String name, String altName)
    {
        this.uuid = uuid;
        this.name = name;
        this.altName = altName;
        this.id = 0;
        this.discordId = 0L;
    }
    public EMIPlayer(UUID uuid, String name, String altName, int id)
    {
        this.uuid = uuid;
        this.name = name;
        this.altName = altName;
        this.id = id;
        this.discordId = 0L;
    }

    public EMIPlayer(UUID uuid, String name, int id) {
        this.uuid = uuid;
        this.name = name;
        this.id = id;
    }

    public EMIPlayer(UUID uuid, String name, String altName, int id, long discordId) {
        this.uuid = uuid;
        this.name = name;
        this.altName = altName;
        this.id = id;
        this.discordId = discordId;
    }

    public EMIPlayer(UUID uuid, String name, String altName, int id, long discordId, LocalDateTime dateAltAdded, UUID altUuid) {
        this.uuid = uuid;
        this.name = name;
        this.altName = altName;
        this.id = id;
        this.discordId = discordId;
        this.dateAltAdded = dateAltAdded;
        this.altUuid = altUuid;
    }

    public EMIPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getAltName() { return this.altName; }
    public void setAltName(String altName) { this.altName = altName; }

    public int getId()
    {
        return this.id;
    }

    public long getDiscordId()
    {
        return this.discordId;
    }
    public void setDiscordId(long discordId) { this.discordId = discordId; }

    public LocalDateTime getDateAltAdded() {
        return dateAltAdded;
    }

    public boolean isEmpty() {
        return this.getId() == 0;
    }

    public boolean isSynced() {
        return discordId != 0;
    }

    public static <T> EMIPlayer getEmiPlayer(T t)
    {
        CompletableFuture<DbRow> futurePlayer;
        DbRow player;

        if (t instanceof Integer) {
            futurePlayer = DB.getFirstRowAsync("SELECT * FROM players WHERE player_id = ?", t);
        } else if (t instanceof UUID) {
            futurePlayer = DB.getFirstRowAsync("SELECT * FROM players WHERE ? IN (player_uuid,alt_uuid)", t.toString());
        } else if (t instanceof Long) {
            futurePlayer = DB.getFirstRowAsync("SELECT * FROM players WHERE discord_id = ?", t);
        } else if (t instanceof String) {
            futurePlayer = DB.getFirstRowAsync("SELECT * FROM players WHERE ? IN (player_name,alt_name)", t);
        } else {
            return new EMIPlayer();
        }
        try {
            player = futurePlayer.get();
            return new EMIPlayer(UUID.fromString(player.getString("player_uuid")),
                    player.getString("player_name"),
                    player.getString("alt_name"),
                    player.getInt("player_id"),
                    player.getLong("discord_id"),
                    player.get("date_alt_added"),
                    UUID.fromString(player.getString("alt_uuid")));
        }
        catch (Exception e)
        {
            EMIAPI.getPlugin().getLogger().info(e.getMessage());
            return new EMIPlayer();
        }
    }

    public static DbRow getAppRecord(long discordId)
    {
        CompletableFuture<DbRow> futurePlayer;
        DbRow player = new DbRow();
        futurePlayer = DB.getFirstRowAsync("SELECT * FROM applications WHERE applicant_discord_id = ?", discordId);
        try {
            player = futurePlayer.get();
        }
        catch (Exception e)
        {
            EMIAPI.getPlugin().getLogger().info(e.getMessage());
        }
        return player;
    }
}
