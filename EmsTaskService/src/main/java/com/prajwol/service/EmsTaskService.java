package com.prajwol.service;

import com.prajwol.Entity.EmsTask;
import com.prajwol.dto.EmsTaskDto;

public interface EmsTaskService {
    public EmsTask addTask(EmsTaskDto task);
}
