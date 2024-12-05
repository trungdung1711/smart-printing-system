package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.dto.payment.PaymentAddWalletResponseDto;
import com.Anonymous.smart_printing_system.dto.payment.PaymentBuyPagesRequestDto;
import com.Anonymous.smart_printing_system.dto.payment.PaymentBuyPagesResponseDto;
import com.Anonymous.smart_printing_system.model.Payment;
import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import com.Anonymous.smart_printing_system.repository.PageConfigRepository;
import com.Anonymous.smart_printing_system.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Transactional
@Service
@AllArgsConstructor
public class PaymentService {

    @Autowired
    private final PageConfigRepository pageConfigRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;

    private long calculatePrice(PageType pageType, long numberOfPages) {
        long finalPrice = 0;

        // Get price of pageType
        long pagePrice = pageConfigRepository.findByPageType(pageType).getPrice();

        if (pagePrice == 0) {
            throw new RuntimeException("Page price not found");
        }

        finalPrice = pagePrice * numberOfPages;

        return finalPrice;
    }

    public PaymentAddWalletResponseDto studentAddWallet(long amount) {
        // Thêm tiền vào ví sinh viên
        Student student = studentService.getCurrentStudentLogIn();

        if (student == null) {
            return new PaymentAddWalletResponseDto("Student not found!");
        }

        student.setStudentWallet(student.getStudentWallet() + amount);

        studentRepository.save(student);

        return new PaymentAddWalletResponseDto("Successfuly add " + amount + " to wallet");

    }

    public PaymentBuyPagesResponseDto studentBuyPagesRequest(PaymentBuyPagesRequestDto paymentBuyPagesRequestDto) {
        // Kiểm tra thông tin yêu cầu mua trang của sinh viên
        Payment payment = new Payment();
        long finalPrice = calculatePrice(paymentBuyPagesRequestDto.getPageType(), paymentBuyPagesRequestDto.getNumOfPages());

        payment.setPayCost((double) finalPrice);
        payment.setPayDate(java.time.LocalDateTime.now());
        payment.setNumberOfPages(paymentBuyPagesRequestDto.getNumOfPages());
        Student student = studentService.getCurrentStudentLogIn();
        try {
            ResponseEntity<PaymentBuyPagesResponseDto> response;

            if (student.getStudentWallet() < finalPrice) {
                throw new RuntimeException("Not enough money!");
            }

            student.setStudentWallet(student.getStudentWallet() - finalPrice);
            PaymentBuyPagesResponseDto paymentBuyPagesResponseDto = new PaymentBuyPagesResponseDto("Buy pages successfully!");

            payment.setStudent(student);
            payment.setStatus(PaymentStatus.COMPLETED);
            student.getPayments().add(payment);
            studentRepository.save(student);
            return paymentBuyPagesResponseDto;
        }
        catch (Exception e) {
            payment.setStudent(student);
            payment.setStatus(PaymentStatus.FAILED);
            student.getPayments().add(payment);
            studentRepository.save(student);
            return new PaymentBuyPagesResponseDto(e.getMessage());
        }
    }
}
