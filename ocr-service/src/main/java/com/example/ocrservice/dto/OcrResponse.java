package com.example.ocrservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
