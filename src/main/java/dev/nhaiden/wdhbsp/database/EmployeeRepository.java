package dev.nhaiden.wdhbsp.database;

import dev.nhaiden.wdhbsp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value = "SELECT  from ")
    public Integer getHoursWorkedByEmployee(Employee employee);
}
