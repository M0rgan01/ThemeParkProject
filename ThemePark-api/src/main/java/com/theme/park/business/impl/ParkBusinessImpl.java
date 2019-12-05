package com.theme.park.business.impl;

import com.theme.park.business.ParkBusiness;
import com.theme.park.doa.ParkRepository;
import com.theme.park.object.SearchCriteria;
import com.theme.park.doa.specification.SpecificationBuilder;
import com.theme.park.entities.Comment;
import com.theme.park.entities.Park;
import com.theme.park.exception.AlreadyExistException;
import com.theme.park.exception.CriteriaException;
import com.theme.park.exception.NotFoundException;
import com.theme.park.utilities.StringNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class ParkBusinessImpl implements ParkBusiness {

    private ParkRepository parkRepository;

    private static final Logger logger = LoggerFactory.getLogger(ParkBusinessImpl.class);

    public ParkBusinessImpl(ParkRepository parkRepository) {
        this.parkRepository = parkRepository;
    }

    @Override
    public Page<Park> searchParks(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException {

        if (searchCriteria == null)
            searchCriteria = new ArrayList<>();

        SpecificationBuilder builder = new SpecificationBuilder<Park>(searchCriteria);
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
    public Park findByUrlName(String urlName) throws NotFoundException {
        Optional<Park> park = parkRepository.findByUrlName(urlName);

        if (!park.isPresent())
            throw new NotFoundException("park.not.found");

        logger.debug("Getting park with urlName " + urlName);
        return park.get();
    }

    @Override
    public Park createPark(Park park) throws AlreadyExistException {

        park.setUrlName(StringNormalizer.normalize(park.getName()));

        if (parkRepository.findByUrlName(park.getUrlName()).isPresent())
            throw new AlreadyExistException("park.name.already.exist");

        park.setDateCreation(new Date());
        logger.info("Create park with name " + park.getName());
        return parkRepository.save(park);
    }

    @Override
    public Park updatePark(Long id, Park park) throws AlreadyExistException, NotFoundException {

        Park parkCompare = findById(id);

        if (!parkCompare.getName().equals(park.getName())) {
            park.setUrlName(StringNormalizer.normalize(park.getName()));
            if (parkRepository.findByUrlName(park.getUrlName()).isPresent())
                throw new AlreadyExistException("park.name.already.exist");
        }

        logger.info("Update park with id " + park.getId());
        return parkRepository.save(park);
    }

    @Override
    public void updateNotation(Long id) throws NotFoundException {
        Park park = findById(id);

        int total = 0;
        int size = 0;
        for (Comment comment : park.getComments()) {
            if (comment.getNotation() != 0){
                total += comment.getNotation();
                size ++;
            }

        }

        float truncatedDouble = BigDecimal.valueOf((float) total / size)
                .setScale(2, RoundingMode.HALF_UP)
                .floatValue();

        park.setGlobalNotation(truncatedDouble);
        logger.info("Update notation for park with id " + id);
        parkRepository.save(park);
    }


}
