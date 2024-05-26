package com.rtechnologies.echofriend.services;

import com.rtechnologies.echofriend.entities.admin.AdminEntity;
import com.rtechnologies.echofriend.models.security.CustomUserDetails;
import com.rtechnologies.echofriend.repositories.adminrepo.AdminRespo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AdminRespo adminRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to load Admin
        Optional<AdminEntity> admin = adminRepository.findByEmail(username);
        if (admin.isPresent()){
            return CustomUserDetails.builder()
                    .username(admin.get().getEmail())
                    .password(admin.get().getPassword())
                    .roles(Set.of("ROLE_ADMIN"))
                    .admin(admin.get())
                    .authorities(Collections.singletonList(() -> "ROLE_ADMIN")) // Assuming "ROLE_TEACHER" as the authority for teachers
                    .build();
        }

//        // Try to load a Teacher
//        Optional<Teacher> teacher = teacherRepository.findByEmail(username);
//        if (teacher.isPresent()){
//           return CustomUserDetails.builder()
//                    .username(teacher.get().getEmail())
//                    .password(teacher.get().getPassword())
//                    .roles(Set.of("ROLE_TEACHER"))
//                   .teacher(teacher.get())
//                   .student(null)
//                    .authorities(Collections.singletonList(() -> "ROLE_TEACHER")) // Assuming "ROLE_TEACHER" as the authority for teachers
//                    .build();
//        }
//
//        // Try to load a Student
//        Optional<Student> student = studentRepository.findByRollNumber(username);
//        if (student.isPresent()) {
//            studentAttendanceService.markAttendanceOnLogin(student.get().getRollNumber());
//            // Create UserDetails object for student
//            return CustomUserDetails.builder()
//                    .username(student.get().getRollNumber())
//                    .password(student.get().getPassword())
//                    .roles(Set.of("ROLE_STUDENT"))
//                    .teacher(null)
//                    .student(student.get())
//                    .authorities(Collections.singletonList(() -> "ROLE_STUDENT")) // Assuming "ROLE_STUDENT" as the authority for students
//                    .build();
//        }
        throw new UsernameNotFoundException("User not found with email: " + username);
    }

}


