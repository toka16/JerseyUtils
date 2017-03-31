/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tabramishvili
 */
public class PropertiesLoader {
    public static final String INJECTED_PROPERTIES_NAME = "ge.ambro.jerseyutils.properties";

    static final Properties properties = new Properties();

    public void load(Object pathObject) {
        if (pathObject == null) {
            return;
        }

        if (pathObject instanceof String) {
            loadProperties((String) pathObject);
        } else if (Collection.class.isAssignableFrom(pathObject.getClass())) {
            ((Collection) pathObject).forEach((path) -> {
                loadProperties((String) path);
            });
        }
    }

    protected void loadProperties(String path) {
        readProperties(path, properties);
    }

    protected Properties readProperties(String path) {
        return readProperties(path, new Properties());
    }

    protected Properties readProperties(String path, Properties properties) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            properties.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}
