package net.skeyurt.lit.commons.event.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import net.skeyurt.lit.commons.event.AppStartedEvent;
import net.skeyurt.lit.commons.event.Event;
import net.skeyurt.lit.commons.event.EventComponent;
import net.skeyurt.lit.commons.event.EventPublisher;
import net.skeyurt.lit.commons.spring.condition.ConditionalOnClass;
import net.skeyurt.lit.commons.spring.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;
import java.util.concurrent.Executors;

/**
 * User : liulu
 * Date : 2017/8/3 20:47
 * version $Id: EventConfig.java, v 0.1 Exp $
 */
@Configuration
@ConditionalOnClass(EventBus.class)
public class EventConfig {


    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher() {

        AsyncEventBus asyncEventBus = new AsyncEventBus("async-default", Executors.newScheduledThreadPool(10));

        return new GuavaEventPublisher(new EventBus(), asyncEventBus);
    }


    @EventListener
    @Event(eventClass = AppStartedEvent.class)
    public void registerEvent(ContextRefreshedEvent event) {

        ApplicationContext context = event.getApplicationContext();

        EventPublisher publisher = context.getBean(EventPublisher.class);
        Map<String, Object> eventListenerBeans = context.getBeansWithAnnotation(EventComponent.class);
        for (Object eventListener : eventListenerBeans.values()) {
            publisher.register(eventListener);
        }
    }

}
