package com.qinpr.trf.config;

import com.qinpr.trf.config.support.Parameter;

import java.util.Map;

/**
 * Created by qinpr on 18/4/27.
 */
public class MonitorConfig extends AbstractConfig {
    private static final long serialVersionUID = -607285505668366379L;

    private String protocol;

    private String address;

    private String username;

    private String password;

    private String group;

    private String version;

    private String interval;

    // customized parameters
    private Map<String, String> parameters;

    // if it's default
    private Boolean isDefault;

    @Parameter(excluded = true)
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
