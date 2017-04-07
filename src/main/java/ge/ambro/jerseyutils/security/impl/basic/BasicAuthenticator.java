/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security.impl.basic;

import ge.ambro.jerseyutils.security.Authenticator;
import ge.ambro.jerseyutils.security.model.AuthenticationData;
import java.util.Objects;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author tabramishvili
 */
public abstract class BasicAuthenticator implements Authenticator {

    @Override
    public boolean supports(String type) {
        return Objects.equals(BasicAuthenticationDataExtractor.AUTHENTICATION_TYPE, type);
    }

    @Override
    public SecurityContext authenticateUser(AuthenticationData token, ContainerRequestContext context) {
        BasicAuthenticationData data = (BasicAuthenticationData) token;
        if (data.getToken() == null) {
            return null;
        }

        return authenticateUser(data, context.getUriInfo().getAbsolutePath().getScheme());
    }

    protected abstract SecurityContext authenticateUser(BasicAuthenticationData authData, String scheme);

}
