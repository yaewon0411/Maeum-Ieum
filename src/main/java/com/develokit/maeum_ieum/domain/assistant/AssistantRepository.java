package com.develokit.maeum_ieum.domain.assistant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssistantRepository extends JpaRepository<Assistant, Long> {

    Optional<Assistant> findByAccessCode(String accessCode);
}
