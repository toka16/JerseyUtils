/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.properties;

import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.ServiceLocatorProvider;

/**
 *
 * @author tabramishvili
 */
public class PropertiesLoaderFeature implements Feature {

    public static final String PROPERIES_FILE_PATH = "properties.path";

    @Override
    public boolean configure(FeatureContext context) {
        context.register(PropertiesLoaderFactory.class);
        context.register(new PropertyBinder());

        ServiceLocatorProvider.getServiceLocator(context)
                .createAndInitialize(PropertiesLoaderFactory.class)
                .getLoader()
                .load(context.getConfiguration().getProperty(PROPERIES_FILE_PATH));

        return true;
    }

    public static class PropertyBinder extends AbstractBinder {

        @Override
        protected void configure() {
            bind(PropertiesInjector.class)
                    .to(new TypeLiteral<InjectionResolver<Property>>() {
                    })
                    .in(Singleton.class);
        }

    }

}
