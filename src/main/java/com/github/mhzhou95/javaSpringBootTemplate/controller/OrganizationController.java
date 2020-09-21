package com.github.mhzhou95.javaSpringBootTemplate.controller;

import com.github.mhzhou95.javaSpringBootTemplate.model.Organization;
import com.github.mhzhou95.javaSpringBootTemplate.service.OrganizationService;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//Added Swagger documentation. Can be viewed at http://localhost:8080/swagger-ui/#/

@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
    private OrganizationService service;

    @Autowired
    public OrganizationController(OrganizationService service) {
        this.service = service;
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        //use OrganizationService Function to find the organization
        return service.findById(id);
    }

    @PostMapping("/create-org")
    public ResponseEntity<?> createOrg(@RequestBody Organization organization){
        return service.createOrganization(organization);

    }

    @DeleteMapping("/{id}")
    //Add another response code for the Swagger Documentation
    @ApiResponse(code = 404, message = "Not Found")
    public ResponseEntity<?> deleteOrg(@PathVariable Long id){
        //the delete service returns a http response itself.
        return service.delete(id);
    }

    @PutMapping("/{id}/edit-name")
    public ResponseEntity<?> editOrg(@PathVariable Long id, @RequestBody String name){
        return service.editOrgName(id, name);
    }
}
