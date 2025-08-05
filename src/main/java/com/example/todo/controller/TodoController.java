package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*") // Permite CORS para React frontend
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService service) {
        this.todoService = service;
    }

    @GetMapping
    public List<Todo> getAll() {
        return todoService.findAll();
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        todo.setCompleted(false);
        return todoService.save(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.findById(id)
                .map(t -> {
                    t.setTitle(todo.getTitle());
                    t.setCompleted(todo.isCompleted());
                    Todo updated = todoService.save(t);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
