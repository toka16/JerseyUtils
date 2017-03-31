/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.inject;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.binding.ScopedBindingBuilder;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.glassfish.jersey.process.internal.RequestScoped;

/**
 *
 * @author tabramishvili
 */
public class ComponentInjectorFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {

        final ServiceLocator locator = ServiceLocatorProvider.getServiceLocator(context);
        context.register(new AbstractBinder() {
            @Override
            protected void configure() {
                context.getConfiguration().getClasses().stream()
                        .filter((clazz) -> {
                            return clazz.isAnnotationPresent(ComponentFactory.class);
                        })
                        .forEach((clazz) -> {
                            final Object instance = locator.createAndInitialize(clazz);
                            Arrays.stream(clazz.getDeclaredMethods())
                                    .filter((method) -> {
                                        return method.isAnnotationPresent(Component.class);
                                    })
                                    .forEach((method) -> {

                                        Component compDetails = method.getAnnotation(Component.class);
                                        Method destructor = null;
                                        if (compDetails.destructor() != null && !compDetails.destructor().isEmpty()) {
                                            try {
                                                destructor = clazz.getDeclaredMethod(compDetails.destructor(), method.getReturnType());
                                            } catch (SecurityException | NoSuchMethodException ex) {
                                                Logger.getLogger(ComponentInjectorFeature.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        ComponentInjectorFactory factory = locator.createAndInitialize(ComponentInjectorFactory.class);
                                        factory.setClazz(clazz);
                                        factory.setInstance(instance);
                                        factory.setMethod(method);
                                        factory.setDestructor(destructor);
                                        Class scope = RequestScoped.class;
                                        if (method.isAnnotationPresent(Singleton.class)) {
                                            scope = Singleton.class;
                                        } else if (method.isAnnotationPresent(PerLookup.class)) {
                                            scope = PerLookup.class;
                                        }
                                        ScopedBindingBuilder binder = bindFactory(factory)
                                                .to(method.getReturnType())
                                                .in(scope);

                                        if (method.isAnnotationPresent(Named.class)) {
                                            Named named = method.getAnnotation(Named.class);
                                            binder.named(named.value());
                                        }
                                        if (method.isAnnotationPresent(Rank.class)) {
                                            Rank rank = method.getAnnotation(Rank.class);
                                            binder.ranked(rank.value());
                                        }
                                    });
                        });
            }
        });
        return true;
    }

}
