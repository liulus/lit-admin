package net.skeyurt.lit.commons.event.guava;

import com.google.common.eventbus.EventBus;
import net.skeyurt.lit.commons.condition.ConditionalOnClass;
import net.skeyurt.lit.commons.condition.ConditionalOnMissingBean;
import net.skeyurt.lit.commons.event.EventComponent;
import net.skeyurt.lit.commons.event.EventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.Map;

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
        return new GuavaEventPublisher(new EventBus());
    }

    @EventListener
    public void registerEvent(ContextRefreshedEvent refreshedEvent) {
        ApplicationContext context = refreshedEvent.getApplicationContext();
        EventPublisher publisher = context.getBean(EventPublisher.class);

        Map<String, Object> eventListenerBeans = context.getBeansWithAnnotation(EventComponent.class);
        for (Object eventListener : eventListenerBeans.values()) {
            publisher.register(eventListener);
        }
    }

}
