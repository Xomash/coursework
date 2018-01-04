package com.khomandiak.courseWork;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Created by o.khomandiak on 14.08.2017.
 */
@JsonRootName(value = "configs")
public class Config {
    private String path;
    private int time;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
