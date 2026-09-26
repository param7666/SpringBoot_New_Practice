package com.param.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class StudentLoginResponse {

    private Long id;
    private String username;
    private String name;
    private String role;
}
