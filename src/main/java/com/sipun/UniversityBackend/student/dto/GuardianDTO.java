package com.sipun.UniversityBackend.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuardianDTO {
    private String guardianName;
    private String guardianPhone;
    private String relationship;

}
