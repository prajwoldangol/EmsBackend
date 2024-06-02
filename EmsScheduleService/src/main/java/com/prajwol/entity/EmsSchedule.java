package com.prajwol.entity;
import lombok.*;
import com.mongodb.lang.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "schedules")
public class EmsSchedule {
    @Id
    private ObjectId id;
    @NonNull
    private String employerId;
    private String departmentId;
    private Instant startDate;
    private Instant endDate;
    private Map<Instant, List<EmsEmployeeSchedule>> employeeSchedules;

}
