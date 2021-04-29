package com.github.ticketProject.javaSpringBootTemplate.searchUtil;

import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganization;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ClientsOrganizationSpecification implements Specification<ClientsOrganization> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<ClientsOrganization> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {

        //Key is the field/ column name
        //Operation is the >, : , or <
        //value is the search keyword that the user entered in

        Path<String> searchKey = root. <String> get(searchCriteria.getKey());
        String searchValue = searchCriteria.getValue().toString();

        switch (searchCriteria.getOperation().toUpperCase())
        {
            case ">":
                return criteriaBuilder.greaterThanOrEqualTo(searchKey,searchValue);
            case ":":
                return criteriaBuilder.like(searchKey,searchValue);
            case "<":
                return criteriaBuilder.lessThanOrEqualTo(searchKey,searchValue);
            default:
                return null;

        }
    }
}
