package com.qinpr.trf.config;

import com.qinpr.trf.config.support.Parameter;

import java.util.List;

/**
 * Created by qinpr on 18/4/27.
 */
public class ModuleConfig extends AbstractConfig {
    private static final long serialVersionUID = 2505756598888091916L;

    // module name
    private String name;

    // module version
    private String version;

    // module owner
    private String owner;

    // module's organization
    private String organization;

    // registry centers
    private List<RegistryConfig> registries;

    // monitor center
    private MonitorConfig monitor;

    // if it's default
    private Boolean isDefault;

    @Parameter(key = "module", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public List<RegistryConfig> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryConfig> registries) {
        this.registries = registries;
    }

    public MonitorConfig getMonitor() {
        return monitor;
    }

    public void setMonitor(MonitorConfig monitor) {
        this.monitor = monitor;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }
}
