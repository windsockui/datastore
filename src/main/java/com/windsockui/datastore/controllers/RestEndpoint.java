package com.windsockui.datastore.controllers;

import com.windsockui.datastore.entities.JsonData;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class RestEndpoint {

    JsonDataRepository jsonDataRepository;

    RestEndpoint(@Autowired JsonDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    @GetMapping(value="/data/**")
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

}
