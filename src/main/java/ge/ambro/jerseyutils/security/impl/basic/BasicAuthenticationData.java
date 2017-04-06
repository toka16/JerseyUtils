/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ge.ambro.jerseyutils.security.impl.basic;

import ge.ambro.jerseyutils.security.model.AuthenticationData;

/**
 *
 * @author tabramishvili
 */
public class BasicAuthenticationData implements AuthenticationData {

    private final String type;
    private final String token;

    public BasicAuthenticationData(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "BasicAuthenticationData{" + "type=" + type + ", token=" + token + '}';
    }

}
