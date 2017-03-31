/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.inject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.Factory;

/**
 *
 * @author tabramishvili
 */
public class ComponentInjectorFactory<T> implements Factory<T> {

    private Class clazz;
    private Method method;
    private Method destructor;
    private Object instance;

    @Override
    public T provide() {
        try {
            T value = (T) method.invoke(instance);
            return value;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ComponentInjectorFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void dispose(T t) {
        if (destructor != null) {
            try {
                destructor.invoke(instance, t);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(ComponentInjectorFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getDestructor() {
        return destructor;
    }

    public void setDestructor(Method destructor) {
        this.destructor = destructor;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

}
