package com.example.todocrudbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoDto {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
