package com.prajwol.service;

import com.prajwol.Entity.EmsTask;
import com.prajwol.Entity.TaskDetails;
import com.prajwol.dto.EmsTaskDto;
import com.prajwol.repository.EmsTaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmsTaskServiceImpl implements EmsTaskService {
    private EmsTaskRepository emsTaskRepository;
    public static final Predicate<String> NOT_EMPTY_STRING = s -> s != null && !s.isEmpty();


    @Autowired
    public EmsTaskServiceImpl(EmsTaskRepository emsTaskRepository) {
        this.emsTaskRepository = emsTaskRepository;
    }

    private static <T> void updateFieldIfPresent(T value, Consumer<T> updater, Predicate<T> condition) {
        if (condition.test(value)) {
            updater.accept(value);
        }
    }

    @Override
    public EmsTask addTask(EmsTaskDto task) {
        EmsTask emsTask = new EmsTask().builder()
                .taskOwner(task.getTaskOwner())
                .taskBoardTitle(task.getTaskBoardTitle())
                .department(task.getDepartment())
                .taskDetailsList(
                        task.getTaskDetailsList().entrySet().stream()
                                .collect(Collectors.toMap(Map.Entry::getKey,
                                        entry -> entry.getValue().stream()
                                                .map(TaskDetails::new)
                                                .toList(),
                                        (a, b) -> a, LinkedHashMap::new)

                                )
                )
                .build();
        return emsTaskRepository.save(emsTask);
    }
    public EmsTask updateTask(String taskId, EmsTaskDto taskDto) {
        ObjectId id = new ObjectId(taskId);
        Optional<EmsTask> optionalEmsTask = emsTaskRepository.findById(id);
        if (optionalEmsTask.isPresent()) {
            EmsTask existingEmsTask = optionalEmsTask.get();

            // Update taskOwner if present and not empty
            updateFieldIfPresent(taskDto.getTaskOwner(), existingEmsTask::setTaskOwner, NOT_EMPTY_STRING);

            // Update taskBoardTitle if present and not empty
            updateFieldIfPresent(taskDto.getTaskBoardTitle(), existingEmsTask::setTaskBoardTitle, NOT_EMPTY_STRING);

            // Update department if present and not empty
            updateFieldIfPresent(taskDto.getDepartment(), existingEmsTask::setDepartment, NOT_EMPTY_STRING);

            // Update taskDetailsList
            Map<String, List<TaskDetails>> updatedTaskDetailsList = taskDto.getTaskDetailsList().entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> entry.getValue().stream()
                                    .map(TaskDetails::new)
                                    .toList(),
                            (a, b) -> a, LinkedHashMap::new)
                    );
            existingEmsTask.setTaskDetailsList(updatedTaskDetailsList);

            return emsTaskRepository.save(existingEmsTask);
        } else {
            // Handle case where task with given ID is not found
            throw new RuntimeException("Task not found with ID: " + taskId);
        }
    }
    public void deleteTask(String taskIdString) {
        // Parse the taskIdString to ObjectId
        ObjectId taskId = new ObjectId(taskIdString);

        // Delete the task from the repository
        emsTaskRepository.deleteById(taskId);
    }

    public List<EmsTask> getAllTasks() {
        return emsTaskRepository.findAll();
    }
}
