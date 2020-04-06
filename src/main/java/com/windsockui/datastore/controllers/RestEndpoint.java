package com.windsockui.datastore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RestEndpoint {

    JsonDataRepository jsonDataRepository;

    RestEndpoint(@Autowired JsonDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    /*@TODO: Replace this with something that returns real data, not dummy data from /src/main/resources/test-data.json */
    @GetMapping(value="/data/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object fetchData() throws IOException {
        Resource resource = new ClassPathResource("/test-data.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(resource.getInputStream(), Object.class);
    }

    /* // @TODO: DO NOT DELETE
    public ResponseEntity<JsonData> fetchData(HttpServletRequest request) {

        String servletPath = request.getServletPath();
        String[] segments = servletPath.split("/");

        String domain = segments[2];
        String path = "/" + String.join("/", Arrays.copyOfRange(segments, 3, segments.length));

        System.out.println(domain);
        System.out.println(path);

        Optional<JsonData> first = jsonDataRepository.findByDomainAndPath(domain, path);
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
     */

}
