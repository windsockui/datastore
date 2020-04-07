package com.windsockui.datastore.config;

import com.windsockui.datastore.entities.JsonData;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class Config {

    JsonDataRepository jsonDataRepository;

    public Config(@Autowired JsonDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    @PostConstruct
    public void populateData() throws IOException {

        if (jsonDataRepository.count() == 0) {

            JsonData data = new JsonData();
            data.setDomain("www.windsockui.com");
            data.setPath("/");

            try {
                Resource resource = new ClassPathResource("/test-data.json");
                data.setPath(resource.getInputStream().toString());
                System.out.println(data);
                jsonDataRepository.save(data);
            } catch (IOException ioe) {
                throw new IOException("Trying to load the example JSON stub (file) failed before being able to stick it in the database", ioe);
            }
        }
    }
}
