package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.TicketColumnTemplate;
import com.github.ticketProject.javaSpringBootTemplate.model.TicketColumnTemplateList;

import org.springframework.data.repository.CrudRepository;

public interface TicketColumnTemplateRepository extends CrudRepository<TicketColumnTemplate, Long> {

    Iterable<TicketColumnTemplate> findAllByTicketColumnTemplateListEquals(TicketColumnTemplateList ticketColumnTemplateList);

}
