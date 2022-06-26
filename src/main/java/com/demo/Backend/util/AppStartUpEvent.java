package com.demo.Backend.util;


import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartUpEvent implements ApplicationListener<ApplicationReadyEvent> {

    public AppStartUpEvent() {
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

    }
}
