package com.prajwol.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

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
public class EmsDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "employer_id")
//    @JsonIgnoreProperties({"password", "username", "phone", "signedUpDate", "role", "emsEmployee", "emsUserDetails", "departments", "emsSubscriptionsList"})
    private EmsEmployer emsEmployer;

    @OneToMany(mappedBy = "emsDepartment")
//    @JsonIgnoreProperties({"password", "username", "phone", "employerDetails", "role", "emsDepartment", "emsUserDetails"})
    private List<EmsEmployee> emsEmployeeList;

}
