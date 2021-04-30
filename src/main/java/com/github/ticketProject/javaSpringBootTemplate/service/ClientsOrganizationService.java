package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import com.github.ticketProject.javaSpringBootTemplate.repository.ClientsOrganizationRepository;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.ClientsOrganizationSpecification;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientsOrganizationService {

    private final ClientsOrganizationRepository clientsOrganizationRepository;
    private final UserService userService;

    @Autowired
    public ClientsOrganizationService(ClientsOrganizationRepository clientsOrganizationRepository, UserService userService) {
        this.clientsOrganizationRepository = clientsOrganizationRepository;
        this.userService = userService;
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
            clientsOrganization.setClientsOrganizationListId(clientsOrganizationList.getId());
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

    public boolean editClientsOrganization(Authentication authResult, long id, ClientsOrganization clientsOrganization){

        User user = userService.getUserByUsername(authResult.getName());

        if (user == null) {
            return false;
        }

        ClientsOrganizationList clientsOrganizationList = user.getUsersList().getTicketList().getClientsOrganizationLists();

        ClientsOrganization foundClientsOrganization=  findClientsOrganizationById(clientsOrganizationList, id);
        if (foundClientsOrganization == null){
            return false;
        }

        foundClientsOrganization.setDateModified(ZonedDateTime.now());
        foundClientsOrganization.setOrganizationName(clientsOrganization.getOrganizationName());
        foundClientsOrganization.setStreetAddress(clientsOrganization.getStreetAddress());
        foundClientsOrganization.setCity(clientsOrganization.getCity());
        foundClientsOrganization.setState(clientsOrganization.getState());
        foundClientsOrganization.setCountry(clientsOrganization.getCountry());
        foundClientsOrganization.setZipcode(clientsOrganization.getZipcode());


        return true;

    }

    public List<ClientsOrganization> findByCriteria(Authentication authResult,
//            , String fieldName, String operation, Object value
        SearchCriteria searchCriteria
    ){

        User user = userService.getUserByUsername(authResult.getName());

        ClientsOrganizationSpecification specification1 =
                new ClientsOrganizationSpecification(searchCriteria);

        //This spec is to make so that the user only pull data from the organization they are a part of
        ClientsOrganizationSpecification specification2 =
                new ClientsOrganizationSpecification(new SearchCriteria("clientsOrganizationListId", ":",user.getUsersList().getTicketList().getClientsOrganizationLists().getId()));

//        ClientsOrganizationSpecification specification3 =
//                new ClientsOrganizationSpecification(new SearchCriteria("clientsOrganizationList", ":",user.getUsersList().getTicketList().getClientsOrganizationLists()));


        List<ClientsOrganization> results = clientsOrganizationRepository.findAll(Specification.where(specification1).and(specification2));

//        List<ClientsOrganization> test = clientsOrganizationRepository.findAll(specification3);

        return results;
    }

}
