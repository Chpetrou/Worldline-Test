package com.example.worldline_test.service;

import com.example.worldline_test.dto.TaskDto;
import com.example.worldline_test.model.Task;
import com.example.worldline_test.model.TaskList;
import com.example.worldline_test.repository.TaskListRepository;
import com.example.worldline_test.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskListServiceTest {

    private TaskListRepository listRepo;
    private TaskRepository taskRepo;
    private TaskListService service;

    @BeforeEach
    void setUp() {

        listRepo = mock(TaskListRepository.class);
        taskRepo = mock(TaskRepository.class);
        service = new TaskListService();

        service.listRepo = listRepo;
        service.taskRepo = taskRepo;
    }

    @Test
    void getListById_returnsList_whenExists() {

        TaskList list = new TaskList();
        list.setId(1L);
        when(listRepo.findById(1L)).thenReturn(Optional.of(list));

        TaskList result = service.getListById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getListById_throws_whenNotFound() {

        when(listRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.getListById(1L));
    }

    @Test
    void addTaskToList_savesTaskWithList() {

        TaskList list = new TaskList();
        list.setId(1L);
        Task task = new Task();
        when(listRepo.findById(1L)).thenReturn(Optional.of(list));
        when(taskRepo.save(any(Task.class))).thenReturn(task);

        Task result = service.addTaskToList(1L, task);

        assertEquals(list, result.getTaskList());
        verify(taskRepo).save(task);
    }

    @Test
    void updateTask_updatesFields() {

        TaskList list = new TaskList();
        list.setId(1L);
        Task task = new Task();
        task.setId(2L);
        task.setTaskList(list);
        when(taskRepo.findById(2L)).thenReturn(Optional.of(task));
        TaskDto dto = new TaskDto();
        dto.name = "Updated";
        dto.description = "Desc";

        when(taskRepo.save(any(Task.class))).thenReturn(task);

        Task updated = service.updateTask(1L, 2L, dto);

        assertEquals("Updated", updated.getName());
        assertEquals("Desc", updated.getDescription());
    }

    @Test
    void deleteTask_removesTask() {

        TaskList list = new TaskList();
        list.setId(1L);
        Task task = new Task();
        task.setId(2L);
        task.setTaskList(list);
        when(taskRepo.findById(2L)).thenReturn(Optional.of(task));

        service.deleteTask(1L, 2L);

        verify(taskRepo).delete(task);
    }
}