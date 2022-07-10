package com.everneth.emiapi.api;

import lombok.Getter;
import lombok.Setter;

public class Path {
    public static class Web {
        @Getter public static final String ONE_STATS = "/stats/:uuid";
        @Getter public static final String ONE_ADV = "/advs/:uuid";
        @Setter public static final String WHITELIST_COMMAND = "/cmd/:wlcommand/:player?token=:token";
    }
}
