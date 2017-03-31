/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security.impl;

import ge.ambro.jerseyutils.security.AuthenticationDataExtractor;
import ge.ambro.jerseyutils.security.Authenticator;
import ge.ambro.jerseyutils.security.AuthenticatorManager;
import java.util.Collection;
import java.util.LinkedList;
import javax.ws.rs.container.ContainerRequestContext;

public class AuthenticatorManagerImpl implements AuthenticatorManager {

    private final Collection<Authenticator> authenticators;
    private final Collection<AuthenticationDataExtractor> extractors;

    public AuthenticatorManagerImpl() {
        authenticators = new LinkedList<>();
        extractors = new LinkedList<>();
    }

    @Override
    public void registerAuthenticator(Authenticator auth) {
        authenticators.add(auth);
    }

    @Override
    public Authenticator getAuthenticatorFor(String type) {
        for (Authenticator auth : authenticators) {
            if (auth.supports(type)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public void registerExtractor(AuthenticationDataExtractor extrctor) {
        extractors.add(extrctor);
    }

    @Override
    public AuthenticationDataExtractor getDataExtractorFor(ContainerRequestContext context) {
        for (AuthenticationDataExtractor extractor : extractors) {
            if (extractor.supports(context)) {
                return extractor;
            }
        }
        return null;
    }

}
