/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import java.util.logging.Level;
import java.util.stream.Stream;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 *
 * @author tabramishvili
 */
public class AppEventListener implements ApplicationEventListener {

    @Override
    public void onEvent(ApplicationEvent event) {
        if (event.getType() == ApplicationEvent.Type.INITIALIZATION_APP_FINISHED) {
            streamForClass(Authenticator.class, event).forEach((clazz) -> {
                JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authenticator found: {0}", clazz);
                ManagerHolder.getManager().registerAuthenticator((Class<? extends Authenticator>) clazz);
            });
            streamForObjects(Authenticator.class, event).forEach((ob) -> {
                JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authenticator instance found: {0}", ob);
                ManagerHolder.getManager().registerAuthenticator((Authenticator) ob);
            });
            streamForClass(AuthenticationDataExtractor.class, event).forEach((clazz) -> {
                JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authentication data extractor found: {0}", clazz);
                ManagerHolder.getManager().registerExtractor((Class<? extends AuthenticationDataExtractor>) clazz);
            });
            streamForObjects(AuthenticationDataExtractor.class, event).forEach((ob) -> {
                JerseyUtilsSecurityLogger.LOGGER.log(Level.INFO, "Authentication data extractor instance found: {0}", ob);
                ManagerHolder.getManager().registerExtractor((AuthenticationDataExtractor) ob);
            });
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }

    static Stream<Class<?>> streamForClass(Class c, ApplicationEvent event) {
        return event.getResourceConfig().getClasses().stream()
                .filter((clazz) -> {
                    return c.isAssignableFrom(clazz);
                });
    }

    static Stream<Object> streamForObjects(Class c, ApplicationEvent event) {
        return event.getResourceConfig().getInstances().stream()
                .filter((ob) -> {
                    return c.isAssignableFrom(ob.getClass());
                });
    }

}
