package com.prajwol.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EmsEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String phone;

    private Instant joinedDate;
    @Enumerated(EnumType.STRING)
    private EmsRole role;
    @ManyToOne
    @JoinColumn(name = "employer_id")
//    @JsonIgnoreProperties({"password", "username", "phone", "signedUpDate", "role", "emsEmployee", "emsUserDetails", "departments", "emsSubscriptionsList"})
    private EmsEmployer employerDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_details")
    private EmsUserDetails emsUserDetails;

    @ManyToOne
    @JoinColumn(name = "dept_id")
//    @JsonIgnoreProperties({"emsEmployer", "emsEmployeeList"})
    private EmsDepartment emsDepartment;
}
