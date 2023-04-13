package com.mjc.school.controller.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {
    private String errorCode;
    private String errorMessage;

}
