package com.dsjk.boot.service.user.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * dubbo自动配置
 *
 * @author fengcheng
 * @version 2017/3/13
 */
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
public class DubboAutoConfiguration {

    private final DubboProperties properties;

    @Autowired
    public DubboAutoConfiguration(DubboProperties properties) {
        this.properties = properties;
    }


    /**
     * 设置dubbo扫描包
     */
    @Bean
    public static AnnotationBean annotationBean(@Value("${dubbo.annotation-package}") String packageName) {
        AnnotationBean annotationBean = new AnnotationBean();
        annotationBean.setPackage(packageName);
        return annotationBean;
    }

    /**
     * 注入dubbo上下文
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(properties.getApplicationName());
        return applicationConfig;
    }

    /**
     * 注入dubbo注册中心配置,基于zookeeper
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(properties.getRegistryProtocol());
        registry.setAddress(properties.getRegistryAddress());
        return registry;
    }

    /**
     * 默认基于dubbo协议提供服务
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(properties.getProtocolName());
        protocolConfig.setPort(properties.getProtocolPort());
        return protocolConfig;
    }

    /**
     * dubbo服务提供
     */
    @Bean(name = "defaultProvider")
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setApplication(applicationConfig());
        providerConfig.setRegistry(registryConfig());
        providerConfig.setProtocol(protocolConfig());
        return providerConfig;
    }
}
