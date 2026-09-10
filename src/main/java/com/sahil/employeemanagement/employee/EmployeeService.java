package com.sahil.employeemanagement.employee;

import com.sahil.employeemanagement.department.Department;
import com.sahil.employeemanagement.department.DepartmentRepository;
import com.sahil.employeemanagement.role.Role;
import com.sahil.employeemanagement.role.RoleRepository;
import com.sahil.employeemanagement.shared.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;
    private final RoleRepository roles;

    public EmployeeService(EmployeeRepository employees, DepartmentRepository departments, RoleRepository roles) {
        this.employees = employees;
        this.departments = departments;
        this.roles = roles;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDtos.Response> findAll() { return employees.findAll().stream().map(this::toResponse).toList(); }

    @Transactional(readOnly = true)
    public EmployeeDtos.Response findById(Long id) { return toResponse(getEmployee(id)); }

    public EmployeeDtos.Response create(EmployeeDtos.Request request) {
        if (employees.existsByEmailIgnoreCase(request.email())) throw new DataIntegrityViolationException("Email is already in use");
        Employee employee = new Employee(request.firstName().trim(), request.lastName().trim(), request.email().trim().toLowerCase(),
                request.phone(), request.jobTitle().trim(), request.salary(), request.hireDate(), getDepartment(request.departmentId()), getRole(request.roleId()));
        return toResponse(employees.save(employee));
    }

    public EmployeeDtos.Response update(Long id, EmployeeDtos.Request request) {
        Employee employee = getEmployee(id);
        employees.findByEmailIgnoreCase(request.email()).filter(found -> !found.getId().equals(id)).ifPresent(found -> {
            throw new DataIntegrityViolationException("Email is already in use");
        });
        employee.update(request.firstName().trim(), request.lastName().trim(), request.email().trim().toLowerCase(), request.phone(),
                request.jobTitle().trim(), request.salary(), request.hireDate(), getDepartment(request.departmentId()), getRole(request.roleId()));
        return toResponse(employee);
    }

    public void delete(Long id) { employees.delete(getEmployee(id)); }

    private Employee getEmployee(Long id) { return employees.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", id)); }
    private Department getDepartment(Long id) { return departments.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department", id)); }
    private Role getRole(Long id) { return roles.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", id)); }
    private EmployeeDtos.Response toResponse(Employee e) {
        return new EmployeeDtos.Response(e.getId(), e.getFirstName(), e.getLastName(), e.getEmail(), e.getPhone(), e.getJobTitle(), e.getSalary(), e.getHireDate(),
                e.getDepartment().getId(), e.getDepartment().getName(), e.getRole().getId(), e.getRole().getName());
    }
}
