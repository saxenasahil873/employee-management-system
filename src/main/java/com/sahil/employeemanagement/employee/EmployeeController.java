package com.sahil.employeemanagement.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service) { this.service = service; }

    @GetMapping public List<EmployeeDtos.Response> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public EmployeeDtos.Response findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public EmployeeDtos.Response create(@Valid @RequestBody EmployeeDtos.Request request) { return service.create(request); }
    @PutMapping("/{id}") public EmployeeDtos.Response update(@PathVariable Long id, @Valid @RequestBody EmployeeDtos.Request request) { return service.update(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.delete(id); }
}
