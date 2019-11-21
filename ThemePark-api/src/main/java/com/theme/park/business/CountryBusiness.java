package com.theme.park.business;

import com.theme.park.doa.specification.SearchCriteria;
import com.theme.park.entities.Country;
import com.theme.park.exception.CriteriaException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CountryBusiness {
    List<Country> getListCountries();
    Page<Country> searchCountries(List<SearchCriteria> searchCriteria, int page, int size) throws CriteriaException;
}
