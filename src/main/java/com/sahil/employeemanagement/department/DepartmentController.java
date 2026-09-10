package com.sahil.employeemanagement.department;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin
public class DepartmentController {
    private final DepartmentRepository repository;
    public DepartmentController(DepartmentRepository repository) { this.repository = repository; }

    @GetMapping public List<DepartmentResponse> findAll() { return repository.findAll().stream().map(d -> new DepartmentResponse(d.getId(), d.getName())).toList(); }
    public record DepartmentResponse(Long id, String name) {}
}
