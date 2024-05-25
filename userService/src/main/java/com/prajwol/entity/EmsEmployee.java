package com.prajwol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmsEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String phone;

    private Instant joinedDate;
    @Enumerated(EnumType.STRING)
    private EmsRole role;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmsEmployer employerDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details")
    private EmsUserDetails emsUserDetails;



    @ManyToOne
    @JoinColumn(name = "dept_id")
    private EmsDepartment emsDepartment;
}
