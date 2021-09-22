package dev.nhaiden.wdhbsp.rest;

import dev.nhaiden.wdhbsp.database.EmployeeRepository;
import dev.nhaiden.wdhbsp.database.TaskRepository;
import dev.nhaiden.wdhbsp.exception.ArgumentIsNullException;
import dev.nhaiden.wdhbsp.exception.EmployeeAlreadyExistsException;
import dev.nhaiden.wdhbsp.exception.EmployeeNotFoundException;
import dev.nhaiden.wdhbsp.exception.IdIsNullException;
import dev.nhaiden.wdhbsp.model.Employee;
import dev.nhaiden.wdhbsp.model.Task;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    public RestController(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping("/api/employees")
    public List<Employee> getAllEmployees() {
        //System.out.println(employeeRepository.findAll());
        return employeeRepository.findAll();
    }

    @GetMapping("/api/tasks")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/api/employee/{id}")
    public Employee getUserById(@PathVariable String id) {
        if (Objects.isNull(id)) {
            throw new IdIsNullException("The Employee ID can't be null!");
        }

        return employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("The Employee with ID " + id + " was not found in the database!");
        });
    }

    @PostMapping("/api/employees")
    public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
        if (Objects.isNull(employee.getId())) {
            throw new IdIsNullException("The Employee ID can't be null!");
        }

        try {
            Employee savedEmp = employeeRepository.save(employee);
            String path = "/api/employees";
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath(path).build(savedEmp.getId());
            return ResponseEntity.created(uri).body(savedEmp);
        } catch (DataIntegrityViolationException ex) {
            if (ex.getMessage().contains("ConstraintViolationException")) {
                throw new EmployeeAlreadyExistsException("The Employee with the ID:" + employee.getId() + " already exists!");
            }
            throw ex;
        }
    }

    @GetMapping("/api/employees/{id}/hoursWorked")
    public Integer getHoursWorked(@PathVariable String id) {
        if (Objects.isNull(id)) {
            throw new IdIsNullException("The Employee ID can't be null!");
        }
        Integer val = employeeRepository.getHoursWorkedByEmployee(id);
        if (Objects.isNull(val)) {
            val = 0;
        }

        return val;
    }

    @GetMapping("/api/employees/{id}/tasks")
    public List<Task> getTasksBetweenDates(@PathVariable String id,
                                           @RequestParam String from,
                                           @RequestParam String to) {

        if (Objects.isNull(id)) {
            throw new IdIsNullException("The Employee ID can't be null!");
        }

        if (Objects.isNull(from)) {
            throw new ArgumentIsNullException("The GET \"from\" argument can't be null!");
        }

        if (Objects.isNull(to)) {
            throw new ArgumentIsNullException("The GET \"to\" argument can't be null!");
        }

        LocalDate fromDate;
        LocalDate toDate;

        Employee emp = employeeRepository.findById(id).orElseThrow(() -> {
            throw new EmployeeNotFoundException("The Employee with ID " + id + " was not found in the database!");
        });

        //System.out.println(emp);

        try {
            fromDate = LocalDate.parse(from);
            toDate = LocalDate.parse(to);

        } catch (DateTimeParseException ex) {
            throw ex;
        }

        //System.out.println(fromDate);
        //System.out.println(toDate);

        return taskRepository.findTaskByEmployeeAndFinishedBetween(emp, fromDate, toDate);
    }


}
