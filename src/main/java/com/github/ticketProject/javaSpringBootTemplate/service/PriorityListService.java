package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.PriorityList;
import com.github.ticketProject.javaSpringBootTemplate.repository.PriorityListRepository;
import com.github.ticketProject.javaSpringBootTemplate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class PriorityListService {

    private PriorityListRepository priorityListRepository;

    @Autowired
    public PriorityListService(PriorityListRepository priorityListRepository) {
        this.priorityListRepository = priorityListRepository;
    }

    public boolean addToPriorityList(PriorityList priorityList, String priority) {

        if (priorityList != null){
            priorityList.addPriorityToList(priority);
            priorityListRepository.save(priorityList);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeFromPriorityList(PriorityList priorityList, String priority) {


        if (priorityList != null){
            priorityList.removePriorityFromList(priority);
            priorityListRepository.save(priorityList);
            return true;
        }else{
            return false;
        }
    }

//    public Long createNewPriorityList(){
//
//        try{
//            PriorityList newPriorityList = new PriorityList();
//            newPriorityList.getPriorities().add("Low");
//            newPriorityList.getPriorities().add("Medium");
//            newPriorityList.getPriorities().add("High");
//            newPriorityList.getPriorities().add("Critical");
//            PriorityList savedPriorityList= priorityListRepository.save(newPriorityList);
//            Long savedListId = savedPriorityList.getPriorityListId();
//            return savedListId;
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return (long) -1;
//        }
//    }
}
