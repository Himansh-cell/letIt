package com.letit.letit.Repo;

import com.letit.letit.Entity.BaseProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public interface BaseProfileRepo extends JpaRepository<BaseProfile,String> {


    Optional<BaseProfile> findByEmail(String id);

    Optional<BaseProfile> findByUserName(String username);

}
