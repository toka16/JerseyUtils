/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

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

        streamForClass(Authenticator.class, context).forEach((clazz) -> {
            ManagerHolder.getManager().registerAuthenticator((Authenticator) locator.createAndInitialize(clazz));
        });
        streamForClass(AuthenticationDataExtractor.class, context).forEach((clazz) -> {
            ManagerHolder.getManager().registerExtractor((AuthenticationDataExtractor) locator.createAndInitialize(clazz));
        });
        return true;
    }

    protected Stream<Class<?>> streamForClass(Class c, FeatureContext context) {
        return context.getConfiguration().getClasses().stream()
                .filter((clazz) -> {
                    return c.isAssignableFrom(clazz);
                });
    }

}
