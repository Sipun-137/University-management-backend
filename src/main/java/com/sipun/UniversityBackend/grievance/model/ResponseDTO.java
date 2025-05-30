package com.sipun.UniversityBackend.grievance.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String actionTaken;
    private String response;
    private String status;
}
