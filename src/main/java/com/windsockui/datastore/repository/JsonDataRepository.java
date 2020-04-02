package com.windsockui.datastore.repository;

import com.windsockui.datastore.entities.JsonData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JsonDataRepository extends JpaRepository<JsonData, UUID> {

    Optional<JsonData> findByDomainAndPath(String domain, String path);
}
