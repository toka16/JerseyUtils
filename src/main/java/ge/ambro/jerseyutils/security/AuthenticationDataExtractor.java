/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security;

import ge.ambro.jerseyutils.security.model.AuthenticationData;
import javax.ws.rs.container.ContainerRequestContext;

/**
 *
 * @author tabramishvili
 */
public interface AuthenticationDataExtractor {

    boolean supports(ContainerRequestContext requestContext);

    AuthenticationData extractData(ContainerRequestContext requestContext);

}
