package com.example.gestordecanchas.gestordecanchas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOErrorResponse {
    
    private int status;
    private String error;
    private String message;
}
