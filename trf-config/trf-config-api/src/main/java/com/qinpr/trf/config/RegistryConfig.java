package com.qinpr.trf.config;

import java.util.Map;

/**
 * Created by qinpr on 18/4/27.
 */
public class RegistryConfig extends AbstractConfig {
    private static final long serialVersionUID = -3395736731086839577L;
    public static final String NO_AVAILABLE = "N/A";
    // register center address
    private String address;

    // username to login register center
    private String username;

    // password to login register center
    private String password;

    // default port for register center
    private Integer port;

    // protocol for register center
    private String protocol;

    // client impl
    private String transporter;

    private String server;

    private String client;

    private String cluster;

    private String group;

    private String version;

    // request timeout in milliseconds for register center
    private Integer timeout;

    // session timeout in milliseconds for register center
    private Integer session;

    // file for saving register center dynamic list
    private String file;

    // wait time before stop
    private Integer wait;

    // whether to check if register center is available when boot up
    private Boolean check;

    // whether to allow dynamic service to register on the register center
    private Boolean dynamic;

    // whether to export service on the register center
    private Boolean register;

    // whether allow to subscribe service on the register center
    private Boolean subscribe;

    // customized parameters
    private Map<String, String> parameters;

    // if it's default
    private Boolean isDefault;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
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

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getSession() {
        return session;
    }

    public void setSession(Integer session) {
        this.session = session;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Boolean getDynamic() {
        return dynamic;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
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
