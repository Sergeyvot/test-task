package com.example.test.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Valid
public class UserDto {
    @NotBlank(message = "Field: annotation. Error: must not be blank. Value: null")
    @Size(max = 12, message = "Field: annotation. Error: length of the field does not meet the restrictions")
    private String passport;
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
}
