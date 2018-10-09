package com.qinpr.trf.config.spring;

import com.qinpr.trf.config.ProviderConfig;
import com.qinpr.trf.config.ServiceConfig;
import com.qinpr.trf.config.annotation.Service;


import java.lang.reflect.Method;

/**
 * Created by qinpr on 18/5/4.
 */
//public class ServiceBean<T> extends ServiceConfig<T> implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, BeanNameAware {
//
//    private static final long serialVersionUID = 4445737905084872400L;
//
//    private static transient ApplicationContext SPRING_CONTEXT;
//
//    private final transient Service service;
//
//    private transient ApplicationContext applicationContext;
//
//    private transient String beanName;
//
//    private transient boolean supportedApplicationListener;
//
//    public ServiceBean() {
//        super();
//        this.service = null;
//    }
//
//    public ServiceBean(Service service) {
//        super(service);
//        this.service = service;
//    }
//
//    public static ApplicationContext getSpringContext() {
//        return SPRING_CONTEXT;
//    }
//
//    public void setBeanName(String name) {
//        this.beanName = name;
//    }
//
//    public void destroy() throws Exception {
//
//    }
//
//    public void afterPropertiesSet() throws Exception {
//
//    }
//
//
//
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        if (isDelay() && !isExported() && isUnexported()) {
//
//        }
//
//        export();
//
//    }
//
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//
//        if (applicationContext != null) {
//            SPRING_CONTEXT = applicationContext;
//            try {
//                Method method = applicationContext.getClass().getMethod("addApplicationListener", new Class<?>[]{ApplicationListener.class});
//                method.invoke(applicationContext, new Object[]{this});
//                supportedApplicationListener = true;
//
//            } catch (Throwable t) {
//                if (applicationContext instanceof AbstractApplicationContext) {
//                    try {
//                        Method method = AbstractApplicationContext.class.getDeclaredMethod("addListener", new Class<?>[]{AbstractApplicationContext.class});
//                        if (!method.isAccessible()) {
//                            method.setAccessible(true);
//                        }
//
//                        method.invoke(applicationContext, new Object[]{this});
//                        supportedApplicationListener = true;
//
//                    } catch (Throwable t2) {}
//                }
//            }
//
//        }
//
//    }
//
//    private boolean isDelay() {
//        Integer delay = getDelay();
//        ProviderConfig provider = getProvider();
//        if (delay == null && provider != null) {
//            delay = provider.getDelay();
//        }
//
//        return supportedApplicationListener && (delay == null || delay == -1);
//    }
//}
