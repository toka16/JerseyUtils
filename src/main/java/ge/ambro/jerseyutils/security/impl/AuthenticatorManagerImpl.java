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
import org.glassfish.hk2.api.ServiceLocator;

public class AuthenticatorManagerImpl implements AuthenticatorManager {

    private final Collection<Class<? extends Authenticator>> authenticators;
    private final Collection<Authenticator> authenticatorsInstances;
    private final Collection<Class<? extends AuthenticationDataExtractor>> extractors;
    private final Collection<AuthenticationDataExtractor> extractorsInstances;

    private ServiceLocator locator;

    public AuthenticatorManagerImpl() {
        authenticators = new LinkedList<>();
        authenticatorsInstances = new LinkedList<>();
        extractors = new LinkedList<>();
        extractorsInstances = new LinkedList<>();
    }

    @Override
    public void registerAuthenticator(Class<? extends Authenticator> auth) {
        authenticators.add(auth);
    }

    @Override
    public void registerAuthenticator(Authenticator auth) {
        authenticatorsInstances.add(auth);
    }

    @Override
    public Authenticator getAuthenticatorFor(String type) {
        for (Authenticator auth : authenticatorsInstances) {
            if (auth.supports(type)) {
                return auth;
            }
        }
        for (Class<? extends Authenticator> authClass : authenticators) {
            Authenticator auth = locator.createAndInitialize(authClass);
            if (auth.supports(type)) {
                return auth;
            }
        }
        return null;
    }

    @Override
    public void registerExtractor(Class<? extends AuthenticationDataExtractor> extractor) {
        extractors.add(extractor);
    }

    @Override
    public void registerExtractor(AuthenticationDataExtractor extractor) {
        extractorsInstances.add(extractor);
    }

    @Override
    public AuthenticationDataExtractor getDataExtractorFor(ContainerRequestContext context) {
        for (AuthenticationDataExtractor extractor : extractorsInstances) {
            if (extractor.supports(context)) {
                return extractor;
            }
        }
        for (Class<? extends AuthenticationDataExtractor> extractorClass : extractors) {
            AuthenticationDataExtractor extractor = locator.createAndInitialize(extractorClass);
            if (extractor.supports(context)) {
                return extractor;
            }
        }
        return null;
    }

    @Override
    public void setLocator(ServiceLocator locator) {
        this.locator = locator;
    }

}
