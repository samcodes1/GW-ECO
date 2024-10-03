package com.rtechnologies.echofriend.controller;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.config.JwtConfig;
import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.JwtAuthenticationResponse;
import com.rtechnologies.echofriend.models.LoginRequest;
import com.rtechnologies.echofriend.models.otp.OtpRequest;
import com.rtechnologies.echofriend.models.otp.OtpResponse;
import com.rtechnologies.echofriend.models.security.CustomUserDetails;
import com.rtechnologies.echofriend.repositories.adminrepo.AdminRespo;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.services.CompanyAuthService;
import com.rtechnologies.echofriend.services.CustomEndUserDetailService;
import com.rtechnologies.echofriend.services.CustomUserDetailService;
import com.rtechnologies.echofriend.utility.Utility;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtConfig jwtTokenProvider;

    @Autowired
    private CustomUserDetailService customUserDetailsService;

    @Autowired
    private CustomEndUserDetailService customEndUserDetailService;

    @Autowired
    private AdminRespo adminRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    CompaniesRepo companiesRepoObj;

    @Autowired
    CompanyAuthService companyAuthServiceobj;

    @PostMapping("/login/admin")
    @ApiOperation(value = "Authenticate Admin", notes = "Authenticate user with username/email and password.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication successful", response = JwtAuthenticationResponse.class),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public JwtAuthenticationResponse authenticateAdmin(@RequestBody LoginRequest loginRequest) {
        AdminEntity user = adminRepository.findByEmail(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new NotFoundException("Admin not found with email: " + loginRequest.getUsernameOrEmail()));

        if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotFoundException("Incorrect password");
        }
        //        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(loginRequest,1);

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return new JwtAuthenticationResponse(jwt, authorities,"ROLE_ADMIN", userDetails.getAdmin(), userDetails.getUserEntity(), userDetails.getCompanyE());

        }

        return null;
    }

    @PostMapping("/login/user")
    @ApiOperation(value = "Authenticate User", notes = "Authenticate user with username/email and password.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication successful", response = JwtAuthenticationResponse.class),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public JwtAuthenticationResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Find mentor by email
        UserEntity user = userRepository.findByEmail(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new NotFoundException("User not found with email: " + loginRequest.getUsernameOrEmail()));

        if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotFoundException("Incorrect password");
        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(loginRequest);
        CustomUserDetails userDetails = null;
        userDetails = customEndUserDetailService.loadUserByUsername(loginRequest.getUsernameOrEmail());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if(userDetails != null) {
            return new JwtAuthenticationResponse(jwt, authorities,"ROLE_USER", userDetails.getAdmin(), userDetails.getUserEntity(), userDetails.getCompanyE());
        }
        return null;
    }

    @PostMapping("/login/company")
    @ApiOperation(value = "Authenticate User", notes = "Authenticate user with username/email and password.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication successful", response = JwtAuthenticationResponse.class),
            @ApiResponse(code = 401, message = "Invalid credentials"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public JwtAuthenticationResponse authenticateCompany(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {
        // Find mentor by email
        CompaniesEntity company = companiesRepoObj.findByCompanyEmail(loginRequest.getUsernameOrEmail())
                .orElseThrow(() -> new NotFoundException("User not found with email: " + loginRequest.getUsernameOrEmail()));
        System.out.println("HASH ==>> "+Utility.hashPassword(loginRequest.getPassword()));
        if (!Utility.compareHashes(Utility.hashPassword(loginRequest.getPassword()), company.getPassword())) {
            throw new RecordNotFoundException("Incorrect password");
        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(loginRequest, true);
        CustomUserDetails userDetails = null;
        userDetails = companyAuthServiceobj.loadUserByUsername(loginRequest.getUsernameOrEmail());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if(userDetails != null) {
            return new JwtAuthenticationResponse(jwt, authorities,"ROLE_COMPANY", userDetails.getAdmin(), userDetails.getUserEntity(), userDetails.getCompanyE());
        }
        return null;
    }

    @PostMapping("/logout")
    @ApiOperation(value = "Logout User", notes = "Logout currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Logout successful"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/sendotp")
    public ResponseEntity<?> postMethodName(@RequestBody OtpRequest otp) throws MessagingException {
        OtpResponse  response = customEndUserDetailService.sendotp(otp);
        // return ResponseEntity.ok("OTP SEND");
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otp) throws MessagingException {
        OtpResponse response = customEndUserDetailService.verify(otp);
        return ResponseEntity.status(response.getResponseMessage().equalsIgnoreCase(AppConstants.SUCCESS_MESSAGE) ? 200 : 500).body(response);
    }
    
}
