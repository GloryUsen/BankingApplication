package com.glory.bankapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity // This is for jpa(Java persistence api, which helps through the @Entity annotation to convert all the java syntax to the corresponding Mysql db tables or fields).
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor

public class BankUser {

    @Id // This @Id is the primary key that unique identifies this records here.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String firstname;
    private String lastname;
    private String othername;
    private String gender;
    private String address;
    private String email;
    private String status;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String stateOfOrigin;
    private String accountNumber; // Generated on creation
    private BigDecimal accountBalance;
    @CreationTimestamp
    private LocalDate createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;



}
