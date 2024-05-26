package com.rtechnologies.echofriend.models;

import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Collection<? extends GrantedAuthority> authorities;
    private String role;
//    private Compan teacher;
//    private Student student;
    private AdminEntity admin;
    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String accessToken, Collection<? extends GrantedAuthority> authorities, String role,
                                     AdminEntity admin) {
        this.accessToken = accessToken;
        this.authorities = authorities;
        this.role = role;
        this.admin = admin;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin = admin;
    }
}
