package com.tcs.comp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BF {

    @Autowired
    private ApplicationContext context;

    @Value("${gf.id}")
    private String beanId;

    public void go() {
        IGFS gfs = context.getBean(beanId, IGFS.class);
        gfs.gotoOyo();
    }
}
