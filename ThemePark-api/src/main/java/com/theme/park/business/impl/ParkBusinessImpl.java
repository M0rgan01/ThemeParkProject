package com.theme.park.business.impl;

import com.theme.park.business.ParkBusiness;
import com.theme.park.doa.ParkRepository;
import com.theme.park.doa.specification.ParkSpecificationBuilder;
import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Park;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ParkBusinessImpl implements ParkBusiness {

    private ParkRepository parkRepository;

    public ParkBusinessImpl(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    @Override
    public Page<Park> searchParks(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException {

        if (searchCriteria == null)
            searchCriteria = new ArrayList<>();

        ParkSpecificationBuilder builder = new ParkSpecificationBuilder(searchCriteria);
        Specification<Park> spec = builder.build();
        return parkRepository.findAll(spec, PageRequest.of(page, size));
    }

    @Override
    public Park findById(Long id) throws NotFoundException {

        Optional<Park> park = parkRepository.findById(id);

        if(!park.isPresent())
        throw new NotFoundException("park.not.found");

        return park.get();
    }
}
