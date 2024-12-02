package com.Anonymous.smart_printing_system.model;

import com.Anonymous.smart_printing_system.model.eenum.Orderstatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders") // Đổi tên nếu bảng cơ sở dữ liệu khác
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "order_status", nullable = false)
    private Orderstatus orderStatus;

    @Column(name = "number_pages", nullable = false)
    private Integer numberPages;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, optional = false, orphanRemoval = true)
    private Payment payment;

}
