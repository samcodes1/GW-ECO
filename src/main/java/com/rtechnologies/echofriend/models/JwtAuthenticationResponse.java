package com.rtechnologies.echofriend.models;

import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Collection<? extends GrantedAuthority> authorities;
    private String role;
    private AdminEntity admin;
    private UserEntity user;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String accessToken, Collection<? extends GrantedAuthority> authorities, String role,
                                     AdminEntity admin, UserEntity userEntity) {
        System.out.println("Done 3");
        this.accessToken = accessToken;
        this.authorities = authorities;
        this.role = role;
        this.admin = admin;
        this.user = userEntity;
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
