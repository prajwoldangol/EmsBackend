package com.prajwol.Entity;

import com.mongodb.lang.NonNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ems_tasks")
public class EmsTask {
    @Id
    private ObjectId id;
    @NonNull
    private String taskBoardTitle;
    @NonNull
    private String taskOwner;
    private String department;
    Map<String, List<TaskDetails>> taskDetailsList;
}
