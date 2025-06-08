package com.example.worldline_test.service;

import com.example.worldline_test.dto.TaskDto;
import com.example.worldline_test.dto.TaskListDto;
import com.example.worldline_test.model.*;
import com.example.worldline_test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskListService {

    @Autowired
    TaskListRepository listRepo;

    @Autowired
    TaskRepository taskRepo;

    public List<TaskList> getAllLists() {

        return listRepo.findAll();
    }

    public TaskList getListById(Long id) {

        return listRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List not found"));
    }

    public Task getTaskById(Long id) {

        return taskRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    public TaskList createList(TaskList list) {

        return listRepo.save(list);
    }

    public Task addTaskToList(Long listId, Task task) {

        TaskList list = getListById(listId);
        task.setTaskList(list);

        return taskRepo.save(task);
    }

    public void deleteTask(Long listId, Long taskId) {

        Task task = getTaskById(taskId);

        if (!task.getTaskList().getId().equals(listId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task does not belong to specified list");
        }

        taskRepo.delete(task);
    }

    public void deleteList(Long listId) {

        TaskList list = getListById(listId);

        listRepo.delete(list);
    }

    public Task updateTask(Long listId, Long taskId, TaskDto taskDto) {

        Task existingTask = getTaskById(taskId);

        if (!existingTask.getTaskList().getId().equals(listId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task does not belong to specified list");
        }

        existingTask.setName(taskDto.name);
        existingTask.setDescription(taskDto.description);

        return taskRepo.save(existingTask);
    }

    public Task moveTaskToAnotherList(Long fromListId, Long taskId, Long toListId) {

        Task task = getTaskById(taskId);

        if (!task.getTaskList().getId().equals(fromListId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task does not belong to source list");
        }

        TaskList newList = getListById(toListId);
        task.setTaskList(newList);

        return taskRepo.save(task);
    }

    public TaskListDto convertToDto(TaskList list) {

        TaskListDto dto = new TaskListDto();

        dto.id = list.getId();
        dto.name = list.getName();
        dto.tasks = list.getTasks().stream().map(this::convertToDto).collect(Collectors.toList());

        return dto;
    }

    public TaskDto convertToDto(Task task) {

        TaskDto dto = new TaskDto();

        dto.id = task.getId();
        dto.name = task.getName();
        dto.description = task.getDescription();
        dto.listId = task.getTaskList().getId();

        return dto;
    }

    public Task convertToEntity(TaskDto dto, TaskList parentList) {

        Task task = new Task();

        task.setName(dto.name);
        task.setDescription(dto.description);
        task.setTaskList(parentList);

        return task;
    }
}
