package com.theme.park.business.impl;

import com.theme.park.business.ParkBusiness;
import com.theme.park.doa.ParkRepository;
import com.theme.park.doa.specification.ParkSpecificationBuilder;
import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Park;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.object.ParkDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ParkBusinessImpl implements ParkBusiness {

    private ParkRepository parkRepository;
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ParkBusinessImpl.class);

    public ParkBusinessImpl(ParkRepository parkRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.parkRepository = parkRepository;
    }

    @Override
    public Page<Park> searchParks(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException {

        if (searchCriteria == null)
            searchCriteria = new ArrayList<>();

        ParkSpecificationBuilder builder = new ParkSpecificationBuilder(searchCriteria);
        Specification<Park> spec = builder.build();

        logger.debug("searching park with " + searchCriteria.size() + " criteria list size for page " + page + " with size " + size);
        return parkRepository.findAll(spec, PageRequest.of(page, size));
    }

    @Override
    public Park findById(Long id) throws NotFoundException {

        Optional<Park> park = parkRepository.findById(id);

        if (!park.isPresent())
            throw new NotFoundException("park.not.found");

        logger.debug("Getting park with id " + id);
        return park.get();
    }

    @Override
    public Park createPark(ParkDTO parkDTO) throws AlreadyExistException {
        if (parkRepository.findByName(parkDTO.getName()).isPresent())
            throw new AlreadyExistException("park.name.already.exist");
        Park park = modelMapper.map(parkDTO, Park.class);
        park.setDateCreation(new Date());
        logger.info("Create park with name " + parkDTO.getName());
        return parkRepository.save(park);
    }

    @Override
    public Park updatePark(ParkDTO parkDTO) throws AlreadyExistException, NotFoundException {

        Park parkCompare = findById(parkDTO.getId());

        if (!parkCompare.getName().equals(parkDTO.getName()))
            if (parkRepository.findByName(parkDTO.getName()).isPresent())
                throw new AlreadyExistException("park.name.already.exist");


        Park park = modelMapper.map(parkDTO, Park.class);

        logger.info("Update park with id " + parkDTO.getId());
        return parkRepository.save(park);
    }


}
