package com.example.todocrudbackend.service;

import com.example.todocrudbackend.dto.TodoDto;
import com.example.todocrudbackend.entity.Todo;
import com.example.todocrudbackend.exception.ResourceNotFoundException;
import com.example.todocrudbackend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public TodoDto addTodo(TodoDto todoDto){
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo saveTodo = todoRepository.save(todo);
        TodoDto saveTodoDto = modelMapper.map(saveTodo, TodoDto.class);
        return saveTodoDto;
    }

    public TodoDto getTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "+ id));
        return modelMapper.map(todo, TodoDto.class);
    }

    public List<TodoDto> getALlTodos(){
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }
    
    public TodoDto updateTodo(TodoDto todoDto,Long id){
        Todo todo = getTodoById(id);
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo updateTodo = todoRepository.save(todo);
        return modelMapper.map(updateTodo, TodoDto.class);
    }

    private Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));
    }

    public void deleteTodo(Long id){
        if (todoRepository.existsById(id)){
            todoRepository.deleteById(id);
        }else 
            throw new ResourceNotFoundException("Todo not found with id : "+id);
    }

    public TodoDto completeTodo(Long id){
        Todo todo = getTodoById(id);
        todo.setCompleted(Boolean.TRUE);
        Todo updateTodo = todoRepository.save(todo);
        return modelMapper.map(updateTodo, TodoDto.class);
    }

    public TodoDto incompleteTodo(Long id){
        Todo todo = getTodoById(id);
        todo.setCompleted(Boolean.FALSE);
        Todo updateTodo = todoRepository.save(todo);
        return modelMapper.map(updateTodo, TodoDto.class);
    }
    

}
