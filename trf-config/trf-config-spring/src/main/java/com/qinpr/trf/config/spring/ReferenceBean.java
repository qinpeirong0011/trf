package com.qinpr.trf.config.spring;

import com.qinpr.trf.config.ApplicationConfig;
import com.qinpr.trf.config.ReferenceConfig;
import com.qinpr.trf.config.RegistryConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by qinpr on 2018/11/20.
 */
public class ReferenceBean<T> extends ReferenceConfig<T> implements FactoryBean, ApplicationContextAware, InitializingBean, DisposableBean {

    private transient ApplicationContext applicationContext;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object getObject() throws Exception {
        return get();
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
       if ( getApplication() == null && (getConsumer() == null || getConsumer().getApplication() == null)) {
           Map<String, ApplicationConfig> applicationConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, ApplicationConfig.class, false, false);
           if (applicationConfigMap != null && applicationConfigMap.size() > 0) {
               ApplicationConfig applicationConfig = null;
               for (ApplicationConfig config : applicationConfigMap.values()) {
                   applicationConfig = config;
               }
               if (applicationConfig != null) {
                   setApplication(applicationConfig);
               }
           }
       }

       if (getRegistries() == null ||  getRegistries().isEmpty()
               && (getConsumer() == null || getConsumer().getRegistries() == null || getConsumer().getRegistries().isEmpty())
               && (getApplication() == null || getApplication().getRegistries() == null || getApplication().getRegistries().isEmpty())) {
           Map<String, RegistryConfig> registryConfigMap = applicationContext == null ? null : BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, RegistryConfig.class, false, false);
           if (registryConfigMap != null && registryConfigMap.size() > 0) {
               List<RegistryConfig> registryConfigs = new ArrayList<>();
               for (RegistryConfig config : registryConfigMap.values()) {
                   registryConfigs.add(config);
               }
               if (!registryConfigs.isEmpty()) {
                   super.setRegistries(registryConfigs);
               }
           }
       }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
       this.applicationContext = applicationContext;
    }
}
