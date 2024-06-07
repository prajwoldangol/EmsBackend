package com.prajwol.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EmsEmployer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    private String companyName;

    @Column(unique = true, nullable = false)
    private String phone;

    private Instant signedUpDate;

    @Enumerated(EnumType.STRING)
    private EmsRole role;
    @OneToMany(mappedBy = "employerDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmsEmployee> emsEmployee;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_details")
    private EmsUserDetails emsUserDetails;

    @OneToMany(mappedBy = "emsEmployer", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<EmsDepartment> departments;

    @OneToMany(mappedBy = "emsEmployer" , orphanRemoval = true)
    private List<EmsSubscriptions> emsSubscriptionsList;

    public EmsEmployer(String username, String password, String phone) {
        this.password = password;
        this.username = username;
        this.phone = phone;
    }
}
