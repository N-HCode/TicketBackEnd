package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.*;
import com.github.ticketProject.javaSpringBootTemplate.repository.ClientsOrganizationRepository;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.ClientsOrganizationSpecification;
import com.github.ticketProject.javaSpringBootTemplate.searchUtil.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    public Page<ClientsOrganization> findByCriteria(Authentication authResult,
//            , String fieldName, String operation, Object value
        String searchTerm, int pageNo, int numberPerPage
    ){

        User user = userService.getUserByUsername(authResult.getName());

        //We do this because the rest API will be using a GET. Which does not have a body
        //meaning we can just put query string from the URL.
        //Thus we will create the searchCriteria in the backend instead of having the frontend create a model for it.
        ClientsOrganizationSpecification specification1 =
                new ClientsOrganizationSpecification( new SearchCriteria("organizationName", "~", searchTerm));

        //This spec is to make so that the user only pull data from the organization they are a part of
        ClientsOrganizationSpecification specification2 =
                new ClientsOrganizationSpecification(new SearchCriteria("clientsOrganizationListId", ":",user.getUsersList().getTicketList().getClientsOrganizationLists().getId()));

//        ClientsOrganizationSpecification specification3 =
//                new ClientsOrganizationSpecification(new SearchCriteria("clientsOrganizationList", ":",user.getUsersList().getTicketList().getClientsOrganizationLists()));

        Pageable pageConfig = PageRequest.of(pageNo, numberPerPage);
        //You can use Paging combined with specifications as well.
        Page<ClientsOrganization> results = clientsOrganizationRepository.findAll(Specification.where(specification1).and(specification2), pageConfig);

//        List<ClientsOrganization> test = clientsOrganizationRepository.findAll(specification3);

        return results;
    }

}
