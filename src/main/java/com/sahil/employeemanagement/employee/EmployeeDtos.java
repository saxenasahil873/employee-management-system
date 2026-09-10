package com.sahil.employeemanagement.employee;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public final class EmployeeDtos {
    private EmployeeDtos() {}

    public record Request(
            @NotBlank @Size(max = 80) String firstName,
            @NotBlank @Size(max = 80) String lastName,
            @NotBlank @Email @Size(max = 160) String email,
            @Size(max = 30) String phone,
            @NotBlank @Size(max = 120) String jobTitle,
            @NotNull @DecimalMin("0.0") BigDecimal salary,
            @NotNull LocalDate hireDate,
            @NotNull Long departmentId,
            @NotNull Long roleId) {}

    public record Response(Long id, String firstName, String lastName, String email, String phone,
                           String jobTitle, BigDecimal salary, LocalDate hireDate,
                           Long departmentId, String departmentName, Long roleId, String roleName) {}
}
