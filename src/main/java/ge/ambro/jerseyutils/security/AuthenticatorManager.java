/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import javax.ws.rs.container.ContainerRequestContext;

/**
 *
 * @author tabramishvili
 */
public interface AuthenticatorManager {

    void registerAuthenticator(Authenticator auth);

    Authenticator getAuthenticatorFor(String type);

    void registerExtractor(AuthenticationDataExtractor extrctor);

    AuthenticationDataExtractor getDataExtractorFor(ContainerRequestContext context);
}
