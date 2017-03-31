/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.properties;

import ge.ambro.jerseyutils.inject.Component;
import ge.ambro.jerseyutils.inject.ComponentFactory;
import java.util.Properties;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 *
 * @author tabramishvili
 */
@ComponentFactory
public class PropertiesLoaderFactory {

    @Component
    @Singleton
    public PropertiesLoader getLoader() {
        return new PropertiesLoader();
    }

    @Component
    @Singleton
    @Named(PropertiesLoader.INJECTED_PROPERTIES_NAME)
    public Properties getProperties() {
        return PropertiesLoader.properties;
    }
}
