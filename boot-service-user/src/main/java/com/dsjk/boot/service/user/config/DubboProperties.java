package com.dsjk.boot.service.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * dubbo属性读取
 *
 * @author fengcheng
 * @version 2017/3/13
 */
@ConfigurationProperties(prefix = "dubbo")
public class DubboProperties {

    private String applicationName;
    private String annotationPackage;
    private String protocolName;
    private Integer protocolPort;
    private String registryProtocol;
    private String registryAddress;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAnnotationPackage() {
        return annotationPackage;
    }

    public void setAnnotationPackage(String annotationPackage) {
        this.annotationPackage = annotationPackage;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public Integer getProtocolPort() {
        return protocolPort;
    }

    public void setProtocolPort(Integer protocolPort) {
        this.protocolPort = protocolPort;
    }

    public String getRegistryProtocol() {
        return registryProtocol;
    }

    public void setRegistryProtocol(String registryProtocol) {
        this.registryProtocol = registryProtocol;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }
}
