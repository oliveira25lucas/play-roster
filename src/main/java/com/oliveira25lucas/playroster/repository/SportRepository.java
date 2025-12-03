package com.oliveira25lucas.playroster.repository;

import com.oliveira25lucas.playroster.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
}
