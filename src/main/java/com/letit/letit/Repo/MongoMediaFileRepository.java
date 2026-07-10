package com.letit.letit.Repo;

import com.letit.letit.Entity.MongoMediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoMediaFileRepository extends MongoRepository<MongoMediaFile, String> {
}
