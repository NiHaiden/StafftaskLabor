package dev.nhaiden.wdhbsp.database;

import dev.nhaiden.wdhbsp.model.Employee;
import dev.nhaiden.wdhbsp.model.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Override
    Optional<Task> findById(Integer integer);

    @Override
    <S extends Task> S save(S s);

    @Override
    List<Task> findAll();

    @Override
    void delete(Task task);

    List<Task> findTaskByEmployeeAndFinishedBetween(Employee employee, LocalDate from, LocalDate to);


}
