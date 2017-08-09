package net.skeyurt.lit.commons.event;

import net.skeyurt.lit.commons.util.ClassUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/8/9 22:00
 * version $Id: PublishEventAspect.java, v 0.1 Exp $
 */
@Aspect
@Component
public class PublishEventAspect {

    @Resource
    private EventPublisher eventPublisher;

    @AfterReturning("@annotation(publishEvent)")
    public void publishEvent(PublishEvent publishEvent) {
        Class<?> eventClass = publishEvent.eventClass();
        Object instance = ClassUtils.newInstance(eventClass);

        if (Objects.equals(publishEvent.publishType(), PublishEvent.PublishType.SYNC)) {
            eventPublisher.publish(instance);
        } else if (Objects.equals(publishEvent.publishType(), PublishEvent.PublishType.ASYNC)) {
            eventPublisher.asyncPublish(instance);
        }

    }


}
