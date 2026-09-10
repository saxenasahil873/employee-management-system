package com.sahil.employeemanagement;

import com.sahil.employeemanagement.department.*;
import com.sahil.employeemanagement.employee.*;
import com.sahil.employeemanagement.role.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class SeedData {
    @Bean
    CommandLineRunner loadDemoData(DepartmentRepository departments, RoleRepository roles, EmployeeRepository employees) {
        return args -> {
            Department engineering = departments.findByNameIgnoreCase("Engineering")
                    .orElseGet(() -> departments.save(new Department("Engineering")));
            Department people = departments.findByNameIgnoreCase("People & Culture")
                    .orElseGet(() -> departments.save(new Department("People & Culture")));
            Role developer = roles.findByNameIgnoreCase("Software Developer")
                    .orElseGet(() -> roles.save(new Role("Software Developer")));
            Role manager = roles.findByNameIgnoreCase("Engineering Manager")
                    .orElseGet(() -> roles.save(new Role("Engineering Manager")));

            if (employees.count() == 0) {
                employees.save(new Employee("Aarav", "Sharma", "aarav.sharma@example.com", "+91 98765 43210", "Backend Developer", new BigDecimal("85000"), LocalDate.of(2024, 3, 11), engineering, developer));
                employees.save(new Employee("Meera", "Kapoor", "meera.kapoor@example.com", "+91 98765 43211", "Engineering Manager", new BigDecimal("125000"), LocalDate.of(2022, 8, 1), engineering, manager));
                employees.save(new Employee("Rohan", "Malhotra", "rohan.malhotra@example.com", "+91 98765 43212", "People Operations Associate", new BigDecimal("65000"), LocalDate.of(2023, 1, 16), people, developer));
            }
        };
    }
}
