/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import ge.ambro.jerseyutils.security.model.AuthenticationData;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author tabramishvili
 */
public interface Authenticator {
    boolean supports(String type);
    SecurityContext authenticateUser(AuthenticationData token, ContainerRequestContext context);
}
