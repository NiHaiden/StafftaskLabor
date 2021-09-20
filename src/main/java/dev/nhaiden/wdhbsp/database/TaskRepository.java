package dev.nhaiden.wdhbsp.database;

import dev.nhaiden.wdhbsp.model.Task;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

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


}
