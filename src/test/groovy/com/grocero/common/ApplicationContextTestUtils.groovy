package com.grocero.common

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class ApplicationContextTestUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        ApplicationContextTestUtils.applicationContext = applicationContext;
    }
}
