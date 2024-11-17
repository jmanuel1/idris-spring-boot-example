package io.github.mmhelloworld.helloworld;

import io.github.mmhelloworld.helloworld.Employee;
import io.micronaut.transaction.annotation.ReadOnly;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class EmployeeRepositoryBean {

    public final EntityManager entityManager;

    public EmployeeRepositoryBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Employee save(@NotNull Employee employee) {
        entityManager.persist(employee);
        return employee;
    }

    @ReadOnly
    public List<Employee> findAll() {
        return EmployeeRepository.findAll(entityManager);
    }
}
