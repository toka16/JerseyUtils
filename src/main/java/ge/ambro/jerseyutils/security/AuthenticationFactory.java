/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import ge.ambro.jerseyutils.inject.Component;
import ge.ambro.jerseyutils.inject.ComponentFactory;
import org.glassfish.hk2.api.PerLookup;

/**
 *
 * @author tabramishvili
 */
@ComponentFactory
public class AuthenticationFactory {

    @Component
    @PerLookup
    public AuthenticatorManager getAuthenticatorManager() {
        return ManagerHolder.getManager();
    }

}
