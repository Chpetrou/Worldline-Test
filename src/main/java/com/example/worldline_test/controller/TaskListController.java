package com.example.worldline_test.controller;

import com.example.worldline_test.dto.CreateTaskListRequest;
import com.example.worldline_test.dto.MoveTaskRequest;
import com.example.worldline_test.dto.TaskDto;
import com.example.worldline_test.dto.TaskListDto;
import com.example.worldline_test.model.TaskList;
import com.example.worldline_test.model.Task;
import com.example.worldline_test.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lists")
public class TaskListController {

    @Autowired
    private TaskListService taskListService;

    @GetMapping
    public List<TaskListDto> getAllLists() {

        return taskListService.getAllLists().stream()
                .map(taskListService::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public TaskListDto createList(@RequestBody CreateTaskListRequest request) {

        TaskList list = new TaskList();
        list.setName(request.name);
        TaskList created = taskListService.createList(list);
        
        return taskListService.convertToDto(created);
    }

    @PostMapping("/{listId}/tasks")
    public TaskDto addTask(@PathVariable Long listId, @RequestBody TaskDto taskDto) {

        TaskList list = taskListService.getListById(listId);
        Task saved = taskListService.addTaskToList(listId, taskListService.convertToEntity(taskDto, list));

        return taskListService.convertToDto(saved);
    }

    @PutMapping("/{listId}/tasks/{taskId}")
    public TaskDto updateTask(@PathVariable Long listId, @PathVariable Long taskId, @RequestBody TaskDto taskDto) {

        Task updated = taskListService.updateTask(listId, taskId, taskDto);

        return taskListService.convertToDto(updated);
    }

    @DeleteMapping("/{listId}/tasks/{taskId}")
    public void deleteTask(@PathVariable Long listId, @PathVariable Long taskId) {

        taskListService.deleteTask(listId, taskId);
    }

    @DeleteMapping("/{listId}")
    public void deleteList(@PathVariable Long listId) {

        taskListService.deleteList(listId);
    }

    @PostMapping("/tasks/move")
    public TaskDto moveTask(@RequestBody MoveTaskRequest request) {

        Task moved = taskListService.moveTaskToAnotherList(
                request.fromListId,
                request.taskId,
                request.toListId
        );

        return taskListService.convertToDto(moved);
    }
}
