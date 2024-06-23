package com.lokomotif.schedulerinfo.repository;

import com.lokomotif.schedulerinfo.model.Lokomotif;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LokoRepository extends MongoRepository<Lokomotif, Long> {
}
