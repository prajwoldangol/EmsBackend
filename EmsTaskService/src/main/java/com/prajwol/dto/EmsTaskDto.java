package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsTaskDto {
    private String id;
    private String taskBoardTitle;
    private String taskOwner;
    private String department;
    private Map<String, List<TaskDetailsDto>> taskDetailsList;
}
