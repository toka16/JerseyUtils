/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.properties;

import java.util.Properties;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.ServiceHandle;

/**
 *
 * @author tabramishvili
 */
public class PropertiesInjector implements InjectionResolver<Property> {

    @Inject
    @Named(PropertiesLoader.INJECTED_PROPERTIES_NAME)
    Properties properties;

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> arg1) {
        Object value = null;
        Property annotation = injectee.getParent().getAnnotation(Property.class);
        if (annotation != null) {
            String prop = annotation.value();
            value = properties.getProperty(prop);
        }
        if (value != null) {
            if (String.class == injectee.getRequiredType()) {
                return String.valueOf(value);
            }
            if (Boolean.class == injectee.getRequiredType() || boolean.class == injectee.getRequiredType()) {
                return Boolean.valueOf(String.valueOf(value));
            }
            if (Short.class == injectee.getRequiredType() || short.class == injectee.getRequiredType()) {
                return Short.valueOf(String.valueOf(value));
            }
            if (Integer.class == injectee.getRequiredType() || int.class == injectee.getRequiredType()) {
                return Integer.valueOf(String.valueOf(value));
            }
            if (Long.class == injectee.getRequiredType() || long.class == injectee.getRequiredType()) {
                return Long.valueOf(String.valueOf(value));
            }
            if (Float.class == injectee.getRequiredType() || float.class == injectee.getRequiredType()) {
                return Float.valueOf(String.valueOf(value));
            }
            if (Double.class == injectee.getRequiredType() || double.class == injectee.getRequiredType()) {
                return Double.valueOf(String.valueOf(value));
            }
        }
        return value;
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return false;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }

}
