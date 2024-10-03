package com.rtechnologies.echofriend.services;

import com.rtechnologies.echofriend.appconsts.AppConstants;
import com.rtechnologies.echofriend.entities.companies.CompaniesEntity;
import com.rtechnologies.echofriend.entities.otp.OtpEntity;
import com.rtechnologies.echofriend.entities.user.UserEntity;
import com.rtechnologies.echofriend.exceptions.RecordNotFoundException;
import com.rtechnologies.echofriend.models.otp.OtpRequest;
import com.rtechnologies.echofriend.models.otp.OtpResponse;
import com.rtechnologies.echofriend.models.security.CustomUserDetails;
import com.rtechnologies.echofriend.repositories.companies.CompaniesRepo;
import com.rtechnologies.echofriend.repositories.otp.OtpRepo;
import com.rtechnologies.echofriend.repositories.user.UserRepo;
import com.rtechnologies.echofriend.utility.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.text.Utilities;

@Service
public class CustomEndUserDetailService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    CompaniesRepo companiesRepoObj;

    @Autowired
    OtpRepo otpRepoObj;

    public CustomUserDetails loadUserByUsername(String username)  {
        // Try to load a mentor
        Optional<UserEntity> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return CustomUserDetails.builder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles(Set.of("ROLE_USER"))
                    .admin(null)
                    .userEntity(user.get())
                    .authorities(Collections.singletonList(() -> "ROLE_USER")) // Assuming "ROLE_TEACHER" as the authority for teachers
                    .build();
        }
        throw new NotFoundException("User not found with email: " + username);
    }

    public OtpResponse sendotp(OtpRequest otp) throws MessagingException{
        OtpResponse response = new OtpResponse();
        // try{
            SimpleMailMessage message = new SimpleMailMessage();
            String otpCode = Utility.generateOTP();
            Optional<CompaniesEntity> companydata = companiesRepoObj.findByCompanyEmail(otp.getEmail());
            if(!companydata.isPresent()){
                throw new RecordNotFoundException("Company not found with email: " + otp.getEmail());
            }
            OtpEntity otpdata = new OtpEntity(
                null, otpCode,false,Utility.getExpiryTimestampOneMinute(),companydata.get().getCompanyid(), Utility.getcurrentTimeStamp(),"company"
            );
            otpRepoObj.save(otpdata);
            System.out.println("SENDING NOW1");
            // MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setFrom("greenwavegib@gmail.com");
            System.out.println("SENDING NOW2");
            message.setTo(otp.getEmail());
            System.out.println("SENDING NOW3");
            message.setText("Your password reset OTP : "+otpCode+" \n Dont share with anyone.");
            System.out.println("SENDING NOW4");
            message.setSubject("OTP!");
            // helper.setTo(otp.getEmail());
            // helper.setSubject("OTP for Password reset");
            // helper.setText("Your password reset OTP : "+otpCode+" \n Dont share with anyone.", true);
            System.out.println("SENDING NOW");
            javaMailSender.send(message);
            System.out.println("send....................");
            // MimeMessageHelper helper = new MimeMessageHelper(message, true);
            // helper.setTo(otp.getEmail());
            // helper.setSubject("OTP for Password reset");
            // helper.setText("Your password reset OTP : "+otpCode+" \n Dont share with anyone.", true);
            // javaMailSender.send(message);
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        // }catch(Exception e){
        //     response.setResponseMessage("exception : "+e.toString());
        //     return response;
        // }
    }

    public OtpResponse sendotptouser(OtpRequest otp) throws MessagingException{
        OtpResponse response = new OtpResponse();
        // try{
            SimpleMailMessage message = new SimpleMailMessage();
            String otpCode = Utility.generateOTP();
            Optional<UserEntity> companydata = userRepository.findByEmail(otp.getEmail());
            if(!companydata.isPresent()){
                throw new RecordNotFoundException("Company not found with email: " + otp.getEmail());
            }
            OtpEntity otpdata = new OtpEntity(
                null, otpCode,false,Utility.getExpiryTimestampOneMinute(),companydata.get().getUserid(), Utility.getcurrentTimeStamp(),"user"
            );
            System.out.println("SENDING NOW10000 OTP+++ = "+otpCode);
            otpRepoObj.save(otpdata);
            System.out.println("SENDING NOW1");
            // MimeMessageHelper helper = new MimeMessageHelper(message, true);
            message.setFrom("greenwavegib@gmail.com");
            System.out.println("SENDING NOW2");
            message.setTo(otp.getEmail());
            System.out.println("SENDING NOW3");
            message.setText("Your password reset OTP : "+otpCode+" \n Dont share with anyone.");
            System.out.println("SENDING NOW4");
            message.setSubject("OTP!");
            // helper.setTo(otp.getEmail());
            // helper.setSubject("OTP for Password reset");
            // helper.setText("Your password reset OTP : "+otpCode+" \n Dont share with anyone.", true);
            System.out.println("SENDING NOW");
            javaMailSender.send(message);
            System.out.println("send....................");
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        // }catch(Exception e){
        //     System.out.println("EXCEPTION OCCURED: > "+ e.toString());
        //     response.setResponseMessage("exception : "+e.toString());
        //     return response;
        // }
    }

    public OtpResponse verify(OtpRequest otp) throws MessagingException{

        Optional<CompaniesEntity> companydata = companiesRepoObj.findByCompanyEmail(otp.getEmail());
        if(!companydata.isPresent()){
            throw new RecordNotFoundException("Company not found with email: " + otp.getEmail());
        }
        OtpEntity otpdata = otpRepoObj.otpdata(companydata.get().getCompanyid(), "company");
        if(otpdata==null){
            throw new RecordNotFoundException("OTP expired");
        }
        OtpResponse response = new OtpResponse();
        if(otpdata.getOtp().equalsIgnoreCase(otp.getOtp())){
            otpdata.setIsused(true);
            
            otpRepoObj.save(otpdata);

            response.setData(otpdata);
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }
        throw new RecordNotFoundException("Otp Failed ");
    }

    public OtpResponse verifyuser(OtpRequest otp) throws MessagingException{
        OtpResponse response = new OtpResponse();
        Optional<UserEntity> companydata = userRepository.findByEmail(otp.getEmail());
        if(!companydata.isPresent()){
            throw new RecordNotFoundException("Company not found with email: " + otp.getEmail());
        }
        OtpEntity otpdata = otpRepoObj.otpdata(companydata.get().getUserid(), "user");
        if(otpdata==null){
            throw new RecordNotFoundException("OTP expired");
        }

        if(otpdata.getOtp().equalsIgnoreCase(otp.getOtp())){
            otpdata.setIsused(true);
            otpRepoObj.save(otpdata);
            response.setResponseMessage(AppConstants.SUCCESS_MESSAGE);
            return response;
        }
        throw new RecordNotFoundException("Otp Failed ");
    }
}


