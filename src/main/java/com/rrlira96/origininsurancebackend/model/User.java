package com.rrlira96.origininsurancebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class User {

    @Positive
    @NotNull
    @JsonProperty(value = "age")
    private int age;

    @PositiveOrZero
    @NotNull
    @JsonProperty(value = "dependents")
    private int dependents;

    @JsonProperty(value = "house")
    private House house;

    @PositiveOrZero
    @NotNull
    @JsonProperty(value = "income")
    private int income;

    @NotNull
    @JsonProperty(value = "marital_status")
    private MaritalStatus maritalStatus;

    @Size(min = 3, max = 3, message = "must be of size 3")
    @NotNull
    @JsonProperty(value = "risk_questions")
    private List<Integer> riskAnswers;

    @JsonProperty(value = "vehicle")
    private Vehicle vehicle;

}
