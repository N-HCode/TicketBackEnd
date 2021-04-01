package com.github.ticketProject.javaSpringBootTemplate.repository;

import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganization;

import com.github.ticketProject.javaSpringBootTemplate.model.ClientsOrganizationList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsOrganizationRepository extends PagingAndSortingRepository<ClientsOrganization, Long> {

    Page<ClientsOrganization> findAllByClientsOrganizationListEquals(ClientsOrganizationList clientsOrganizationList, Pageable pageable);

}
