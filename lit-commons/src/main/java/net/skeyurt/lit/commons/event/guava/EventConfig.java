package net.skeyurt.lit.commons.event.guava;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import net.skeyurt.lit.commons.condition.ConditionalOnClass;
import net.skeyurt.lit.commons.condition.ConditionalOnMissingBean;
import net.skeyurt.lit.commons.event.EventComponent;
import net.skeyurt.lit.commons.event.EventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Map;
import java.util.concurrent.Executors;

/**
 * User : liulu
 * Date : 2017/8/3 20:47
 * version $Id: EventConfig.java, v 0.1 Exp $
 */
@Configuration
@Order(1)
@ConditionalOnClass(EventBus.class)
public class EventConfig {


    @Bean
    @ConditionalOnMissingBean(EventPublisher.class)
    public EventPublisher eventPublisher() {

        AsyncEventBus asyncEventBus = new AsyncEventBus("async-default", Executors.newScheduledThreadPool(10));

        return new GuavaEventPublisher(new EventBus(), asyncEventBus);
    }

    @Bean
    public Object registerEvent(ApplicationContext context) {
        EventPublisher publisher = context.getBean(EventPublisher.class);

        Map<String, Object> eventListenerBeans = context.getBeansWithAnnotation(EventComponent.class);
        for (Object eventListener : eventListenerBeans.values()) {
            publisher.register(eventListener);
        }
        return null;
    }

//    @EventListener
//    public void registerEvent(ContextRefreshedEvent refreshedEvent) {
//        ApplicationContext context = refreshedEvent.getApplicationContext();
//
//    }

}
