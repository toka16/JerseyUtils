/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security.impl;

import java.security.Principal;

/**
 *
 * @author tabramishvili
 */
public class UsernamePrincipal implements Principal {

    private final String username;

    public UsernamePrincipal(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String toString() {
        return getName();
    }

}
