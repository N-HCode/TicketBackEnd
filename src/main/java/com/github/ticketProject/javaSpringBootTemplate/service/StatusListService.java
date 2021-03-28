package com.github.ticketProject.javaSpringBootTemplate.service;

import com.github.ticketProject.javaSpringBootTemplate.model.StatusList;

import com.github.ticketProject.javaSpringBootTemplate.repository.StatusListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusListService {

    private StatusListRepository statusListRepository;


    @Autowired
    public StatusListService(StatusListRepository statusListRepository) {
        this.statusListRepository = statusListRepository;

    }

//    public List<String> findStatusListById(Long id){
//
//        Optional<StatusList> statusListOfOrg = statusListRepository.findById(id);
//
//        if (statusListOfOrg.isPresent()){
//
//            return statusListOfOrg.get().getStatusList();
//        }else{
//            return null;
//        }
//
//    }

    public boolean addToStatusList(StatusList statusList, String status) {
        if (statusList != null){
            statusList.addStatus(status);
            statusListRepository.save(statusList);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeFromStatusList(StatusList statusList, String status) {

        if (statusList != null){
            statusList.removeStatus(status);
            statusListRepository.save(statusList);
            return true;
        }else{
            return false;
        }
    }

//    public StatusList createNewStatusList(){
//
//        try{
//            StatusList newStatusList = new StatusList();
//            newStatusList.getStatusList().add("New");
//            newStatusList.getStatusList().add("In Progress");
//            newStatusList.getStatusList().add("Closed");
//            StatusList savedStatusList = statusListRepository.save(newStatusList);
//            Long savedStatusListId = savedStatusList.getStatusListId();
//            return newStatusList;
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }



}
