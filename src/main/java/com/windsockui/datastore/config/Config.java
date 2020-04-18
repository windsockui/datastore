package com.windsockui.datastore.config;

import com.windsockui.datastore.entities.JsonData;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

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
            Resource resource = new ClassPathResource("/test-data.json");
            try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                data.setJson(FileCopyUtils.copyToString(reader));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            jsonDataRepository.save(data);
        }
    }
}
