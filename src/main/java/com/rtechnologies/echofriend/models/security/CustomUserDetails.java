package com.rtechnologies.echofriend.models.security;

import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    // Additional fields depending on your user entity
    private final Long userId;
    private final String userType;  // For example, "TEACHER" or "STUDENT"
    private final Set<String> roles;
    private AdminEntity admin;
    private CompaniesEntity companyE;
    private UserEntity userEntity;
    public Set<String> getRoles() {
        return roles;
    }

    public CustomUserDetails(AdminEntity admin) {
        this.username = admin.getEmail();
        this.password = admin.getPassword();
        this.authorities = Collections.singletonList(() -> "ROLE_ADMIN");
        this.userId = admin.getId();
        this.userType = "ADMIN";
        this.admin = admin;
        this.userEntity = null;
        this.companyE = null;
        // Add roles based on your application logic
        this.roles = Set.of("ROLE_ADMIN");

    }

    public CustomUserDetails(UserEntity user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Collections.singletonList(() -> "ROLE_USER");
        this.userId = user.getUserid();
        this.userType = "`USER`";
        this.userEntity = user;
        this.admin = null;
        this.companyE = null;
        // Add roles based on your application logic
        this.roles = Set.of("ROLE_USER");

    }

    public CustomUserDetails(CompaniesEntity company) {
        this.username = company.getCompanyEmail();
        this.password = company.getPassword();
        this.authorities = Collections.singletonList(() -> "ROLE_COMPANY");
        this.userId = company.getCompanyid();
        this.userType = "`COMPANY`";
        this.userEntity = null;
        this.admin = null;
        this.companyE = company;
        // Add roles based on your application logic
        this.roles = Set.of("ROLE_COMPANY");

    }

//    public CustomUserDetails(Mentor mentor) {
//        this.username = mentor.getEmail();
//        this.password = mentor.getPassword();
//        this.authorities = Collections.singletonList(() -> "ROLE_MENTOR");
//        this.userId = mentor.getId();
//        this.userType = "MENTOR";
//        // Add roles based on your application logic
//        this.roles = Set.of("ROLE_MENTOR");
//
//    }

//    public CustomUserDetails(Student student) {
//        this.username = student.getRollNumber();
//        this.password = student.getPassword();
//        this.authorities = Collections.singletonList(() -> "ROLE_STUDENT");
//        this.userId = student.getStudentId();
//        this.userType = "STUDENT";
//        // Add roles based on your application logic
//        this.roles = Set.of("ROLE_STUDENT");
//
//    }

    // Uncomment this part if you have a Student class
    /*
    public CustomUserDetails(Student student) {
        this.username = student.getUsername();
        this.password = student.getPassword();
        this.authorities = Collections.singletonList(() -> "ROLE_STUDENT");
        this.userId = student.getId();
        this.userType = "STUDENT";
        // Add roles based on your application logic
        this.roles = Set.of("ROLE_STUDENT");
    }
    */

    public Long getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin = admin;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
