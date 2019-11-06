package com.theme.park.doa.specification;

import com.theme.park.entities.Park;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Constructeur de spécifications
 *
 * @author Pichat morgan
 *
 * 05 octobre 2019
 */
public class ParkSpecificationBuilder {

    private List<SearchCriteria> params;


    public ParkSpecificationBuilder(List<SearchCriteria> searchCriteriaList) {
        params = searchCriteriaList;
    }

    public ParkSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Park> build() {
        if (params == null || params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
                .map(ParkSpecification::new)
                .collect(Collectors.toList());

        // récupération de la 1er spécification
        Specification<Park> result = specs.get(0);

        // pour chaque critère de recherche, on construit la spécification
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
