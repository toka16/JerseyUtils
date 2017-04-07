/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import java.util.logging.Level;
import java.util.stream.Stream;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 *
 * @author tabramishvili
 */
public class BaseSecurityFeature implements Feature {

    @Override
    public boolean configure(FeatureContext context) {
        context.register(RolesAllowedDynamicFeature.class);
        context.register(AuthenticationFactory.class);

        ServiceLocator locator = ServiceLocatorProvider.getServiceLocator(context);
        ManagerHolder.getManager().setLocator(locator);

        streamForClass(Authenticator.class, context).forEach((clazz) -> {
            JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authenticator found: {0}", clazz);
            ManagerHolder.getManager().registerAuthenticator((Class<? extends Authenticator>) clazz);
        });
        streamForObjects(Authenticator.class, context).forEach((ob) -> {
            JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authenticator instance found: {0}", ob);
            ManagerHolder.getManager().registerAuthenticator((Authenticator) ob);
        });
        streamForClass(AuthenticationDataExtractor.class, context).forEach((clazz) -> {
            JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authentication data extractor found: {0}", clazz);
            ManagerHolder.getManager().registerExtractor((Class<? extends AuthenticationDataExtractor>) clazz);
        });
        streamForObjects(AuthenticationDataExtractor.class, context).forEach((ob) -> {
            JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authentication data extractor instance found: {0}", ob);
            ManagerHolder.getManager().registerExtractor((AuthenticationDataExtractor) ob);
        });
        return true;
    }

    protected Stream<Class<?>> streamForClass(Class c, FeatureContext context) {
        return context.getConfiguration().getClasses().stream()
                .filter((clazz) -> {
                    return c.isAssignableFrom(clazz);
                });
    }

    protected Stream<Object> streamForObjects(Class c, FeatureContext context) {
        return context.getConfiguration().getInstances()
                .stream()
                .filter((ob) -> {
                    return c.isAssignableFrom(ob.getClass());
                });
    }

}
