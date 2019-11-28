package com.theme.park.doa;

import com.theme.park.entities.Park;
import com.theme.park.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByPark(Park park);
}
