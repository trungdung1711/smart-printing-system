package com.Anonymous.smart_printing_system.service;

import com.Anonymous.smart_printing_system.dto.payment.*;
import com.Anonymous.smart_printing_system.model.PagePrice;
import com.Anonymous.smart_printing_system.model.Payment;
import com.Anonymous.smart_printing_system.model.Student;
import com.Anonymous.smart_printing_system.model.eenum.PageType;
import com.Anonymous.smart_printing_system.model.eenum.PaymentStatus;
import com.Anonymous.smart_printing_system.repository.PagePriceRepository;
import com.Anonymous.smart_printing_system.repository.PaymentRepository;
import com.Anonymous.smart_printing_system.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Transactional
@Service
@AllArgsConstructor
public class PaymentService {

    @Autowired
    private final PagePriceRepository pagePriceRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PaymentRepository paymentRepository;

    private long calculatePrice(long numberOfPages) {
        long finalPrice = 0;

        ArrayList<PagePrice> pagePrices = pagePriceRepository.findAllByOrderByNumberOfPagesAsc();

        int pagePricesSize = pagePrices.toArray().length;

        int highestPriceIndex = 0;
        for (int i = pagePricesSize - 1; i > 0 ; i--) {
            if (numberOfPages > pagePrices.get(i).getNumberOfPages()) {
                highestPriceIndex = i;
                break;
            }
        }
        finalPrice += pagePrices.get(highestPriceIndex).getPrice() * (numberOfPages - pagePrices.get(highestPriceIndex).getNumberOfPages());
        for (int i = highestPriceIndex - 1; i >= 0; i--) {
            finalPrice += pagePrices.get(i).getPrice() * (pagePrices.get(i + 1).getNumberOfPages() - pagePrices.get(i).getNumberOfPages());
        }

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
        long finalPrice = calculatePrice(paymentBuyPagesRequestDto.getNumOfPages());

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
            student.setStudentNumRemained(student.getStudentNumRemained() + paymentBuyPagesRequestDto.getNumOfPages());
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

    public StudentGetPaymentsHistoryResponseDto studentGetPaymentHistory(
            GetPaymentHistoryRequestDto request,
            Pageable pageable
    ) {
        try {
            Student student = studentService.getCurrentStudentLogIn();

            if (student == null) {
                throw new RuntimeException("Student not found!");
            }

            long studentId = student.getStudentId();
            LocalDateTime startDate = request.getStartDate();
            LocalDateTime endDate = request.getEndDate();

            Page<Payment> payments = paymentRepository.findPaymentsByStudentIdAndDateRange(studentId, startDate, endDate, pageable);

            List<StudentPaymentDto> studentPaymentDtoList = payments.getContent().stream()
                    .map(payment -> new StudentPaymentDto(
                            payment.getPayCost(),
                            payment.getPayDate(),
                            payment.getStatus(),
                            payment.getNumberOfPages()
                    ))
                    .collect(Collectors.toList());

            StudentGetPaymentsHistoryResponseDto response = new
                    StudentGetPaymentsHistoryResponseDto(studentPaymentDtoList,
                    pageable.getPageNumber(), studentPaymentDtoList.toArray().length, payments.getTotalPages(), "Get payment history successfully!");

            return response;
        }
        catch (Exception e) {
            return new StudentGetPaymentsHistoryResponseDto(null, 0, 0, 0, e.getMessage());
        }
    }

    public AdminGetAllPaymentResponseDto adminGetAllPaymentHistory(
            GetPaymentHistoryRequestDto request, Pageable pageable
    ) {

        try {

            LocalDateTime startDate = request.getStartDate();
            LocalDateTime endDate = request.getEndDate();

            Page<Payment> payments = paymentRepository.findPaymentsByDateRange(startDate, endDate, pageable);

            List<PaymentDto> paymentDtoList = payments.getContent().stream()
                    .map(payment -> new PaymentDto(
                            payment.getPayCost(),
                            payment.getPayDate(),
                            payment.getStatus(),
                            payment.getNumberOfPages(),
                            payment.getStudent().getStudentId()
                    ))
                    .collect(Collectors.toList());

            AdminGetAllPaymentResponseDto response = new AdminGetAllPaymentResponseDto(paymentDtoList,
                    pageable.getPageNumber(), paymentDtoList.toArray().length, payments.getTotalPages(), "Get payment history successfully!");
            return response;
        }
        catch (Exception e) {
            return new AdminGetAllPaymentResponseDto(null, 0, 0, 0, e.getMessage());
        }
    }

    public StudentGetNumPagesDto getNumPages() {
        Student student = studentService.getCurrentStudentLogIn();
        return new StudentGetNumPagesDto(student.getStudentNumRemained());
    }

    public StudentGetWalletDto studentGetWallet() {
        Student student = studentService.getCurrentStudentLogIn();

        Long wallet = student.getStudentWallet();
        Long studentId = student.getStudentId();

        return new StudentGetWalletDto(studentId, wallet);
    }

}
