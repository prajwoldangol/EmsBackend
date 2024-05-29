package com.prajwol.entity;

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
public class EmsEmployer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String phone;

    private Instant signedUpDate;

    @Enumerated(EnumType.STRING)
    private EmsRole role;
    @OneToMany(mappedBy = "employerDetails", cascade = CascadeType.ALL)
    private List<EmsEmployee> emsEmployee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_details")
    private EmsUserDetails emsUserDetails;





    @OneToMany(mappedBy = "emsEmployer", cascade = CascadeType.ALL)
    private Set<EmsDepartment> departments;


    @OneToMany(mappedBy = "emsSubscriber")
    private List<EmsSubscriptions> emsSubscriptionsList;

    public EmsEmployer(String username, String password, String phone) {
        this.password = password;
        this.username = username;
        this.phone = phone;
    }
}
