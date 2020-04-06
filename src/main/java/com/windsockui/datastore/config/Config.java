package com.windsockui.datastore.config;

import com.windsockui.datastore.entities.JsonData;
import com.windsockui.datastore.repository.JsonDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Config {

    JsonDataRepository jsonDataRepository;

    public Config(@Autowired JsonDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    @PostConstruct
    public void populateData() {

        if (jsonDataRepository.count() == 0) {

            JsonData data = new JsonData();
            data.setDomain("www.windsockui.com");
            data.setPath("/");
            data.setJson("[\n" +
                    "  {\n" +
                    "    \"component\":\"heading\",\n" +
                    "    \"data\": {\n" +
                    "      \"value\": \"Welcome to windsockUI\"\n" +
                    "    }\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"component\":\"paragraph\",\n" +
                    "    \"data\": {\n" +
                    "      \"value\": \"This data was served to the components from a server.\"\n" +
                    "    }\n" +
                    "  }\n" +
                    "]");
            System.out.println(data);
            jsonDataRepository.save(data);
        }
    }
}
