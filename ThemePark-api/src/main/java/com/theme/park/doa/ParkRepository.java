package com.theme.park.doa;

import com.theme.park.entities.Park;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ParkRepository extends JpaRepository<Park, Long>, JpaSpecificationExecutor<Park> {

    Optional<Park> findByUrlName(String name);
}
