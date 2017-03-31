/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security.impl;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author tabramishvili
 */
public class SimpleSecurityContext implements SecurityContext {

    private final Principal principal;
    private final String scheme;
    private final List<String> roles;

    public SimpleSecurityContext(Principal principal, String scheme) {
        this.principal = principal;
        this.scheme = scheme;
        this.roles = new LinkedList<>();
    }

    public SimpleSecurityContext addRoles(String... roles) {
        return addRoles(Arrays.asList(roles));
    }

    public SimpleSecurityContext addRoles(Collection<? extends String> roles) {
        this.roles.addAll(roles);
        return this;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return roles.contains(role);
    }

    @Override
    public boolean isSecure() {
        return "https".equals(scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return scheme;
    }
}
