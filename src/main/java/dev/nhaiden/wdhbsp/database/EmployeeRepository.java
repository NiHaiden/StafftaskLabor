package dev.nhaiden.wdhbsp.database;

import dev.nhaiden.wdhbsp.model.Employee;
import dev.nhaiden.wdhbsp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Override
    Optional<Employee> findById(String s);

    @Override
    List<Employee> findAll();

    @Override
    <S extends Employee> S save(S s);

    @Override
    void delete(Employee employee);

    @Query(value = "SELECT sum(t.hoursWorked) from Task t where t.employee.id = :employeeID")
    public Integer getHoursWorkedByEmployee(
            @Param("employeeID") String employeeID //Named Parameters
    );





}
