package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.StatusList;

import com.github.mhzhou95.javaSpringBootTemplate.repository.StatusListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatusListService {

    private StatusListRepository statusListRepository;


    @Autowired
    public StatusListService(StatusListRepository statusListRepository) {
        this.statusListRepository = statusListRepository;

    }

    public List<String> findStatusListById(Long id){

        Optional<StatusList> statusListOfOrg = statusListRepository.findById(id);

        if (statusListOfOrg.isPresent()){

            return statusListOfOrg.get().getStatusList();
        }else{
            return null;
        }



    }

    public boolean addToStatusList(Long id, String status) {
        Optional<StatusList> statusList = statusListRepository.findById(id);

        if (statusList.isPresent()){
            StatusList foundStatuslist = statusList.get();
            foundStatuslist.getStatusList().add(status);
            statusListRepository.save(foundStatuslist);
            return true;
        }else{
            return false;
        }
    }

    public boolean removeFromStatusList(Long id, String status) {
        Optional<StatusList> statusList = statusListRepository.findById(id);

        if (statusList.isPresent()){
            StatusList foundStatusList = statusList.get();
            foundStatusList.getStatusList().remove(status);
            statusListRepository.save(foundStatusList);
            return true;
        }else{
            return false;
        }
    }

    public Long createNewStatusList(){

        try{
            StatusList newStatusList = new StatusList();
            newStatusList.getStatusList().add("New");
            newStatusList.getStatusList().add("In Progress");
            newStatusList.getStatusList().add("Closed");
            StatusList savedStatusList = statusListRepository.save(newStatusList);
            Long savedStatusListId = savedStatusList.getStatusListId();
            return savedStatusListId;


        } catch (Exception e) {
            e.printStackTrace();
            return (long) -1;
        }
    }



}
