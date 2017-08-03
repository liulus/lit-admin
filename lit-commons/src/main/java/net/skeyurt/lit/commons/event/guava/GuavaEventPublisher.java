package net.skeyurt.lit.commons.event.guava;

import com.google.common.eventbus.EventBus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.skeyurt.lit.commons.event.EventPublisher;

/**
 * User : liulu
 * Date : 2017/8/3 20:29
 * version $Id: GuavaEventPublisher.java, v 0.1 Exp $
 */
@NoArgsConstructor
public class GuavaEventPublisher implements EventPublisher {

    @Getter
    @Setter
    private EventBus eventBus;

    public GuavaEventPublisher(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void register(Object event) {
        eventBus.register(event);
    }

    @Override
    public void unregister(Object event) {
        eventBus.unregister(event);
    }

    @Override
    public void publish(Object event) {
        eventBus.post(event);
    }
}
