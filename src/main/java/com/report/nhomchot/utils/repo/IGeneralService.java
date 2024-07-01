package com.report.nhomchot.utils.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IGeneralService<T> {
    List<T> findAll();

    Optional<T> findById(UUID id);

    T save(T t);

    void remove(UUID id);
}
