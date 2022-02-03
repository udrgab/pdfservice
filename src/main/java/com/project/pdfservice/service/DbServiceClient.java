package com.project.pdfservice.service;


import com.project.pdfservice.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8080/api", value = "db-service")
public interface DbServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/persons/{id}")
    Person getPerson(@PathVariable(value = "id") Long id);


}
