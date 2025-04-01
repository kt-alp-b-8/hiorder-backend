package com.example.ocrservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OcrResponse {
    private String type;
    private String name;
    private String rrn;
    private String address;
    private String issueDate;
    private String licenseNum;
    private String renewStartDate;
    private String renewEndDate;
    private String condition;
}
