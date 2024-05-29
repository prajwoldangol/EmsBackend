package com.prajwol.entity;

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
public class EmsDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private EmsEmployer emsEmployer;

    @OneToMany(mappedBy = "emsDepartment")
    private List<EmsEmployee> emsEmployeeList;


}
