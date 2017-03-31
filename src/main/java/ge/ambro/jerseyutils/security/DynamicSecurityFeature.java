/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.server.model.AnnotatedMethod;

/**
 *
 * @author tabramishvili
 */
public class DynamicSecurityFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        final AnnotatedMethod am = new AnnotatedMethod(resourceInfo.getResourceMethod());

        // DenyAll on the method take precedence over RolesAllowed and PermitAll
        if (am.isAnnotationPresent(DenyAll.class)) {
            // Do nothing.
            return;
        }

        // RolesAllowed on the method takes precedence over PermitAll
        RolesAllowed ra = am.getAnnotation(RolesAllowed.class);
        if (am.isAnnotationPresent(RolesAllowed.class)) {
            context.register(AuthenticationFilter.class);
            return;
        }

        // PermitAll takes precedence over RolesAllowed on the class
        if (am.isAnnotationPresent(PermitAll.class)) {
            // Do nothing.
            return;
        }

        // DenyAll can't be attached to classes
        // RolesAllowed on the class takes precedence over PermitAll
        ra = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
        if (ra != null) {
            context.register(AuthenticationFilter.class);
        }
    }

}
