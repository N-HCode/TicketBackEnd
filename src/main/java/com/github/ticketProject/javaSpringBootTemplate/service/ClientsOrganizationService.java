package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import com.github.ticketProject.javaSpringBootTemplate.repository.ClientsOrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class ClientsOrganizationService {

    private final ClientsOrganizationRepository clientsOrganizationRepository;

    @Autowired
    public ClientsOrganizationService(ClientsOrganizationRepository clientsOrganizationRepository) {
        this.clientsOrganizationRepository = clientsOrganizationRepository;
    }

    public Iterable<ClientsOrganization> findAllClientsOrganizationByClientOrgList(ClientsOrganizationList clientsOrganizationList, int pageNo, int numberPerPage)
    {
        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);

        return clientsOrganizationRepository.findAllByClientsOrganizationListEquals(clientsOrganizationList, pageConfig).toList();
    }

    public ClientsOrganization findClientsOrganizationById(ClientsOrganizationList clientsOrganizationList, long id){

         Optional<ClientsOrganization> clientsOrganization = clientsOrganizationRepository.findById(id);

        if (clientsOrganization.isPresent() && clientsOrganization.get().getClientsOrganizationList().equals(clientsOrganizationList)){
            return clientsOrganization.get();
        }

        return null;
    }

    public boolean addClientsOrganization(ClientsOrganizationList clientsOrganizationList, ClientsOrganization clientsOrganization){

            clientsOrganization.setDateModified(ZonedDateTime.now());
            clientsOrganization.setClientsOrganizationList(clientsOrganizationList);
            clientsOrganizationRepository.save(clientsOrganization);
            return true;


    }

    public boolean removeClientsOrganization(ClientsOrganizationList clientsOrganizationList, long id){

        ClientsOrganization clientsOrganization = findClientsOrganizationById(clientsOrganizationList,id);

        if (clientsOrganization == null){
            return false;
        }

        clientsOrganizationRepository.delete(clientsOrganization);

        return true;

    }

}
