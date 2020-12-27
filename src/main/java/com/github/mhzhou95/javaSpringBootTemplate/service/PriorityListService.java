package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.PriorityList;
import com.github.mhzhou95.javaSpringBootTemplate.repository.PriorityListRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<String> findPriorityListById(Long id){

        Optional<PriorityList> priorityListOfOrg = priorityListRepository.findById(id);

        if (priorityListOfOrg.isPresent()){

            return priorityListOfOrg.get().getPriorities();
        }else{
            return null;
        }



    }

    public boolean addToPriorityList(Long id, String priority) {
        Optional<PriorityList> priorityList = priorityListRepository.findById(id);

        if (priorityList.isPresent()){
            PriorityList foundPrioritylist = priorityList.get();
            foundPrioritylist.getPriorities().add(priority);
            priorityListRepository.save(foundPrioritylist);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeFromPriorityList(Long id, String priority) {
        Optional<PriorityList> priorityList = priorityListRepository.findById(id);

        if (priorityList.isPresent()){
            PriorityList foundPrioritylist = priorityList.get();
            foundPrioritylist.getPriorities().remove(priority);
            priorityListRepository.save(foundPrioritylist);
            return true;
        }else{
            return false;
        }
    }

    public Long createNewPriorityList(){

        try{
            PriorityList newPriorityList = new PriorityList();
            newPriorityList.getPriorities().add("Low");
            newPriorityList.getPriorities().add("Medium");
            newPriorityList.getPriorities().add("High");
            newPriorityList.getPriorities().add("Critical");
            PriorityList savedPriorityList= priorityListRepository.save(newPriorityList);
            Long savedListId = savedPriorityList.getPriorityListId();
            return savedListId;


        } catch (Exception e) {
            e.printStackTrace();
            return (long) -1;
        }
    }
}
