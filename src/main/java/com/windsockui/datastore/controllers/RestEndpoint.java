package com.windsockui.datastore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windsockui.datastore.beans.CmsData;
import com.windsockui.datastore.config.Config;
import com.windsockui.datastore.entities.JsonData;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class RestEndpoint {

    @Autowired
    Config config;

    JsonDataRepository jsonDataRepository;

    RestEndpoint(@Autowired JsonDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    @GetMapping(value="/data/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData> fetchData(HttpServletRequest request) {

        DomainAndPath domainAndPath = new DomainAndPath(request);
        Optional<JsonData> first = jsonDataRepository.findByDomainAndPath(domainAndPath.getDomain(), domainAndPath.getPath());

        /* @TODO: This is disgusting. Only return pages over 462 bytes (with content) else return 404 or if / return stub */
        /* @TODO: Golly, at least convert to JSON Object and return default if there are no items in the component array */

        boolean noContent = false;

        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        } else {
            if (domainAndPath.getPath().equals("/")) {
                JsonData data = loadStub();
                data.setPath("/");
                data.setDomain(domainAndPath.getDomain());
                return new ResponseEntity<>(data, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }


    @PutMapping(value="/data/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonData> updateData(HttpServletRequest request, @RequestBody String body) {

        DomainAndPath domainAndPath = new DomainAndPath(request);
        Optional<JsonData> first = jsonDataRepository.findByDomainAndPath(domainAndPath.getDomain(), domainAndPath.getPath());

        JsonData record = first.orElseGet(JsonData::new);
        if (record != null && isNoContent(body)) {
            jsonDataRepository.delete(record);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        record.setJson(body);
        record.setDomain(domainAndPath.getDomain());
        record.setPath(domainAndPath.getPath());
        jsonDataRepository.save(record);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    /*** Private Methods ***/

    private JsonData loadStub() {

        JsonData data = new JsonData();

        Resource resource = new ClassPathResource("/test-data.json");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            data.setJson(FileCopyUtils.copyToString(reader));
            return data;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    private boolean isNoContent(String rawJson) {
        try {
            CmsData data = new ObjectMapper().readValue(rawJson, CmsData.class);
            if (data.getComponents().size() == 0 || data.getComponents().size() == 1 && data.getComponents().getFirst().getComponentName().equals("windsock-404")) {
                return true;
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
        return false;
    }

    private static class DomainAndPath {
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
