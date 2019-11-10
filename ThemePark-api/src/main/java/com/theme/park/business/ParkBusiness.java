package com.theme.park.business;

import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Park;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.ParkDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ParkBusiness {

    Page<Park> searchParks(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException;
    Park findById(Long id) throws NotFoundException;
    Park createPark(ParkDTO parkDTO) throws AlreadyExistException;
    Park updatePark(ParkDTO parkDTO) throws AlreadyExistException, NotFoundException;
}
