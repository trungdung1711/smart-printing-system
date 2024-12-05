package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class StudentService {
    @Autowired
    private final StudentRepository studentRepository;

    public Student getCurrentStudentLogIn() {
        String email = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated())
        {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Trả về email từ đối tượng UserDetails
                email = ((UserDetails) principal).getUsername();
            }
        }
        return studentRepository.findByEmail(email);
    }
}
