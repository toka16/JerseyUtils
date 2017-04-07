/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import javax.ws.rs.container.ContainerRequestContext;
import org.glassfish.hk2.api.ServiceLocator;

/**
 *
 * @author tabramishvili
 */
public interface AuthenticatorManager {

    void registerAuthenticator(Class<? extends Authenticator> auth);

    void registerAuthenticator(Authenticator auth);

    Authenticator getAuthenticatorFor(String type);

    void registerExtractor(Class<? extends AuthenticationDataExtractor> extrctor);

    void registerExtractor(AuthenticationDataExtractor extrctor);

    AuthenticationDataExtractor getDataExtractorFor(ContainerRequestContext context);

    void setLocator(ServiceLocator locator);
}
