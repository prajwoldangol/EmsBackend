package com.prajwol.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "employeeClocks")
public class EmsEmployeeClockData {
    @Id
    private ObjectId id;
    private String employeeId;
    private Instant todayDate;
    private Instant clockInTime;
    private Instant clockOutTime;
    private Instant breakInTime;
    private Instant breakOutTime;
}
