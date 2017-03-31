/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import ge.ambro.jerseyutils.security.model.AuthenticationData;
import java.io.IOException;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author tabramishvili
 */
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    AuthenticatorManager manager;
    

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        System.out.println("filter");
        AuthenticationDataExtractor extractor = manager.getDataExtractorFor(requestContext);
        if (extractor == null) {
            return;
        }
        AuthenticationData data = extractor.extractData(requestContext);
        if (data == null) {
            return;
        }
        Authenticator auth = manager.getAuthenticatorFor(data.getType());
        if (auth == null) {
            return;
        }
        SecurityContext context = auth.authenticateUser(data, requestContext);
        if (context == null) {
            return;
        }
        requestContext.setSecurityContext(context);

    }
}
