package com.khomandiak.courseWork;

import com.fasterxml.jackson.annotation.*;

import java.util.List;

/**
 * Created by o.khomandiak on 14.08.2017.
 */
@JsonRootName(value = "server")
public class Server {
    private String host;
    private String password;
    private String loginOn;
    private List<Config> configs;

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginOn() {
        return loginOn;
    }

    public void setLoginOn(String loginOn) {
        this.loginOn = loginOn;
    }
}
