/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import ge.ambro.jerseyutils.security.impl.AuthenticatorManagerImpl;

/**
 *
 * @author tabramishvili
 */
public class ManagerHolder {

    private static AuthenticatorManager manager = new AuthenticatorManagerImpl();

    public static AuthenticatorManager getManager() {
        return manager;
    }

    public static void setManager(AuthenticatorManager manager) {
        ManagerHolder.manager = manager;
    }
}
