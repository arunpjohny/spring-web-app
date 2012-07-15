package com.arunpjohny.core.spring.context.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextLocator implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    public ApplicationContextLocator() {
        super();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextLocator.applicationContext = applicationContext;
    }

}
