package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.TicketColumnTemplate;
import com.github.ticketProject.javaSpringBootTemplate.model.TicketColumnTemplateList;
import com.github.ticketProject.javaSpringBootTemplate.model.User;
import com.github.ticketProject.javaSpringBootTemplate.repository.TicketColumnTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketColumnTemplateService {

    private final TicketColumnTemplateRepository ticketColumnTemplateRepository;
    private final UserService userService;

    @Autowired
    public TicketColumnTemplateService(TicketColumnTemplateRepository ticketColumnTemplateRepository, UserService userService) {
        this.ticketColumnTemplateRepository = ticketColumnTemplateRepository;
        this.userService = userService;
    }


    public Iterable<TicketColumnTemplate> findAllByTicketTemplateList(Authentication authResult){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null){
            return null;
        }

        TicketColumnTemplateList ticketColumnTemplateList = user.getTicketColumnTemplateList();

        return ticketColumnTemplateRepository.findAllByTicketColumnTemplateListEquals(ticketColumnTemplateList);
    }

    public TicketColumnTemplate findById(Authentication authResult, long id){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null){
            return null;
        }

        TicketColumnTemplateList userTicketColumnList =  user.getTicketColumnTemplateList();

        Optional<TicketColumnTemplate> foundTicketColumnList = ticketColumnTemplateRepository.findById(id);

        if (foundTicketColumnList.isPresent() && userTicketColumnList.equals(foundTicketColumnList.get().getTicketColumnTemplateList())){
            return foundTicketColumnList.get();
        }

        return null;

    }

    public long addTicketColumnTemplate(Authentication authResult, TicketColumnTemplate ticketColumnTemplate){

        User user = userService.getUserByUsername(authResult.getName());
        if (user == null){
            return -1;
        }

        ticketColumnTemplate.setTicketColumnTemplateList(user.getTicketColumnTemplateList());
        TicketColumnTemplate savedTemplate= ticketColumnTemplateRepository.save(ticketColumnTemplate);

        return savedTemplate.getId();
    }

    public boolean removeTicketColumnTemplate(Authentication authResult, long id){

        TicketColumnTemplate foundTicketColumnTemplate = findById(authResult, id);
        if (foundTicketColumnTemplate == null){
            return false;
        }

        ticketColumnTemplateRepository.delete(foundTicketColumnTemplate);

        return true;

    }

    public boolean editTicketColumnTemplate(Authentication authResult, TicketColumnTemplate ticketColumnTemplate){

        TicketColumnTemplate foundTicketColumnTemplate = findById(authResult, ticketColumnTemplate.getId());
        if (foundTicketColumnTemplate == null){
            return false;
        }

        foundTicketColumnTemplate.setTemplateName(ticketColumnTemplate.getTemplateName());
        foundTicketColumnTemplate.setColumnNames(ticketColumnTemplate.getColumnNames());
        ticketColumnTemplateRepository.save(foundTicketColumnTemplate);

        return true;

    }



}
