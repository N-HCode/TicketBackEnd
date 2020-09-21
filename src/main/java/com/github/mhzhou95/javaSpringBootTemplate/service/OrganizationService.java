package com.github.mhzhou95.javaSpringBootTemplate.service;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public ResponseEntity<?> findAll()
    {
        Iterable<Organization> AllOrg = organizationRepository.findAll();
        return new ResponseEntity<>(AllOrg, HttpStatus.OK);
    }

    public ResponseEntity<?> findById(Long id) {
        //Optional are used to avoid null exception.
        //It is just a container object which may or may not contain a non-null value
        Optional<Organization> organization = organizationRepository.findById(id);

        //initialize the HTTP response
        ResponseEntity<?> responseFindId;

        //See if there is a value in the Optional. If not, send back a 404 error.
        if (organization.isPresent()){
            responseFindId = new ResponseEntity<>(organization, HttpStatus.OK);
        }else{
            responseFindId = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return responseFindId;
    }

    public ResponseEntity<?> createOrganization(Organization organization)
    {
        ResponseEntity<?> responseCreateOrg;

        //make sure the body is not null
        if (organization != null){
            Organization response = organizationRepository.save(organization);
            responseCreateOrg = new ResponseEntity(response, HttpStatus.CREATED);
        } else {
            responseCreateOrg = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        return responseCreateOrg;
    }

    public ResponseEntity<?> delete(Long id) {
        //Could not write JSON: failed to lazily initialize a collection
        ResponseEntity<?> responseFindId = this.findById(id);

        if(responseFindId.getStatusCode() == HttpStatus.OK ){
            organizationRepository.deleteById(id);
            responseFindId = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseFindId;

    }

    public ResponseEntity<?> editOrgName(Long id, String name) {
        ResponseEntity<?> responseFindId;
        if( name != null){
            responseFindId = this.findById(id);

            if(responseFindId.getStatusCode() == HttpStatus.OK ){
                Organization editableOrg = (Organization) responseFindId.getBody();
                editableOrg.setOrganizationName(name);
                organizationRepository.save(editableOrg);
            }else{
                responseFindId = new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }else{
            responseFindId = new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return responseFindId;
    }
}
