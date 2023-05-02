package com.rrlira96.origininsurancebackend.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private List<StandardError> errors;
}
