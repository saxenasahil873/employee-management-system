package com.sahil.employeemanagement.role;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin
public class RoleController {
    private final RoleRepository repository;
    public RoleController(RoleRepository repository) { this.repository = repository; }

    @GetMapping public List<RoleResponse> findAll() { return repository.findAll().stream().map(r -> new RoleResponse(r.getId(), r.getName())).toList(); }
    public record RoleResponse(Long id, String name) {}
}
