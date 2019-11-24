package com.theme.park.business;

import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Park;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ParkBusiness {

    Page<Park> searchParks(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException;
    Park findById(Long id) throws NotFoundException;
    Park findByUrlName(String urlName) throws NotFoundException;
    Park createPark(Park park) throws AlreadyExistException;
    Park updatePark(Long id, Park park) throws AlreadyExistException, NotFoundException;
    void updateNotation(Long id) throws NotFoundException;
}
