package net.skeyurt.lit.commons.event;

import net.skeyurt.lit.commons.bean.BeanUtils;
import net.skeyurt.lit.commons.util.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * User : liulu
 * Date : 2017/8/9 22:00
 * version $Id: PublishEventAspect.java, v 0.1 Exp $
 */
@Aspect
@Component
public class PublishEventAspect {

    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Resource
    private EventPublisher eventPublisher;

    @AfterReturning("@annotation(event)")
    public void publishEvent(JoinPoint joinPoint, Event event) {

        Class<?> eventClass = event.eventClass();

        Object eventObj = newInstanceAndInitProperty(eventClass, joinPoint);

        if (Objects.equals(event.publishType(), Event.Type.SYNC)) {
            eventPublisher.publish(eventObj);
        } else if (Objects.equals(event.publishType(), Event.Type.ASYNC)) {
            eventPublisher.asyncPublish(eventObj);
        }

    }

    private Object newInstanceAndInitProperty(Class<?> eventClass, JoinPoint joinPoint) {

        Object eventObj = ClassUtils.newInstance(eventClass);

        Method targetMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();

        String[] parameterNames = parameterNameDiscoverer.getParameterNames(targetMethod);
        Object[] args = joinPoint.getArgs();
        Class<?>[] parameterTypes = targetMethod.getParameterTypes();

        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(eventClass);
        for (PropertyDescriptor descriptor : descriptors) {

            Class<?> propertyType = descriptor.getPropertyType();

            int nameIndex = getParameterNameIndex(parameterNames, descriptor.getName());
            if (nameIndex >= 0 && Objects.equals(propertyType, parameterTypes[nameIndex])) {
                ClassUtils.invokeMethod(descriptor.getWriteMethod(), eventObj, args[nameIndex]);
            } else if (!ClassUtils.isPrimitiveOrWrapper(propertyType)) {
                int typeIndex = getParameterTypeIndex(parameterTypes, propertyType);
                if (typeIndex >= 0) {
                    ClassUtils.invokeMethod(descriptor.getWriteMethod(), eventObj, args[typeIndex]);
                }
            }
        }
        return eventObj;
    }

    private int getParameterNameIndex(String[] parameterNames, String searchName) {
        for (int i = 0; i < parameterNames.length; i++) {
            if (Objects.equals(parameterNames[i], searchName)) {
                return i;
            }
        }
        return -1;
    }

    private int getParameterTypeIndex(Class<?>[] parameterTypes, Class<?> searchType) {
        int result = -1, count = 0;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (Objects.equals(parameterTypes[i], searchType)) {
                result = i;
                count++;
            }
        }
        return count > 1 ? -1 : result;
    }


}
