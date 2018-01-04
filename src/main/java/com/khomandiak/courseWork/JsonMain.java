package com.khomandiak.courseWork;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by o.khomandiak on 14.08.2017.
 */
@JsonRootName(value = "json")
public class JsonMain {
    private Server server;
    private Config config;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
