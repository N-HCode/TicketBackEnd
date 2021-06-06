package com.github.ticketProject.javaSpringBootTemplate.searchUtil;

import com.github.ticketProject.javaSpringBootTemplate.model.Contact;
import com.github.ticketProject.javaSpringBootTemplate.model.ContactList;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ContactSpecification implements Specification<Contact> {

    private SearchCriteria searchCriteria;

    public ContactSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Contact> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {

        //Key is the field/ column name
        //Operation is the >, : , or <
        //value is the search keyword that the user entered in

        Path<String> searchKey = root. <String> get(searchCriteria.getKey());
        Path<ContactList> contactListSearchKey = root.get(searchCriteria.getKey());

        String searchValue = searchCriteria.getValue().toString();

        switch (searchCriteria.getOperation().toUpperCase())
        {
            case ">":
                return criteriaBuilder.greaterThanOrEqualTo(searchKey,searchValue);
            case ":":
                return criteriaBuilder.equal(searchKey,searchValue);
//            case "::":
//                return criteriaBuilder.equal(contactListSearchKey,searchValue);
            case "~":
                //This will be the LIKE in the SQL language, so you will put %test% if you want anything containing the word test
                //or other complex expressions like "_est%" and what not.
                //we put everything to lower so we can ignore casing
                return criteriaBuilder.like(criteriaBuilder.lower(searchKey),searchValue.toLowerCase());
            case "<":
                return criteriaBuilder.lessThanOrEqualTo(searchKey,searchValue);
            default:
                return null;

        }
    }
}
