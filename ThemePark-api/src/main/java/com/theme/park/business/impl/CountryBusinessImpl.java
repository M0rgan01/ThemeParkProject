package com.theme.park.business.impl;

import com.theme.park.business.CountryBusiness;
import com.theme.park.doa.CountryRepository;
import com.theme.park.object.SearchCriteria;
import com.theme.park.doa.specification.SpecificationBuilder;
import com.theme.park.entities.Country;
import com.theme.park.exception.CriteriaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryBusinessImpl implements CountryBusiness {

    private CountryRepository countryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ParkBusinessImpl.class);

    public CountryBusinessImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getListCountries() {
        return null;
    }

    @Override
    public Page<Country> searchCountries(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException {

        if (searchCriteria == null)
            searchCriteria = new ArrayList<>();

        SpecificationBuilder builder = new SpecificationBuilder<Country>(searchCriteria);
        Specification<Country> spec = builder.build();

        logger.debug("searching country with " + searchCriteria.size() + " criteria list size for page " + page + " with size " + size);
        return countryRepository.findAll(spec, PageRequest.of(page, size));
    }
}
