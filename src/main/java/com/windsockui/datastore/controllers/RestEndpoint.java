package com.windsockui.datastore.controllers;

import com.windsockui.datastore.entities.JsonData;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping(value="/data/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData> fetchData(HttpServletRequest request) {

        DomainAndPath domainAndPath = new DomainAndPath(request);
        Optional<JsonData> first = jsonDataRepository.findByDomainAndPath(domainAndPath.getDomain(), domainAndPath.getPath());

        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value="/data/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData> updateData(HttpServletRequest request, @RequestBody String body) {

        DomainAndPath domainAndPath = new DomainAndPath(request);
        Optional<JsonData> first = jsonDataRepository.findByDomainAndPath(domainAndPath.getDomain(), domainAndPath.getPath());
        JsonData record;
        if (first.isPresent()) {
            record = first.get();
        } else {
            record = new JsonData();
        }
        record.setJson(body);
        record.setDomain(domainAndPath.getDomain());
        record.setPath(domainAndPath.getPath());
        jsonDataRepository.save(record);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    private class DomainAndPath {
        DomainAndPath(HttpServletRequest request) {
            String servletPath = request.getServletPath();
            String[] segments = servletPath.split("/");

            this.domain = segments[2];
            this.path = "/" + String.join("/", Arrays.copyOfRange(segments, 3, segments.length));
        }

        private String domain;
        private String path;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPath() {
            if (path == null || path.equals("")) {
                return "/";
            }
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

}
