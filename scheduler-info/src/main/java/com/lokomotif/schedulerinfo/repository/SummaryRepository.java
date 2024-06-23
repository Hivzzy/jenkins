package com.lokomotif.schedulerinfo.repository;

import com.lokomotif.schedulerinfo.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface SummaryRepository extends JpaRepository<Summary, Long> {
    List<Summary> findAllByStatusContainingIgnoreCase(String status);
}
